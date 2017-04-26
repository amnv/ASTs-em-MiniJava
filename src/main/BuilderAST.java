package main;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.*;
import jdk.nashorn.internal.ir.BlockStatement;
import main.amnv_jrmParser.*;
import visitor.*;

public class BuilderAST {
	
	public Program visit(GoalContext m)
	{
		return new Program(this.visitMainClass(m.mainClass()), 
						this.visitClassDeclaration(m.classDeclaration()));
	}

	public ClassDeclList visitClassDeclaration(List<ClassDeclarationContext> classDeclaration) {
		ClassDeclList cdl = new ClassDeclList();
		for (int i = 0; i < classDeclaration.size(); i++)
		{
			cdl.addElement(this.visitClassDecl(classDeclaration.get(i)));
		}
		return cdl;
	}

	public MainClass visitMainClass(MainClassContext mainClass) {
		Identifier i1 = this.visitIdentifier(mainClass.IDENTIFIER(0));
		Identifier i2 = this.visitIdentifier(mainClass.IDENTIFIER(1));
		Statement st = this.visitStatement(mainClass.statement());
		return new MainClass(i1, i2, st);
	}
	
	public ClassDecl visitClassDecl(ClassDeclarationContext c)
	{
		List<TerminalNode> id = c.IDENTIFIER();
		VarDeclList vdl = this.visitVarDeclarationContext(c.varDeclaration());
		MethodDeclList mdl = this.visitMethodoDeclarationContext(c.methodDeclaration ());
		
		if (id.size() > 1)
		{
			Identifier id1 = this.visitIdentifier(c.IDENTIFIER(0));
			Identifier id2 = this.visitIdentifier(c.IDENTIFIER(1));	
			return new  ClassDeclExtends(id1, id2, vdl,mdl);
		}
		
		return new ClassDeclSimple(this.visitIdentifier(id.get(0)), vdl, mdl);
	}
	
	public VarDeclList visitVarDeclarationContext(List<VarDeclarationContext> v)
	{
		VarDeclList var = new VarDeclList();
		
		for (int i = 0; i < v.size(); i++) {
			var.addElement(this.visitVarDecl(v.get(i)));
		}
		
		return var;
	}
	
	public VarDecl visitVarDecl(VarDeclarationContext v)
	{
		Type at = this.visitTypeContext(v.type());
		Identifier ai = this.visitIdentifier(v.IDENTIFIER());
		return new VarDecl(at, ai);
	}
	
	public Type visitTypeContext(TypeContext t)
	{
		String type = t.getText();
		Type ret;
		if (type.contains("int"))
		{
			ret = new IntegerType();
		} 
		else if (type.contains("int[]"))
		{
			ret = new IntArrayType();
		}
		else if (type.contains("boolean"))
		{
			ret = new BooleanType();
		}
		else ret = new IdentifierType(t.IDENTIFIER().getText());
		
		return ret;
	}
	
	public Identifier visitIdentifier(TerminalNode t)
	{
		return new Identifier(t.getText());
	}
	
	public Statement visitStatement(StatementContext statement)
	{		
		if (statement.isEmpty()) return null;
		
		String st = statement.getChild(0).getText();
		Identifier id = this.visitIdentifier(statement.IDENTIFIER());
		if (st.startsWith("if"))
		{
			return new If(this.visitExpression(statement.expression (0)), 
					this.visitStatement(statement.statement(0)),
					this.visitStatement(statement.statement(1)));
		}
		else if (st.startsWith("while")) 
		{
			return new While(this.visitExpression(statement.expression(0)),
					this.visitStatement(statement.statement(0)));
		}
		else if (st.startsWith("System.out.println"))
		{
			return new Print(this.visitExpression(statement.expression(0)));
		}
		else if (id != null) {
			if (statement.expression().size() == 1)		
				return new Assign(id, this.visitExpression(statement.expression(0)));
			else return new ArrayAssign(id,
					this.visitExpression(statement.expression(0)),
					this.visitExpression(statement.expression(1)));
		}
		else  return new Block(this.visitBlockStament(statement.statement()));
	}
	
	public Exp visitExpression(ExpressionContext ec)
	{
		return new NewArray(null);
	}
	
	public StatementList visitBlockStament(List<StatementContext> sc)
	{
		return new StatementList();
	}
	
	public MethodDeclList visitMethodoDeclarationContext(List<MethodDeclarationContext> m)
	{
		return new MethodDeclList();
	}
	
}

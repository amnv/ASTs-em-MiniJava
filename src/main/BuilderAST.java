package main;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.*;
import main.amnv_jrmParser.*;
import visitor.*;

public class BuilderAST {
	
	public Program visit(GoalContext m)
	{
		return new Program(this.visitMainClass(m.mainClass()), this.visitClassDeclaration(m.classDeclaration()));
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
		Identifier i1 = new Identifier(mainClass.IDENTIFIER(0).toString());
		Identifier i2 = new Identifier(mainClass.IDENTIFIER(1).toString());
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
	
	public MethodDeclList visitMethodoDeclarationContext(List<MethodDeclarationContext> m)
	{
		return new MethodDeclList();
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
		return new Identifier("");
	}
	
	
	
	
	public Statement visitStatement(StatementContext st)
	{		
		return new If(null,null, null);
	}
}

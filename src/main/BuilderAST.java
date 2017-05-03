package main;

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import ast.*;
import jdk.nashorn.internal.ir.BlockStatement;
import main.amnv_jrmParser.*;
import visitor.*;

public class BuilderAST {
	
	public Program visitGoal(GoalContext gc){
		MainClass main = this.visitMain(gc.mainClass());
		ClassDeclList cdList = this.visitClassDeclList(gc.classDeclaration());
		return new Program(main, cdList);
	}

	private ClassDeclList visitClassDeclList(List<ClassDeclarationContext> listCD) {
		ClassDeclList cdList = new ClassDeclList();
		for(int i = 0; i < listCD.size(); i++){
			cdList.addElement(this.visitClassDecl(listCD.get(i)));
		}
		return cdList;
	}
	
	private MainClass visitMain(MainClassContext mc) {
		Identifier id1 = new Identifier(mc.IDENTIFIER(0).toString());
		Identifier id2 = new Identifier(mc.IDENTIFIER(1).toString());
		Statement stmt = this.visitStatement(mc.statement());
		return new MainClass(id1, id2, stmt);
	}

	private Statement visitStatement(StatementContext st) {
		String stmt = st.getText();
		TerminalNode ids = st.IDENTIFIER();
		List<ExpressionContext> exps = st.expression();
		if(stmt.startsWith("if(") || stmt.startsWith("if ")){
			return new If(this.visitExp(exps.get(0)), this.visitStatement(st.statement(0)), this.visitStatement(st.statement(1)));
		}else if(stmt.startsWith("while")){
			return new While(this.visitExp(exps.get(0)), this.visitStatement(st.statement(0)));
		}else if(ids != null && exps.size() == 2){
			return new ArrayAssign(new Identifier(ids.getText()), this.visitExp(exps.get(0)), this.visitExp(exps.get(1)));
		}else if(ids != null && exps.size() == 1){
			return new Assign(new Identifier(ids.getText()), this.visitExp(exps.get(0)));
		}else if(stmt.startsWith("System.out.println")){
			return new Print(this.visitExp(exps.get(0)));
		}else{
			return new Block(this.visitStatementList(st.statement()));
		}
	}

	private ClassDecl visitClassDecl(ClassDeclarationContext cdc) {
		List<TerminalNode> tokens = cdc.IDENTIFIER();
		ClassDecl cd;
		if(tokens.size() < 2){
			cd = new ClassDeclSimple(new Identifier(tokens.get(0).toString()),this.visitVarDeclList(cdc.varDeclaration()), this.visitMethodDeclList(cdc.methodDeclaration()));
		}else{
		    cd = new ClassDeclExtends(new Identifier(tokens.get(0).toString()),new Identifier(tokens.get(3).toString()),this.visitVarDeclList(cdc.varDeclaration()), this.visitMethodDeclList(cdc.methodDeclaration()));
		}
		return cd;
	}

	private MethodDeclList visitMethodDeclList(List<MethodDeclarationContext> md) {
		MethodDeclList mdList = new MethodDeclList();
		for(int i = 0; i < md.size(); i++){
			mdList.addElement(this.visitMethodDecl(md.get(i)));
		}
		return mdList;
	}

	private MethodDecl visitMethodDecl(MethodDeclarationContext mdc) {
		List<TypeContext> type = mdc.type();
		List<TerminalNode> tokens = mdc.IDENTIFIER();
		FormalList args = new FormalList();
		Type tipoMetodo = this.visitType(type.get(0));
		Identifier nomeMetodo = new Identifier(tokens.get(0).toString());
		for(int i = 1; i< type.size();i++){
			args.addElement(new Formal(this.visitType(type.get(i)),new Identifier(tokens.get(i).toString())));
		}
		VarDeclList variaveis = this.visitVarDeclList(mdc.varDeclaration());
		StatementList statements = this.visitStatementList(mdc.statement());
		Exp exp = this.visitExp(mdc.expression());
		return new MethodDecl(tipoMetodo,nomeMetodo,args,variaveis,statements,exp);
	}

	
	
	
	public Exp visitExp(ExpressionContext ec)
	{
		String tipo = ec.getChild(0).getText();
		TerminalNode op = ec.OP();
		TerminalNode lit = ec.INTEGER_LITERAL();
		if (op != null)
		{
			Exp exp1 = this.visitExp(ec.expression(0));
			Exp exp2 = this.visitExp(ec.expression(1));
			switch (op.getText()) {
			case "&&":
				return new And(exp1, exp2);
			case "*":
				return new Times(exp1, exp2);
			case "<":
				return new LessThan(exp1, exp2);
			case "-":
				return new Minus(exp1, exp2);
			case "+":
				return new Plus(exp1, exp2);
			}
		}
		else if (lit != null)
		{
			return new IntegerLiteral(Integer.parseInt(lit.getText()));
		}
		else if (tipo.contains("length"))
		{
			return new ArrayLength(this.visitExp(ec.expression(0))); 
		}
		else if (ec.getChild(1).getText().contains("["))
		{
			return new ArrayLookup(this.visitExp(ec.expression(0)), this.visitExp(ec.expression(0))); 
		}
		else if (tipo.contains("new"))
		{
			if(ec.expression().size() == 1){
				return new NewArray(this.visitExp(ec.expression().get(0)));
			}else{
				return new NewObject(new Identifier(ec.IDENTIFIER().getText()));
			}
		}
		else if (tipo.contains("false"))
		{
			return new False();
		}
		else if (tipo.contains("true"))
		{
			return new True();
		}
		else
		{
			return new IdentifierExp(ec.IDENTIFIER().getText());
		} 	
		
		return new NewArray(null);
	}	
	

	
	
	
	
	@SuppressWarnings("unused")
	private ExpList visitExpList(List<ExpressionContext> exps) {
		ExpList expList = new ExpList();
		for(int i = 1; i<exps.size();i++){
			expList.addElement(this.visitExp(exps.get(i)));
		}
		return expList;

	}

	private StatementList visitStatementList(List<StatementContext> sts) {
		StatementList stmtList = new StatementList();
		for(int i = 0; i < sts.size(); i++){
			stmtList.addElement(this.visitStatement(sts.get(i)));
		}
		return stmtList;
	}

	private Type visitType(TypeContext tc) {
		String type = tc.getText();
		if(type.contains("boolean")){
			return new BooleanType();
		}else if(type.contains("int[]")){
			return new IntArrayType();
		}else if(type.contains("int")){
			return new IntegerType();
		}else{
			return new IdentifierType(tc.IDENTIFIER().getText());
		}
	}

	private VarDeclList visitVarDeclList(List<VarDeclarationContext> listVD) {
		VarDeclList vdList = new VarDeclList();
		for(int i = 0; i < listVD.size(); i++){
			vdList.addElement(this.visitVarDecl(listVD.get(i)));
		}
		//comentario
		return vdList;
	}

	private VarDecl visitVarDecl(VarDeclarationContext vd) {
		return new VarDecl(this.visitType(vd.type()), new Identifier(vd.IDENTIFIER().getText()));
	}	
	

}

package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ast.*;
import visitor.*;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class Start {

	public static void main(String[] args) {
		try {
			InputStream entrada = new FileInputStream("And.java");
			ANTLRInputStream input = new ANTLRInputStream(entrada);
			amnv_jrmLexer lexer = new amnv_jrmLexer(input);
			CommonTokenStream token = new CommonTokenStream(lexer);
			amnv_jrmParser parser = new amnv_jrmParser(token);
			
			//ajeitar BuilderAST
			Program prog = new BuilderAST().visitGoal(parser.goal());
			BuildSymbolTableVisitor stVis = new BuildSymbolTableVisitor();
			//construindo tabela de símbolos
			prog.accept(stVis); 
			//fazendo a checagem de tipos
			prog.accept(new TypeCheckVisitor(stVis.getSymbolTable())); 
/*
			prog.accept(new PrettyPrintVisitor());
			//visitor.visit(program);......;.
			entrada.close();
*/		
		} catch (FileNotFoundException e) {}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

// Generated from amnv_jrm.g4 by ANTLR 4.4
package main;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link amnv_jrmParser}.
 */
public interface amnv_jrmListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull amnv_jrmParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull amnv_jrmParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull amnv_jrmParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull amnv_jrmParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void enterMainClass(@NotNull amnv_jrmParser.MainClassContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#mainClass}.
	 * @param ctx the parse tree
	 */
	void exitMainClass(@NotNull amnv_jrmParser.MainClassContext ctx);
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(@NotNull amnv_jrmParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(@NotNull amnv_jrmParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull amnv_jrmParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull amnv_jrmParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#goal}.
	 * @param ctx the parse tree
	 */
	void enterGoal(@NotNull amnv_jrmParser.GoalContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#goal}.
	 * @param ctx the parse tree
	 */
	void exitGoal(@NotNull amnv_jrmParser.GoalContext ctx);
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMethodDeclaration(@NotNull amnv_jrmParser.MethodDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#methodDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMethodDeclaration(@NotNull amnv_jrmParser.MethodDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link amnv_jrmParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(@NotNull amnv_jrmParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link amnv_jrmParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(@NotNull amnv_jrmParser.VarDeclarationContext ctx);
}
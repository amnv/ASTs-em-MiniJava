package main;

import ast.Program;

public class BuilderAST {
	public Program visitGoal()
	{
		return new Program(null, null);
	}
}

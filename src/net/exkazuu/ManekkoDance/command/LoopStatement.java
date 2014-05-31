package net.exkazuu.ManekkoDance.command;

import java.util.ArrayList;
import java.util.List;

class LoopStatement implements Statement {
	public List<Statement> statements = new ArrayList<Statement>();
	public int loopCount;

	public LoopStatement(int loopCount) {
		this.loopCount = loopCount;
	}

	public void createExpandedCommands(List<String> result, boolean isLeft) {
		for (int i = 0; i < loopCount; i++) {
			for (Statement statement : statements) {
				statement.createExpandedCommands(result, isLeft);
			}
		}
	}

	@Override
	public void createExpandedLineNumbers(List<Integer> result, boolean isLeft) {
		for (int i = 0; i < loopCount; i++) {
			for (Statement statement : statements) {
				statement.createExpandedLineNumbers(result, isLeft);
			}
		}
	}
}
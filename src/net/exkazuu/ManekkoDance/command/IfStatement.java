package net.exkazuu.ManekkoDance.command;

import java.util.ArrayList;
import java.util.List;

class IfStatement implements Statement {
	public List<Statement> trueStatements = new ArrayList<Statement>();
	public List<Statement> falseStatements = new ArrayList<Statement>();
	public boolean isLeftCondition;

	public IfStatement(boolean isLeftCondition) {
		this.isLeftCondition = isLeftCondition;
	}

	public void createExpandedCommands(List<String> result, boolean isLeft) {
		int maxSize = getMaxSize(isLeft);

		if (isLeftCondition == isLeft) {
			int tmpSize = result.size();
			for (Statement statement : trueStatements) {
				statement.createExpandedCommands(result, isLeft);
			}
			tmpSize = result.size() - tmpSize;
			for (int i = tmpSize; i < maxSize; i++) {
				result.add("");
			}
		} else {
			int tmpSize = result.size();
			for (Statement statement : falseStatements) {
				statement.createExpandedCommands(result, isLeft);
			}
			tmpSize = result.size() - tmpSize;
			for (int i = tmpSize; i < maxSize; i++) {
				result.add("");
			}
		}
	}

	private int getMaxSize(boolean isLeft) {
		ArrayList<String> tmp1 = new ArrayList<String>();
		ArrayList<String> tmp2 = new ArrayList<String>();
		for (Statement statement : trueStatements) {
			statement.createExpandedCommands(tmp1, isLeft);
		}
		for (Statement statement : falseStatements) {
			statement.createExpandedCommands(tmp2, isLeft);
		}
		int maxSize = Math.max(tmp1.size(), tmp2.size());
		return maxSize;
	}

	@Override
	public void createExpandedLineNumbers(List<Integer> result, boolean isLeft) {
		int maxSize = getMaxSize(isLeft);
		if (isLeftCondition == isLeft) {
			int tmpSize = result.size();
			for (Statement statement : trueStatements) {
				statement.createExpandedLineNumbers(result, isLeft);
			}
			int lastNumber = 0;
			if (result.size() > 0) {
				lastNumber = result.get(result.size() - 1);
			}
			tmpSize = result.size() - tmpSize;
			for (int i = tmpSize; i < maxSize; i++) {
				result.add(lastNumber);
			}
		} else {
			int tmpSize = result.size();
			for (Statement statement : falseStatements) {
				statement.createExpandedLineNumbers(result, isLeft);
			}
			int lastNumber = 0;
			if (result.size() > 0) {
				lastNumber = result.get(result.size() - 1);
			}
			tmpSize = result.size() - tmpSize;
			for (int i = tmpSize; i < maxSize; i++) {
				result.add(lastNumber);
			}
		}
	}
}
package net.exkazuu.ManekkoDance.command;

import java.util.List;

class Command implements Statement {
	public String content;
	public int lineIndex;

	public Command(String content, int lineIndex) {
		super();
		this.content = content;
		this.lineIndex = lineIndex;
	}

	@Override
	public void createExpandedCommands(List<String> result, boolean isLeft) {
		result.add(content);
	}

	@Override
	public void createExpandedLineNumbers(List<Integer> result, boolean isLeft) {
		result.add(lineIndex);
	}
}
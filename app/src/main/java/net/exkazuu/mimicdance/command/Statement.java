package net.exkazuu.mimicdance.command;

import java.util.List;

interface Statement {
	void createExpandedCommands(List<String> result, boolean isLeft);

	void createExpandedLineNumbers(List<Integer> result, boolean isLeft);
}
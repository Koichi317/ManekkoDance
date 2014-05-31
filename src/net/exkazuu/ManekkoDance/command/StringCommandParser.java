package net.exkazuu.ManekkoDance.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface Statement {
	void createExpandedCommands(List<String> result, boolean isLeft);

	void createExpandedLineNumbers(List<Integer> result, boolean isLeft);
}

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

class Block implements Statement {
	public List<Statement> statements = new ArrayList<Statement>();

	public void createExpandedCommands(List<String> result, boolean isLeft) {
		for (Statement statement : statements) {
			statement.createExpandedCommands(result, isLeft);
		}
	}

	@Override
	public void createExpandedLineNumbers(List<Integer> result, boolean isLeft) {
		for (Statement statement : statements) {
			statement.createExpandedLineNumbers(result, isLeft);
		}
	}
}

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

enum StateType {
	Block, Loop, If, Else,
}

class ParseState {
	public StateType type;
	public Statement statement;

	public ParseState(StateType type, Statement statement) {
		this.type = type;
		this.statement = statement;
	}

	public void addStatement(Statement added) {
		switch (type) {
		case Block:
			((Block) statement).statements.add(added);
			break;
		case Else:
			((IfStatement) statement).falseStatements.add(added);
			break;
		case If:
			((IfStatement) statement).trueStatements.add(added);
			break;
		case Loop:
			((LoopStatement) statement).statements.add(added);
			break;
		default:
			break;
		}
	}
}

public class StringCommandParser {

	private StringCommandParser() {

	}

	public static int countNewLines(CharSequence seq) {
		String text = seq.toString().trim();
		int count = 1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				count++;
			}
		}
		return count;
	}

	public static void parse(String commandsText,
			List<String> expandedCommands, List<Integer> expandedLineNumbers,
			boolean isLeft) {
		String[] commands = commandsText.split("\n"); // 1行毎に配列に格納
		List<String> originalCommand = new ArrayList<String>(
				Arrays.asList(commands)); // List型配列に変換
		List<Integer> originalLineNumbers = new ArrayList<Integer>(); // 文字の行を記憶

		// 行番号を付ける(1行目：0番目、2行目:1番目、・・・)
		for (int i = 0; i < commands.length; i++)
			originalLineNumbers.add(i);

		expandCommands(originalCommand, originalLineNumbers, expandedCommands,
				expandedLineNumbers, isLeft);
	}

	private static void expandCommands(List<String> originalCommands,
			List<Integer> originalLineNumbers, List<String> expandedCommands,
			List<Integer> expandedLineNumbers, boolean isLeft) {
		Stack<ParseState> parseStateStack = new Stack<ParseState>();
		Block block = new Block();
		parseStateStack.push(new ParseState(StateType.Block, block));

		for (int i = 0; i < originalCommands.size(); i++) {
			if (originalCommands.get(i) == null) {
				continue;
			} else if (originalCommands.get(i).contains("もしも")) {
				IfStatement ifStatement = new IfStatement(
						readCondition(originalCommands.get(i)));
				parseStateStack.peek().addStatement(ifStatement);
				parseStateStack.push(new ParseState(StateType.If, ifStatement));
			} else if (originalCommands.get(i).contains("もしくは")) {
				ParseState state = parseStateStack.peek();
				if (state.type == StateType.If) {
					state.type = StateType.Else;
				}
			} else if (originalCommands.get(i).contains("くりかえし")) {
				LoopStatement loopStatement = new LoopStatement(
						readCount(originalCommands.get(i)));
				parseStateStack.peek().addStatement(loopStatement);
				parseStateStack.push(new ParseState(StateType.Loop,
						loopStatement));
			} else if (originalCommands.get(i).contains("もしおわり")) {
				if (parseStateStack.peek().type == StateType.If
						|| parseStateStack.peek().type == StateType.Else) {
					parseStateStack.pop();
				}
			} else if (originalCommands.get(i).contains("ここまで")) {
				if (parseStateStack.peek().type == StateType.Loop) {
					parseStateStack.pop();
				}
			} else {
				Command command = new Command(originalCommands.get(i),
						originalLineNumbers.get(i));
				parseStateStack.peek().addStatement(command);
			}
		}
		block.createExpandedCommands(expandedCommands, isLeft);
		block.createExpandedLineNumbers(expandedLineNumbers, isLeft);
	}

	private static int readCount(String loopCount) {
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(loopCount);
		if (!m.find()) {
			return 0; // 0回繰り返し
		} else {
			int startIndex = m.start();
			int countNumber = Integer.parseInt(loopCount.substring(startIndex));
			return countNumber;
		}
	}

	private static boolean readCondition(String conditionString) {
		return !conditionString.contains("茶");
	}
}
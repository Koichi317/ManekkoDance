package net.exkazuu.mimicdance.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            String strCommand = originalCommands.get(i);
            if (strCommand == null) {
                continue;
            }
            ParseState peekStatement = parseStateStack.peek();
            if (strCommand.contains("もしも")) {
                IfStatement ifStatement = new IfStatement(readCondition(strCommand));
                peekStatement.addStatement(ifStatement);
                parseStateStack.push(new ParseState(StateType.If, ifStatement));
            } else if (strCommand.contains("もしくは")) {
                if (peekStatement.type == StateType.If) {
                    peekStatement.type = StateType.Else;
                }
            } else if (strCommand.contains("くりかえし")) {
                LoopStatement loopStatement = new LoopStatement(readCount(strCommand));
                peekStatement.addStatement(loopStatement);
                parseStateStack.push(new ParseState(StateType.Loop,
                    loopStatement));
            } else if (strCommand.contains("もしおわり")) {
                StateType peekType = peekStatement.type;
                if (peekType == StateType.If || peekType == StateType.Else) {
                    parseStateStack.pop();
                }
            } else if (strCommand.contains("ここまで")) {
                if (peekStatement.type == StateType.Loop) {
                    parseStateStack.pop();
                }
            } else {
                Integer lineNumber = originalLineNumbers.get(i);
                Command command = new Command(strCommand, lineNumber);
                peekStatement.addStatement(command);
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

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

    public static void parse(String commandsText, List<String> expandedCommands,
                             List<Integer> expandedLineNumbers, boolean isLeft) {
        String[] lines = commandsText.split("\n"); // 1行毎に配列に格納
        List<String> originalLines = Arrays.asList(lines); // List型配列に変換

        // 行番号を付ける(1行目：0番目、2行目:1番目、・・・)
        List<Integer> originalLineNumbers = new ArrayList<>();
        for (int i = 0; i < lines.length; i++)
            originalLineNumbers.add(i);

        expandCommands(originalLines, originalLineNumbers, expandedCommands, expandedLineNumbers, isLeft);
    }

    private static void expandCommands(List<String> originalLines, List<Integer> originalLineNumbers,
                                       List<String> expandedLines, List<Integer> expandedLineNumbers, boolean isLeft) {
        Stack<ParseState> parseStateStack = new Stack<>();
        Block block = new Block();
        parseStateStack.push(new ParseState(StateType.Block, block));

        for (int i = 0; i < originalLines.size(); i++) {
            String line = originalLines.get(i);
            ParseState lastState = parseStateStack.peek();
            if (line.contains("もしも")) {
                IfStatement ifStatement = new IfStatement(readCondition(line));
                lastState.addStatement(ifStatement);
                parseStateStack.push(new ParseState(StateType.If, ifStatement));
            } else if (line.contains("もしくは")) {
                if (lastState.type == StateType.If) {
                    lastState.type = StateType.Else;
                }
            } else if (line.contains("くりかえし")) {
                LoopStatement loopStatement = new LoopStatement(readCount(line));
                lastState.addStatement(loopStatement);
                parseStateStack.push(new ParseState(StateType.Loop,
                    loopStatement));
            } else if (line.contains("もしおわり")) {
                if (lastState.type == StateType.If
                    || lastState.type == StateType.Else) {
                    parseStateStack.pop();
                }
            } else if (line.contains("ここまで")) {
                if (lastState.type == StateType.Loop) {
                    parseStateStack.pop();
                }
            } else {
                lastState.addStatement(new Command(line, originalLineNumbers.get(i)));
            }
        }
        block.createExpandedCommands(expandedLines, isLeft);
        block.createExpandedLineNumbers(expandedLineNumbers, isLeft);
    }

    private static int readCount(String loopCount) {
        Pattern p = Pattern.compile("[0-9]");
        Matcher m = p.matcher(loopCount);
        if (!m.find()) {
            return 0; // 0回繰り返し
        } else {
            return Integer.parseInt(m.group());
        }
    }

    private static boolean readCondition(String conditionString) {
        return !conditionString.contains("茶");
    }
}

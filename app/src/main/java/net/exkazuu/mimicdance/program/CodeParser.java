package net.exkazuu.mimicdance.program;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeParser {

    private CodeParser() {
    }

    public static UnrolledProgram parse(String commandsText, boolean forPiyo) {
        List<String> lines = Arrays.asList(commandsText.split("\n")); // List型配列に変換
        return expandCommands(lines, forPiyo);
    }

    private static UnrolledProgram expandCommands(List<String> lines, boolean forPiyo) {
        Block rootBlock = new Block();
        Stack<ParseState> parseStateStack = new Stack<>();
        parseStateStack.push(new ParseState(StateType.Block, rootBlock));

        for (int lineIndex = 0; lineIndex < lines.size(); lineIndex++) {
            String line = lines.get(lineIndex);
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
                lastState.addStatement(new Action(line, lineIndex));
            }
        }
        UnrolledProgram program = new UnrolledProgram();
        rootBlock.unrollProgram(program, forPiyo);
        return program;
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

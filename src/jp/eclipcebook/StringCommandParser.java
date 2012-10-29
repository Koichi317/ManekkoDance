package jp.eclipcebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCommandParser {
	private StringCommandParser() {

	}

	public static List<String> parse(String commandsText) {
		String[] commands = commandsText.split("\n"); // 1�s���ɔz��Ɋi�[
		List<String> originalCommand = new ArrayList<String>(Arrays.asList(commands));
		List<String> expandedCommands = new ArrayList<String>();
		expandCommands(originalCommand, expandedCommands);
		expandedCommands.add("\n");
		return expandedCommands;
	}

	private static void expandCommands(List<String> originalCommands, List<String> expandedCommands) {
		// TODO Auto-generated method stub
		Stack<Integer> loopStack = new Stack<Integer>();
		Stack<Integer> loopCountStack = new Stack<Integer>();
		for (int i = 0; i < originalCommands.size(); i++) {
			if (originalCommands.get(i) == null)
				continue;
			else if (originalCommands.get(i).contains("loop")) { // loop������
				loopStack.push(i);
				loopCountStack.push(readCount(originalCommands.get(i)));
			} else if (originalCommands.get(i).contains("�����܂�")) { // koko������
				int loopPosition = loopStack.pop();
				int loopCount = loopCountStack.pop();
				originalCommands.remove(i);
				originalCommands.remove(loopPosition);
				makeLoop(originalCommands, loopPosition, i - 2, loopCount);
				i = loopPosition - 1;
			} else if (loopStack.empty()) { // �X�^�b�N����
				expandedCommands.add(originalCommands.get(i));
			}
		}
	}

	private static void makeLoop(List<String> originalCommands, int firstIndex,
			int lastIndex, int count) {
		String[] str = new String[lastIndex - firstIndex + 1];
		for (int i = firstIndex; i <= lastIndex; i++) {
			str[i - firstIndex] = originalCommands.get(i);
		}
		for (int i = 0; i < count - 1; i++) { // 3��J��Ԃ��Ȃ�Ai < 2 (1�񕪁{2�񕪒ǉ�)
			for (int j = str.length - 1; j >= 0; j--) {
				originalCommands.add(firstIndex, str[j]);
			}
		}
	}

	private static int readCount(String loopCount) {
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(loopCount);
		if (!m.find()) {
			return 0; // 0��J��Ԃ�
		} else {
			int startIndex = m.start();
			int countNumber = Integer.parseInt(loopCount.substring(startIndex));
			return countNumber;
		}
	}

}

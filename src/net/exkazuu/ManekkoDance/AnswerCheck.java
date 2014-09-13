package net.exkazuu.ManekkoDance;

import java.util.List;

import android.util.Log;

public class AnswerCheck {

	static boolean[][] playerAnswer;
	static boolean[][] partnerAnswer;
	public boolean judge;

	public AnswerCheck(List<String> playerCommands, List<String> partnerCommands) { // プレイヤーとお手本の使用されている命令をフラグとして管理

		playerAnswer = new boolean[9][playerCommands.size()];
		partnerAnswer = new boolean[9][partnerCommands.size()];

		for (int i = 0; i < playerCommands.size(); i++) {
			if (playerCommands.get(i).indexOf("左腕を上げる") != -1)
				playerAnswer[0][i] = true;
			if (playerCommands.get(i).indexOf("左腕を下げる") != -1)
				playerAnswer[1][i] = true;
			if (playerCommands.get(i).indexOf("右腕を上げる") != -1)
				playerAnswer[2][i] = true;
			if (playerCommands.get(i).indexOf("右腕を下げる") != -1)
				playerAnswer[3][i] = true;
			if (playerCommands.get(i).indexOf("左足を上げる") != -1)
				playerAnswer[4][i] = true;
			if (playerCommands.get(i).indexOf("左足を下げる") != -1)
				playerAnswer[5][i] = true;
			if (playerCommands.get(i).indexOf("右足を上げる") != -1)
				playerAnswer[6][i] = true;
			if (playerCommands.get(i).indexOf("右足を下げる") != -1)
				playerAnswer[7][i] = true;
			if (playerCommands.get(i).indexOf("ジャンプする") != -1)
				playerAnswer[8][i] = true;
		}

		for (int i = 0; i < partnerCommands.size(); i++) {
			if (partnerCommands.get(i).indexOf("左腕を上げる") != -1)
				partnerAnswer[0][i] = true;
			if (partnerCommands.get(i).indexOf("左腕を下げる") != -1)
				partnerAnswer[1][i] = true;
			if (partnerCommands.get(i).indexOf("右腕を上げる") != -1)
				partnerAnswer[2][i] = true;
			if (partnerCommands.get(i).indexOf("右腕を下げる") != -1)
				partnerAnswer[3][i] = true;
			if (partnerCommands.get(i).indexOf("左足を上げる") != -1)
				partnerAnswer[4][i] = true;
			if (partnerCommands.get(i).indexOf("左足を下げる") != -1)
				partnerAnswer[5][i] = true;
			if (partnerCommands.get(i).indexOf("右足を上げる") != -1)
				partnerAnswer[6][i] = true;
			if (partnerCommands.get(i).indexOf("右足を下げる") != -1)
				partnerAnswer[7][i] = true;
			if (partnerCommands.get(i).indexOf("ジャンプする") != -1)
				partnerAnswer[8][i] = true;
		}

	}

	public void compare() {
		judge = true;
		Log.v("compare1", String.valueOf(judge));
		if (playerAnswer[0].length != partnerAnswer[0].length) {
			judge = false;
		} else {
			for (int i = 0; i < playerAnswer[0].length; i++) {
				for (int j = 0; j < playerAnswer.length; j++) {
					if (playerAnswer[j][i] == partnerAnswer[j][i]) {
						// Log.v("i", String.valueOf(i));
						// Log.v("j", String.valueOf(j));
						// continue; // お手本の各行の命令がプレイヤーの同じ行にあるかどうか

					} else {
						judge = false;
						break;
					}
				}
			}
		}
	}

	public String show() {
		return (judge == true) ? "正解です！" : "不正解です・・・";
	}
}


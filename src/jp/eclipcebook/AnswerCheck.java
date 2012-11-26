package jp.eclipcebook;

import java.util.List;

public class AnswerCheck {

	static int[][] playerAnswer;
	static int[][] partnerAnswer;
	public static boolean judge;

	public AnswerCheck(List<String> playerCommands, List<String> partnerCommands) { //プレイヤーとお手本の使用されている命令をフラグとして管理
		for(int i = 0; i < playerCommands.size(); i++) {
			playerAnswer = new int[9][playerCommands.size()];
			partnerAnswer = new int[9][partnerCommands.size()];
			
			if (playerCommands.get(i).indexOf("左腕を上げる") != -1) { // player
				playerAnswer[0][i] = 1;
			}
			if (playerCommands.get(i).indexOf("左腕を下げる") != -1) {
				playerAnswer[1][i] = 1;
			}
			if (playerCommands.get(i).indexOf("右腕を上げる") != -1) {
				playerAnswer[2][i] = 1;
			}
			if (playerCommands.get(i).indexOf("右腕を下げる") != -1) {
				playerAnswer[3][i] = 1;
			}
			if (playerCommands.get(i).indexOf("左足を上げる") != -1) {
				playerAnswer[4][i] = 1;
			}
			if (playerCommands.get(i).indexOf("左足を下げる") != -1) {
				playerAnswer[5][i] = 1;
			}
			if (playerCommands.get(i).indexOf("右足を上げる") != -1) {
				playerAnswer[6][i] = 1;
			}
			if (playerCommands.get(i).indexOf("右足を下げる") != -1) {
				playerAnswer[7][i] = 1;
			}
			if (playerCommands.get(i).indexOf("ジャンプする") != -1) {
				playerAnswer[8][i] = 1;
			}
		}
		for(int i = 0; i < partnerCommands.size(); i++) {
			if (partnerCommands.get(i).indexOf("左腕を上げる") != -1) { // partner(answer)
				partnerAnswer[0][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("左腕を下げる") != -1) {
				partnerAnswer[1][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("右腕を上げる") != -1) {
				partnerAnswer[2][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("右腕を下げる") != -1) {
				partnerAnswer[3][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("左足を上げる") != -1) {
				partnerAnswer[4][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("左足を下げる") != -1) {
				partnerAnswer[5][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("右足を上げる") != -1) {
				partnerAnswer[6][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("右足を下げる") != -1) {
				partnerAnswer[7][i] = 1;
			}
			if (partnerCommands.get(i).indexOf("ジャンプする") != -1) {
				partnerAnswer[8][i] = 1;
			}
		}
	}

	public void compare() {
		// TODO Auto-generated method stub
		if(playerAnswer[1].length != partnerAnswer[1].length) {
			judge = false;
		}else {
			for (int i = 0; i < playerAnswer[1].length; i++) {
				for(int j = 0; j < playerAnswer[0].length; j++) {
					if (playerAnswer[j][i] == partnerAnswer[j][i]) { // お手本の各行の命令がプレイヤーの同じ行にあるかどうか
						judge = true;
					} else {
						judge = false;
						break;
					}
				}
			}
		}
	}

	public String show() {
		// TODO Auto-generated method stub
		if (judge == true) {
			return "正解です！";
		} else {
			return "不正解です・・・";
		}
	}

}

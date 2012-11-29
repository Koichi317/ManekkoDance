package jp.eclipcebook;

import java.util.List;

public class AnswerCheck {

	static int[][] playerAnswer;
	static int[][] partnerAnswer;
	public static boolean judge;

	public AnswerCheck(List<String> playerCommands, List<String> partnerCommands) { // �v���C���[�Ƃ���{�̎g�p����Ă��閽�߂��t���O�Ƃ��ĊǗ�

		playerAnswer = new int[9][playerCommands.size()];
		partnerAnswer = new int[9][partnerCommands.size()];

		for (int i = 0; i < playerCommands.size(); i++) {
			if (playerCommands.get(i).indexOf("���r���グ��") != -1)
				playerAnswer[0][i] = 1;
			if (playerCommands.get(i).indexOf("���r��������") != -1)
				playerAnswer[1][i] = 1;
			if (playerCommands.get(i).indexOf("�E�r���グ��") != -1)
				playerAnswer[2][i] = 1;
			if (playerCommands.get(i).indexOf("�E�r��������") != -1)
				playerAnswer[3][i] = 1;
			if (playerCommands.get(i).indexOf("�������グ��") != -1)
				playerAnswer[4][i] = 1;
			if (playerCommands.get(i).indexOf("������������") != -1)
				playerAnswer[5][i] = 1;
			if (playerCommands.get(i).indexOf("�E�����グ��") != -1)
				playerAnswer[6][i] = 1;
			if (playerCommands.get(i).indexOf("�E����������") != -1)
				playerAnswer[7][i] = 1;
			if (playerCommands.get(i).indexOf("�W�����v����") != -1)
				playerAnswer[8][i] = 1;
		}

		for (int i = 0; i < partnerCommands.size(); i++) {
			if (partnerCommands.get(i).indexOf("���r���グ��") != -1)
				partnerAnswer[0][i] = 1;
			if (partnerCommands.get(i).indexOf("���r��������") != -1)
				partnerAnswer[1][i] = 1;
			if (partnerCommands.get(i).indexOf("�E�r���グ��") != -1)
				partnerAnswer[2][i] = 1;
			if (partnerCommands.get(i).indexOf("�E�r��������") != -1)
				partnerAnswer[3][i] = 1;
			if (partnerCommands.get(i).indexOf("�������グ��") != -1)
				partnerAnswer[4][i] = 1;
			if (partnerCommands.get(i).indexOf("������������") != -1)
				partnerAnswer[5][i] = 1;
			if (partnerCommands.get(i).indexOf("�E�����グ��") != -1)
				partnerAnswer[6][i] = 1;
			if (partnerCommands.get(i).indexOf("�E����������") != -1)
				partnerAnswer[7][i] = 1;
			if (partnerCommands.get(i).indexOf("�W�����v����") != -1)
				partnerAnswer[8][i] = 1;
		}

	}

	public void compare() {
		// TODO Auto-generated method stub
		judge = true;
		if (playerAnswer[1].length != partnerAnswer[1].length) {
			judge = false;
		} else {
			for (int i = 0; i < playerAnswer[1].length; i++) {
				for (int j = 0; j < playerAnswer[0].length; j++) {
					if (playerAnswer[j][i] == partnerAnswer[j][i])
						continue; // ����{�̊e�s�̖��߂��v���C���[�̓����s�ɂ��邩�ǂ���
					judge = false;
					break;
				}
			}
		}
	}

	public String show() {
		// TODO Auto-generated method stub
		return (judge == true) ? "�����ł��I" : "�s�����ł��E�E�E";
	}
}

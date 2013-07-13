package net.exkazuu.ManekkoDance;

import java.util.List;

import android.util.Log;
import android.widget.TextView;

public class AnswerCheck {

	static boolean[][] playerAnswer;
	static boolean[][] partnerAnswer;
	public boolean judge;

	public AnswerCheck(List<String> playerCommands, List<String> partnerCommands) { // �v���C���[�Ƃ���{�̎g�p����Ă��閽�߂��t���O�Ƃ��ĊǗ�

		playerAnswer = new boolean[9][playerCommands.size()];
		partnerAnswer = new boolean[9][partnerCommands.size()];

		for (int i = 0; i < playerCommands.size(); i++) {
			if (playerCommands.get(i).indexOf("���r���グ��") != -1)
				playerAnswer[0][i] = true;
			if (playerCommands.get(i).indexOf("���r��������") != -1)
				playerAnswer[1][i] = true;
			if (playerCommands.get(i).indexOf("�E�r���グ��") != -1)
				playerAnswer[2][i] = true;
			if (playerCommands.get(i).indexOf("�E�r��������") != -1)
				playerAnswer[3][i] = true;
			if (playerCommands.get(i).indexOf("�������グ��") != -1)
				playerAnswer[4][i] = true;
			if (playerCommands.get(i).indexOf("������������") != -1)
				playerAnswer[5][i] = true;
			if (playerCommands.get(i).indexOf("�E�����グ��") != -1)
				playerAnswer[6][i] = true;
			if (playerCommands.get(i).indexOf("�E����������") != -1)
				playerAnswer[7][i] = true;
			if (playerCommands.get(i).indexOf("�W�����v����") != -1)
				playerAnswer[8][i] = true;
		}

		for (int i = 0; i < partnerCommands.size(); i++) {
			if (partnerCommands.get(i).indexOf("���r���グ��") != -1)
				partnerAnswer[0][i] = true;
			if (partnerCommands.get(i).indexOf("���r��������") != -1)
				partnerAnswer[1][i] = true;
			if (partnerCommands.get(i).indexOf("�E�r���グ��") != -1)
				partnerAnswer[2][i] = true;
			if (partnerCommands.get(i).indexOf("�E�r��������") != -1)
				partnerAnswer[3][i] = true;
			if (partnerCommands.get(i).indexOf("�������グ��") != -1)
				partnerAnswer[4][i] = true;
			if (partnerCommands.get(i).indexOf("������������") != -1)
				partnerAnswer[5][i] = true;
			if (partnerCommands.get(i).indexOf("�E�����グ��") != -1)
				partnerAnswer[6][i] = true;
			if (partnerCommands.get(i).indexOf("�E����������") != -1)
				partnerAnswer[7][i] = true;
			if (partnerCommands.get(i).indexOf("�W�����v����") != -1)
				partnerAnswer[8][i] = true;
		}

	}

	public void compare() {
		// TODO Auto-generated method stub
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
						// continue; // ����{�̊e�s�̖��߂��v���C���[�̓����s�ɂ��邩�ǂ���

					} else {
						judge = false;
						break;
					}
				}
			}
		}
	}

	public void loopCheck(String message, TextView textView) {
		int loopNum = Integer.parseInt(message);
		String playerCommand = textView.getText().toString();
		int i = -1;
		int count = 0;
		if (!(loopNum >= 8))
			return;
		while ((i = playerCommand.indexOf("loop", i + 1)) >= 0)
			count++;
		if (loopNum == 8 || loopNum == 11) {
			if (count != 1)
				judge = false;
		} else if (loopNum == 9 || loopNum == 10 || loopNum == 12
				|| loopNum == 13) {
			if (count != 2)
				judge = false;
		}
	}

	public String show() {
		// TODO Auto-generated method stub
		return (judge == true) ? "�����ł��I" : "�s�����ł��E�E�E";
	}
}

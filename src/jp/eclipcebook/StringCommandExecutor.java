package jp.eclipcebook;

import java.util.List;

import android.widget.ImageView;

public class StringCommandExecutor implements Runnable {

	/**** �t�B�[���h ****/
	private ImageView image;
	private List<String> expandedCommands;
	private int lineIndex;

	/**** �R���X�g���N�^ ****/
	public StringCommandExecutor(ImageView image, List<String> stringArray) {
		this.image = image;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
	}

	@Override
	public void run() {
		if (expandedCommands.get(lineIndex).indexOf("�E�����グ��") != -1) {
			Animation.rightFootUpAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("�E����������") != -1) {
			Animation.rightFootDownAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("�E�r���グ��") != -1) {
			if (expandedCommands.get(lineIndex).indexOf("���r���グ��") != -1) {
				Animation.bothHandUpAnimation(image);
			} else {
				Animation.rightHandUpAnimation(image);
			}
		} else if (expandedCommands.get(lineIndex).indexOf("�E�r��������") != -1) {
			if (expandedCommands.get(lineIndex).indexOf("���r��������") != -1) {
				Animation.bothHandDownAnimation(image);
			} else {
				Animation.rightHandDownAnimation(image);
			}
		} else if (expandedCommands.get(lineIndex).indexOf("�������グ��") != -1) {
			Animation.leftFootUpAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("������������") != -1) {
			Animation.leftFootDownAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("���r���グ��") != -1) {
			Animation.leftHandUpAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("���r��������") != -1) {
			Animation.leftHandDownAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("�W�����v����") != -1) {
			Animation.jumpAnimation(image);
		} else {
			Animation.basicAnimation(image);
		}
		lineIndex++;
	}
}

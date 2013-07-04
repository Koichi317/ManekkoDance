package jp.eclipcebook;

import java.util.List;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StringCommandExecutor implements Runnable {

	private static class Input {
		boolean ���r���グ��;
		boolean ���r��������;
		boolean �E�r���グ��;
		boolean �E�r��������;
		boolean �������グ��;
		boolean ������������;
		boolean �E�����グ��;
		boolean �E����������;
		boolean �W�����v����;

		private void inputReset() {
			���r���グ�� = false;
			���r�������� = false;
			�E�r���グ�� = false;
			�E�r�������� = false;
			�������グ�� = false;
			������������ = false;
			�E�����グ�� = false;
			�E���������� = false;
			�W�����v���� = false;
		}
	}

	private static class State {
		boolean isLeftHandUp;
		boolean isLeftHandDown;
		boolean isRightHandUp;
		boolean isRightHandDown;
		boolean isLeftFootUp;
		boolean isLeftFootDown;
		boolean isRightFootUp;
		boolean isRightFootDown;

		private State() {
			isLeftHandUp = false;
			isLeftHandDown = true;
			isRightHandUp = false;
			isRightHandDown = true;
			isLeftFootUp = false;
			isLeftFootDown = true;
			isRightFootUp = false;
			isRightFootDown = true;
		}
	}

	/**** �t�B�[���h ****/
	private List<String> expandedCommands;
	private int lineIndex;
	private boolean addLineIndex;
	private final ImageContainer images;
	private TextView textView;
	private List<Integer> playerNumberSorting;
	private String[] playerCommandsText;
	private int colorPosition;
	private boolean player; // true:player false:partner
	public static boolean errorCheck;
	private Input input;
	private State state;

	/**** �R���X�g���N�^ ****/
	// ����{
	public StringCommandExecutor(ImageContainer images, List<String> stringArray) {
		this.images = images;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
		input = new Input();
		state = new State();
		player = false;
		errorCheck = false;
	}

	// �v���C���[
	public StringCommandExecutor(ImageContainer images, List<String> stringArray,
			TextView textView, List<Integer> playerNumberSorting) {
		this.images = images;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
		input = new Input();
		state = new State();
		this.textView = textView;
		this.playerNumberSorting = playerNumberSorting;
		colorPosition = 0;
		player = true;
		errorCheck = false;
	}

	@Override
	public void run() {
		if (addLineIndex) {

			if (player) { // ���s���̕������Ԃ�����
				colorPosition = playerNumberSorting.get(lineIndex);
				playerCommandsText = textView.getText().toString().split("\n");
				textView.getEditableText().clear();

				for (int i = 0; i < playerCommandsText.length; i++) {

					if (colorPosition == i) {
						textView.append(Html.fromHtml("<font color=#ff0000>"
								+ playerCommandsText[i] + "</font>"));
						textView.append("\n");
					} else {
						textView.append(playerCommandsText[i] + "\n");
					}
				}
			}

			input.inputReset(); // input�̏�����

			// input�̎擾
			if (expandedCommands.get(lineIndex).indexOf("���r���グ��") != -1)
				input.���r���グ�� = true;
			if (expandedCommands.get(lineIndex).indexOf("���r��������") != -1)
				input.���r�������� = true;
			if (expandedCommands.get(lineIndex).indexOf("�E�r���グ��") != -1)
				input.�E�r���グ�� = true;
			if (expandedCommands.get(lineIndex).indexOf("�E�r��������") != -1)
				input.�E�r�������� = true;
			if (expandedCommands.get(lineIndex).indexOf("�������グ��") != -1)
				input.�������グ�� = true;
			if (expandedCommands.get(lineIndex).indexOf("������������") != -1)
				input.������������ = true;
			if (expandedCommands.get(lineIndex).indexOf("�E�����グ��") != -1)
				input.�E�����グ�� = true;
			if (expandedCommands.get(lineIndex).indexOf("�E����������") != -1)
				input.�E���������� = true;
			if (expandedCommands.get(lineIndex).indexOf("�W�����v����") != -1)
				input.�W�����v���� = true;

			// �����Ȗ��߂̕��сi���r���グ��&&���r�������� ���j
			if ((input.���r���グ�� && input.���r��������)
					|| (input.�E�r���グ�� && input.�E�r��������)
					|| (input.�������グ�� && input.������������)
					|| (input.�E�����グ�� && input.�E����������)
					|| (input.�������グ�� && input.�E�����グ��)
					|| (input.������������ && input.�E����������)
					|| (input.�W�����v���� && (input.���r���グ�� || input.���r�������� || input.�E�r���グ��
							|| input.�E�r�������� || input.�������グ�� || input.������������ || input.�E�����グ�� || input.�E����������))) {
				errorCheck = true;
				Log.v("tag", "error");
				errorImage(images);
				addLineIndex = false;
				return;
			}

			// �����Ȗ��߁i���r���グ�Ă����Ԃ̎���"���r���グ��" ���j
			if ((input.���r���グ�� && state.isLeftHandUp)
					|| (input.���r�������� && state.isLeftHandDown)
					|| (input.�E�r���グ�� && state.isRightHandUp)
					|| (input.�E�r�������� && state.isRightHandDown)
					|| (input.�������グ�� && state.isLeftFootUp)
					|| (input.������������ && state.isLeftFootDown)
					|| (input.�E�����グ�� && state.isRightFootUp)
					|| (input.�E���������� && state.isRightFootDown)
					|| (input.�W�����v���� && (state.isLeftHandUp || state.isRightHandUp
							|| state.isLeftFootUp || state.isRightFootUp))) {
				errorCheck = true;
				errorImage(images);
				addLineIndex = false;
				return;
			}

			if (input.���r���グ��) { // ���r���グ��
				if (player)
					images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up2);
				if (!player)
					images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up2);
				state.isLeftHandUp = true; // ���r���グ�Ă���(1:true)
				state.isLeftHandDown = false; // ���r�������Ă���(0:false)
			}

			if (input.���r��������) {
				if (player)
					images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up2);
				if (!player)
					images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up2);
				state.isLeftHandUp = false;
				state.isLeftHandDown = true;
			}

			if (input.�E�r���グ��) {
				if (player)
					images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up2);
				if (!player)
					images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up2);
				state.isRightHandUp = true;
				state.isRightHandDown = false;
			}

			if (input.�E�r��������) {
				if (player)
					images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up2);
				if (!player)
					images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up2);
				state.isRightHandUp = false;
				state.isRightHandDown = true;
			}

			if (input.�������グ��) {
				if (player)
					images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up2);
				if (!player)
					images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up2);
				state.isLeftFootUp = true;
				state.isLeftFootDown = false;
			}

			if (input.������������) {
				if (player)
					images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up2);
				if (!player)
					images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up2);
				state.isLeftFootUp = false;
				state.isLeftFootDown = true;
			}

			if (input.�E�����グ��) {
				if (player)
					images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up2);
				if (!player)
					images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up2);
				state.isRightFootUp = true;
				state.isRightFootDown = false;
			}

			if (input.�E����������) {
				if (player)
					images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up2);
				if (!player)
					images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up2);
				state.isRightFootUp = false;
				state.isRightFootDown = true;
			}

			if (input.�W�����v����) {
				images.getLeftHand1().setVisibility(View.INVISIBLE);
				images.getRightHand1().setVisibility(View.INVISIBLE);
				images.getLeftFoot1().setVisibility(View.INVISIBLE);
				images.getRightFoot1().setVisibility(View.INVISIBLE);
				if (player)
					images.getBasic().setImageResource(R.drawable.piyo_jump2);
				if (!player)
					images.getBasic().setImageResource(R.drawable.cocco_jump2);
			}
			addLineIndex = false;

		} else {
			if(player) {
				if (errorCheck) {
					errorImage(images);
					addLineIndex = true;
					return;
				}
			}

			if (expandedCommands.get(lineIndex).indexOf("���r���グ��") != -1) {
				if (player)
					images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up3);
				if (!player)
					images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("���r��������") != -1) {
				if (player)
					images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up1);
				if (!player)
					images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�r���グ��") != -1) {
				if (player)
					images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up3);
				if (!player)
					images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�r��������") != -1) {
				if (player)
					images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up1);
				if (!player)
					images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�������グ��") != -1) {
				if (player)
					images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up3);
				if (!player)
					images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("������������") != -1) {
				if (player)
					images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up1);
				if (!player)
					images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�����グ��") != -1) {
				if (player)
					images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up3);
				if (!player)
					images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E����������") != -1) {
				if (player)
					images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up1);
				if (!player)
					images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�W�����v����") != -1) {
				if (player)
					images.getBasic().setImageResource(R.drawable.piyo_basic);
				if (!player)
					images.getBasic().setImageResource(R.drawable.cocco_basic);
				images.getLeftHand1().setVisibility(View.VISIBLE);
				images.getRightHand1().setVisibility(View.VISIBLE);
				images.getLeftFoot1().setVisibility(View.VISIBLE);
				images.getRightFoot1().setVisibility(View.VISIBLE);
			}

			lineIndex++;
			addLineIndex = true;
		}
	}

	public void errorImage(ImageContainer images) {
		if (addLineIndex) {
			images.getBasic().setImageResource(R.drawable.korobu_1);
			images.getLeftHand1().setVisibility(View.INVISIBLE);
			images.getRightHand1().setVisibility(View.INVISIBLE);
			images.getLeftFoot1().setVisibility(View.INVISIBLE);
			images.getRightFoot1().setVisibility(View.INVISIBLE);
		} else {
			images.getBasic().setImageResource(R.drawable.korobu_3);
			addLineIndex = true;
		}

	}
}

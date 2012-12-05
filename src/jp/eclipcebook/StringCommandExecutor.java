package jp.eclipcebook;

import java.util.List;

import jp.eclipcebook.R;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class StringCommandExecutor implements Runnable {

	/**** �t�B�[���h ****/
	private List<String> expandedCommands;
	private int lineIndex;
	private boolean addLineIndex;
	private boolean[] fragState;
	private boolean[] fragNextState;
	private final ImageContainer images;
	private TextView textView;
	private List<Integer> playerNumberSorting;
	private int colorPosition;
	private String[] playerCommandsText;
	private boolean colorAttached; //true:player false:partner

	/**** �R���X�g���N�^ ****/
	public StringCommandExecutor(ImageContainer images, List<String> stringArray) {
		this.images = images;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
		fragState = new boolean[8];
		fragStateInitialization(fragState);
		fragNextState = new boolean[9];
		colorAttached = false;
	}

	public StringCommandExecutor(ImageContainer images, List<String> stringArray,
			TextView textView, List<Integer> playerNumberSorting) {
		this.images = images;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
		fragState = new boolean[8];
		fragStateInitialization(fragState);
		fragNextState = new boolean[9];
		this.textView = textView;
		this.playerNumberSorting = playerNumberSorting;
		colorPosition = 0;
		colorAttached = true;
	}

	private void fragStateInitialization(boolean[] fragState) {
		// TODO Auto-generated method stub
		fragState[0] = false; // ���r���グ�Ă���(0:false, 1:true)
		fragState[1] = true; // ���r�������Ă���
		fragState[2] = false; // �E�r���グ�Ă���
		fragState[3] = true; // �E�r�������Ă���
		fragState[4] = false; // �������グ�Ă���
		fragState[5] = true; // �����������Ă���
		fragState[6] = false; // �E�����グ�Ă���
		fragState[7] = true; // �E���������Ă���
	}

	@Override
	public void run() {
		if (addLineIndex) {
			// ImageView answerWord = (ImageView)findViewById(R.id.imageView3);
			/*
			 * AlertDialog.Builder builder = new
			 * AlertDialog.Builder(ActionActivity.this);
			 * builder.setMessage("hoge"); builder.show();
			 */// �_�C�A���O�̐����łǂ����Ă��G���[���o�Ă��܂�
			if (colorAttached) {
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

			for (int i = 0; i < fragNextState.length; i++)
				fragNextState[i] = false; // frag�̃��Z�b�g

			if (expandedCommands.get(lineIndex).indexOf("���r���グ��") != -1)
				fragNextState[0] = true;
			if (expandedCommands.get(lineIndex).indexOf("���r��������") != -1)
				fragNextState[1] = true;
			if (expandedCommands.get(lineIndex).indexOf("�E�r���グ��") != -1)
				fragNextState[2] = true;
			if (expandedCommands.get(lineIndex).indexOf("�E�r��������") != -1)
				fragNextState[3] = true;
			if (expandedCommands.get(lineIndex).indexOf("�������グ��") != -1)
				fragNextState[4] = true;
			if (expandedCommands.get(lineIndex).indexOf("������������") != -1)
				fragNextState[5] = true;
			if (expandedCommands.get(lineIndex).indexOf("�E�����グ��") != -1)
				fragNextState[6] = true;
			if (expandedCommands.get(lineIndex).indexOf("�E����������") != -1)
				fragNextState[7] = true;
			if (expandedCommands.get(lineIndex).indexOf("�W�����v����") != -1)
				fragNextState[8] = true;

			// �����Ȗ��߂̕��сi���r���グ��&&���r�������� ���j
			if ((fragNextState[0] && fragNextState[1])
					|| (fragNextState[2] && fragNextState[3])
					|| (fragNextState[4] && fragNextState[5])
					|| (fragNextState[6] && fragNextState[7])
					|| (fragNextState[4] && fragNextState[6])
					|| (fragNextState[5] && fragNextState[7])
					|| (fragNextState[0] || fragNextState[1] || fragNextState[2]
							|| fragNextState[3] || fragNextState[4] || fragNextState[5]
							|| fragNextState[6] || fragNextState[7])) {
				// �]�񂾉摜�̃G���[����
			}

			if (fragNextState[0]) { // ���r���グ��
				if (fragState[0]) { // ���ɍ��r���グ�Ă���
					// �G���[����
				} else {
					if(colorAttached) images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up2);
					if(!colorAttached) images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up2);
					fragState[0] = true; // ���r���グ�Ă���(1:true)
					fragState[1] = false; // ���r�������Ă���(0:false)
				}
			}

			if (fragNextState[1]) {
				if (fragState[1]) {
					// �G���[����
				} else {
					if(colorAttached) images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up2);
					if(!colorAttached) images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up2);
					fragState[0] = false;
					fragState[1] = true;
				}
			}

			if (fragNextState[2]) {
				if (fragState[2]) {
					// �G���[����
				} else {
					if(colorAttached) images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up2);
					if(!colorAttached) images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up2);
					fragState[2] = true;
					fragState[3] = false;
				}
			}

			if (fragNextState[3]) {
				if (fragState[3]) {
					// �G���[����
				} else {
					if(colorAttached) images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up2);
					if(!colorAttached) images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up2);
					fragState[2] = false;
					fragState[3] = true;
				}
			}

			if (fragNextState[4]) {
				if (fragState[4]) {
					// �G���[����
				} else {
					if(colorAttached) images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up2);
					if(!colorAttached) images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up2);
					fragState[4] = true;
					fragState[5] = false;
				}
			}

			if (fragNextState[5]) {
				if (fragState[5]) {
					// �G���[����
				} else {
					if(colorAttached) images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up2);
					if(!colorAttached) images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up2);
					fragState[4] = false;
					fragState[5] = true;
				}
			}

			if (fragNextState[6]) {
				if (fragState[6]) {
					// �G���[����
				} else {
					if(colorAttached) images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up2);
					if(!colorAttached) images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up2);
					fragState[6] = true;
					fragState[7] = false;
				}
			}

			if (fragNextState[7]) {
				if (fragState[7]) {
					// �G���[����
				} else {
					if(colorAttached) images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up2);
					if(!colorAttached) images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up2);
					fragState[6] = false;
					fragState[7] = true;
				}
			}

			if (fragNextState[8]) {
				if (fragState[0] || fragState[2] || fragState[4] || fragState[6]) {
					// �G���[����
				} else { // �����グ�Ă��Ȃ���΁A
					images.getLeftHand1().setVisibility(View.INVISIBLE);
					images.getRightHand1().setVisibility(View.INVISIBLE);
					images.getLeftFoot1().setVisibility(View.INVISIBLE);
					images.getRightFoot1().setVisibility(View.INVISIBLE);
					if(colorAttached) images.getBasic().setImageResource(R.drawable.piyo_jump2);
					if(!colorAttached) images.getBasic().setImageResource(R.drawable.cocco_jump2);
				}
			}

			addLineIndex = false;

		} else {

			if (expandedCommands.get(lineIndex).indexOf("���r���グ��") != -1) {
				if(colorAttached) images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up3);
				if(!colorAttached) images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("���r��������") != -1) {
				if(colorAttached) images.getLeftHand1().setImageResource(R.drawable.piyo_left_hand_up1);
				if(!colorAttached) images.getLeftHand1().setImageResource(R.drawable.cocco_left_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�r���グ��") != -1) {
				if(colorAttached) images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up3);
				if(!colorAttached) images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�r��������") != -1) {
				if(colorAttached) images.getRightHand1().setImageResource(R.drawable.piyo_right_hand_up1);
				if(!colorAttached) images.getRightHand1().setImageResource(R.drawable.cocco_right_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�������グ��") != -1) {
				if(colorAttached) images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up3);
				if(!colorAttached) images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("������������") != -1) {
				if(colorAttached) images.getLeftFoot1().setImageResource(R.drawable.piyo_left_foot_up1);
				if(!colorAttached) images.getLeftFoot1().setImageResource(R.drawable.cocco_left_foot_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�����グ��") != -1) {
				if(colorAttached) images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up3);
				if(!colorAttached) images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E����������") != -1) {
				if(colorAttached) images.getRightFoot1().setImageResource(R.drawable.piyo_right_foot_up1);
				if(!colorAttached) images.getRightFoot1().setImageResource(R.drawable.cocco_right_foot_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�W�����v����") != -1) {
				if(colorAttached) images.getBasic().setImageResource(R.drawable.piyo_basic);
				if(!colorAttached) images.getBasic().setImageResource(R.drawable.cocco_basic);
				images.getLeftHand1().setVisibility(View.VISIBLE);
				images.getRightHand1().setVisibility(View.VISIBLE);
				images.getLeftFoot1().setVisibility(View.VISIBLE);
				images.getRightFoot1().setVisibility(View.VISIBLE);
			} 
			
			lineIndex++;
			addLineIndex = true;
		}
	}
}

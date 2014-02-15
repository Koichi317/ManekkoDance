package net.exkazuu.ManekkoDance;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import jp.eclipcebook.R;

import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StringCommandExecutor implements Runnable {
	String arg = "";

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
	public StringCommandExecutor(ImageContainer images,
			List<String> stringArray, TextView textView,
			List<Integer> playerNumberSorting) {
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
					|| (input.�W�����v���� && (input.���r���グ�� || input.���r��������
							|| input.�E�r���グ�� || input.�E�r�������� || input.�������グ��
							|| input.������������ || input.�E�����グ�� || input.�E����������))) {
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
					|| (input.�W�����v���� && (state.isLeftHandUp
							|| state.isRightHandUp || state.isLeftFootUp || state.isRightFootUp))) {
				errorCheck = true;
				errorImage(images);
				addLineIndex = false;
				return;
			}

			// String arg = "";
			if (input.���r���グ��) { // ���r���グ��
				if (player)
					images.getLeftHand1().setImageResource(
							R.drawable.piyo_left_hand_up2);
				if (!player)
					images.getLeftHand1().setImageResource(
							R.drawable.cocco_left_hand_up2);
				state.isLeftHandUp = true; // ���r���グ�Ă���(1:true)
				state.isLeftHandDown = false; // ���r�������Ă���(0:false)
				arg += "lau";
			}

			if (input.���r��������) {
				if (player)
					images.getLeftHand1().setImageResource(
							R.drawable.piyo_left_hand_up2);
				if (!player)
					images.getLeftHand1().setImageResource(
							R.drawable.cocco_left_hand_up2);
				state.isLeftHandUp = false;
				state.isLeftHandDown = true;
				arg = arg.replace("lau", "");
			}

			if (input.�E�r���グ��) {
				if (player)
					images.getRightHand1().setImageResource(
							R.drawable.piyo_right_hand_up2);
				if (!player)
					images.getRightHand1().setImageResource(
							R.drawable.cocco_right_hand_up2);
				state.isRightHandUp = true;
				state.isRightHandDown = false;
				arg += "rau";
			}

			if (input.�E�r��������) {
				if (player)
					images.getRightHand1().setImageResource(
							R.drawable.piyo_right_hand_up2);
				if (!player)
					images.getRightHand1().setImageResource(
							R.drawable.cocco_right_hand_up2);
				state.isRightHandUp = false;
				state.isRightHandDown = true;
				arg = arg.replace("rau", "");
			}

			if (input.�������グ��) {
				if (player)
					images.getLeftFoot1().setImageResource(
							R.drawable.piyo_left_foot_up2);
				if (!player)
					images.getLeftFoot1().setImageResource(
							R.drawable.cocco_left_foot_up2);
				state.isLeftFootUp = true;
				state.isLeftFootDown = false;
				arg += "llu";
			}

			if (input.������������) {
				if (player)
					images.getLeftFoot1().setImageResource(
							R.drawable.piyo_left_foot_up2);
				if (!player)
					images.getLeftFoot1().setImageResource(
							R.drawable.cocco_left_foot_up2);
				state.isLeftFootUp = false;
				state.isLeftFootDown = true;
				arg = arg.replace("llu", "");
			}

			if (input.�E�����グ��) {
				if (player)
					images.getRightFoot1().setImageResource(
							R.drawable.piyo_right_foot_up2);
				if (!player)
					images.getRightFoot1().setImageResource(
							R.drawable.cocco_right_foot_up2);
				state.isRightFootUp = true;
				state.isRightFootDown = false;
				arg += "rlu";
			}

			if (input.�E����������) {
				if (player)
					images.getRightFoot1().setImageResource(
							R.drawable.piyo_right_foot_up2);
				if (!player)
					images.getRightFoot1().setImageResource(
							R.drawable.cocco_right_foot_up2);
				state.isRightFootUp = false;
				state.isRightFootDown = true;
				arg = arg.replace("rlu", "");
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
				arg += "jump";
			} else {
				arg = arg.replace("jump", "");
			}

			if (player) {
				commandBear(arg);
			}
			addLineIndex = false;

		} else {
			if (player) {
				if (errorCheck) {
					errorImage(images);
					addLineIndex = true;
					return;
				}
			}

			if (expandedCommands.get(lineIndex).indexOf("���r���グ��") != -1) {
				if (player)
					images.getLeftHand1().setImageResource(
							R.drawable.piyo_left_hand_up3);
				if (!player)
					images.getLeftHand1().setImageResource(
							R.drawable.cocco_left_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("���r��������") != -1) {
				if (player)
					images.getLeftHand1().setImageResource(
							R.drawable.piyo_left_hand_up1);
				if (!player)
					images.getLeftHand1().setImageResource(
							R.drawable.cocco_left_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�r���グ��") != -1) {
				if (player)
					images.getRightHand1().setImageResource(
							R.drawable.piyo_right_hand_up3);
				if (!player)
					images.getRightHand1().setImageResource(
							R.drawable.cocco_right_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�r��������") != -1) {
				if (player)
					images.getRightHand1().setImageResource(
							R.drawable.piyo_right_hand_up1);
				if (!player)
					images.getRightHand1().setImageResource(
							R.drawable.cocco_right_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�������グ��") != -1) {
				if (player)
					images.getLeftFoot1().setImageResource(
							R.drawable.piyo_left_foot_up3);
				if (!player)
					images.getLeftFoot1().setImageResource(
							R.drawable.cocco_left_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("������������") != -1) {
				if (player)
					images.getLeftFoot1().setImageResource(
							R.drawable.piyo_left_foot_up1);
				if (!player)
					images.getLeftFoot1().setImageResource(
							R.drawable.cocco_left_foot_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E�����グ��") != -1) {
				if (player)
					images.getRightFoot1().setImageResource(
							R.drawable.piyo_right_foot_up3);
				if (!player)
					images.getRightFoot1().setImageResource(
							R.drawable.cocco_right_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("�E����������") != -1) {
				if (player)
					images.getRightFoot1().setImageResource(
							R.drawable.piyo_right_foot_up1);
				if (!player)
					images.getRightFoot1().setImageResource(
							R.drawable.cocco_right_foot_up1);
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

	public static void commandBear(String arg) {
		PostTask posttask = new PostTask(arg);
		posttask.execute();
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

class PostTask extends AsyncTask<Void, String, Boolean> {

	private String arg;

	PostTask(String arg) {
		this.arg = arg;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		boolean result = false;

		// All your code goes in here
		try {
			// URL�w��
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://192.168.91.200:3000/form");
			// BODY�ɓo�^�A�ݒ�
			ArrayList<NameValuePair> value = new ArrayList<NameValuePair>();
			value.add(new BasicNameValuePair("input1", arg));

			String body = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(value, "UTF-8"));
				// ���N�G�X�g���M
				HttpResponse response = client.execute(post);
				// �擾
				HttpEntity entity = response.getEntity();
				body = EntityUtils.toString(entity, "UTF-8");
			} catch (IOException e) {
				System.out.println(e);
			}
			client.getConnectionManager().shutdown();
		} catch (Exception e2) {
			System.out.println(e2);
		}
		// If you want to do something on the UI use progress update

		publishProgress("progress");
		return result;
	}

	protected void onProgressUpdate(String... progress) {
	}
}
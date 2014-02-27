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
		boolean 左腕を上げる;
		boolean 左腕を下げる;
		boolean 右腕を上げる;
		boolean 右腕を下げる;
		boolean 左足を上げる;
		boolean 左足を下げる;
		boolean 右足を上げる;
		boolean 右足を下げる;
		boolean ジャンプする;

		private void inputReset() {
			左腕を上げる = false;
			左腕を下げる = false;
			右腕を上げる = false;
			右腕を下げる = false;
			左足を上げる = false;
			左足を下げる = false;
			右足を上げる = false;
			右足を下げる = false;
			ジャンプする = false;
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

	/**** フィールド ****/
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

	/**** コンストラクタ ****/
	// お手本
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

	// プレイヤー
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

			if (player) { // 実行中の文字列を赤くする
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

			input.inputReset(); // inputの初期化

			// inputの取得
			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1)
				input.左腕を上げる = true;
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1)
				input.左腕を下げる = true;
			if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1)
				input.右腕を上げる = true;
			if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1)
				input.右腕を下げる = true;
			if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1)
				input.左足を上げる = true;
			if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1)
				input.左足を下げる = true;
			if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1)
				input.右足を上げる = true;
			if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1)
				input.右足を下げる = true;
			if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1)
				input.ジャンプする = true;

			// 無効な命令の並び（左腕を上げる&&左腕を下げる 等）
			if ((input.左腕を上げる && input.左腕を下げる)
					|| (input.右腕を上げる && input.右腕を下げる)
					|| (input.左足を上げる && input.左足を下げる)
					|| (input.右足を上げる && input.右足を下げる)
					|| (input.左足を上げる && input.右足を上げる)
					|| (input.左足を下げる && input.右足を下げる)
					|| (input.ジャンプする && (input.左腕を上げる || input.左腕を下げる
							|| input.右腕を上げる || input.右腕を下げる || input.左足を上げる
							|| input.左足を下げる || input.右足を上げる || input.右足を下げる))) {
				errorCheck = true;
				Log.v("tag", "error");
				errorImage(images);
				addLineIndex = false;
				return;
			}

			// 無効な命令（左腕を上げている状態の時に"左腕を上げる" 等）
			if ((input.左腕を上げる && state.isLeftHandUp)
					|| (input.左腕を下げる && state.isLeftHandDown)
					|| (input.右腕を上げる && state.isRightHandUp)
					|| (input.右腕を下げる && state.isRightHandDown)
					|| (input.左足を上げる && state.isLeftFootUp)
					|| (input.左足を下げる && state.isLeftFootDown)
					|| (input.右足を上げる && state.isRightFootUp)
					|| (input.右足を下げる && state.isRightFootDown)
					|| (input.ジャンプする && (state.isLeftHandUp
							|| state.isRightHandUp || state.isLeftFootUp || state.isRightFootUp))) {
				errorCheck = true;
				errorImage(images);
				addLineIndex = false;
				return;
			}

			// String arg = "";
			if (input.左腕を上げる) { // 左腕を上げる
				if (player)
					images.getLeftHand1().setImageResource(
							R.drawable.piyo_left_hand_up2);
				if (!player)
					images.getLeftHand1().setImageResource(
							R.drawable.cocco_left_hand_up2);
				state.isLeftHandUp = true; // 左腕を上げている(1:true)
				state.isLeftHandDown = false; // 左腕を下げている(0:false)
				arg += "lau";
			}

			if (input.左腕を下げる) {
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

			if (input.右腕を上げる) {
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

			if (input.右腕を下げる) {
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

			if (input.左足を上げる) {
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

			if (input.左足を下げる) {
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

			if (input.右足を上げる) {
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

			if (input.右足を下げる) {
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

			if (input.ジャンプする) {
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

			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1) {
				if (player)
					images.getLeftHand1().setImageResource(
							R.drawable.piyo_left_hand_up3);
				if (!player)
					images.getLeftHand1().setImageResource(
							R.drawable.cocco_left_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1) {
				if (player)
					images.getLeftHand1().setImageResource(
							R.drawable.piyo_left_hand_up1);
				if (!player)
					images.getLeftHand1().setImageResource(
							R.drawable.cocco_left_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1) {
				if (player)
					images.getRightHand1().setImageResource(
							R.drawable.piyo_right_hand_up3);
				if (!player)
					images.getRightHand1().setImageResource(
							R.drawable.cocco_right_hand_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1) {
				if (player)
					images.getRightHand1().setImageResource(
							R.drawable.piyo_right_hand_up1);
				if (!player)
					images.getRightHand1().setImageResource(
							R.drawable.cocco_right_hand_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1) {
				if (player)
					images.getLeftFoot1().setImageResource(
							R.drawable.piyo_left_foot_up3);
				if (!player)
					images.getLeftFoot1().setImageResource(
							R.drawable.cocco_left_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1) {
				if (player)
					images.getLeftFoot1().setImageResource(
							R.drawable.piyo_left_foot_up1);
				if (!player)
					images.getLeftFoot1().setImageResource(
							R.drawable.cocco_left_foot_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1) {
				if (player)
					images.getRightFoot1().setImageResource(
							R.drawable.piyo_right_foot_up3);
				if (!player)
					images.getRightFoot1().setImageResource(
							R.drawable.cocco_right_foot_up3);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1) {
				if (player)
					images.getRightFoot1().setImageResource(
							R.drawable.piyo_right_foot_up1);
				if (!player)
					images.getRightFoot1().setImageResource(
							R.drawable.cocco_right_foot_up1);
			}
			if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1) {
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
			// URL指定
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://192.168.91.97:3000/form");
			// BODYに登録、設定
			ArrayList<NameValuePair> value = new ArrayList<NameValuePair>();
			value.add(new BasicNameValuePair("input1", arg));

			String body = null;
			try {
				post.setEntity(new UrlEncodedFormEntity(value, "UTF-8"));
				// リクエスト送信
				HttpResponse response = client.execute(post);
				// 取得
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
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
import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StringCommandExecutor implements Runnable {
	private String arg = "";

	private enum CharacterType {
		PiyoLeft, PiyoRight, Coco,
	}

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
	private CharacterType charaType;
	public static boolean errorCheck;
	private Input input;
	private State state;
	private Context context;

	/**** コンストラクタ ****/
	// お手本
	public StringCommandExecutor(ImageContainer images, List<String> stringArray) {
		this.images = images;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
		input = new Input();
		state = new State();
		charaType = CharacterType.Coco;
		errorCheck = false;
	}

	// プレイヤー
	public StringCommandExecutor(ImageContainer images,
			List<String> stringArray, TextView textView,
			List<Integer> playerNumberSorting, Context context, boolean isLeft) {
		this.context = context;
		this.images = images;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
		input = new Input();
		state = new State();
		this.textView = textView;
		this.playerNumberSorting = playerNumberSorting;
		colorPosition = 0;
		charaType = isLeft ? CharacterType.PiyoLeft : CharacterType.PiyoRight;
		errorCheck = false;
	}

	@Override
	public void run() {
		if (addLineIndex) {

			if (charaType == CharacterType.PiyoLeft) { // 実行中の文字列を赤くする
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

			if (input.左腕を上げる) { // 左腕を上げる
				switch (charaType) {
				case Coco:
					images.getLeftHand().setImageResource(
							R.drawable.cocco_left_hand_up2);
					break;
				case PiyoLeft:
					images.getLeftHand().setImageResource(
							R.drawable.piyo_left_hand_up2);
					arg += "lau";
					break;
				case PiyoRight:
					images.getLeftHand().setImageResource(
							R.drawable.piyo_left_hand_up2);
					break;
				}
				state.isLeftHandUp = true; // 左腕を上げている(1:true)
				state.isLeftHandDown = false; // 左腕を下げている(0:false)
			}

			if (input.左腕を下げる) {
				switch (charaType) {
				case Coco:
					images.getLeftHand().setImageResource(
							R.drawable.cocco_left_hand_up2);
					break;
				case PiyoLeft:
					images.getLeftHand().setImageResource(
							R.drawable.piyo_left_hand_up2);
					arg = arg.replace("lau", "");
					break;
				case PiyoRight:
					images.getLeftHand().setImageResource(
							R.drawable.piyo_left_hand_up2);
					break;
				}
				state.isLeftHandUp = false;
				state.isLeftHandDown = true;
			}

			if (input.右腕を上げる) {
				switch (charaType) {
				case Coco:
					images.getRightHand().setImageResource(
							R.drawable.cocco_right_hand_up2);
					break;
				case PiyoLeft:
					images.getRightHand().setImageResource(
							R.drawable.piyo_right_hand_up2);
					arg += "rau";
					break;
				case PiyoRight:
					images.getRightHand().setImageResource(
							R.drawable.piyo_right_hand_up2);
					break;
				}
				state.isRightHandUp = true;
				state.isRightHandDown = false;
			}

			if (input.右腕を下げる) {
				switch (charaType) {
				case Coco:
					images.getRightHand().setImageResource(
							R.drawable.cocco_right_hand_up2);
					break;
				case PiyoLeft:
					images.getRightHand().setImageResource(
							R.drawable.piyo_right_hand_up2);
					arg = arg.replace("rau", "");
					break;
				case PiyoRight:
					images.getRightHand().setImageResource(
							R.drawable.piyo_right_hand_up2);
					break;
				}
				state.isRightHandUp = false;
				state.isRightHandDown = true;
			}

			if (input.左足を上げる) {
				switch (charaType) {
				case Coco:
					images.getLeftFoot().setImageResource(
							R.drawable.cocco_left_foot_up2);
					break;
				case PiyoLeft:
					images.getLeftFoot().setImageResource(
							R.drawable.piyo_left_foot_up2);
					arg += "llu";
					break;
				case PiyoRight:
					images.getLeftFoot().setImageResource(
							R.drawable.piyo_left_foot_up2);
					break;
				}
				state.isLeftFootUp = true;
				state.isLeftFootDown = false;
			}

			if (input.左足を下げる) {
				switch (charaType) {
				case Coco:
					images.getLeftFoot().setImageResource(
							R.drawable.cocco_left_foot_up2);
					break;
				case PiyoLeft:
					images.getLeftFoot().setImageResource(
							R.drawable.piyo_left_foot_up2);
					arg = arg.replace("llu", "");
					break;
				case PiyoRight:
					images.getLeftFoot().setImageResource(
							R.drawable.piyo_left_foot_up2);
					break;
				}
				state.isLeftFootUp = false;
				state.isLeftFootDown = true;
			}

			if (input.右足を上げる) {
				switch (charaType) {
				case Coco:
					images.getRightFoot().setImageResource(
							R.drawable.cocco_right_foot_up2);
					break;
				case PiyoLeft:
					images.getRightFoot().setImageResource(
							R.drawable.piyo_right_foot_up2);
					arg += "rlu";
					break;
				case PiyoRight:
					images.getRightFoot().setImageResource(
							R.drawable.piyo_right_foot_up2);
					break;
				}
				state.isRightFootUp = true;
				state.isRightFootDown = false;
			}

			if (input.右足を下げる) {
				switch (charaType) {
				case Coco:
					images.getRightFoot().setImageResource(
							R.drawable.cocco_right_foot_up2);
					break;
				case PiyoLeft:
					images.getRightFoot().setImageResource(
							R.drawable.piyo_right_foot_up2);
					arg = arg.replace("rlu", "");
					break;
				case PiyoRight:
					images.getRightFoot().setImageResource(
							R.drawable.piyo_right_foot_up2);
					break;
				}
				state.isRightFootUp = false;
				state.isRightFootDown = true;
			}

			if (input.ジャンプする) {
				images.getLeftHand().setVisibility(View.INVISIBLE);
				images.getRightHand().setVisibility(View.INVISIBLE);
				images.getLeftFoot().setVisibility(View.INVISIBLE);
				images.getRightFoot().setVisibility(View.INVISIBLE);
				switch (charaType) {
				case Coco:
					images.getBasic().setImageResource(R.drawable.cocco_jump2);
					break;
				case PiyoLeft:
					images.getBasic().setImageResource(R.drawable.piyo_jump2);
					arg += "jump";
					break;
				case PiyoRight:
					images.getBasic().setImageResource(R.drawable.piyo_jump2);
					break;
				}
			} else {
				if (charaType == CharacterType.PiyoLeft) {
					arg = arg.replace("jump", "");
				}
			}
			
			if (charaType == CharacterType.PiyoLeft) {
				commandBear(arg);
			}
			addLineIndex = false;

		} else {
			if (charaType != CharacterType.Coco) {
				if (errorCheck) {
					errorImage(images);
					addLineIndex = true;
					return;
				}
			}

			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getLeftHand().setImageResource(R.drawable.cocco_left_hand_up3);
					break;
				case PiyoLeft:
					images.getLeftHand().setImageResource(R.drawable.piyo_left_hand_up3);
					break;
				case PiyoRight:
					images.getLeftHand().setImageResource(R.drawable.piyo_left_hand_up3);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getLeftHand().setImageResource(R.drawable.cocco_left_hand_up1);
					break;
				case PiyoLeft:
					images.getLeftHand().setImageResource(R.drawable.piyo_left_hand_up1);
					break;
				case PiyoRight:
					images.getLeftHand().setImageResource(R.drawable.piyo_left_hand_up1);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getRightHand().setImageResource(R.drawable.cocco_right_hand_up3);
					break;
				case PiyoLeft:
					images.getRightHand().setImageResource(R.drawable.piyo_right_hand_up3);
					break;
				case PiyoRight:
					images.getRightHand().setImageResource(R.drawable.piyo_right_hand_up3);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getRightHand().setImageResource(R.drawable.cocco_right_hand_up1);
					break;
				case PiyoLeft:
					images.getRightHand().setImageResource(R.drawable.piyo_right_hand_up1);
					break;
				case PiyoRight:
					images.getRightHand().setImageResource(R.drawable.piyo_right_hand_up1);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getLeftFoot().setImageResource(R.drawable.cocco_left_foot_up3);
					break;
				case PiyoLeft:
					images.getLeftFoot().setImageResource(R.drawable.piyo_left_foot_up3);
					break;
				case PiyoRight:
					images.getLeftFoot().setImageResource(R.drawable.piyo_left_foot_up3);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getLeftFoot().setImageResource(R.drawable.cocco_left_foot_up1);
					break;
				case PiyoLeft:
					images.getLeftFoot().setImageResource(R.drawable.piyo_left_foot_up1);
					break;
				case PiyoRight:
					images.getLeftFoot().setImageResource(R.drawable.piyo_left_foot_up1);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getRightFoot().setImageResource(R.drawable.cocco_right_foot_up3);
					break;
				case PiyoLeft:
					images.getRightFoot().setImageResource(R.drawable.piyo_right_foot_up3);
					break;
				case PiyoRight:
					images.getRightFoot().setImageResource(R.drawable.piyo_right_foot_up3);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1) {
				switch (charaType) {
				case Coco:
					images.getRightFoot().setImageResource(R.drawable.cocco_right_foot_up1);
					break;
				case PiyoLeft:
					images.getRightFoot().setImageResource(R.drawable.piyo_right_foot_up1);
					break;
				case PiyoRight:
					images.getRightFoot().setImageResource(R.drawable.piyo_right_foot_up1);
					break;
				}
			}
			if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1) {
				switch (charaType) {
				case Coco:
					images.getBasic().setImageResource(R.drawable.cocco_basic);
					break;
				case PiyoLeft:
					images.getBasic().setImageResource(R.drawable.piyo_basic);
					break;
				case PiyoRight:
					images.getBasic().setImageResource(R.drawable.piyo_basic);
					break;
				}
				images.getLeftHand().setVisibility(View.VISIBLE);
				images.getRightHand().setVisibility(View.VISIBLE);
				images.getLeftFoot().setVisibility(View.VISIBLE);
				images.getRightFoot().setVisibility(View.VISIBLE);
			}

			lineIndex++;
			addLineIndex = true;
		}
	}
	
	private void handleDanbo() {
	}

	public static void commandBear(String arg) {
		PostTask posttask = new PostTask(arg);
		posttask.execute();
	}

	public void errorImage(ImageContainer images) {
		if (addLineIndex) {
			images.getBasic().setImageResource(R.drawable.korobu_1);
			images.getLeftHand().setVisibility(View.INVISIBLE);
			images.getRightHand().setVisibility(View.INVISIBLE);
			images.getLeftFoot().setVisibility(View.INVISIBLE);
			images.getRightFoot().setVisibility(View.INVISIBLE);
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
			HttpPost post = new HttpPost("http://192.168.91.98:3000/form");
			// BODYに登録、設定
			ArrayList<NameValuePair> value = new ArrayList<NameValuePair>();
			value.add(new BasicNameValuePair("input1", arg));
			System.out.println("Send: " + arg);

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
package jp.eclipcebook;

import java.util.List;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StringCommandExecutor implements Runnable {

	/**** フィールド ****/
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
	private String[] baseCommandsText;

	/**** コンストラクタ ****/
	public StringCommandExecutor(ImageContainer images, List<String> stringArray) {
		this.images = images;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
		fragState = new boolean[8];
		fragStateInitialization(fragState);
		fragNextState = new boolean[9];
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
		baseCommandsText = textView.getText().toString().split("\n");
	}

	private void fragStateInitialization(boolean[] fragState) {
		// TODO Auto-generated method stub
		fragState[0] = false; // 左腕を上げている(0:false, 1:true)
		fragState[1] = true; // 左腕を下げている
		fragState[2] = false; // 右腕を上げている
		fragState[3] = true; // 右腕を下げている
		fragState[4] = false; // 左足を上げている
		fragState[5] = true; // 左足を下げている
		fragState[6] = false; // 右足を上げている
		fragState[7] = true; // 右足を下げている
	}

	@Override
	public void run() {
		if (addLineIndex) {
			
			 colorPosition =playerNumberSorting.get(lineIndex);
			 playerCommandsText =textView.getText().toString().split("\n");
			 textView.getEditableText().clear();
			 
			 for(int i = 0; i < playerCommandsText.length; i++) {
			
				 if(colorPosition == i) {
					 textView.append(Html.fromHtml("<font color=#ff0000>"+playerCommandsText[i]+"</font>"));
					 textView.append("\n");
				 }else {
					 textView.append(playerCommandsText[i]+"\n"); 
				 }
			 }
			 

			for (int i = 0; i < fragNextState.length; i++)
				fragNextState[i] = false; // fragのリセット

			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1)
				fragNextState[0] = true;
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1)
				fragNextState[1] = true;
			if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1)
				fragNextState[2] = true;
			if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1)
				fragNextState[3] = true;
			if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1)
				fragNextState[4] = true;
			if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1)
				fragNextState[5] = true;
			if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1)
				fragNextState[6] = true;
			if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1)
				fragNextState[7] = true;
			if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1)
				fragNextState[8] = true;

			// 無効な命令の並び（左腕を上げる&&左腕を下げる 等）
			if ((fragNextState[0] && fragNextState[1])
					|| (fragNextState[2] && fragNextState[3])
					|| (fragNextState[4] && fragNextState[5])
					|| (fragNextState[6] && fragNextState[7])
					|| (fragNextState[4] && fragNextState[6])
					|| (fragNextState[5] && fragNextState[7])
					|| (fragNextState[0] || fragNextState[1] || fragNextState[2]
							|| fragNextState[3] || fragNextState[4] || fragNextState[5]
							|| fragNextState[6] || fragNextState[7])) {
				// 転んだ画像のエラー処理
			}

			if (fragNextState[0]) { // 左腕を上げる
				if (fragState[0]) { // 既に左腕を上げている
					// エラー処理
				} else {
					images.getLeftHand1().setVisibility(View.INVISIBLE);
					images.getLeftHand2().setVisibility(View.VISIBLE);
					fragState[0] = true; // 左腕を上げている(1:true)
					fragState[1] = false; // 左腕を下げている(0:false)
				}
			}

			if (fragNextState[1]) {
				if (fragState[1]) {
					// エラー処理
				} else {
					images.getLeftHand3().setVisibility(View.INVISIBLE);
					images.getLeftHand2().setVisibility(View.VISIBLE);
					fragState[0] = false;
					fragState[1] = true;
				}
			}

			if (fragNextState[2]) {
				if (fragState[2]) {
					// エラー処理
				} else {
					images.getRightHand1().setVisibility(View.INVISIBLE);
					images.getRightHand2().setVisibility(View.VISIBLE);
					fragState[2] = true;
					fragState[3] = false;
				}
			}

			if (fragNextState[3]) {
				if (fragState[3]) {
					// エラー処理
				} else {
					images.getRightHand3().setVisibility(View.INVISIBLE);
					images.getRightHand2().setVisibility(View.VISIBLE);
					fragState[2] = false;
					fragState[3] = true;
				}
			}

			if (fragNextState[4]) {
				if (fragState[4]) {
					// エラー処理
				} else {
					images.getLeftFoot1().setVisibility(View.INVISIBLE);
					images.getLeftFoot2().setVisibility(View.VISIBLE);
					fragState[4] = true;
					fragState[5] = false;
				}
			}

			if (fragNextState[5]) {
				if (fragState[5]) {
					// エラー処理
				} else {
					images.getLeftFoot3().setVisibility(View.INVISIBLE);
					images.getLeftFoot2().setVisibility(View.VISIBLE);
					fragState[4] = false;
					fragState[5] = true;
				}
			}

			if (fragNextState[6]) {
				if (fragState[6]) {
					// エラー処理
				} else {
					images.getRightFoot1().setVisibility(View.INVISIBLE);
					images.getRightFoot2().setVisibility(View.VISIBLE);
					fragState[6] = true;
					fragState[7] = false;
				}
			}

			if (fragNextState[7]) {
				if (fragState[7]) {
					// エラー処理
				} else {
					images.getRightFoot3().setVisibility(View.INVISIBLE);
					images.getRightFoot2().setVisibility(View.VISIBLE);
					fragState[6] = false;
					fragState[7] = true;
				}
			}

			if (fragNextState[8]) {
				if (fragState[0] || fragState[2] || fragState[4] || fragState[6]) {
					// エラー処理
				} else { // 何も上げていなければ、
					images.getLeftHand1().setVisibility(View.INVISIBLE);
					images.getRightHand1().setVisibility(View.INVISIBLE);
					images.getLeftFoot1().setVisibility(View.INVISIBLE);
					images.getRightFoot1().setVisibility(View.INVISIBLE);
					images.getBasic().setImageResource(R.drawable.jump2_piyo);
				}
			}

			addLineIndex = false;

		} else {

			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1) {
				images.getLeftHand2().setVisibility(View.INVISIBLE);
				images.getLeftHand3().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1) {
				images.getLeftHand2().setVisibility(View.INVISIBLE);
				images.getLeftHand1().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1) {
				images.getRightHand2().setVisibility(View.INVISIBLE);
				images.getRightHand3().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1) {
				images.getRightHand2().setVisibility(View.INVISIBLE);
				images.getRightHand1().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1) {
				images.getLeftFoot2().setVisibility(View.INVISIBLE);
				images.getLeftFoot3().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1) {
				images.getLeftFoot2().setVisibility(View.INVISIBLE);
				images.getLeftFoot1().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1) {
				images.getRightFoot2().setVisibility(View.INVISIBLE);
				images.getRightFoot3().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1) {
				images.getRightFoot2().setVisibility(View.INVISIBLE);
				images.getRightFoot1().setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1) {
				images.getBasic().setImageResource(R.drawable.basic_bo);
				images.getLeftHand1().setVisibility(View.VISIBLE);
				images.getRightHand1().setVisibility(View.VISIBLE);
				images.getLeftFoot1().setVisibility(View.VISIBLE);
				images.getRightFoot1().setVisibility(View.VISIBLE);
			} else {

			}
			lineIndex++;
			addLineIndex = true;
		}
	}
}

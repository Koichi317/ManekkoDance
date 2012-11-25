package jp.eclipcebook;

import java.util.List;

import android.view.View;
import android.widget.ImageView;

public class StringCommandExecutor implements Runnable {

	/**** フィールド ****/
	private ImageView leftHand1, leftHand2, leftHand3, rightHand1, rightHand2,
			rightHand3, basic, leftFoot1, leftFoot2, leftFoot3, rightFoot1,
			rightFoot2, rightFoot3;
	private List<String> expandedCommands;
	private int lineIndex;
	private boolean addLineIndex;

	/**** コンストラクタ ****/
	public StringCommandExecutor(ImageView leftHand1, ImageView leftHand2,
			ImageView leftHand3, ImageView rightHand1, ImageView rightHand2,
			ImageView rightHand3, ImageView basic, ImageView leftFoot1,
			ImageView leftFoot2, ImageView leftFoot3, ImageView rightFoot1,
			ImageView rightFoot2, ImageView rightFoot3, List<String> stringArray) {
		this.leftHand1 = leftHand1;
		this.leftHand2 = leftHand2;
		this.leftHand3 = leftHand3;
		this.rightHand1 = rightHand1;
		this.rightHand2 = rightHand2;
		this.rightHand3 = rightHand3;
		this.basic = basic;
		this.leftFoot1 = leftFoot1;
		this.leftFoot2 = leftFoot2;
		this.leftFoot3 = leftFoot3;
		this.leftFoot1 = rightFoot1;
		this.leftFoot2 = rightFoot2;
		this.leftFoot3 = rightFoot3;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
		this.addLineIndex = true;
	}

	@Override
	public void run() {
		if (addLineIndex) {
			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1) {
				leftHand1.setVisibility(View.INVISIBLE);
				leftHand2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1) {
				leftHand3.setVisibility(View.INVISIBLE);
				leftHand2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1) {
				rightHand1.setVisibility(View.INVISIBLE);
				rightHand2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1) {
				rightHand3.setVisibility(View.INVISIBLE);
				rightHand2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1) {
				leftFoot1.setVisibility(View.INVISIBLE);
				leftFoot2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1) {
				leftFoot3.setVisibility(View.INVISIBLE);
				leftFoot2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1) {
				rightFoot1.setVisibility(View.INVISIBLE);
				rightFoot2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1) {
				rightFoot3.setVisibility(View.INVISIBLE);
				rightFoot2.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1) {
				leftHand1.setVisibility(View.INVISIBLE);
				rightHand1.setVisibility(View.INVISIBLE);
				leftFoot1.setVisibility(View.INVISIBLE);
				rightFoot1.setVisibility(View.INVISIBLE);
				basic.setImageResource(R.drawable.jump2_piyo);
			}
			
			addLineIndex = false;
			
		} else {

			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1) {
				leftHand2.setVisibility(View.INVISIBLE);
				leftHand3.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1) {
				leftHand2.setVisibility(View.INVISIBLE);
				leftHand1.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1) {
				rightHand2.setVisibility(View.INVISIBLE);
				rightHand3.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1) {
				rightHand2.setVisibility(View.INVISIBLE);
				rightHand1.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1) {
				leftFoot2.setVisibility(View.INVISIBLE);
				leftFoot3.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1) {
				leftFoot2.setVisibility(View.INVISIBLE);
				leftFoot1.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1) {
				rightFoot2.setVisibility(View.INVISIBLE);
				rightFoot3.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1) {
				rightFoot2.setVisibility(View.INVISIBLE);
				rightFoot1.setVisibility(View.VISIBLE);
			}
			if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1) {
				basic.setImageResource(R.drawable.basic_piyo);
				leftHand1.setVisibility(View.VISIBLE);
				rightHand1.setVisibility(View.VISIBLE);
				leftFoot1.setVisibility(View.VISIBLE);
				rightFoot1.setVisibility(View.VISIBLE);
			} else {

			}
			lineIndex++;
			addLineIndex = true;
		}
	}
}

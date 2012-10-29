package jp.eclipcebook;

import java.util.List;

import android.widget.ImageView;

public class StringCommandExecutor implements Runnable {

	/**** フィールド ****/
	private ImageView image;
	private List<String> expandedCommands;
	private int lineIndex;

	/**** コンストラクタ ****/
	public StringCommandExecutor(ImageView image, List<String> stringArray) {
		this.image = image;
		this.expandedCommands = stringArray;
		this.lineIndex = 0;
	}

	@Override
	public void run() {
		if (expandedCommands.get(lineIndex).indexOf("右足を上げる") != -1) {
			Animation.rightFootUpAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("右足を下げる") != -1) {
			Animation.rightFootDownAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("右腕を上げる") != -1) {
			if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1) {
				Animation.bothHandUpAnimation(image);
			} else {
				Animation.rightHandUpAnimation(image);
			}
		} else if (expandedCommands.get(lineIndex).indexOf("右腕を下げる") != -1) {
			if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1) {
				Animation.bothHandDownAnimation(image);
			} else {
				Animation.rightHandDownAnimation(image);
			}
		} else if (expandedCommands.get(lineIndex).indexOf("左足を上げる") != -1) {
			Animation.leftFootUpAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("左足を下げる") != -1) {
			Animation.leftFootDownAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("左腕を上げる") != -1) {
			Animation.leftHandUpAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("左腕を下げる") != -1) {
			Animation.leftHandDownAnimation(image);
		} else if (expandedCommands.get(lineIndex).indexOf("ジャンプする") != -1) {
			Animation.jumpAnimation(image);
		} else {
			Animation.basicAnimation(image);
		}
		lineIndex++;
	}
}

package jp.eclipcebook;

import android.content.Context;

public class InterconversionStringAndImage {
	
//	public InterconversionStringAndImage() {
//		
//	}
	
	String convertStringToImage(String text1, ImageInEdit mImageEdit, Context context) {
		//文字から絵文字への変換
		//text1がtab1のテキストデータを取得し、絵文字に変換したデータを返す
//		text1 = text1.replaceAll("左腕を上げる", mImageEdit.insertResourceImage(context, R.drawable.icon_left_hand_up));
//		text1 = text1.replaceAll("左腕を下げる", mImageEdit.insertResourceImage(context, R.drawable.icon_left_hand_down));
//		text1 = text1.replaceAll("右腕を上げる", mImageEdit.insertResourceImage(context(), R.drawable.icon_right_hand_up));
//		text1 = text1.replaceAll("右腕を下げる", mImageEdit.insertResourceImage(context(), R.drawable.icon_right_hand_down));
//		text1 = text1.replaceAll("左足を上げる", mImageEdit.insertResourceImage(context(), R.drawable.icon_left_foot_up));
//		text1 = text1.replaceAll("左足を下げる", mImageEdit.insertResourceImage(context(), R.drawable.icon_left_foot_down));
//		text1 = text1.replaceAll("右足を上げる", mImageEdit.insertResourceImage(context(), R.drawable.icon_right_foot_up));
//		text1 = text1.replaceAll("右足を下げる", mImageEdit.insertResourceImage(context(), R.drawable.icon_right_foot_down));
//		text1 = text1.replaceAll("ジャンプする", mImageEdit.insertResourceImage(context(), R.drawable.icon_jump));
//		text1 = text1.replaceAll("loop", mImageEdit.insertResourceImage(context(), R.drawable.icon_loop));
//		text1 = text1.replaceAll("ここまで", mImageEdit.insertResourceImage(context(), R.drawable.icon_kokomade));
		return text1;
	}
	
	String convertImageToString(String text2) {
		//絵文字から文字への変換
		//text2がtab2のテキストデータを取得し、文字に変換したデータを返す
		return text2;
	}
	
}

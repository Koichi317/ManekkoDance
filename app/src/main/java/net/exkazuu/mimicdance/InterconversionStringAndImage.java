package net.exkazuu.mimicdance;

import android.content.Context;

import java.util.Map.Entry;
import java.util.TreeMap;

public class InterconversionStringAndImage {

    // public InterconversionStringAndImage() {
    //
    // }

    String convertStringToImage(String text1, ImageInEdit mImageEdit,
                                Context context) {
        String[] commands = new String[]{"左腕を上げる", "左腕を下げる"/* .. */};

        for (String line : commands /* fix it */) {
            TreeMap<Integer, String> map = new TreeMap<Integer, String>();
            for (String command : commands) {
                int i = -1;
                while ((i = line.indexOf(command, i + 1)) >= 0) {
                    map.put(i, command);
                }
            }

            for (Entry<Integer, String> indexAndStr : map.entrySet()) {
                String command = indexAndStr.getValue();

                if (command.equals("左腕を上げる")) {

                } else if (command.equals("左腕を下げる")) {

                } else if (command.equals("右腕を上げる")) {

                }
                // ...
            }

        }

        // 文字から絵文字への変換
        // text1がtab1のテキストデータを取得し、絵文字に変換したデータを返す
        // text1 = text1.replaceAll("左腕を上げる",
        // mImageEdit.insertResourceImage(context,
        // R.drawable.icon_left_hand_up));
        // text1 = text1.replaceAll("左腕を下げる",
        // mImageEdit.insertResourceImage(context,
        // R.drawable.icon_left_hand_down));
        // text1 = text1.replaceAll("右腕を上げる",
        // mImageEdit.insertResourceImage(context(),
        // R.drawable.icon_right_hand_up));
        // text1 = text1.replaceAll("右腕を下げる",
        // mImageEdit.insertResourceImage(context(),
        // R.drawable.icon_right_hand_down));
        // text1 = text1.replaceAll("左足を上げる",
        // mImageEdit.insertResourceImage(context(),
        // R.drawable.icon_left_foot_up));
        // text1 = text1.replaceAll("左足を下げる",
        // mImageEdit.insertResourceImage(context(),
        // R.drawable.icon_left_foot_down));
        // text1 = text1.replaceAll("右足を上げる",
        // mImageEdit.insertResourceImage(context(),
        // R.drawable.icon_right_foot_up));
        // text1 = text1.replaceAll("右足を下げる",
        // mImageEdit.insertResourceImage(context(),
        // R.drawable.icon_right_foot_down));
        // text1 = text1.replaceAll("ジャンプする",
        // mImageEdit.insertResourceImage(context(), R.drawable.icon_jump));
        // text1 = text1.replaceAll("loop",
        // mImageEdit.insertResourceImage(context(), R.drawable.icon_loop));
        // text1 = text1.replaceAll("ここまで",
        // mImageEdit.insertResourceImage(context(), R.drawable.icon_kokomade));
        return text1;
    }

    String convertImageToString(String text2) {
        // 絵文字から文字への変換
        // text2がtab2のテキストデータを取得し、文字に変換したデータを返す
        return text2;
    }

}

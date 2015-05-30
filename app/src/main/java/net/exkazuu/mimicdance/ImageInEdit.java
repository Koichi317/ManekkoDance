package net.exkazuu.mimicdance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * EditText内に画像を配置できるカスタムEditText
 */
public class ImageInEdit extends EditText {

    /**
     * テキストサイズ
     */
    private int mTextSize;

    /**
     * コンストラクタ
     *
     * @param context Context
     */
    public ImageInEdit(Context context) {
        super(context);
    }

    /**
     * コンストラクタ
     *
     * @param context Context
     * @param attrs   属性
     */
    public ImageInEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * コンストラクタ
     *
     * @param context  Context
     * @param attrs    属性
     * @param defStyle スタイル
     */
    public ImageInEdit(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mTextSize = 3 * (int) getTextSize();

    }

    /**
     * リソースIDから画像を挿入
     *
     * @param context Context
     * @param id      リソースID
     */
    public void insertResourceImage(Context context, int id) {
        Drawable drawable = context.getResources().getDrawable(id);
        insertImage(drawable);
    }

    /**
     * assets内の画像を挿入
     *
     * @param context Context
     * @param path    assets内パス
     */
    public void insertAssetsImage(Context context, String path) {
        try {
            Drawable drawable = Drawable.createFromStream(context.getAssets()
                .open(path), null);
            insertImage(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Drawabaleから画像を挿入
     *
     * @param drawable Drawable
     */
    public void insertImage(final Drawable drawable) {

        ImageGetter imageGetter = new ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                drawable.setBounds(0, 0, mTextSize, mTextSize);
                return drawable;
            }
        };

        String img = "<img src=\"" + drawable.toString() + "\" />";

        Spanned spanned = Html.fromHtml(img, imageGetter, null);

        int start = this.getSelectionStart();
        int end = this.getSelectionEnd();

        this.getText().replace(Math.min(start, end), Math.max(start, end),
            spanned, 0, spanned.length());
    }

    public void replaceTextToImage(final IconContainer icon) {

        String[] commands = new String[]{"左腕を上げる", "左腕を下げる", "右腕を上げる",
            "右腕を下げる", "くりかえし", "ここまで", "もしも", "もしくは", "もしおわり", "黄色", "茶色"};

        // String[] commands = new String[] { "左腕を上げる", "左腕を下げる", "右腕を上げる",
        // "右腕を下げる", "左足を上げる", "左足を下げる", "右足を上げる", "右足を下げる", "ジャンプする",
        // "くりかえし", "ここまで", "もしも", "もしくは", "もしおわり", "黄色", "茶色" };

        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
        ArrayList<Integer> start = new ArrayList<Integer>(); // プログラム的に
        ArrayList<Integer> end = new ArrayList<Integer>();

        for (String command : commands) {
            // commandの位置を探す
            int i = -1;
            while ((i = this.getText().toString().indexOf(command, i + 1)) >= 0) {
                // ImageInEditから文字を読み取り、commandの位置を探す
                map.put(i * -1, command);
                start.add(i);
                end.add(i + command.length());
            }
        }

        Collections.sort(start);
        Collections.sort(end);

        int i = start.size() - 1;
        for (Entry<Integer, String> indexAndStr : map.entrySet()) {
            String command = indexAndStr.getValue();

            if (command.equals("左腕を上げる")) {
                setIcon(icon.getIconLeftHandUp(), start.get(i), end.get(i));
            } else if (command.equals("左腕を下げる")) {
                setIcon(icon.getIconLeftHandDown(), start.get(i), end.get(i));
            } else if (command.equals("右腕を上げる")) {
                setIcon(icon.getIconRightHandUp(), start.get(i), end.get(i));
            } else if (command.equals("右腕を下げる")) {
                setIcon(icon.getIconRightHandDown(), start.get(i), end.get(i));
            } else if (command.equals("左足を上げる")) {
                setIcon(icon.getIconLeftFootUp(), start.get(i), end.get(i));
            } else if (command.equals("左足を下げる")) {
                setIcon(icon.getIconLeftFootDown(), start.get(i), end.get(i));
            } else if (command.equals("右足を上げる")) {
                setIcon(icon.getIconRightFootUp(), start.get(i), end.get(i));
            } else if (command.equals("右足を下げる")) {
                setIcon(icon.getIconRightFootDown(), start.get(i), end.get(i));
            } else if (command.equals("ジャンプする")) {
                setIcon(icon.getIconJump(), start.get(i), end.get(i));
            } else if (command.equals("くりかえし")) {
                setIcon(icon.getIconLoop(), start.get(i), end.get(i));
            } else if (command.equals("ここまで")) {
                setIcon(icon.getIconLoopEnd(), start.get(i), end.get(i));
            } else if (command.equals("もしも")) {
                setIcon(icon.getIconIf(), start.get(i), end.get(i));
            } else if (command.equals("もしくは")) {
                setIcon(icon.getIconElse(), start.get(i), end.get(i));
            } else if (command.equals("もしおわり")) {
                setIcon(icon.getIconIfEnd(), start.get(i), end.get(i));
            } else if (command.equals("黄色")) {
                setIcon(icon.getIconYellow(), start.get(i), end.get(i));
            } else if (command.equals("茶色")) {
                setIcon(icon.getIconOrange(), start.get(i), end.get(i));
            }
            // Log.v("tag", start.get(i).toString());
            // Log.v("tag", end.get(i).toString());
            i--;
        }

    }

    private void setIcon(final Drawable drawable, int start, int end) {
        ImageGetter imageGetter = new ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                drawable.setBounds(0, 0, mTextSize, mTextSize);
                return drawable;
            }
        };
        String img = "<img src=\"" + drawable.toString() + "\" />";
        Spanned spanned = Html.fromHtml(img, imageGetter, null);
        this.getText().replace(start, end, spanned, 0, spanned.length());
    }

    public String getTextFromImage(IconContainer iconContainer) {
        final Editable text = this.getText();
        List<ImageSpan> spanList = Arrays.asList(text.getSpans(0,
            text.length(), ImageSpan.class));
        ArrayList<ImageSpan> spans = new ArrayList<ImageSpan>(spanList);
        Collections.sort(spans, new Comparator<ImageSpan>() {
            @Override
            public int compare(ImageSpan s1, ImageSpan s2) {
                return text.getSpanStart(s2) - text.getSpanStart(s1);
            }
        });
        for (ImageSpan span : spans) {
            String iconText = iconContainer.getStringFromIcon(span
                .getDrawable());
            text.replace(text.getSpanStart(span), text.getSpanEnd(span),
                iconText, 0, iconText.length());
        }
        return text.toString();
    }

}

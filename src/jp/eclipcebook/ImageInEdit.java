package jp.eclipcebook;

import java.io.IOException;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * EditText���ɉ摜��z�u�ł���J�X�^��EditText
 *
 */
public class ImageInEdit extends EditText {

	/** �e�L�X�g�T�C�Y */
	private int mTextSize;

	/**
	 * �R���X�g���N�^
	 * @param context Context
	 */
	public ImageInEdit(Context context) {
		super(context);
	}

	/**
	 * �R���X�g���N�^
	 * @param context Context
	 * @param attrs ����
	 */
	public ImageInEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * �R���X�g���N�^
	 * @param context Context
	 * @param attrs ����
	 * @param defStyle �X�^�C��
	 */
	public ImageInEdit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		mTextSize = 2*(int) getTextSize();

	}

	/**
	 * ���\�[�XID����摜��}��
	 *
	 * @param context Context
	 * @param id ���\�[�XID
	 */
	public void insertResourceImage(Context context, int id) {
		Drawable drawable = context.getResources().getDrawable(id);
		insertImage(drawable);
	}

	/**
	 * assets���̉摜��}��
	 *
	 * @param context Context
	 * @param path assets���p�X
	 */
	public void insertAssetsImage(Context context, String path) {
		try {
			Drawable drawable = Drawable.createFromStream(context.getAssets().open(path), null);
			insertImage(drawable);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Drawabale����摜��}��
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

        this.getText().replace(Math.min(start, end), Math.max(start, end), spanned, 0, spanned.length());

	}

}

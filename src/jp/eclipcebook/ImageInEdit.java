package jp.eclipcebook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * EditText���ɉ摜��z�u�ł���J�X�^��EditText
 */
public class ImageInEdit extends EditText {

	/** �e�L�X�g�T�C�Y */
	private int mTextSize;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param context
	 *            Context
	 */
	public ImageInEdit(Context context) {
		super(context);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param context
	 *            Context
	 * @param attrs
	 *            ����
	 */
	public ImageInEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param context
	 *            Context
	 * @param attrs
	 *            ����
	 * @param defStyle
	 *            �X�^�C��
	 */
	public ImageInEdit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		mTextSize = 3 * (int) getTextSize();

	}

	/**
	 * ���\�[�XID����摜��}��
	 * 
	 * @param context
	 *            Context
	 * @param id
	 *            ���\�[�XID
	 */
	public void insertResourceImage(Context context, int id) {
		Drawable drawable = context.getResources().getDrawable(id);
		insertImage(drawable);
	}

	/**
	 * assets���̉摜��}��
	 * 
	 * @param context
	 *            Context
	 * @param path
	 *            assets���p�X
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
	 * @param drawable
	 *            Drawable
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

		this.getText().replace(Math.min(start, end), Math.max(start, end), spanned, 0,
				spanned.length());

	}
	

	public void replaceTextToImage(final iconContainer icon) {

		String[] commands = new String[] { "���r���グ��", "���r��������", "�E�r���グ��", "�E�r��������", "�������グ��",
				"������������", "�E�����グ��", "�E����������", "loop", "�����܂�" };

		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		ArrayList<Integer> start = new ArrayList<Integer>(); // �v���O�����I��
		ArrayList<Integer> end = new ArrayList<Integer>();
		
		for (String command : commands) {
			// command�̈ʒu��T��
			int i = -1;
			while ((i = this.getText().toString().indexOf(command, i + 1)) >= 0) {
				//ImageInEdit���當����ǂݎ��Acommand�̈ʒu��T��
				map.put(i, command);
				start.add(i);
				end.add(i);
			}
		}

		Collections.sort(start);
		Collections.sort(end);

		
		for (Entry<Integer, String> indexAndStr : map.entrySet()) {
			String command = indexAndStr.getValue();
			int i = 0;
			
			if (command.equals("���r���グ��")) {
				setIcon(icon.getIconLeftHandUp(), start.get(i), end.get(i));
			} else if (command.equals("���r��������")) {
				setIcon(icon.getIconLeftHandDown(), start.get(i), end.get(i));
			} else if (command.equals("�E�r���グ��")) {
				setIcon(icon.getIconRightHandUp(), start.get(i), end.get(i));
			} else if (command.equals("�E�r��������")) {
				setIcon(icon.getIconRightHandDown(), start.get(i), end.get(i));
			} else if (command.equals("�������グ��")) {
				setIcon(icon.getIconLeftFootUp(), start.get(i), end.get(i));
			} else if (command.equals("������������")) {
				setIcon(icon.getIconLeftFootDown(), start.get(i), end.get(i));
			} else if (command.equals("�E�����グ��")) {
				setIcon(icon.getIconRightFootUp(), start.get(i), end.get(i));
			} else if (command.equals("�E����������")) {
				setIcon(icon.getIconRightFootDown(), start.get(i), end.get(i));
			} else if (command.equals("�W�����v����")) {
				setIcon(icon.getIconJump(), start.get(i), end.get(i));
			} else if (command.equals("loop")) {
				setIcon(icon.getIconLoop(), start.get(i), end.get(i));
			} else if (command.equals("�����܂�")) {
				setIcon(icon.getIconKokomade(), start.get(i), end.get(i));
			}
			i++;
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

}

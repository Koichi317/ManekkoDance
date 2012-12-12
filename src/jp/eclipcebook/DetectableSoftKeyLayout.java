package jp.eclipcebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class DetectableSoftKeyLayout extends LinearLayout {

	public DetectableSoftKeyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public interface OnSoftKeyShownListener {
		public void onSoftKeyShown(boolean isShown);
	}

	private OnSoftKeyShownListener listener;

	public void setListener(OnSoftKeyShownListener listener) {
		this.listener = listener;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// (a)View�̍���
		int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		// (b)�X�e�[�^�X�o�[�̍���
		Activity activity = (Activity) getContext();
		Rect rect = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int statusBarHeight = rect.top;
		// (c)�f�B�X�v���C�T�C�Y
		int screenHeight = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		// (a)-(b)-(c)>100�s�N�Z���ƂȂ�����\�t�g�L�[�{�[�h���\������Ă�Ɣ��f
		//�i�\�t�g�L�[�{�[�h�͂ǂ�Ȃ��̂ł��Œ�100�s�N�Z������Ɖ���j
		int diff = (screenHeight - statusBarHeight) - viewHeight;
		if (listener != null) {
			listener.onSoftKeyShown(diff > 100);
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
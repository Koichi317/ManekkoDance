package net.exkazuu.ManekkoDance;

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
		// (a)Viewの高さ
		int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		// (b)ステータスバーの高さ
		if (getContext() instanceof Activity) {
			Activity activity = (Activity) getContext();
			Rect rect = new Rect();
			activity.getWindow().getDecorView()
					.getWindowVisibleDisplayFrame(rect);
			int statusBarHeight = rect.top;
			// (c)ディスプレイサイズ
			int screenHeight = activity.getWindowManager().getDefaultDisplay()
					.getHeight();
			// (a)-(b)-(c)>100ピクセルとなったらソフトキーボードが表示されてると判断
			// （ソフトキーボードはどんなものでも最低100ピクセルあると仮定）
			int diff = (screenHeight - statusBarHeight) - viewHeight;
			if (listener != null) {
				listener.onSoftKeyShown(diff > 100);
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
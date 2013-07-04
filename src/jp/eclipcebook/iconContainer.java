package jp.eclipcebook;

import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class IconContainer {

	private Drawable iconLeftHandUp;
	private Drawable iconLeftHandDown;
	private Drawable iconRightHandUp;
	private Drawable iconRightHandDown;
	private Drawable iconLeftFootUp;
	private Drawable iconLeftFootDown;
	private Drawable iconRightFootUp;
	private Drawable iconRightFootDown;
	private Drawable iconJump;
	private Drawable iconLoop;
	private Drawable iconKokomade;
	private HashMap<Drawable, String> icon2Strings;

	public IconContainer(Context context) {
		this.iconLeftHandUp = context.getResources().getDrawable(R.drawable.icon_left_hand_up);
		Log.v("", "ctr: width: " + iconLeftHandUp.getBounds().width());
		this.iconLeftHandDown = context.getResources().getDrawable(R.drawable.icon_left_hand_down);
		this.iconRightHandUp = context.getResources().getDrawable(R.drawable.icon_right_hand_up);
		this.iconRightHandDown = context.getResources().getDrawable(R.drawable.icon_right_hand_down);
		this.iconLeftFootUp = context.getResources().getDrawable(R.drawable.icon_left_foot_up);
		this.iconLeftFootDown = context.getResources().getDrawable(R.drawable.icon_left_foot_down);
		this.iconRightFootUp = context.getResources().getDrawable(R.drawable.icon_right_foot_up);
		this.iconRightFootDown = context.getResources().getDrawable(R.drawable.icon_right_foot_down);
		this.iconJump = context.getResources().getDrawable(R.drawable.icon_jump);
		this.iconLoop = context.getResources().getDrawable(R.drawable.icon_loop);
		this.iconKokomade = context.getResources().getDrawable(R.drawable.icon_kokomade);
		this.icon2Strings = new HashMap<Drawable, String>();

		icon2Strings.put(iconLeftHandUp, "���r���グ��");
		icon2Strings.put(iconLeftHandDown, "���r��������");
		icon2Strings.put(iconRightHandUp, "�E�r���グ��");
		icon2Strings.put(iconRightHandDown, "�E�r��������");
		icon2Strings.put(iconLeftFootUp, "�������グ��");
		icon2Strings.put(iconLeftFootDown, "������������");
		icon2Strings.put(iconRightFootUp, "�E�����グ��");
		icon2Strings.put(iconRightFootDown, "�E����������");
		icon2Strings.put(iconJump, "�W�����v����");
		icon2Strings.put(iconLoop, "loop");
		icon2Strings.put(iconKokomade, "�����܂�");
		// ...
	}

	public void reload() {

	}

	public String getStringFromIcon(Drawable icon) {
		return icon2Strings.get(icon);
	}

	public Drawable getIconLeftHandUp() {
		Log.v("", "get: width: " + iconLeftHandUp.getBounds().width());
		return iconLeftHandUp;
	}

	public Drawable getIconLeftHandDown() {
		return iconLeftHandDown;
	}

	public Drawable getIconRightHandUp() {
		return iconRightHandUp;
	}

	public Drawable getIconRightHandDown() {
		return iconRightHandDown;
	}

	public Drawable getIconLeftFootUp() {
		return iconLeftFootUp;
	}

	public Drawable getIconLeftFootDown() {
		return iconLeftFootDown;
	}

	public Drawable getIconRightFootUp() {
		return iconRightFootUp;
	}

	public Drawable getIconRightFootDown() {
		return iconRightFootDown;
	}

	public Drawable getIconJump() {
		return iconJump;
	}

	public Drawable getIconLoop() {
		return iconLoop;
	}

	public Drawable getIconKokomade() {
		return iconKokomade;
	}

}

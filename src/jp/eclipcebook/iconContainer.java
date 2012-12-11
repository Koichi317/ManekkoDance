package jp.eclipcebook;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class iconContainer {
	
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
	
	public iconContainer(Context context) {
		this.iconLeftHandUp = context.getResources().getDrawable(R.drawable.icon_left_hand_up);
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
	}
	
	public Drawable getIconLeftHandUp() {
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

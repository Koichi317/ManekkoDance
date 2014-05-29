package net.exkazuu.ManekkoDance;

import android.widget.ImageView;

public class ImageContainer {
	private ImageView leftHand;
	private ImageView rightHand;
	private ImageView basic;
	private ImageView leftFoot;
	private ImageView rightFoot;

	public ImageContainer(ImageView leftHand, ImageView rightHand,
			ImageView basic, ImageView leftFoot, ImageView rightFoot) {
		this.leftHand = leftHand;
		this.rightHand = rightHand;
		this.basic = basic;
		this.leftFoot = leftFoot;
		this.rightFoot = rightFoot;
	}

	public ImageView getLeftHand() {
		return leftHand;
	}

	public ImageView getRightHand() {
		return rightHand;
	}

	public ImageView getBasic() {
		return basic;
	}

	public ImageView getLeftFoot() {
		return leftFoot;
	}

	public ImageView getRightFoot() {
		return rightFoot;
	}

}

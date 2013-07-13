package net.exkazuu.ManekkoDance;

import android.widget.ImageView;

public class ImageContainer {
	private ImageView leftHand1;
	private ImageView rightHand1;
	private ImageView basic;
	private ImageView leftFoot1;
	private ImageView rightFoot1;

	public ImageContainer(ImageView leftHand1, ImageView rightHand1,
			ImageView basic, ImageView leftFoot1, ImageView rightFoot1) {
		this.leftHand1 = leftHand1;
		this.rightHand1 = rightHand1;
		this.basic = basic;
		this.leftFoot1 = leftFoot1;
		this.rightFoot1 = rightFoot1;
	}

	public ImageView getLeftHand1() {
		return leftHand1;
	}

	public ImageView getRightHand1() {
		return rightHand1;
	}

	public ImageView getBasic() {
		return basic;
	}

	public ImageView getLeftFoot1() {
		return leftFoot1;
	}

	public ImageView getRightFoot1() {
		return rightFoot1;
	}

}

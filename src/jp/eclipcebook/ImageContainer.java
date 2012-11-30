package jp.eclipcebook;

import android.widget.ImageView;

public class ImageContainer {
	private ImageView leftHand1;
	private ImageView leftHand2;
	private ImageView leftHand3;
	private ImageView rightHand1;
	private ImageView rightHand2;
	private ImageView rightHand3;
	private ImageView basic;
	private ImageView leftFoot1;
	private ImageView leftFoot2;
	private ImageView leftFoot3;
	private ImageView rightFoot1;
	private ImageView rightFoot2;
	private ImageView rightFoot3;

	public ImageContainer(ImageView leftHand1, ImageView leftHand2, ImageView leftHand3,
			ImageView rightHand1, ImageView rightHand2, ImageView rightHand3, ImageView basic,
			ImageView leftFoot1, ImageView leftFoot2, ImageView leftFoot3, ImageView rightFoot1,
			ImageView rightFoot2, ImageView rightFoot3) {
		this.leftHand1 = leftHand1;
		this.leftHand2 = leftHand2;
		this.leftHand3 = leftHand3;
		this.rightHand1 = rightHand1;
		this.rightHand2 = rightHand2;
		this.rightHand3 = rightHand3;
		this.basic = basic;
		this.leftFoot1 = leftFoot1;
		this.leftFoot2 = leftFoot2;
		this.leftFoot3 = leftFoot3;
		this.rightFoot1 = rightFoot1;
		this.rightFoot2 = rightFoot2;
		this.rightFoot3 = rightFoot3;
	}

	public ImageView getLeftHand1() {
		return leftHand1;
	}

	public ImageView getLeftHand2() {
		return leftHand2;
	}

	public ImageView getLeftHand3() {
		return leftHand3;
	}

	public ImageView getRightHand1() {
		return rightHand1;
	}

	public ImageView getRightHand2() {
		return rightHand2;
	}

	public ImageView getRightHand3() {
		return rightHand3;
	}

	public ImageView getBasic() {
		return basic;
	}

	public ImageView getLeftFoot1() {
		return leftFoot1;
	}

	public ImageView getLeftFoot2() {
		return leftFoot2;
	}

	public ImageView getLeftFoot3() {
		return leftFoot3;
	}

	public ImageView getRightFoot1() {
		return rightFoot1;
	}

	public ImageView getRightFoot2() {
		return rightFoot2;
	}

	public ImageView getRightFoot3() {
		return rightFoot3;
	}

}

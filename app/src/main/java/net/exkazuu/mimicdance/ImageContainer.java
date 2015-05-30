package net.exkazuu.mimicdance;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class ImageContainer {
    private ImageView leftHand;
    private ImageView rightHand;
    private ImageView basic;
    private ImageView leftFoot;
    private ImageView rightFoot;

    private ImageContainer(ImageView leftHand, ImageView rightHand,
                           ImageView basic, ImageView leftFoot, ImageView rightFoot) {
        this.leftHand = leftHand;
        this.rightHand = rightHand;
        this.basic = basic;
        this.leftFoot = leftFoot;
        this.rightFoot = rightFoot;
    }

    public static ImageContainer createPiyoLeft(Activity activity) {
        ImageView leftHand = (ImageView) activity.findViewById(R.id.playerLeftHand1);
        ImageView rightHand = (ImageView) activity.findViewById(R.id.playerRightHand1);
        ImageView basic = (ImageView) activity.findViewById(R.id.playerBasic1);
        ImageView leftFoot = (ImageView) activity.findViewById(R.id.playerLeftFoot1);
        ImageView rightFoot = (ImageView) activity.findViewById(R.id.playerRightFoot1);
        leftHand.setImageResource(R.drawable.piyo_left_hand_up1);
        rightHand.setImageResource(R.drawable.piyo_right_hand_up1);
        basic.setImageResource(R.drawable.piyo_basic);
        leftFoot.setImageResource(R.drawable.piyo_left_foot_up1);
        rightFoot.setImageResource(R.drawable.piyo_right_foot_up1);
        leftHand.setVisibility(View.VISIBLE);
        rightHand.setVisibility(View.VISIBLE);
        leftFoot.setVisibility(View.VISIBLE);
        rightFoot.setVisibility(View.VISIBLE);
        return new ImageContainer(leftHand, rightHand, basic, leftFoot, rightFoot);
    }

    public static ImageContainer createPiyoRight(Activity activity) {
        ImageView leftHand = (ImageView) activity.findViewById(R.id.playerLeftHand2);
        ImageView rightHand = (ImageView) activity.findViewById(R.id.playerRightHand2);
        ImageView basic = (ImageView) activity.findViewById(R.id.playerBasic2);
        ImageView leftFoot = (ImageView) activity.findViewById(R.id.playerLeftFoot2);
        ImageView rightFoot = (ImageView) activity.findViewById(R.id.playerRightFoot2);
        leftHand.setImageResource(R.drawable.alt_piyo_left_hand_up1);
        rightHand.setImageResource(R.drawable.alt_piyo_right_hand_up1);
        basic.setImageResource(R.drawable.alt_piyo_basic);
        leftFoot.setImageResource(R.drawable.alt_piyo_left_foot_up1);
        rightFoot.setImageResource(R.drawable.alt_piyo_right_foot_up1);
        leftHand.setVisibility(View.VISIBLE);
        rightHand.setVisibility(View.VISIBLE);
        leftFoot.setVisibility(View.VISIBLE);
        rightFoot.setVisibility(View.VISIBLE);
        return new ImageContainer(leftHand, rightHand, basic, leftFoot, rightFoot);
    }

    public static ImageContainer createCoccoLeft(Activity activity) {
        ImageView leftHand = (ImageView) activity.findViewById(R.id.partnerLeftHand1);
        ImageView rightHand = (ImageView) activity.findViewById(R.id.partnerRightHand1);
        ImageView basic = (ImageView) activity.findViewById(R.id.partnerBasic1);
        ImageView leftFoot = (ImageView) activity.findViewById(R.id.partnerLeftFoot1);
        ImageView rightFoot = (ImageView) activity.findViewById(R.id.partnerRightFoot1);
        leftHand.setImageResource(R.drawable.cocco_left_hand_up1);
        rightHand.setImageResource(R.drawable.cocco_right_hand_up1);
        basic.setImageResource(R.drawable.cocco_basic);
        leftFoot.setImageResource(R.drawable.cocco_left_foot_up1);
        rightFoot.setImageResource(R.drawable.cocco_right_foot_up1);
        leftHand.setVisibility(View.VISIBLE);
        rightHand.setVisibility(View.VISIBLE);
        leftFoot.setVisibility(View.VISIBLE);
        rightFoot.setVisibility(View.VISIBLE);
        return new ImageContainer(leftHand, rightHand, basic, leftFoot, rightFoot);
    }

    public static ImageContainer createCoccoRight(Activity activity) {
        ImageView leftHand = (ImageView) activity.findViewById(R.id.partnerLeftHand2);
        ImageView rightHand = (ImageView) activity.findViewById(R.id.partnerRightHand2);
        ImageView basic = (ImageView) activity.findViewById(R.id.partnerBasic2);
        ImageView leftFoot = (ImageView) activity.findViewById(R.id.partnerLeftFoot2);
        ImageView rightFoot = (ImageView) activity.findViewById(R.id.partnerRightFoot2);
        leftHand.setImageResource(R.drawable.alt_cocco_left_hand_up1);
        rightHand.setImageResource(R.drawable.alt_cocco_right_hand_up1);
        basic.setImageResource(R.drawable.alt_cocco_basic);
        leftFoot.setImageResource(R.drawable.alt_cocco_left_foot_up1);
        rightFoot.setImageResource(R.drawable.alt_cocco_right_foot_up1);
        leftHand.setVisibility(View.VISIBLE);
        rightHand.setVisibility(View.VISIBLE);
        leftFoot.setVisibility(View.VISIBLE);
        rightFoot.setVisibility(View.VISIBLE);
        return new ImageContainer(leftHand, rightHand, basic, leftFoot, rightFoot);
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

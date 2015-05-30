package net.exkazuu.mimicdance;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.google.common.base.CaseFormat;

import net.exkazuu.mimicdance.command.ActionType;
import net.exkazuu.mimicdance.command.CharacterType;

import java.util.List;

public class CharacterImageViewSet {
    private final ImageView[] bodyParts;
    private final CharacterType charaType;
    private final int[][] firstImageIds;
    private final int[][] secondImageIds;

    private CharacterImageViewSet(ImageView leftHand, ImageView rightHand,
                                  ImageView mainBody, ImageView leftFoot, ImageView rightFoot, Context context, CharacterType charaType) {
        this.bodyParts = new ImageView[]{leftHand, rightHand, leftFoot, rightFoot, mainBody};
        this.charaType = charaType;

        ActionType[] actions = ActionType.values();
        CharacterType[] characters = CharacterType.values();
        firstImageIds = new int[actions.length][characters.length];
        secondImageIds = new int[actions.length][characters.length];
        for (int i = 0; i < actions.length; i++) {
            ActionType action = actions[i];
            for (int j = 0; j < characters.length; j++) {
                CharacterType chara = characters[j];
                String prefix = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, chara.name()) + "_";
                String firstName = prefix + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, action.name()).replace("down", "up") + "2";
                String secondName = prefix + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, action.name())
                    .replace("up", "up3").replace("down", "up1").replace("jump", "basic");
                firstImageIds[i][j] = context.getResources().getIdentifier(firstName, "id", context.getPackageName());
                secondImageIds[i][j] = context.getResources().getIdentifier(secondName, "id", context.getPackageName());
            }
        }
    }

    public static CharacterImageViewSet createPiyoLeft(Activity activity) {
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
        return new CharacterImageViewSet(leftHand, rightHand, basic, leftFoot, rightFoot, activity, CharacterType.Piyo);
    }

    public static CharacterImageViewSet createPiyoRight(Activity activity) {
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
        return new CharacterImageViewSet(leftHand, rightHand, basic, leftFoot, rightFoot, activity, CharacterType.AltPiyo);
    }

    public static CharacterImageViewSet createCoccoLeft(Activity activity) {
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
        return new CharacterImageViewSet(leftHand, rightHand, basic, leftFoot, rightFoot, activity, CharacterType.Cocco);
    }

    public static CharacterImageViewSet createCoccoRight(Activity activity) {
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
        return new CharacterImageViewSet(leftHand, rightHand, basic, leftFoot, rightFoot, activity, CharacterType.AltCocco);
    }

    public void changeToMovingImages(List<ActionType> actions) {
        for (ActionType action : actions) {
            bodyParts[action.toBodyPart().ordinal()]
                .setImageResource(firstImageIds[action.ordinal()][charaType.ordinal()]);
        }
        if (actions.contains(ActionType.Jump)) {
            for (int i = 0; i < bodyParts.length - 1; i++) {
                bodyParts[i].setVisibility(View.INVISIBLE);
            }
        }
    }

    public void changeToMovedImages(List<ActionType> actions) {
        for (ActionType actionType : actions) {
            bodyParts[actionType.toBodyPart().ordinal()]
                .setImageResource(secondImageIds[actionType.ordinal()][charaType.ordinal()]);
        }
        if (actions.contains(ActionType.Jump)) {
            for (int i = 0; i < bodyParts.length - 1; i++) {
                bodyParts[i].setVisibility(View.VISIBLE);
            }
        }
    }

    public void changeToMovingErrorImage() {
        if (charaType == CharacterType.Piyo) {
            getMainBody().setImageResource(R.drawable.korobu_1);
        } else {
            getMainBody().setImageResource(R.drawable.alt_korobu_1);
        }
        for (int i = 0; i < bodyParts.length - 1; i++) {
            bodyParts[i].setVisibility(View.INVISIBLE);
        }
    }

    public void changeToMovedErrorImage() {
        if (charaType == CharacterType.Piyo) {
            getMainBody().setImageResource(R.drawable.korobu_3);
        } else {
            getMainBody().setImageResource(R.drawable.alt_korobu_3);
        }
    }

    public ImageView getLeftHand() {
        return bodyParts[0];
    }

    public ImageView getRightHand() {
        return bodyParts[1];
    }

    public ImageView getLeftFoot() {
        return bodyParts[2];
    }

    public ImageView getRightFoot() {
        return bodyParts[3];
    }

    public ImageView getMainBody() {
        return bodyParts[4];
    }
}

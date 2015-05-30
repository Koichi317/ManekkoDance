package net.exkazuu.mimicdance.command;

import java.util.ArrayList;
import java.util.List;

public enum ActionType {
    LeftHandDown("左腕を下げる"),
    LeftHandUp("左腕を上げる"),
    RightHandDown("右腕を下げる"),
    RightHandUp("右腕を上げる"),
    LeftFootDown("左足を下げる"),
    LeftFootUp("左足を上げる"),
    RightFootDown("右足を下げる"),
    RightFootUp("右足を上げる"),
    Jump("ジャンプする"),;

    public String text;

    ActionType(String text) {
        this.text = text;
    }

    public BodyPartType toBodyPart() {
        return BodyPartType.values()[ordinal() / 2];
    }

    @Override
    public String toString() {
        return text;
    }

    public boolean isUp() {
        return (ordinal() & 1) == 1;
    }

    public static List<ActionType> parse(String line) {
        List<ActionType> ret = new ArrayList<ActionType>();
        for (ActionType actionType : ActionType.values()) {
            if (line.contains(actionType.text)) {
                ret.add(actionType);
            }
        }
        return ret;
    }

    public static boolean validate(List<ActionType> actionTypes) {
        BodyPartType[] bodyPartTypes = BodyPartType.values();
        int[] part2count = new int[bodyPartTypes.length];
        for (ActionType actionType : actionTypes) {
            part2count[actionType.toBodyPart().ordinal()]++;
        }
        int handOrFootCount = 0;
        for (int i = 0; i < bodyPartTypes.length - 1; i++) {
            handOrFootCount += part2count[i];
            // A body part cannot move up and down simultaneously
            if (part2count[i] >= 2) { return false; }
        }
        // When jumping, another body part cannot move
        if (part2count[BodyPartType.Body.ordinal()] > 0 && handOrFootCount > 0) {
            return false;
        }
        return true;
    }
}

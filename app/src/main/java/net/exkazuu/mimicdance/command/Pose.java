package net.exkazuu.mimicdance.command;

import java.util.List;

public class Pose {
    private final boolean[] bodyPart2up;

    public Pose() {
        bodyPart2up = new boolean[4];
    }

    public boolean validate(List<ActionType> actions) {
        for (ActionType actionType : actions) {
            if (actionType == ActionType.Jump) {
                for (boolean up : bodyPart2up) {
                    if (up) {
                        return false;
                    }
                }
            } else if (actionType.isUp() == bodyPart2up[actionType.toBodyPart().ordinal()]) {
                return false;
            }
        }
        return true;
    }

    public void change(List<ActionType> actions) {
        for (ActionType actionType : actions) {
            if (actionType == ActionType.Jump) {
                continue;
            }
            bodyPart2up[actionType.toBodyPart().ordinal()] = actionType.isUp();
        }
    }

    public boolean isLeftHandUp() {
        return bodyPart2up[BodyPartType.LeftHand.ordinal()];
    }

    public boolean isRightHandUp() {
        return bodyPart2up[BodyPartType.RightHand.ordinal()];
    }

    public boolean isLeftFootUp() {
        return bodyPart2up[BodyPartType.LeftFoot.ordinal()];
    }

    public boolean isRightFootUp() {
        return bodyPart2up[BodyPartType.RightFoot.ordinal()];
    }
}

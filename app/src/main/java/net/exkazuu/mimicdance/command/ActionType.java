package net.exkazuu.mimicdance.command;

public enum ActionType {
    LeftHandUp("左腕を上げる"),
    LeftHandDown("左腕を下げる"),
    RightHandUp("右腕を上げる"),
    RightHandDown("右腕を下げる"),
    LeftLegUp("左足を上げる"),
    LeftLegDown("左足を下げる"),
    RightLegUp("右足を上げる"),
    RightLegDown("右足を下げる"),
    Jump("ジャンプする"),
    ;

    public String text;

    ActionType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

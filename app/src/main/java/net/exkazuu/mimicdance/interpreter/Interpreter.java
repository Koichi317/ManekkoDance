package net.exkazuu.mimicdance.interpreter;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import net.exkazuu.mimicdance.CharacterImageViewSet;
import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.program.UnrolledProgram;

import java.util.Collection;
import java.util.Set;

public class Interpreter implements Runnable {

    private final UnrolledProgram program;
    private final CharacterImageViewSet charaViewSet;
    private final CharacterType charaType;
    private final Pose pose;
    private TextView textView;
    private Context context;
    private MediaPlayer bgm;
    private Set<ActionType> actions;
    private int executionCount;
    private String bearCommand = "";
    private boolean failed;

    /**
     * * コンストラクタ ***
     */
    // お手本
    public Interpreter(CharacterImageViewSet charaViewSet, UnrolledProgram program,
                       boolean forStandard) {
        this.charaViewSet = charaViewSet;
        this.program = program;
        this.pose = new Pose();
        this.charaType = forStandard ? CharacterType.Cocco : CharacterType.AltCocco;
    }

    // プレイヤー
    public Interpreter(CharacterImageViewSet charaViewSet, UnrolledProgram program,
                       TextView textView, Context context, boolean forStandard) {
        this.context = context;
        this.charaViewSet = charaViewSet;
        this.program = program;
        this.pose = new Pose();
        this.textView = textView;
        this.charaType = forStandard ? CharacterType.Piyo : CharacterType.AltPiyo;
    }

    @Override
    public void run() {
        if (getLineIndex() >= program.size()) {
            return;
        }
        if (isMoving()) {
            if (charaType == CharacterType.Piyo || charaType == CharacterType.AltPiyo) {
                highlightLine();
            }

            actions = ActionType.parse(program.getLine(getLineIndex()));

            if (!failed && ActionType.validate(actions) && pose.validate(actions)) {
                pose.change(actions);
                charaViewSet.changeToMovingImages(actions);

                if (charaType == CharacterType.Piyo) {
                    handleDanbo();
                    handleBear(actions);
                }
            } else {
                failed = true;
                Log.v("tag", "failed");
                charaViewSet.changeToMovingErrorImage();
            }
        } else {
            if (!failed) {
                charaViewSet.changeToMovedImages(actions);
            } else {
                charaViewSet.changeToMovedErrorImage();
            }
        }
        executionCount++;
    }

    public boolean existsError() {
        return failed;
    }

    private int getLineIndex() {
        return executionCount / 2;
    }

    private boolean isMoving() {
        return (executionCount & 1) == 0;
    }

    private void highlightLine() {
        int currentLineIndex = program.getOriginalLineIndex(getLineIndex());
        String[] lines = textView.getText().toString().split("\n");
        textView.getEditableText().clear();
        for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
            if (currentLineIndex == lineIndex) {
                textView.append(
                    Html.fromHtml("<font color=#ff0000>" + lines[lineIndex] + "</font>"));
                textView.append("\n");
            } else {
                textView.append(lines[lineIndex] + "\n");
            }
        }
    }

    private void handleDanbo() {
        if (bgm != null) {
            bgm.stop();
        }

        if (pose.isLeftHandUp()) {
            if (pose.isRightHandUp()) {
                bgm = MediaPlayer.create(context, R.raw.danbo_luru);
            } else {
                bgm = MediaPlayer.create(context, R.raw.danbo_lu);
            }
        } else {
            if (pose.isRightHandUp()) {
                bgm = MediaPlayer.create(context, R.raw.danbo_ru);
            } else {
                bgm = MediaPlayer.create(context, R.raw.danbo_c);
            }
        }
        bgm.start();
    }

    public void handleBear(Collection<ActionType> actions) {
        if (actions.contains(ActionType.LeftHandDown)) {
            bearCommand += "lau";
        } else if (actions.contains(ActionType.LeftHandUp)) {
            bearCommand = bearCommand.replace("lau", "");
        }
        if (actions.contains(ActionType.RightHandDown)) {
            bearCommand += "rau";
        } else if (actions.contains(ActionType.RightHandUp)) {
            bearCommand = bearCommand.replace("rau", "");
        }
        if (actions.contains(ActionType.LeftFootDown)) {
            bearCommand += "llu";
        } else if (actions.contains(ActionType.LeftFootUp)) {
            bearCommand = bearCommand.replace("llu", "");
        }
        if (actions.contains(ActionType.RightFootDown)) {
            bearCommand += "rlu";
        } else if (actions.contains(ActionType.RightFootUp)) {
            bearCommand = bearCommand.replace("rlu", "");
        }
        if (actions.contains(ActionType.Jump)) {
            bearCommand += "jump";
        } else {
            bearCommand = bearCommand.replace("jump", "");
        }

        new BearHandlingTask(bearCommand).execute();
    }
}


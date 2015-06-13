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

    public static final int WAITING_COUNT = 2;
    private final UnrolledProgram program;
    private final CharacterImageViewSet charaViewSet;
    private final Context context;
    private final TextView textView;
    private final boolean isPiyo;
    private final Pose pose;

    private Set<ActionType> actions;
    private int executionCount;
    private boolean failed;
    private MediaPlayer playerForHandlingDanbo;
    private String bearCommand;

    public static Interpreter createForPiyo(UnrolledProgram program, CharacterImageViewSet charaViewSet, TextView textView, Context context) {
        return new Interpreter(program, charaViewSet, textView, context, true);
    }

    public static Interpreter createForCocco(UnrolledProgram program, CharacterImageViewSet charaViewSet) {
        return new Interpreter(program, charaViewSet, null, null, false);
    }

    private Interpreter(UnrolledProgram program, CharacterImageViewSet charaViewSet, TextView textView, Context context, boolean isPiyo) {
        this.program = program;
        this.charaViewSet = charaViewSet;
        this.textView = textView;
        this.context = context;
        this.isPiyo = isPiyo;
        this.pose = new Pose();
        this.bearCommand = "";
    }

    @Override
    public void run() {
        if (executionCount < WAITING_COUNT) {
            charaViewSet.changeToInitialImages();
        }
        else if (finished()) {
            return;
        }
        else if (isMoving()) {
            if (isPiyo) {
                highlightLine();
            }

            actions = program.getActionSet(getLineIndex());
            if (!failed && ActionType.validate(actions) && pose.validate(actions)) {
                pose.change(actions);
                charaViewSet.changeToMovingImages(actions);
                if (isPiyo) {
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

    public boolean finished() {
        return getLineIndex() >= program.size();
    }

    public boolean existsError() {
        return failed;
    }

    private int getLineIndex() {
        return (executionCount - WAITING_COUNT) / 2;
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
        if (playerForHandlingDanbo != null) {
            playerForHandlingDanbo.stop();
        }
        if (pose.isLeftHandUp()) {
            if (pose.isRightHandUp()) {
                playerForHandlingDanbo = MediaPlayer.create(context, R.raw.danbo_luru);
            } else {
                playerForHandlingDanbo = MediaPlayer.create(context, R.raw.danbo_lu);
            }
        } else {
            if (pose.isRightHandUp()) {
                playerForHandlingDanbo = MediaPlayer.create(context, R.raw.danbo_ru);
            } else {
                playerForHandlingDanbo = MediaPlayer.create(context, R.raw.danbo_c);
            }
        }
        playerForHandlingDanbo.start();
    }

    private void handleBear(Collection<ActionType> actions) {
        if (actions.contains(ActionType.LeftHandDown)) {
            bearCommand = bearCommand.replace("lau", "");
        } else if (actions.contains(ActionType.LeftHandUp)) {
            bearCommand += "lau";
        }
        if (actions.contains(ActionType.RightHandDown)) {
            bearCommand = bearCommand.replace("rau", "");
        } else if (actions.contains(ActionType.RightHandUp)) {
            bearCommand += "rau";
        }
        if (actions.contains(ActionType.LeftFootDown)) {
            bearCommand = bearCommand.replace("llu", "");
        } else if (actions.contains(ActionType.LeftFootUp)) {
            bearCommand += "llu";
        }
        if (actions.contains(ActionType.RightFootDown)) {
            bearCommand = bearCommand.replace("rlu", "");
        } else if (actions.contains(ActionType.RightFootUp)) {
            bearCommand += "rlu";
        }
        if (actions.contains(ActionType.Jump)) {
            bearCommand += "jump";
        } else {
            bearCommand = bearCommand.replace("jump", "");
        }
        new BearHandlingTask(bearCommand).execute();
    }
}


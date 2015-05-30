package net.exkazuu.mimicdance.command;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import net.exkazuu.mimicdance.CharacterImageViewSet;
import net.exkazuu.mimicdance.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringCommandExecutor implements Runnable {
    private String bearCommand = "";


    /**
     * * フィールド ***
     */
    private boolean failed;

    private List<String> expandedCommands;
    private int executionCount;
    private final CharacterImageViewSet images;
    private TextView textView;
    private List<Integer> playerNumberSorting;
    private final CharacterType charaType;
    private Pose pose;
    private Context context;
    private MediaPlayer bgm;
    private List<ActionType> actions;

    public boolean existsError() {
        return failed;
    }

    /**
     * * コンストラクタ ***
     */
    // お手本
    public StringCommandExecutor(CharacterImageViewSet images,
                                 List<String> stringArray, boolean isLeft) {
        this.images = images;
        this.expandedCommands = stringArray;
        pose = new Pose();
        charaType = isLeft ? CharacterType.Cocco : CharacterType.AltCocco;
        failed = false;
    }

    // プレイヤー
    public StringCommandExecutor(CharacterImageViewSet images,
                                 List<String> stringArray, TextView textView,
                                 List<Integer> playerNumberSorting, Context context, boolean isLeft) {
        this.context = context;
        this.images = images;
        this.expandedCommands = stringArray;
        pose = new Pose();
        this.textView = textView;
        this.playerNumberSorting = playerNumberSorting;
        charaType = isLeft ? CharacterType.Piyo : CharacterType.AltPiyo;
        failed = false;
    }

    @Override
    public void run() {
        if ((executionCount & 1) == 0) {
            if (charaType == CharacterType.Piyo || charaType == CharacterType.AltPiyo) {
                highlightLine();
            }

            actions = ActionType.parse(expandedCommands.get(executionCount / 2));

            // 無効な命令の並び（左腕を上げる&&左腕を下げる 等）
            if (!ActionType.validate(actions) || !pose.validate(actions)) {
                failed = true;
                Log.v("tag", "failed");
                images.changeToMovingErrorImage();
            } else {
                pose.change(actions);
                images.changeToMovingImages(actions);

                if (charaType == CharacterType.Piyo) {
                    handleDanbo();
                    commandBear(actions);
                }
            }
        } else {
            if (failed && (charaType == CharacterType.Piyo || charaType == CharacterType.AltPiyo)) {
                images.changeToMovedErrorImage();
            } else {
                images.changeToMovedImages(actions);
            }
        }
        executionCount++;
    }

    private void highlightLine() {
        // 実行中の文字列を赤くする
        int colorPosition = playerNumberSorting.get(executionCount / 2);
        String[] lines = textView.getText().toString().split("\n");
        textView.getEditableText().clear();
        for (int i = 0; i < lines.length; i++) {
            if (colorPosition == i) {
                textView.append(
                    Html.fromHtml("<font color=#ff0000>" + lines[i] + "</font>"));
                textView.append("\n");
            } else {
                textView.append(lines[i] + "\n");
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

    public void commandBear(List<ActionType> actions) {
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

        PostTask posttask = new PostTask(bearCommand);
        posttask.execute();
    }

}

class PostTask extends AsyncTask<Void, String, Boolean> {

    private String arg;

    PostTask(String arg) {
        this.arg = arg;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        boolean result = false;

        // All your code goes in here
        try {
            // URL指定
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://192.168.91.99:3000/form");
            // BODYに登録、設定
            ArrayList<NameValuePair> value = new ArrayList<NameValuePair>();
            value.add(new BasicNameValuePair("input1", arg));
            System.out.println("Send: " + arg);

            String body = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(value, "UTF-8"));
                // リクエスト送信
                HttpResponse response = client.execute(post);
                // 取得
                HttpEntity entity = response.getEntity();
                body = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                System.out.println(e);
            }
            client.getConnectionManager().shutdown();
        } catch (Exception e2) {
            System.out.println(e2);
        }
        // If you want to do something on the UI use progress update

        publishProgress("progress");
        return result;
    }

    protected void onProgressUpdate(String... progress) {
    }
}

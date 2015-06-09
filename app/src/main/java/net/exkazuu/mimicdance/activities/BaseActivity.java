package net.exkazuu.mimicdance.activities;

import android.app.Activity;
import android.content.Intent;

public abstract class BaseActivity extends Activity {

    public static final int REQUEST_CODE_FOR_CODING = 5000;
    public static final int REQUEST_CODE_FOR_COCCO = 5001;

    /**
     * 実行結果を取得できる形で判定画面に遷移します。
     *
     * @param lessonNumber
     * @param piyoCode
     */
    protected void startEvaluationActivityForResult(int lessonNumber, String piyoCode) {
        Intent intent = new Intent(this, EvaluationActivity.class);
        intent.putExtra("lessonNumber", lessonNumber);
        intent.putExtra("piyoCode", piyoCode);
        startActivity(intent);
    }

    /**
     * お手本画面に遷移します。
     *
     * @param lessonNumber
     */
    protected void startCoccoActivity(int lessonNumber, String piyoCode) {
        Intent intent = new Intent(this, CoccoActivity.class);
        intent.putExtra("lessonNumber", lessonNumber);
        intent.putExtra("piyoCode", piyoCode);
        startActivity(intent);
    }

    /**
     * ヘルプ画面に遷移します。
     */
    protected void startHelpActivity() {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }

    /**
     * タイトル画面に遷移します。
     */
    protected void startTitleActivity() {
        Intent intent = new Intent(this, TitleActivity.class);
        startActivity(intent);
    }

    /**
     * レッスン選択画面に遷移します。
     */
    protected void startLessonListActivity() {
        Intent intent = new Intent(this, LessonListActivity.class);
        startActivity(intent);
    }
}

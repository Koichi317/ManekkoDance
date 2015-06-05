package net.exkazuu.mimicdance.activities;

import android.app.Activity;
import android.content.Intent;

public abstract class BaseActivity extends Activity {
    /**
     * 実装画面に遷移します。
     *
     * @param lessonNumber
     * @param piyoCode
     */
    protected void startCodingActivity(int lessonNumber, String piyoCode) {
        Intent intent = new Intent(this, CodingActivity.class);
        intent.putExtra("lessonNumber", lessonNumber);
        intent.putExtra("piyoCode", piyoCode);
        startActivity(intent);
    }

    /**
     * 判定画面に遷移します。
     *
     * @param lessonNumber
     * @param piyoCode
     */
    protected void startEvaluationActivity(int lessonNumber, String piyoCode) {
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
    protected void startCoccoActivity(int lessonNumber) {
        Intent intent = new Intent(this, CoccoActivity.class);
        intent.putExtra("lessonNumber", lessonNumber);
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

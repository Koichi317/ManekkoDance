package net.exkazuu.mimicdance.activities;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.Lessons;


/**
 * Created by t-yokoi on 2015/06/12.
 */
public class CorrectAnswerActivity extends BaseActivity {
    AnimationDrawable anim1 = null;
    AnimationDrawable anim2 = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.correct_answer);
        final int lessonNumber = getIntent().getIntExtra("lessonno", -1);
        ImageView jumpCocco = (ImageView) findViewById(R.id.cocco);
        ImageView jumpPiyo = (ImageView) findViewById(R.id.piyo);

        jumpCoccoAnimation(this, jumpCocco);
        jumpPiyoAnimation(this, jumpPiyo);

        Button list = (Button) findViewById(R.id.correct_lesson_list);
        Button next = (Button) findViewById(R.id.next_lesson);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLessonListActivity(true);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCoccoActivity(Math.min(lessonNumber + 1, Lessons.getLessonCount()), "", true);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        anim1.stop();
        anim2.stop();
        finish();
    }

    // Frameアニメーションのテスト
    void jumpCoccoAnimation(Context con, View v) {
        anim1 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.cocco_jump1);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.cocco_jump2);

        // 画像をアニメーションのコマとして追加していく
        anim1.addFrame(frame1, 500);
        anim1.addFrame(frame2, 500);

        // 繰り返し設定
        anim1.setOneShot(false);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim1);

        // アニメーション開始
        anim1.start();
    }

    void jumpPiyoAnimation(Context con, View v) {
        anim2 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.piyo_jump1);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.piyo_jump2);

        // 画像をアニメーションのコマとして追加していく
        anim2.addFrame(frame1, 500);
        anim2.addFrame(frame2, 500);

        // 繰り返し設定
        anim2.setOneShot(false);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim2);

        // アニメーション開始
        anim2.start();
    }


}

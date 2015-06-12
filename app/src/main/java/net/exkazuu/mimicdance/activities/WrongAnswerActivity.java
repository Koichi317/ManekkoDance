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
public class WrongAnswerActivity extends BaseActivity {
    AnimationDrawable anim1 = null;
    AnimationDrawable anim2 = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.wrong_answer);
        final int lessonNumber = getIntent().getIntExtra("lessonno", -1);
        final String piyocode = getIntent().getStringExtra("piyocode");
        ImageView fallAltPiyo = (ImageView) findViewById(R.id.cocco);
        ImageView fallPiyo = (ImageView) findViewById(R.id.piyo);

        fallAltPiyoAnimation(this, fallPiyo);
        fallPiyoAnimation(this, fallAltPiyo);

        Button list = (Button) findViewById(R.id.wrong_lesson_list);
        Button again = (Button) findViewById(R.id.try_again);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLessonListActivity(true);
            }
        });
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startCodingActivity(lessonNumber, piyocode, true);
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
    void fallAltPiyoAnimation(Context con, View v) {
        anim1 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.alt_korobu_1);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.alt_korobu_2);
        Drawable frame3 = con.getResources().getDrawable(R.drawable.alt_korobu_3);

        // 画像をアニメーションのコマとして追加していく
        anim1.addFrame(frame1, 700);
        anim1.addFrame(frame2, 700);
        anim1.addFrame(frame3, 700);

        anim1.setOneShot(true);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim1);

        // アニメーション開始
        anim1.start();
    }

    void fallPiyoAnimation(Context con, View v) {
        anim2 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.korobu_1);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.korobu_2);
        Drawable frame3 = con.getResources().getDrawable(R.drawable.korobu_3);

        // 画像をアニメーションのコマとして追加していく
        anim2.addFrame(frame1, 700);
        anim2.addFrame(frame2, 700);
        anim2.addFrame(frame3, 700);

        anim2.setOneShot(true);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim2);

        // アニメーション開始
        anim2.start();
    }


}

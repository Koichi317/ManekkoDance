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
    AnimationDrawable anim3 = null;
    AnimationDrawable anim4 = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.wrong_answer);
        final int lessonNumber = getIntent().getIntExtra("lessonno", -1);
        final String piyocode = getIntent().getStringExtra("piyocode");
        ImageView wrongAltPiyo = (ImageView) findViewById(R.id.cocco);
        ImageView wrongPiyo = (ImageView) findViewById(R.id.piyo);

        //転ぶ
        fallAltPiyoAnimation(this, wrongPiyo);
        fallPiyoAnimation(this, wrongAltPiyo);

        //起き上がる
//        standPiyoAnimation(this,wrongPiyo);
//        standAltPiyoAnimation(this,wrongAltPiyo);

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
//        anim3.stop();
//        anim4.stop();
        finish();
    }

    // オレンジひよこの転倒
    void fallAltPiyoAnimation(Context con, View v) {
        anim1 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.alt_korobu_1);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.alt_korobu_2);
        Drawable frame3 = con.getResources().getDrawable(R.drawable.alt_korobu_3);

        // 画像をアニメーションのコマとして追加していく
        anim1.addFrame(frame1, 1000);
        anim1.addFrame(frame2, 700);
        anim1.addFrame(frame3, 700);

        anim1.setOneShot(true);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim1);

        // アニメーション開始
        anim1.start();
    }

    //黄色ひよこの転倒
    void fallPiyoAnimation(Context con, View v) {
        anim2 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.korobu_1);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.korobu_2);
        Drawable frame3 = con.getResources().getDrawable(R.drawable.korobu_3);

        // 画像をアニメーションのコマとして追加していく
        anim2.addFrame(frame1, 1000);
        anim2.addFrame(frame2, 700);
        anim2.addFrame(frame3, 700);

        anim2.setOneShot(true);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim2);

        // アニメーション開始
        anim2.start();
    }

    //オレンジひよこの立ち上がり
    void standAltPiyoAnimation(Context con, View v) {
        anim3 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.alt_korobu_3);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.alt_korobu_1);
        Drawable frame3 = con.getResources().getDrawable(R.drawable.alt_piyo_stand);
        Drawable frame4 = con.getResources().getDrawable(R.drawable.alt_piyo_raising_hand);

        // 画像をアニメーションのコマとして追加していく
        anim3.addFrame(frame1, 1500);
        anim3.addFrame(frame2, 700);
        anim3.addFrame(frame3, 700);
        anim3.addFrame(frame4, 700);

        anim3.setOneShot(true);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim3);

        // アニメーション開始
        anim3.start();
    }

    //黄色ひよこの立ち上がり
    void standPiyoAnimation(Context con, View v) {
        anim4 = new AnimationDrawable();

        // 画像の読み込み //
        Drawable frame1 = con.getResources().getDrawable(R.drawable.korobu_3);
        Drawable frame2 = con.getResources().getDrawable(R.drawable.korobu_1);
        Drawable frame3 = con.getResources().getDrawable(R.drawable.piyo_stand);
        Drawable frame4 = con.getResources().getDrawable(R.drawable.piyo_raising_hand);

        // 画像をアニメーションのコマとして追加していく
        anim4.addFrame(frame1, 1500);
        anim4.addFrame(frame2, 700);
        anim4.addFrame(frame3, 700);
        anim4.addFrame(frame4, 700);

        anim4.setOneShot(true);

        // ビューの背景画像にアニメーションを設定
        v.setBackgroundDrawable(anim4);

        // アニメーション開始
        anim4.start();
    }
}

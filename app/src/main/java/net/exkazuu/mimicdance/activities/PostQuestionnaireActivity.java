package net.exkazuu.mimicdance.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.PostQuestionnaireResult;

/**
 * Created by t-yokoi on 2015/07/31.
 */
public class PostQuestionnaireActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.post_quetionnaire);

    }

    public void save() {
        PostQuestionnaireResult result = new PostQuestionnaireResult();
        EditText id = (EditText) findViewById(R.id.id);
        result.id = id.getText().toString();
        EditText opinion = (EditText) findViewById(R.id.opinion);
        SeekBar gladness = (SeekBar) findViewById(R.id.gladness);
        SeekBar vexation = (SeekBar) findViewById(R.id.vexation);
        SeekBar desireToPlay = (SeekBar) findViewById(R.id.desire_to_play);
        SeekBar desireToStudy = (SeekBar) findViewById(R.id.desire_to_study);
        SeekBar fun = (SeekBar) findViewById(R.id.fun);
        SeekBar feasibility = (SeekBar) findViewById(R.id.feasibility);
        SeekBar usefulness = (SeekBar) findViewById(R.id.usefulness);
        RadioGroup time = (RadioGroup) findViewById(R.id.time);

        result.save();
        startTitleActivity(true);

    }


    public void startTitleActivity(View view) {
        startTitleActivity(true);
    }
}

package net.exkazuu.mimicdance.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import net.exkazuu.mimicdance.R;
import net.exkazuu.mimicdance.models.PreQuestionnaireResult;

/**
 * Created by t-yokoi on 2015/07/31.
 */
public class PreQuestionnaireActivity extends BaseActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.pre_questionnaire);

    }

    public void save() {
        PreQuestionnaireResult result = new PreQuestionnaireResult();
        EditText id = (EditText) findViewById(R.id.id);
        result.id = id.getText().toString();
        EditText age = (EditText) findViewById(R.id.age);
        // TODO
        SeekBar interest = (SeekBar) findViewById(R.id.interest);
        // TODO
        SeekBar enjoyable = (SeekBar) findViewById(R.id.fun);
        // TODO
        SeekBar dekisou = (SeekBar) findViewById(R.id.feasibility);
        // TODO
        SeekBar useful = (SeekBar) findViewById(R.id.usefulness);
        // TODO
        RadioGroup sex = (RadioGroup) findViewById(R.id.sex);
        // TODO
        RadioGroup knowProg = (RadioGroup) findViewById(R.id.knowledge_of_programming);
        // TODO
        RadioGroup knowMimic = (RadioGroup) findViewById(R.id.knowledge_of_mimicdance);
        // TODO
        result.save();
        startTitleActivity(true);
    }

    public void startTitleActivity(View view) {
        startTitleActivity(true);
    }

}

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
        EditText examineeId = (EditText) findViewById(R.id.examineeId);
        result.examineeId = examineeId.getText().toString();
        EditText age = (EditText) findViewById(R.id.age);
        SeekBar interest = (SeekBar) findViewById(R.id.interest);

        SeekBar fun = (SeekBar) findViewById(R.id.fun);
        SeekBar feasibility = (SeekBar) findViewById(R.id.feasibility);
        SeekBar useful = (SeekBar) findViewById(R.id.usefulness);
        RadioGroup sex = (RadioGroup) findViewById(R.id.sex);
        RadioGroup knowledgeOfProgramming = (RadioGroup) findViewById(R.id.knowledge_of_programming);
        RadioGroup knowledgeOfMimicDance = (RadioGroup) findViewById(R.id.knowledge_of_mimicdance);

        result.save();
        startTitleActivity(true);
    }

    public void startTitleActivity(View view) {
        startTitleActivity(true);
    }

}

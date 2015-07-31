package net.exkazuu.mimicdance.activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import net.exkazuu.mimicdance.R;

/**
 * Created by t-yokoi on 2015/07/31.
 */
public class PreQuestionnaireActivity extends BaseActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // タイトルバー非表示
        setContentView(R.layout.pre_questionnaire);

        EditText id = (EditText) findViewById(R.id.id);
        EditText age = (EditText) findViewById(R.id.age);
        SeekBar interest = (SeekBar) findViewById(R.id.interest);
        SeekBar fun = (SeekBar) findViewById(R.id.fun);
        SeekBar feasibility = (SeekBar) findViewById(R.id.feasibility);
        SeekBar useful = (SeekBar) findViewById(R.id.usefulness);
        RadioGroup sex = (RadioGroup) findViewById(R.id.sex);
        RadioGroup knowledgeOfProgramming = (RadioGroup) findViewById(R.id.know_programming);
        RadioGroup knowledgeOfMimicDance = (RadioGroup) findViewById(R.id.know_mimic);


        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPreQuestionnaireValue();
            }
        });
    }

    public void getPreQuestionnaireValue() {

    }

    public void startTitleActivity(View view) {
        startTitleActivity(false);
    }

}

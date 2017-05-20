package com.quiz.android.animalsquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int QUIZ_LENGTH = 5;

    private boolean[] results = new boolean[QUIZ_LENGTH];
    private int quiz_stage;
    private TextView[] stageTextViews = new TextView[QUIZ_LENGTH];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillStageTextViews();
        resetStageTextViews();
    }

    public void executeRestart(View view) {
        results = new boolean[QUIZ_LENGTH];
        quiz_stage = 0;
        resetStageTextViews();
    }

    public void executeSubmit(View view) {
        if (quiz_stage < QUIZ_LENGTH) {
            boolean isAnswerCorrect = checkAnswer();
            results[quiz_stage] = isAnswerCorrect;
            stageTextViews[quiz_stage].setBackgroundResource(isAnswerCorrect ?
                    R.color.colorPositive : R.color.colorNegative);

            quiz_stage += 1;

            Toast.makeText(getApplicationContext(), prepareToastText(), Toast.LENGTH_SHORT).show();
        }

        if (quiz_stage < QUIZ_LENGTH) {
            stageTextViews[quiz_stage].setBackgroundResource(R.color.colorAccent);
        } else {
            Toast.makeText(getApplicationContext(), prepareToastText(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillStageTextViews() {
        stageTextViews[0] = (TextView) findViewById(R.id.quiz_stage_1);
        stageTextViews[1] = (TextView) findViewById(R.id.quiz_stage_2);
        stageTextViews[2] = (TextView) findViewById(R.id.quiz_stage_3);
        stageTextViews[3] = (TextView) findViewById(R.id.quiz_stage_4);
        stageTextViews[4] = (TextView) findViewById(R.id.quiz_stage_5);
    }

    private void resetStageTextViews() {
        for (TextView stageTextView : stageTextViews) {
            stageTextView.setBackgroundResource(R.color.colorNeutral);
        }

        findViewById(R.id.quiz_stage_1).setBackgroundResource(R.color.colorAccent);
    }

    private boolean checkAnswer() {
        return true;
    }

    private String prepareToastText() {
        String toastText = quiz_stage == QUIZ_LENGTH ? getString(R.string.quiz_completed) : "";
        int result = countResults();

        if (result == quiz_stage) {
            toastText += getString(R.string.result_perfect);
        } else if (result == 0) {
            toastText += getString(R.string.result_sad);
        } else {
            toastText += getString(R.string.result_not_perfect) + result + "/" + quiz_stage;
        }

        return toastText;
    }

    private int countResults() {
        int count = 0;

        for (boolean result : results) {
            if (result) count++;
        }

        return count;
    }
}

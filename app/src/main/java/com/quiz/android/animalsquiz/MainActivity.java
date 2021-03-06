package com.quiz.android.animalsquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.android.animalsquiz.model.Animal;
import com.quiz.android.animalsquiz.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static final int QUIZ_LENGTH = 5;
    private static final int OPTIONS_COUNT = 4;

    private final List<Question> questions = new ArrayList<>();
    private final List<Boolean> results = new ArrayList<>();
    private final List<Animal> possibleAnswers = new ArrayList<>();
    private Question question;
    private int quiz_stage;

    private final TextView[] stageTextViews = new TextView[QUIZ_LENGTH];

    private TextView questionTitleText;
    private TextView questionOptionalText;

    private final LinearLayout[] optionLayouts = new LinearLayout[OPTIONS_COUNT];
    private TextView optionATitle;
    private final TextView[] optionTextViews = new TextView[OPTIONS_COUNT];
    private final ImageView[] optionImageViews = new ImageView[OPTIONS_COUNT];

    private RadioGroup answerRadioGroup;
    private final RadioButton[] answerRadioButtons = new RadioButton[OPTIONS_COUNT];
    private LinearLayout answerCheckBoxGroup;
    private final CheckBox[] answerCheckBoxes = new CheckBox[OPTIONS_COUNT];
    private EditText answerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillViews();
        executeRestart(null);
    }

    public void executeRestart(View view) {
        results.clear();
        quiz_stage = 0;
        resetStageTextViews();
        prepareQuestions();
        askQuestion();
    }

    public void executeSubmit(View view) {
        if (!checkIfAnswerProvided()) {
            Toast.makeText(this, getString(R.string.please_provide_answer), Toast.LENGTH_SHORT).show();
            return;
        }

        if (quiz_stage < QUIZ_LENGTH) {
            boolean isAnswerCorrect = checkAnswer();
            results.add(isAnswerCorrect);
            stageTextViews[quiz_stage].setBackgroundResource(isAnswerCorrect ?
                    R.color.colorPositive : R.color.colorNegative);
            quiz_stage += 1;
            Toast.makeText(this, prepareToastText(), Toast.LENGTH_SHORT).show();
        }

        if (quiz_stage < QUIZ_LENGTH) {
            stageTextViews[quiz_stage].setBackgroundResource(R.color.colorAccent);
            askQuestion();
        } else {
            Toast.makeText(this, prepareToastText(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEditTextQuizStage() {
        return quiz_stage % 3 == 0;
    }

    private void fillViews() {
        stageTextViews[0] = (TextView) findViewById(R.id.quiz_stage_1);
        stageTextViews[1] = (TextView) findViewById(R.id.quiz_stage_2);
        stageTextViews[2] = (TextView) findViewById(R.id.quiz_stage_3);
        stageTextViews[3] = (TextView) findViewById(R.id.quiz_stage_4);
        stageTextViews[4] = (TextView) findViewById(R.id.quiz_stage_5);

        questionTitleText = (TextView) findViewById(R.id.question_title_text);
        questionOptionalText = (TextView) findViewById(R.id.question_optional_text);

        optionLayouts[0] = (LinearLayout) findViewById(R.id.option_A_layout);
        optionLayouts[1] = (LinearLayout) findViewById(R.id.option_B_layout);
        optionLayouts[2] = (LinearLayout) findViewById(R.id.option_C_layout);
        optionLayouts[3] = (LinearLayout) findViewById(R.id.option_D_layout);

        optionATitle = (TextView) findViewById(R.id.option_A_title);

        optionTextViews[0] = (TextView) findViewById(R.id.option_A_text);
        optionTextViews[1] = (TextView) findViewById(R.id.option_B_text);
        optionTextViews[2] = (TextView) findViewById(R.id.option_C_text);
        optionTextViews[3] = (TextView) findViewById(R.id.option_D_text);

        optionImageViews[0] = (ImageView) findViewById(R.id.option_A_image);
        optionImageViews[1] = (ImageView) findViewById(R.id.option_B_image);
        optionImageViews[2] = (ImageView) findViewById(R.id.option_C_image);
        optionImageViews[3] = (ImageView) findViewById(R.id.option_D_image);

        answerRadioGroup = (RadioGroup) findViewById(R.id.answer_radio_group);
        answerRadioButtons[0] = (RadioButton) findViewById(R.id.answer_radio_A);
        answerRadioButtons[1] = (RadioButton) findViewById(R.id.answer_radio_B);
        answerRadioButtons[2] = (RadioButton) findViewById(R.id.answer_radio_C);
        answerRadioButtons[3] = (RadioButton) findViewById(R.id.answer_radio_D);

        answerCheckBoxGroup = (LinearLayout) findViewById(R.id.answer_checkbox_group);
        answerCheckBoxes[0] = (CheckBox) findViewById(R.id.answer_check_A);
        answerCheckBoxes[1] = (CheckBox) findViewById(R.id.answer_check_B);
        answerCheckBoxes[2] = (CheckBox) findViewById(R.id.answer_check_C);
        answerCheckBoxes[3] = (CheckBox) findViewById(R.id.answer_check_D);

        answerEditText = (EditText) findViewById(R.id.answer_edit_text);
    }

    private void resetStageTextViews() {
        for (TextView stageTextView : stageTextViews) {
            stageTextView.setBackgroundResource(R.color.colorNeutral);
        }

        findViewById(R.id.quiz_stage_1).setBackgroundResource(R.color.colorAccent);
    }

    private void prepareQuestions() {
        questions.clear();
        questions.addAll(new ArrayList<>(Arrays.asList(Question.values())));
        Collections.shuffle(questions);
    }

    private void askQuestion() {
        prepareQuestionAndPossibleAnswers();
        hideViewsAndResetAnswers();

        if (isEditTextQuizStage()) {
            displayEditTextQuestion();
        } else if (question.answers.size() == 1) {
            addWrongToPossibleAnswers();
            displaySingleAnswerQuestion();
        } else {
            addWrongToPossibleAnswers();
            displayMultiAnswerQuestion();
        }
    }

    private void prepareQuestionAndPossibleAnswers() {
        question = questions.get(quiz_stage);
        possibleAnswers.clear();
        possibleAnswers.addAll(question.answers);
    }

    private void addWrongToPossibleAnswers() {
        List<Animal> animals = new ArrayList<>(Arrays.asList(Animal.values()));

        for (int optionIndex = 1; optionIndex < OPTIONS_COUNT; optionIndex++) {
            if (possibleAnswers.size() <= optionIndex) {
                for (Animal animal : animals) {
                    if (!possibleAnswers.contains(animal)) {
                        possibleAnswers.add(animal);
                        break;
                    }
                }
            }

            Collections.shuffle(possibleAnswers);
        }
    }

    private void hideViewsAndResetAnswers() {
        questionOptionalText.setVisibility(GONE);
        optionATitle.setVisibility(GONE);
        optionTextViews[0].setVisibility(GONE);

        optionLayouts[1].setVisibility(GONE);
        optionLayouts[2].setVisibility(GONE);
        optionLayouts[3].setVisibility(GONE);

        answerRadioGroup.setVisibility(GONE);
        answerRadioGroup.clearCheck();

        answerCheckBoxGroup.setVisibility(GONE);
        for (CheckBox checkBox : answerCheckBoxes) checkBox.setChecked(false);

        answerEditText.setVisibility(GONE);
        answerEditText.setText("");
    }

    private void displayEditTextQuestion() {
        questionTitleText.setText(getString(R.string.please_enter_the_animal_title));
        optionImageViews[0].setImageResource(question.answers.get(0).iconResId);
        answerEditText.setVisibility(VISIBLE);
    }

    private void displaySingleAnswerQuestion() {
        prepareViewsForSelectionQuestion();
        answerRadioGroup.setVisibility(VISIBLE);
    }

    private void displayMultiAnswerQuestion() {
        prepareViewsForSelectionQuestion();
        answerCheckBoxGroup.setVisibility(VISIBLE);
    }

    private void prepareViewsForSelectionQuestion() {
        questionTitleText.setText(getString(R.string.please_select_the));
        questionOptionalText.setVisibility(VISIBLE);
        questionOptionalText.setText(getString(question.textResId));

        optionATitle.setVisibility(VISIBLE);
        optionTextViews[0].setVisibility(VISIBLE);

        optionLayouts[1].setVisibility(VISIBLE);
        optionLayouts[2].setVisibility(VISIBLE);
        optionLayouts[3].setVisibility(VISIBLE);

        for (int index = 0; index < OPTIONS_COUNT; index++) {
            optionTextViews[index].setText(getString(possibleAnswers.get(index).textResId));
            optionImageViews[index].setImageResource(possibleAnswers.get(index).iconResId);
        }
    }

    private boolean checkAnswer() {
        if (isEditTextQuizStage()) {
            return checkEditTextAnswer();
        } else if (question.answers.size() == 1) {
            return checkSingleAnswer();
        } else {
            return checkMultiAnswer();
        }
    }

    private boolean checkEditTextAnswer() {
        String rightAnimalTitle = getString(question.answers.get(0).textResId);
        String enteredAnimalTitle = answerEditText.getText().toString().trim();
        return rightAnimalTitle.equalsIgnoreCase(enteredAnimalTitle);
    }

    private boolean checkSingleAnswer() {
        for (int index = 0; index < OPTIONS_COUNT; index++) {
            if (answerRadioButtons[index].isChecked()) {
                return possibleAnswers.get(index).equals(question.answers.get(0));
            }
        }
        return false;
    }

    private boolean checkMultiAnswer() {
        int selectedCheckBoxesCount = 0;

        for (int index = 0; index < OPTIONS_COUNT; index++) {
            if (answerCheckBoxes[index].isChecked()) {
                if (!question.answers.contains(possibleAnswers.get(index))) {
                    return false;
                }

                selectedCheckBoxesCount++;
            }
        }

        return selectedCheckBoxesCount == question.answers.size();
    }

    private boolean checkIfAnswerProvided() {
        for (CheckBox checkBox : answerCheckBoxes) {
            if (checkBox.isChecked()) {
                return true;
            }
        }

        return answerRadioGroup.getCheckedRadioButtonId() != -1
                || answerEditText.getText().length() > 0;
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

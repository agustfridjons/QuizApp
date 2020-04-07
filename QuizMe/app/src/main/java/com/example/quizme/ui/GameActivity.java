package com.example.quizme.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.example.quizme.quizMe.Question;
import com.example.quizme.quizMe.QuestionDatabaseHelper;
import com.example.quizme.quizMe.UserDatabaseHelper;

import java.util.Stack;

public class GameActivity extends AppCompatActivity {

    QuestionDatabaseHelper db;

    private Button mButtonOne, mButtonTwo, mButtonThree, mButtonFour;
    private TextView mQuestion, mQuestionNumber, mUserAnswer, mPointsCounter;
    private String correctAnswer;
    private int numCorrectAnswers = 0;
    private int questionCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        db = new QuestionDatabaseHelper(this);

        mButtonOne = (Button) findViewById(R.id.button_one);
        mButtonTwo = (Button) findViewById(R.id.button_two);
        mButtonThree = (Button) findViewById(R.id.button_three);
        mButtonFour = (Button) findViewById(R.id.button_four);
        mQuestion = (TextView) findViewById(R.id.question);
        mQuestionNumber = (TextView) findViewById(R.id.question_number);
        mPointsCounter = (TextView) findViewById(R.id.points);

        mPointsCounter.setText(" 0");
        mQuestionNumber.setText(questionCounter + " / 7");
        // mUserAnswer = (EditText) findViewById(R.id.userAnswer); TODO fyrir hard mode

        // Get chosen category from DifficultyActivity (CategoryActivity)
        String category = getIntent().getStringExtra("Category");
        System.out.println("GameAct fær inn: " + category);

        // Get chosen difficulty from DifficultyActivity
        String difficulty = getIntent().getStringExtra("Difficulty");

        // Get a list of Question objects from the database helper

        Stack<Question> questions = db.getQuestions(category, difficulty);
        System.out.println("Lengd "+questions.size());
        Question currentQuestion = questions.pop();
        System.out.println("FYRSTA SPURNING ER: " + currentQuestion.getQuestion());


        String[] randomAnswers = randomizeAnswers(currentQuestion.getCorrectAnswer(),currentQuestion.getWrongAnswers());

        for (int i = 0; i < randomAnswers.length; i++) {
            System.out.println("Answer "+1+": " + randomAnswers[i]);
        }

        mQuestion.setText(currentQuestion.getQuestion());
        mButtonOne.setText(randomAnswers[0]);
        mButtonTwo.setText(randomAnswers[1]);
        mButtonThree.setText(randomAnswers[2]);
        mButtonFour.setText(randomAnswers[3]);
        correctAnswer = currentQuestion.getCorrectAnswer();

        // Answer from user
        View.OnClickListener event = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) findViewById(v.getId());
                System.out.println("correct"+ buttonClicked.getText()+"og"+ correctAnswer+".");
                questionCounter++;
                if(buttonClicked.getText().toString().trim().equals(correctAnswer.trim())){
                    Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    numCorrectAnswers++;
                    mPointsCounter.setText(" "+numCorrectAnswers);
                } else {
                    Toast.makeText(GameActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }

                if (questions.isEmpty()) {
                    mQuestionNumber.setText("Questions done");
                    // TODO fara a results síðu
                } else {
                    mQuestionNumber.setText("Question number " + questionCounter);
                    Question currentQuestion = questions.pop(); //TODO Gera meira random
                    String[] randomAnswers = randomizeAnswers(currentQuestion.getCorrectAnswer(),currentQuestion.getWrongAnswers());
                    mQuestion.setText(currentQuestion.getQuestion());
                    mButtonOne.setText(randomAnswers[0]);
                    mButtonTwo.setText(randomAnswers[1]);
                    mButtonThree.setText(randomAnswers[2]);
                    mButtonFour.setText(randomAnswers[3]);
                    correctAnswer = currentQuestion.getCorrectAnswer();
                }
            }
        };

        mButtonOne.setOnClickListener(event);
        mButtonTwo.setOnClickListener(event);
        mButtonThree.setOnClickListener(event);
        mButtonFour.setOnClickListener(event);

            // TODO fyrir hard mode
            /*if (guess == questions.get(0).getCorrectAnswer()) {
                Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
            }*/

    }

    private static String[] randomizeAnswers(String correctAnswer, String[] wrongAnswers){
        String[] Answers = {correctAnswer,wrongAnswers[0],wrongAnswers[1],wrongAnswers[2]};
        int i = 0;
        int x;
        int y;
        String temp;
        while(i < 10){
            x = (int)(Math.random()*4);
            y = (int)(Math.random()*4);
            if (x != y) {
                temp = Answers[x];
                Answers[x] = Answers[y];
                Answers[y] = temp;
            }
            i++;
        }
        return Answers;
    }

}
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameActivity extends AppCompatActivity {

    QuestionDatabaseHelper db;

    private Button mButtonOne, mButtonTwo;
    private TextView mQuestion, mUserAnswer, mPointsCounter;
    private String correctAnswer;
    private int numCorrectAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        db = new QuestionDatabaseHelper(this);

        mButtonOne = (Button) findViewById(R.id.button_one);
        mButtonTwo = (Button) findViewById(R.id.button_two);
        mQuestion = (TextView) findViewById(R.id.question);
        mPointsCounter = (TextView) findViewById(R.id.points);
        mPointsCounter.setText(" 0");
        // mUserAnswer = (EditText) findViewById(R.id.userAnswer); TODO fyrir hard mode

        // Get chosen category from CategoryActivity
        String category = getIntent().getStringExtra("Category");

        // TODO Tengja við difficulty activity

        // Get a list of Question objects from the database helper
        // TODO breyta í Question object
        //List<Question> questions = db.getQuestions(category, "Easy"); // TODO breyta í category
        Stack<Question> questions = db.getQuestions(category, "Easy");
        System.out.println("Lengd "+questions.size());
        Question currentQuestion = questions.pop();
        System.out.println("FYRSTA SPURNING ER: " + currentQuestion.getQuestion());

        mQuestion.setText(currentQuestion.getQuestion());
        mButtonOne.setText(currentQuestion.getCorrectAnswer());
        mButtonTwo.setText(currentQuestion.getWrongAnswer());
        correctAnswer = currentQuestion.getCorrectAnswer();


        // Answer from user
        mButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("correct"+ mButtonOne.getText()+"og"+ correctAnswer+".");
                if(mButtonOne.getText().toString().trim().equals(correctAnswer.trim())){
                    Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    numCorrectAnswers++;
                    mPointsCounter.setText(" "+numCorrectAnswers);
                }else{
                    Toast.makeText(GameActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                if (questions.isEmpty()) {
                    // TODO fara a results síðu
                } else {
                    Question currentQuestion = questions.pop(); //TODO Gera meira random
                    mQuestion.setText(currentQuestion.getQuestion());
                    mButtonOne.setText(currentQuestion.getCorrectAnswer());
                    mButtonTwo.setText(currentQuestion.getWrongAnswer());
                    correctAnswer = currentQuestion.getCorrectAnswer();
                }
            }
        });

        mButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("correct"+ mButtonTwo.getText()+"og"+ correctAnswer+".");
                if(mButtonTwo.getText().toString().trim().equals(correctAnswer.trim())){
                    Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                    numCorrectAnswers++;
                    mPointsCounter.setText(" "+numCorrectAnswers);
                }else{
                    Toast.makeText(GameActivity.this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                if (questions.isEmpty()) {
                    // TODO fara a results síðu
                } else {
                    Question currentQuestion = questions.pop(); //TODO Gera meira random
                    mQuestion.setText(currentQuestion.getQuestion());
                    mButtonOne.setText(currentQuestion.getCorrectAnswer());
                    mButtonTwo.setText(currentQuestion.getWrongAnswer());
                    correctAnswer = currentQuestion.getCorrectAnswer();
                }
            }
        });

            // TODO fyrir hard mode
            /*if (guess == questions.get(0).getCorrectAnswer()) {
                Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
            }*/

    }
}
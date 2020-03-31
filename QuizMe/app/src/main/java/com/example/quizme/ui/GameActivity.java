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

public class GameActivity extends AppCompatActivity {

    QuestionDatabaseHelper db;

    private Button mButtonOne, mButtonTwo;
    private TextView mQuestion, mUserAnswer;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        db = new QuestionDatabaseHelper(this);

        mButtonOne = (Button) findViewById(R.id.button_one);
        mButtonTwo = (Button) findViewById(R.id.button_two);
        mQuestion = (TextView) findViewById(R.id.question);
        // mUserAnswer = (EditText) findViewById(R.id.userAnswer); TODO fyrir hard mode

        // Get chosen category from DifficultyActivity (CategoryActivity)
        String category = getIntent().getStringExtra("Category");

        // Get chosen difficulty from DifficultyActivity
        String difficulty = getIntent().getStringExtra("Difficulty");
        
        // Get a list of Question objects from the database helper
        // TODO breyta í Question object
        //List<Question> questions = db.getQuestions(category, "Easy"); // TODO breyta í category
        String[][] questions = db.getQuestions(category, "Easy");
        System.out.println("FYRSTA SPURNING ER: " + questions[0][0]);

        mQuestion.setText(questions[counter][0]);
        mButtonOne.setText(questions[counter][1]);
        mButtonTwo.setText(questions[counter][2]);


        // Answer from user
        mButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
                counter++;
                if (counter >= questions.length) {
                    // TODO fara a results síðu
                } else {
                    mQuestion.setText(questions[counter][0]);
                    mButtonOne.setText(questions[counter][1]);
                    mButtonTwo.setText(questions[counter][2]);
                }
            }
        });

        mButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GameActivity.this, "Inorrect!", Toast.LENGTH_SHORT).show();
                counter++;
                if (counter >= questions.length) {
                    // TODO fara a results síðu
                } else {
                    mQuestion.setText(questions[counter][0]);
                    mButtonOne.setText(questions[counter][1]);
                    mButtonTwo.setText(questions[counter][2]);
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
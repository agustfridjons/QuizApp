package com.example.quizme.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.example.quizme.quizMe.GameResults;
import com.example.quizme.quizMe.GameResultsDatabaseHelper;

import java.util.ArrayList;

public class GameResultsActivity extends AppCompatActivity {

    GameResultsDatabaseHelper db;

    private TableLayout mGameResultsTable;
    private TextView tv, qty;
    private ImageButton addBtn, minusBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        init();
    }

    public void init() {
        db = new GameResultsDatabaseHelper(this);
        ArrayList<GameResults> gameresults = db.getGameResults(1);

        TableLayout mGameResultsTable = (TableLayout) findViewById(R.id.gameresults_table);
        mGameResultsTable.setBackgroundColor(Color.WHITE);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("Question nr.");
        tv0.setTextColor(Color.BLACK);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("Correct/Inccorect");
        tv1.setTextColor(Color.BLACK);
        tbrow0.addView(tv1);
        TextView tv3 = new TextView(this);
        tv3.setText(" Correct Answer ");
        tv3.setTextColor(Color.BLACK);
        tbrow0.addView(tv3);
        ((TableLayout) mGameResultsTable).addView(tbrow0);
        for (int i = 0; i<gameresults.size(); i++){
            String category = gameresults.get(i).getCategory();
            Integer userAnswer = gameresults.get(i).getUserAnswers();

            String correctAnswer = gameresults.get(i).getCorrectAnswer();

            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i) ;
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            if (userAnswer == 0){
                t2v.setText("Correct");
                tbrow.setBackgroundColor(Color.GREEN);

            } else if (userAnswer == 1){
                t2v.setText("Incorrect");
                tbrow.setBackgroundColor(Color.RED);

            }
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t4v = new TextView(this);
            t4v.setText(correctAnswer);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            ((TableLayout) mGameResultsTable).addView(tbrow);
        }

    }
}

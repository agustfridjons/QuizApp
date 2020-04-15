package com.example.quizme.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.example.quizme.quizMe.GameResults;
import com.example.quizme.quizMe.GameResultsDatabaseHelper;
import com.example.quizme.quizMe.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class GameResultsActivity extends AppCompatActivity {

    GameResultsDatabaseHelper db;

    private TableLayout mGameResultsTable;
    private TextView tv, qty;
    private ImageButton addBtn, minusBtn;
    private SessionManager mSession;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        mSession = new SessionManager(GameResultsActivity.this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(GameResultsActivity.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_friends:
                        Intent b = new Intent(GameResultsActivity.this, FriendListActivity.class);
                        startActivity(b);
                        break;
                    case R.id.nav_results:
                        Intent c = new Intent(GameResultsActivity.this, GameResultsActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });

        init();
    }


    public void init() {

        db = new GameResultsDatabaseHelper(this);
        Integer score = 0;
        ArrayList<String> games = new ArrayList<String>();
        String username = mSession.getSession();

        ArrayList<String> gameId = db.getGameId(username);
        for(int o = 0; o<gameId.size(); o++) {
            ArrayList<GameResults> gameresults = db.getGameResults(gameId.get(o));
            String category = gameresults.get(o).getCategory();
            String categoryResult = "Category: " + category;
            TableLayout mGameResultsTable = (TableLayout) findViewById(R.id.gameresults_table);

            TableRow trCategory = new TableRow(this);
            TextView tvCategory = new TextView(this);
            tvCategory.setText(categoryResult);
            tvCategory.setTextColor(Color.BLACK);
            trCategory.addView(tvCategory);
            tvCategory.setPadding(0,40,3,3);
            ((TableLayout) mGameResultsTable).addView(trCategory);

            TableRow tbrow0 = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("Question nr.");
            tv0.setTextColor(Color.BLACK);
            tv0.setPadding(0,10,3,3);
            tbrow0.addView(tv0);

            TextView tv3 = new TextView(this);
            tv3.setText(" Correct Answer ");
            tv3.setTextColor(Color.BLACK);
            tbrow0.addView(tv3);
            ((TableLayout) mGameResultsTable).addView(tbrow0);

            for (int i = 0; i < 7; i++) {
                Integer userAnswer = gameresults.get(i).getUserAnswers();
                String correctAnswer = gameresults.get(i).getCorrectAnswer();
                TableRow tbrow = new TableRow(this);
                if (userAnswer == 1) {
                    tbrow.setBackgroundColor(Color.parseColor("#99FF99"));
                    score++;
                } else if (userAnswer == 0) {
                    tbrow.setBackgroundColor(Color.parseColor("#FF9999"));

                }
                TextView t1v = new TextView(this);
                t1v.setText("" + i);
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);

                TextView t4v = new TextView(this);
                t4v.setText(correctAnswer);
                t4v.setTextColor(Color.BLACK);
                t4v.setGravity(Gravity.LEFT);
                if( i == 6){
                    //tbrow.setPadding(0,0,0, 200);
                }
                tbrow.addView(t4v);
                ((TableLayout) mGameResultsTable).addView(tbrow);

            }
            String scoreString = "Score " + score.toString() + "/7";
            TableRow tbrowScore = new TableRow(this);
            TextView tvScore = new TextView(this);
            tvScore.setGravity(Gravity.CENTER);
            tvScore.setText(scoreString);
            tvScore.setTextColor(Color.BLACK);
            tbrowScore.addView(tvScore);
            ((TableLayout) mGameResultsTable).addView(tbrowScore);
            score = 0;
        }

        }
    }


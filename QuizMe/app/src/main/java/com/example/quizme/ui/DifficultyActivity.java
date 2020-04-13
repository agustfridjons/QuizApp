package com.example.quizme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DifficultyActivity extends AppCompatActivity {

    private Button mEasy, mMedium, mHard;

    private String difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        // Get chosen category from CategoryActivity
        String category = getIntent().getStringExtra("Category");

        mEasy = (Button) findViewById(R.id.easy);
        mMedium = (Button) findViewById(R.id.medium);
        mHard = (Button) findViewById(R.id.hard);


        mEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "Easy";
                Intent easyIntent = new Intent(DifficultyActivity.this, GameActivity.class);
                easyIntent.putExtra("Difficulty", difficulty);
                easyIntent.putExtra("Category", category);
                startActivity(easyIntent);
            }
        });

        mMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "Medium";
                Intent mediumIntent = new Intent(DifficultyActivity.this, GameActivity.class);
                mediumIntent.putExtra("Difficulty", difficulty);
                mediumIntent.putExtra("Category", category);
                startActivity(mediumIntent);
            }
        });

        mHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "Hard";
                Intent hardIntent = new Intent(DifficultyActivity.this, GameActivity.class);
                hardIntent.putExtra("Difficulty", difficulty);
                hardIntent.putExtra("Category", category);
                startActivity(hardIntent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(DifficultyActivity.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_friends:
                        Intent b = new Intent(DifficultyActivity.this, FriendListActivity.class);
                        startActivity(b);
                        break;
                    case R.id.nav_results:
                        Intent c = new Intent(DifficultyActivity.this, GameResultsActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
    }
}

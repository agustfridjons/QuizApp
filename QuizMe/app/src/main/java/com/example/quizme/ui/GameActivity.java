package com.example.quizme.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.example.quizme.quizMe.QuestionDatabaseHelper;

public class GameActivity extends AppCompatActivity {

    QuestionDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Tengja við category/difficulty activity

        // Þetta ætti að skila lista af spurningum
        db.getQuestions("Animal", "Easy");

    }
}
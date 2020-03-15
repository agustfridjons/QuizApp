package com.example.quizme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizme.R;

public class MainActivity extends AppCompatActivity {

    private Button mNewGame, mGameResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewGame = (Button) findViewById(R.id.newgame_button);
        mGameResults = (Button) findViewById(R.id.gameresults_button);

    }
}

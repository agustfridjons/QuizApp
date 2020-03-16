package com.example.quizme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.quizme.R;

public class MainActivity extends AppCompatActivity {

    private Button mNewGame, mGameResults, mViewFriendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewGame = (Button) findViewById(R.id.newgame_button);
        mGameResults = (Button) findViewById(R.id.gameresults_button);
        mViewFriendList = (Button) findViewById(R.id.friendlist_button);

        mNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(categoryIntent);
            }
        });

    }
}

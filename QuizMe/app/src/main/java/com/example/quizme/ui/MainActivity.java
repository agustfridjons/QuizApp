package com.example.quizme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizme.R;

public class MainActivity extends AppCompatActivity {

    private Button mNewGame, mGameResults, mViewFriendList;
    private TextView mLoginText, mSignUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewGame = (Button) findViewById(R.id.newgame_button);
        mGameResults = (Button) findViewById(R.id.gameresults_button);
        mViewFriendList = (Button) findViewById(R.id.friendlist_button);
        mLoginText = (TextView) findViewById(R.id.login_text);
        mSignUpText = (TextView) findViewById(R.id.signup_text);

        // Get username from LoginActivity
        String username = getIntent().getStringExtra("Username");

        // Play a new game
        mNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(categoryIntent);
            }
        });

        // View your friend list
        mViewFriendList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendListIntent = new Intent(MainActivity.this, FriendListActivity.class);
                startActivity(friendListIntent);
            }
        });

        // Go to login screen
        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        // Go to sign up screen
        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        mGameResults.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent gameResultsIntent = new Intent(MainActivity.this, GameResultsActivity.class);
                startActivity(gameResultsIntent);
            }
        });
    }
}

package com.example.quizme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.quizme.quizMe.SessionManager;

public class MainActivity extends AppCompatActivity {

    private Button mNewGame, mGameResults, mViewFriendList;
    private TextView mLoginText, mSignUpText;
    private SessionManager mSession;



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
        String username = getIntent().getStringExtra("Username"); //nota session i staðin

        mSession = new SessionManager(MainActivity.this);

        if(mSession.getSession() != null){
            mLoginText.setText("Log Out");
            mSignUpText.setVisibility(View.GONE);
        }


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

        // Go to login screen or logout
        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginText.getText().equals("Log Out")) {
                    mSession.removeSession();
                    mLoginText.setText("Log in");
                    mSignUpText.setVisibility(View.VISIBLE);
                } else {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_friends:
                        Intent b = new Intent(MainActivity.this, FriendListActivity.class);
                        startActivity(b);
                        break;
                    case R.id.nav_results:
                        Intent c = new Intent(MainActivity.this, GameResultsActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
    }
}

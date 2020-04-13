package com.example.quizme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizme.R;
import com.example.quizme.quizMe.UserDatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SignUpActivity extends AppCompatActivity {

    UserDatabaseHelper db;

    private Button mSignUpButton;
    private EditText mName, mUsername, mPassword;
    private TextView mSignUpValid, mLoginHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new UserDatabaseHelper(this);

        mSignUpButton = (Button) findViewById(R.id.signup_button);

        mName = (EditText) findViewById(R.id.editName);
        mUsername = (EditText) findViewById(R.id.editUsername);
        mPassword = (EditText) findViewById(R.id.editPassword);

        mSignUpValid = (TextView) findViewById(R.id.login_valid);
        mLoginHere = (TextView) findViewById(R.id.login_here);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSignUpValid.setVisibility(View.GONE);
                String name = mName.getText().toString().trim();
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                if (db.checkUser(username, password)){
                    mSignUpValid.setText("Username is not valid (already exists).");
                    mSignUpValid.setVisibility(View.VISIBLE);
                } else if (password.length() < 8){
                    mSignUpValid.setText("Password needs to be at least 8 characters long.");
                    mSignUpValid.setVisibility(View.VISIBLE);
                } else if (false) { // ef við viljum confirm password
                } else {
                    long val = db.addUser(username, password, name);
                    // TODO kannski breyta i dbmanager hvernig þetta er gert
                    if (val > 0) {
                        Toast.makeText(SignUpActivity.this, "New user added yayy", Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(moveToLogin);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mLoginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveToLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(moveToLogin);
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent a = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(a);
                        break;
                    case R.id.nav_friends:
                        Intent b = new Intent(SignUpActivity.this, FriendListActivity.class);
                        startActivity(b);
                        break;
                    case R.id.nav_results:
                        Intent c = new Intent(SignUpActivity.this, GameResultsActivity.class);
                        startActivity(c);
                        break;
                }
                return false;
            }
        });
    }
}

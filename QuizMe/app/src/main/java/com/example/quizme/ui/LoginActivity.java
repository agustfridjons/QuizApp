package com.example.quizme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizme.R;
import com.example.quizme.quizMe.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;

    private Button mLoginButton;

    private EditText username, password;

    private TextView loginValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);

        mLoginButton = (Button) findViewById(R.id.login_button);

        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);

        loginValid = (TextView) findViewById(R.id.login_valid);
        loginValid.setVisibility(View.GONE);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // public void login(View view) {
                    if(username.getText().toString().equals("admin") &&
                       password.getText().toString().equals("admin")) {
                        Toast.makeText(getApplicationContext(),
                                "Redirecting...",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Wrong Credentials", Toast.LENGTH_SHORT).show();

                        loginValid.setVisibility(View.VISIBLE);
                        loginValid.setTextColor(Color.RED);
                    }
              //  }
                // TODO
            }
        });




    }
}

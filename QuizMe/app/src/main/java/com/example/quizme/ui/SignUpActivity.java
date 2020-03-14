package com.example.quizme.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.quizme.R;
import com.example.quizme.quizMe.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    ProgressDialog progressDialog;

    DatabaseHelper db;

    private Button mSignUpButton;

    private EditText name, username, password;

    private TextView signUpValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new DatabaseHelper(this);

        mSignUpButton = (Button) findViewById(R.id.signup_button);

        name = (EditText) findViewById(R.id.editName);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);

        signUpValid = (TextView) findViewById(R.id.login_valid);
        signUpValid.setVisibility(View.GONE);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO signup virkni
            }
        });
    }

}

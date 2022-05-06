package com.uor.hydrochoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView linkLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        btnSignUp = findViewById(R.id.signupButton);
        btnSignUp.setOnClickListener(
                view -> {
                    Toast.makeText(getApplicationContext(),"SingUp successfully. Please Login..!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent1);
                }
        );

        linkLogin = findViewById(R.id.linkLogin);
        linkLogin.setOnClickListener(
                view -> {
                    Intent intent1 = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent1);
                }
        );
    }
}
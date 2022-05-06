package com.uor.hydrochoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;

    TextView linkNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linkNewAccount = findViewById(R.id.linkNewAccount);
        linkNewAccount.setOnClickListener(
                view -> {
                    Intent intent1 = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent1);
                }
        );

    }


}
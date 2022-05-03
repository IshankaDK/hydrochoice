package com.uor.hydrochoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(MainActivity.this, SignUpActivity.class);
                        startActivity(intent1);
                    }
                }
        );

    }

//    public void btnLoginAction(View view){
//        Toast.makeText(getApplicationContext(),"Login button clicked",Toast.LENGTH_SHORT).show();
//    }

    public void txtNewAccAction(View view){
        Toast.makeText(getApplicationContext(),"New Account button clicked",Toast.LENGTH_SHORT).show();
    }
}
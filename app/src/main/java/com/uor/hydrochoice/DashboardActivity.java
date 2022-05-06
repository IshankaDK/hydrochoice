package com.uor.hydrochoice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

public class DashboardActivity extends AppCompatActivity {

    ImageView exitIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        exitIcon = findViewById(R.id.exitIcon);
        exitIcon.setOnClickListener(
                view -> {
                    Toast.makeText(getApplicationContext(),"Exiting from dashboard..!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(DashboardActivity.this, MainActivity.class);
                    startActivity(intent1);
                }
        );
    }
}
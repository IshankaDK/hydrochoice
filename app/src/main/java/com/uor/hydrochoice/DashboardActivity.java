package com.uor.hydrochoice;


import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    ImageView exitIcon;
    private TextView ecValueText;
    private TextView onOrOffText;
    private TextView firstAttempt;
    private TextView secondAttempt;
    private View circle;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("HydrochoiceApp/EC Value");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("name", MODE_PRIVATE);
        String loggedEmail = prefs.getString("email", "");

        setContentView(R.layout.activity_dashboard);


        TextView userEmailText = findViewById(R.id.userMailText);
        userEmailText.setText(loggedEmail);

        //Notification Building
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notification","Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(DashboardActivity.this, "Notification")
                .setSmallIcon(R.drawable.icnotification)
                .setContentTitle("EC Value Warning")
                .setContentText("EC Value is not in good range. pump is ON..!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat from = NotificationManagerCompat.from(DashboardActivity.this);

        //logging out
        exitIcon = findViewById(R.id.exitIcon);
        exitIcon.setOnClickListener(
                view -> {

                    SharedPreferences.Editor editor = getSharedPreferences("name", MODE_PRIVATE).edit();
                    editor.putString("email", "");
                    editor.putBoolean("isLoggedIn", false);
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Exiting from dashboard..!", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(DashboardActivity.this, MainActivity.class);
                    intent1.putExtra("finish",true);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                }
        );


        // Read values from the firebase database
        Query query = myRef.orderByKey().limitToLast(3);
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ecValueText = findViewById(R.id.ecValueText);
                onOrOffText = findViewById(R.id.onOrOff);
                circle = findViewById(R.id.circle);
                GradientDrawable stroke = (GradientDrawable)circle.getBackground();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Double[] ecArray = new Double[3];
                int i=0;
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String ecValue = (String) child.getValue();
                    assert ecValue != null;
                    ecArray[i] = Double.valueOf(ecValue);
                    String formatVal = String.format("%.3f", Double.parseDouble(String.valueOf(ecArray[i])));
                    ecValueText.setText(formatVal);
                    if (Double.parseDouble(formatVal) >= 2 && Double.parseDouble(formatVal) <= 3) {
                        onOrOffText.setText("OFF");
                        ecValueText.setTextColor(Color.rgb(46,204,113));
                        onOrOffText.setTextColor(Color.rgb(46,204,113));
                        stroke.setStroke(20, Color.parseColor("#2ecc71"), 10, 0);
                    } else {
                        onOrOffText.setText("ON");
                        ecValueText.setTextColor(Color.rgb(255, 56, 56));
                        onOrOffText.setTextColor(Color.rgb(255, 56, 56));
                        stroke.setStroke(20, Color.parseColor("#ff3838"), 10, 0);
                        from.notify(100,builder.build());
                    }
                    i++;
                }
                // other 2 values setting
                firstAttempt = findViewById(R.id.firstAttemptText);
                secondAttempt = findViewById(R.id.secondAttemptText);
                String formatFirst = String.format("%.3f", Double.parseDouble(String.valueOf(ecArray[0])));
                String formatSecond = String.format("%.3f", Double.parseDouble(String.valueOf(ecArray[1])));
                firstAttempt.setText(formatFirst);
                secondAttempt.setText(formatSecond);
                if(Double.parseDouble(formatFirst) >= 2 && Double.parseDouble(formatFirst) <= 3){
                    firstAttempt.setTextColor(Color.rgb(46,204,113));
                }else {
                    firstAttempt.setTextColor(Color.rgb(255, 56, 56));
                }
                if(Double.parseDouble(formatSecond) >= 2 && Double.parseDouble(formatSecond) <= 3){
                    secondAttempt.setTextColor(Color.rgb(46,204,113));
                }else {
                    secondAttempt.setTextColor(Color.rgb(255, 56, 56));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                System.out.println("" + error.toException());
            }
        });
    }


}
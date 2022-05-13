package com.uor.hydrochoice;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    ImageView exitIcon;
    TextView ecValueText;
    TextView onOrOffText;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("HydrochoiceApp/EC/EC Value");

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

        // Read from the database
        Query query = myRef.orderByKey().limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot child:dataSnapshot.getChildren()) {
                    String ecValue = (String) child.getValue();
                    ecValueText = findViewById(R.id.ecValueText);
                    onOrOffText = findViewById(R.id.onOrOff);
                    assert ecValue != null;
                    String formatVal = String.format("%.3f", Double.parseDouble(ecValue));
                    ecValueText.setText(formatVal);
                    if (Double.parseDouble(formatVal) >=2 && Double.parseDouble(formatVal) <= 3 ){
                       onOrOffText.setText("OFF");
                    }else {
                        onOrOffText.setText("ON");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                System.out.println(""+error.toException());
            }
        });
    }


}
package com.uor.hydrochoice;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button btnLogin;
    TextView linkNewAccount;

    private TextInputEditText txtEmail, txtPassword;

    private String email, password;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.loginButton);

        btnLogin.setOnClickListener(
                view -> {

                    email = txtEmail.getText().toString();
                    password = txtPassword.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        txtEmail.setError("Please Enter an email");
                    } else if (TextUtils.isEmpty(password)) {
                        txtPassword.setError("Please Enter an password");
                    } else {
                        loginToApp(email, password);
                    }
                }
        );

        linkNewAccount = findViewById(R.id.linkNewAccount);
        linkNewAccount.setOnClickListener(
                view -> {
                    Intent intent1 = new Intent(MainActivity.this, SignUpActivity.class);
                    startActivity(intent1);
                }
        );


    }

    private void loginToApp(String email, String password) {
        Query query = db.collection("users").whereEqualTo("email", email);
        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String dbPassword = (String) document.get("password");
                        if (dbPassword != null && dbPassword.equals(password)) {
                            Toast.makeText(getApplicationContext(), "Welcome to HydroChoice dashboard..!", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent1);
                        }else {
                            Toast.makeText(getApplicationContext(), "Password is wrong. please try again", Toast.LENGTH_SHORT).show();
                        }
                }
            } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
        });


    }


}
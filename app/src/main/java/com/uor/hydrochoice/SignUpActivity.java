package com.uor.hydrochoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView linkLogin;

    private TextInputEditText txtEmail, txtPassword, txtConfirmPassword;

    private String email, password, confirmPassword;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = FirebaseFirestore.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSignUp = findViewById(R.id.signupButton);

        btnSignUp.setOnClickListener(
                view -> {

                    email = txtEmail.getText().toString();
                    password = txtPassword.getText().toString();
                    confirmPassword = txtConfirmPassword.getText().toString();

                    if (TextUtils.isEmpty(email)) {
                        txtEmail.setError("Please Enter an email");
                    } else if (TextUtils.isEmpty(password)) {
                        txtPassword.setError("Please Enter an password");
                    } else if (TextUtils.isEmpty(confirmPassword)) {
                        txtConfirmPassword.setError("Please Enter above password");
                    }else {
                        if (password.equals(confirmPassword)){
                            saveLogin(email,password);
                        }else {
                            txtConfirmPassword.setError("Passwords does not match");
                        }
                    }


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

    public void saveLogin(String email,String password){
        CollectionReference users = db.collection("users");

        users.add(new User(email,password)).addOnSuccessListener(documentReference -> {
            Toast.makeText(getApplicationContext(), "SingUp successfully. Please Login..!", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(intent1);
        }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "SingUp failed. Please try again..!", Toast.LENGTH_SHORT).show());
    }
}
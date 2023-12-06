package com.example.aplikasipelaporan.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasipelaporan.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    Button btn_reset;
    EditText etEmail;
    FirebaseAuth auth;
    String strEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.resetEmail);
        btn_reset = findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(view -> {
            strEmail = etEmail.getText().toString().trim();
            if (!TextUtils.isEmpty(strEmail)){
                ResetPassword();
            } else {
                etEmail.setError("Email field can`t be empty");
            }
        });
    }

    private void ResetPassword() {
        auth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this, "Reset Password link has been sent to your registered Email!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ForgotPassword.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ForgotPassword.this, "Error : - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
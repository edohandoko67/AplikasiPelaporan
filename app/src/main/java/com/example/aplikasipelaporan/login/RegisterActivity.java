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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText etNameReg, etUsernameReg, etPasswordReg;
    Button btnRegister;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        etNameReg = findViewById(R.id.namaReg);
        etUsernameReg = findViewById(R.id.usernameReg);
        etPasswordReg = findViewById(R.id.passwordReg);
        btnRegister = findViewById(R.id.btn_registerReg);

//        if (mAuth.getCurrentUser() != null){
//            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            finish();
//        }

        btnRegister.setOnClickListener(view -> {
            String email = etUsernameReg.getText().toString().trim();
            String password = etPasswordReg.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String userId = mAuth.getCurrentUser().getUid();
                        // String user = String.valueOf(mAuth.getCurrentUser());
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                        userRef.child("role").setValue("user");
                        userRef.child("email").setValue(email);
                        Toast.makeText(RegisterActivity.this, "Auth Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }
}
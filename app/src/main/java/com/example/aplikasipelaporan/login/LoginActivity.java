package com.example.aplikasipelaporan.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.ui.DashboardActivity;
import com.example.aplikasipelaporan.ui.DashboardUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView tvForgot;
    EditText etUsername, etPassword;
    Button btn_login, btn_register1;
    Switch active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        tvForgot = findViewById(R.id.tv_forgot);
        btn_register1 = findViewById(R.id.btn_reg);
        btn_register1.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        tvForgot.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
        });
        btn_login.setOnClickListener(view -> {
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String userRole = snapshot.child("role").getValue(String.class);
                                    handleUserRole(userRole);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
//                        Toast.makeText(LoginActivity.this, "Auth Success", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void handleUserRole(String userRole) {
        if ("admin".equals(userRole)) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        } else {
            startActivity(new Intent(LoginActivity.this, DashboardUser.class));
        }
    }
}
package com.example.aplikasipelaporan.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.ui.DashboardUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    EditText etNameReg, etUsernameReg, etPasswordReg, etTanggalReg, etPekerjaan, etNo;
    Button btnRegister;
    FirebaseAuth mAuth;
    ImageView btnClosed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        etNameReg = findViewById(R.id.namaReg);
        btnClosed = findViewById(R.id.close_btnRegister);
        etUsernameReg = findViewById(R.id.usernameReg);
        etPasswordReg = findViewById(R.id.passwordReg);
        etTanggalReg = findViewById(R.id.tglReg);
        etTanggalReg.setOnClickListener(view -> {
            date();
        });
        btnClosed.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();        });
        etPekerjaan = findViewById(R.id.pekerjaanReg);
        etNo = findViewById(R.id.nomorReg);
        btnRegister = findViewById(R.id.btn_registerReg);

//        if (mAuth.getCurrentUser() != null){
//            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//            finish();
//        }

        btnRegister.setOnClickListener(view -> {
            String email = etUsernameReg.getText().toString().trim();
            String password = etPasswordReg.getText().toString().trim();
            String nama = etUsernameReg.getText().toString().trim();
            String tgl = etTanggalReg.getText().toString().trim();
            String job = etPekerjaan.getText().toString().trim();
            String no = etNo.getText().toString().trim();

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(nama)) {
                Toast.makeText(this, "Enter nama", Toast.LENGTH_SHORT).show();
                return;
            }if (TextUtils.isEmpty(tgl)) {
                Toast.makeText(this, "Enter tanggal lahir", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(job)) {
                Toast.makeText(this, "Enter pekerjaan", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(no)) {
                Toast.makeText(this, "Enter nomor hp", Toast.LENGTH_SHORT).show();
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
                        userRef.child("nama").setValue(nama);
                        userRef.child("tanggal_lahir").setValue(tgl);
                        userRef.child("pekerjaan").setValue(job);
                        userRef.child("no hp").setValue(no);
                        Toast.makeText(RegisterActivity.this, "Auth Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void date() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    etTanggalReg.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
    }

}
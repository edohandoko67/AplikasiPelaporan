package com.example.aplikasipelaporan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aplikasipelaporan.model.Report;
import com.example.aplikasipelaporan.ui.DashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText etLaporan, etName;
    Button btn_create;
    ImageView btnClose;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("reports");
        etLaporan = findViewById(R.id.laporan_et);
        etName = findViewById(R.id.nama_pelapor_et);
        btnClose = findViewById(R.id.close_btn);
        btn_create = findViewById(R.id.btn_create_laporan);
        btn_create.setOnClickListener(view -> {
            saved();
        });
        btnClose.setOnClickListener(view -> {
            startActivity(new Intent(this, DashboardActivity.class));
        });
    }

    private void saved() {
        String lap = etLaporan.getText().toString();
        String name = etName.getText().toString();
        String reportId = databaseReference.push().getKey();
        Report report = new Report(lap, name);
        databaseReference.child(reportId).setValue(report);
        databaseReference.child(reportId).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    Exception exception = task.getException();
                    if (exception != null) {
                        Log.e("Firebase", "Failed to save data", exception);
                    }
                }
            }
        });

    }
}
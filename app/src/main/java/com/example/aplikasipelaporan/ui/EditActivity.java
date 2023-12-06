package com.example.aplikasipelaporan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.model.Report;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Repo;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    EditText etUpdateLaporan, etUpdateName;
    Button btn_Edit;
    String name, laporan, key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        FirebaseApp.initializeApp(this);

        etUpdateLaporan = findViewById(R.id.etUpdateLaporan);
        etUpdateName = findViewById(R.id.etUpdateName);
        btn_Edit = findViewById(R.id.btn_edit);
        btn_Edit.setOnClickListener(view -> {
            simpan();
        });
        loadData();
    }

    private void simpan() {
        name = etUpdateName.getText().toString().trim();
        laporan = etUpdateLaporan.getText().toString().trim();
        if (name.isEmpty()){
            etUpdateName.setError("Harus diisi!");
        }else if (laporan.isEmpty()){
            etUpdateLaporan.setError("Harus diisi!");
        } else {
            Report report = new Report(name, laporan);
            DatabaseReference reportRef = database.child("reports").child(key);
            Map<String, Object> updates = new HashMap<>();
            updates.put("name", report.getName());
            updates.put("laporan", report.getLaporan());
            reportRef.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Data updated successfully
                        Toast.makeText(EditActivity.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditActivity.this, ListActivity.class));
                        finish();
                    } else {
                        // Data could not be updated
                        Toast.makeText(EditActivity.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void loadData() {
        Bundle bundle = getIntent().getExtras();
        if (getIntent().getExtras() != null) {
            name = bundle.getString("name");
            laporan = bundle.getString("laporan");
            key = bundle.getString("Key");

            etUpdateName.setText(name);
            etUpdateLaporan.setText(laporan);
        }
    }
}
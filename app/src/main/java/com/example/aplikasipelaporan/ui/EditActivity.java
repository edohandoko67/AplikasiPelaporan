package com.example.aplikasipelaporan.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.model.Report;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    EditText etTanggalUpdate, etUpdateHasil, etTanggalMasuk;
    Spinner spiner;
    Button btn_Edit;
    String status, hasil, key, tanggalMasuk, tanggalUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        FirebaseApp.initializeApp(this);

        etTanggalMasuk = findViewById(R.id.etTanggalMasuk);
        etTanggalUpdate = findViewById(R.id.etTanggalUpdate);
        etTanggalUpdate.setOnClickListener(view -> {
            dateData();
        });
        etUpdateHasil = findViewById(R.id.etUpdateHasil);
        spiner = findViewById(R.id.spinerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.status_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Terapkan adapter ke spinner
        spiner.setAdapter(adapter);
        btn_Edit = findViewById(R.id.btn_edit);
        btn_Edit.setOnClickListener(view -> {
            simpan();
        });
        loadData();
    }

    private void dateData() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    etTanggalUpdate.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void simpan() {
        hasil = etUpdateHasil.getText().toString().trim();
        tanggalUpdate = etTanggalUpdate.getText().toString().trim();
        String selectedStatus = spiner.getSelectedItem().toString();
        if (hasil.isEmpty()){
            etUpdateHasil.setError("Harus diisi!");
        }else if (tanggalUpdate.isEmpty()){
            etTanggalUpdate.setError("Harus diisi!");
        } else {
            Report report = new Report(selectedStatus, tanggalUpdate, hasil);
            DatabaseReference reportRef = database.child("reports").child(key);
            Map<String, Object> updates = new HashMap<>();
            //updates.put("name", report.getName());
            updates.put("hasil", report.getHasil());
            updates.put("tanggalUpdate", report.getTanggalUpdate());
            updates.put("status", report.getStatus());
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
            //name = bundle.getString("name");
            hasil = bundle.getString("hasil");
            tanggalMasuk = bundle.getString("tanggalMasuk");
            tanggalUpdate = bundle.getString("tanggalUpdate");
            status = bundle.getString("status");
            key = bundle.getString("Key");

            etUpdateHasil.setText(hasil);
            etTanggalUpdate.setText(tanggalUpdate);
            etTanggalMasuk.setText(tanggalMasuk);

//            List<String> statusList = new ArrayList<>();
//            statusList.add("OPEN");
//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
//                    this,
//                    R.array.status_options,
//                    android.R.layout.simple_spinner_item
//            );
//            Log.i("TEST", adapter.toString());
//            spiner.setSelection(status);
        }
    }
}
package com.example.aplikasipelaporan.Unit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.aplikasipelaporan.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivityUnit extends AppCompatActivity {
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    EditText etUpdateLaporan, etUpdateName, etUpdateStatus;
    Button btn_Edit;
    String name, laporan, key, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_unit);

        etUpdateLaporan = findViewById(R.id.etUpdateLaporanUnit);
        etUpdateName = findViewById(R.id.etUpdateNameUnit);
        //etUpdateStatus = findViewById(R.id.e_status_unit)

    }
}
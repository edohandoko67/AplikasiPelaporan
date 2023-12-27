package com.example.aplikasipelaporan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.Unit.ListUnitAct;

public class DashboardUnitAct extends AppCompatActivity {
    CardView cardViewUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_unit);

        cardViewUnit = findViewById(R.id.cardViewUnit);
        cardViewUnit.setOnClickListener(view -> {
            startActivity(new Intent(DashboardUnitAct.this, ListUnitAct.class));
        });
    }
}
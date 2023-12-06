package com.example.aplikasipelaporan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasipelaporan.MainActivity;
import com.example.aplikasipelaporan.R;
import com.google.firebase.FirebaseApp;

public class DashboardUser extends AppCompatActivity {
    CardView cardView1, cardView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        FirebaseApp.initializeApp(this);
        cardView1 = findViewById(R.id.cardViewUserLaporan);
        cardView2 = findViewById(R.id.cardViewUserCek);
        cardView1.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        cardView2.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListUserActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
package com.example.aplikasipelaporan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasipelaporan.MainActivity;
import com.example.aplikasipelaporan.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;

public class DashboardActivity extends AppCompatActivity {
    CardView cardView1, cardView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FirebaseApp.initializeApp(this);
        cardView1 = findViewById(R.id.cardView2);
        cardView2 = findViewById(R.id.cardView3);

        cardView1.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        cardView2.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
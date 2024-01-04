package com.example.aplikasipelaporan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplikasipelaporan.MainActivity;
import com.example.aplikasipelaporan.Notif.NotifActivity;
import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.login.LoginActivity;
import com.example.aplikasipelaporan.login.SharedPref;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class DashboardActivity extends AppCompatActivity {
    CardView cardView2, cardView3, cardView4;
    ImageView imgOut;
    private SharedPref sharedPref;
    TextView tv_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance().signOut();
        sharedPref = new SharedPref(this);
        cardView2 = findViewById(R.id.cardView3);
        cardView3 = findViewById(R.id.cardView4);
        cardView4 = findViewById(R.id.cardView5);
        tv_user = findViewById(R.id.tv_user);
        imgOut = findViewById(R.id.imgOut);
        imgOut.setOnClickListener(view -> {
            sharedPref.logout();
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        });
        cardView2.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);
        });
        cardView3.setOnClickListener(view -> {
            Intent intent = new Intent(this, NotifActivity.class);
            startActivity(intent);
        });

        cardView4.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListActivityNotif.class);
            startActivity(intent);
        });

        if (sharedPref.getLoggedIn()){
            String username = sharedPref.getUserEmail();
            tv_user.setText(username);
        }
    }
}
package com.example.aplikasipelaporan.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplikasipelaporan.MainActivity;
import com.example.aplikasipelaporan.Notif.SendNotif;
import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.login.LoginActivity;
import com.example.aplikasipelaporan.login.SharedPref;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class DashboardUser extends AppCompatActivity {
    CardView cardView1, cardView2;
    ImageView imgOutUser, imgSendLap;
    TextView tv_user;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance().signOut();
        sharedPref = new SharedPref(this);
        cardView1 = findViewById(R.id.cardViewUserLaporan);
        cardView2 = findViewById(R.id.cardViewUserCek);
        tv_user = findViewById(R.id.tv_user);
        imgSendLap = findViewById(R.id.imgSendLaporan);
        imgSendLap.setOnClickListener(view -> {
            startActivity(new Intent(DashboardUser.this, SendNotif.class));
        });
        imgOutUser = findViewById(R.id.imgOutUser);
        imgOutUser.setOnClickListener(view -> {
            sharedPref.logout();
            startActivity(new Intent(DashboardUser.this, LoginActivity.class));
            finish();
        });
        cardView1.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        cardView2.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListUserActivity.class);
            startActivity(intent);
        });

        if (sharedPref.getLoggedIn()){
            String username = sharedPref.getUserEmail();
            tv_user.setText(username);
        }
    }

//    private boolean isLoggedIn() {
//        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
//        return preferences.getBoolean("isLoggedIn", false);
//    }
}
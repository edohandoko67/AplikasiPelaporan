package com.example.aplikasipelaporan.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.login.LoginActivity;
import com.example.aplikasipelaporan.login.SharedPref;
import com.example.aplikasipelaporan.ui.DashboardActivity;
import com.example.aplikasipelaporan.ui.DashboardUser;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPref = new SharedPref(this);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        lottieAnimationView.animate().setDuration(4000);
        new Handler().postDelayed(() -> {
            String userRole = sharedPref.getUserRole();
            if (sharedPref.getLoggedIn() && !userRole.isEmpty()) {
                if ("admin".equals(userRole)) {
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                } else if ("user".equals(userRole)) {
                    startActivity(new Intent(SplashScreen.this, DashboardUser.class));
                } else {
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                }
                finish();
            } else {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}
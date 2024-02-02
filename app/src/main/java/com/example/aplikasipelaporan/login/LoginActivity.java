package com.example.aplikasipelaporan.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.model.Report;
import com.example.aplikasipelaporan.ui.DashboardActivity;
import com.example.aplikasipelaporan.ui.DashboardUnitAct;
import com.example.aplikasipelaporan.ui.DashboardUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView tvForgot;
    EditText etUsername, etPassword;
    Button btn_login, btn_register1;
    private SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        sharedPref = new SharedPref(this);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        tvForgot = findViewById(R.id.tv_forgot);
        btn_register1 = findViewById(R.id.btn_reg);
        btn_register1.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        tvForgot.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
        });
        btn_login.setOnClickListener(view -> {
            String email = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
                return;
            } if (TextUtils.isEmpty(password)){
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        sharedPref.setLoggedIn(true);
                        String userId = mAuth.getCurrentUser().getUid();
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    String userRole = snapshot.child("role").getValue(String.class);
                                    //handleUserRole(userRole);
                                    if (userRole != null){
                                        if (userRole.contains("admin")){
                                            sharedPref.setUserRole("admin");
                                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                                            notifFCM();
                                        } else if (userRole.contains("unit")){
                                            sharedPref.setUserRole("unit");
                                            startActivity(new Intent(LoginActivity.this, DashboardUnitAct.class));
                                        } else if (userRole.contains("user")){
                                            sharedPref.setUserRole("user");
                                            startActivity(new Intent(LoginActivity.this, DashboardUser.class));
                                        }
                                        else {
                                            startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                            finish();
                                            Toast.makeText(LoginActivity.this, "User does not have valid roles", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                                    }
                                    sharedPref.setLoggedIn(true);
                                    sharedPref.setUserEmail(email);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void notifFCM() {
        DatabaseReference reportsRef = FirebaseDatabase.getInstance().getReference("reports");
        reportsRef.orderByChild("status").equalTo("OPEN").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Report report = snapshot.getValue(Report.class);

                    if (report != null && !snapshot.hasChild("tanggalUpdate")) {
                        sendNotification("Terdapat Notif Laporan", "Mohon segera cek di list notification");
                    }
                }
            }

            private void sendNotification(String title, String body) {
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(LoginActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("default_channel_id", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);
                    channel.setDescription("Default Channel Description");
                    channel.enableLights(true);
                    channel.setLightColor(Color.BLUE);
                    channel.enableVibration(true);
                    notificationManager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(LoginActivity.this, "default_channel_id")
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.baseline_message_24)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(pendingIntent);
                notificationManager.notify(0, notificationBuilder.build());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }

//    private void handleUserRole(String userRole) {
//        if ("admin".equals(userRole)) {
//            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
//        } else if ("unit".equals(userRole)){
//            startActivity(new Intent(LoginActivity.this, DashboardUnitAct.class));
//        } else {
//            startActivity(new Intent(LoginActivity.this, DashboardUser.class));
//        }
//    }

//    private void saveLoginState() {
//        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("isLoggedIn", true);
//        editor.apply();
//    }
}
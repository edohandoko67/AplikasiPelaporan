package com.example.aplikasipelaporan;

import static com.example.aplikasipelaporan.Notif.ValuesClass.TO;
import static com.example.aplikasipelaporan.notification.NotificationMessage.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aplikasipelaporan.Notif.ApiUtils;
import com.example.aplikasipelaporan.Notif.PushNotification;
import com.example.aplikasipelaporan.model.NotificationData;
import com.example.aplikasipelaporan.model.Report;
import com.example.aplikasipelaporan.ui.DashboardActivity;
import com.example.aplikasipelaporan.ui.DashboardUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import org.checkerframework.checker.units.qual.C;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RadioButton radioButtonYes1, radioButtonNo1, RBAnak, RBibu, RBanakibu, RByes6,RBno6, RByes7, RBno7;
    EditText etKekerasan, etReaksi, tanggal, etIya;
    Button btn_create;
    ImageButton btnClose;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    private String message;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("reports");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        etKekerasan = findViewById(R.id.et_kekerasan);
        etReaksi = findViewById(R.id.et_tindakan);
        tanggal = findViewById(R.id.et_kapan);
        etIya = findViewById(R.id.et_bantuan);
        radioButtonYes1 = findViewById(R.id.radioButtonYes1);
        radioButtonNo1 = findViewById(R.id.radioButtonNo1);
        RBAnak = findViewById(R.id.radioButtonIbu);
        RBibu = findViewById(R.id.radioButtonAnak);
        RBanakibu = findViewById(R.id.radioButtonAnakIbu);
        RByes6 = findViewById(R.id.radioButtonYes2);
        RBno6 = findViewById(R.id.radioButtonNo2);
        RByes7 = findViewById(R.id.radioButtonYes3);
        RBno7 = findViewById(R.id.radioButtonNo3);

        RByes7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Cek apakah RadioButton 'No' yang dipilih
                if (isChecked) {
                    // Tampilkan TextField
                    etIya.setVisibility(View.VISIBLE);
                } else {
                    // Sembunyikan TextField
                    etIya.setVisibility(View.GONE);
                }
            }
        });

        tanggal.setOnClickListener(view -> {
            showDatePickerDialog();
        });
        btnClose = findViewById(R.id.close_btnUser);
        btn_create = findViewById(R.id.btn_create_laporan);
        btn_create.setOnClickListener(view -> {
            saved();
        });
        btnClose.setOnClickListener(view -> {
            startActivity(new Intent(this, DashboardUser.class));
            finish();
        });
        msg();
    }

    private void msg() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            FirebaseAuth.getInstance()
                    .signInAnonymously()
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseMessaging.getInstance()
                                    .subscribeToTopic("All");
                        }
                    });
        } else {
            FirebaseMessaging.getInstance().getToken()
                    .addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                            NotificationData notificationData = new NotificationData("", s);
                            FirebaseFirestore
                                    .getInstance()
                                    .collection("token")
                                    .document("token")
                                    .set(notificationData);
                        }
                    });
        }
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    tanggal.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void saved() {
        String lap = etKekerasan.getText().toString().trim();
        String name = etReaksi.getText().toString().trim();
        String date = tanggal.getText().toString().trim();
        boolean isYes1Selected = radioButtonYes1.isChecked();
        boolean isYes2Selected = RByes6.isChecked();
        boolean isYes3Selected = RByes7.isChecked();
        boolean isNo1Selected = radioButtonNo1.isChecked();
        boolean isNo2Selected = RBno6.isChecked();
        boolean isNo3Selected = RBno7.isChecked();
        boolean isAnakSelected = RBAnak.isChecked();
        boolean isIbuSelected = RBibu.isChecked();
        boolean isIbuAnakSelected = RBanakibu.isChecked();
        String reportId = databaseReference.push().getKey();
        Report report = new Report(lap, name, date, "OPEN", "New_Notification");
        //report.setStatus("pending");
        report.setYaMengalami(String.valueOf(isYes1Selected));
        report.setYaOrangTau(String.valueOf(isYes2Selected));
        report.setTidakMembantu(String.valueOf(isYes3Selected));
        report.setTidakMengalami(String.valueOf(isNo1Selected));
        report.setTidakTahu(String.valueOf(isNo2Selected));
        report.setTidakMembantu(String.valueOf(isNo3Selected));
        report.setAnak(String.valueOf(isAnakSelected));
        report.setIbu(String.valueOf(isIbuSelected));
        report.setAnakIbu(String.valueOf(isIbuAnakSelected));
        report.setHasil("Belum ditangani oleh admin");

        boolean retrievedIsYes1Selected = Boolean.parseBoolean(report.getYaMengalami());
        boolean retrievedIsYes2Selected = Boolean.parseBoolean(report.getYaOrangTau());
        boolean retrievedIsYes3Selected = Boolean.parseBoolean(report.getYaBantuan());
        boolean retrievedIsNo1Selected = Boolean.parseBoolean(report.getTidakMengalami());
        boolean retrievedIsNo2Selected = Boolean.parseBoolean(report.getTidakTahu());
        boolean retrievedIsNo3Selected = Boolean.parseBoolean(report.getTidakMembantu());
        boolean retrievedIsAnakSelected = Boolean.parseBoolean(report.getAnak());
        boolean retrievedIsIbuSelected = Boolean.parseBoolean(report.getIbu());
        boolean retrievedIsAnakIbuSelected = Boolean.parseBoolean(report.getAnakIbu());

        if (retrievedIsYes1Selected) {
            // Do something when Question 1 is answered with "Yes"
        } else {
            // Do something else when Question 1 is answered with "No"
        }

        if (retrievedIsYes2Selected) {
            // Do something when Question 2 is answered with "Yes"
        } else {
            // Do something else when Question 2 is answered with "No"
        }

        if (retrievedIsYes3Selected) {
            // Do something when Question 3 is answered with "Yes"
        } else {
            // Do something else when Question 3 is answered with "No"
        }

        if (retrievedIsNo1Selected) {
            // Do something when Question 3 is answered with "Yes"
        } else {
            // Do something else when Question 3 is answered with "No"
        }

        if (retrievedIsNo2Selected) {
            // Do something when Question 3 is answered with "Yes"
        } else {
            // Do something else when Question 3 is answered with "No"
        }
        if (retrievedIsNo3Selected) {
            // Do something when Question 3 is answered with "Yes"
        } else {
            // Do something else when Question 3 is answered with "No"
        }
        if (retrievedIsAnakSelected) {
            // Do something when Question 3 is answered with "Yes"
        } else {
            // Do something else when Question 3 is answered with "No"
        }
        if (retrievedIsIbuSelected) {
            // Do something when Question 3 is answered with "Yes"
        } else {
            // Do something else when Question 3 is answered with "No"
        }
        if (retrievedIsAnakIbuSelected) {
            // Do something when Question 3 is answered with "Yes"
        } else {
            // Do something else when Question 3 is answered with "No"
        }


        databaseReference.child(reportId).setValue(report);
        databaseReference.child(reportId).setValue(report).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to save data", Toast.LENGTH_SHORT).show();
                    Exception exception = task.getException();
                    if (exception != null) {
                        Log.e("Firebase", "Failed to save data", exception);
                    }
                }
            }
        });
    }
    private void handleBackPressed() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
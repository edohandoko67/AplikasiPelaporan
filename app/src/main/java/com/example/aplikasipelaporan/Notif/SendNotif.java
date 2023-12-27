package com.example.aplikasipelaporan.Notif;

import static com.example.aplikasipelaporan.Notif.ValuesClass.TO;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aplikasipelaporan.MainActivity;
import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.model.NotificationData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotif extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    EditText etTitle, etMsg;
    Button btnSend;
    private String message, title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notif);

        firebaseFirestore = FirebaseFirestore.getInstance();
        etTitle = findViewById(R.id.et_sendNotifTitle);
        etMsg = findViewById(R.id.et_sendNotif);
        btnSend = findViewById(R.id.btn_sendLaporan);
        btnSend.setOnClickListener(view -> {
            title = etTitle.getText().toString().trim();
            message = etMsg.getText().toString().trim();
            sendNotif();
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
                            Toast.makeText(SendNotif.this, "Done", Toast.LENGTH_SHORT).show();
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


    private void sendNotif() {
        ApiUtils.getClients().sendNotification(new PushNotification(new NotificationData(title, message), TO))
                .enqueue(new Callback<PushNotification>() {
                    @Override
                    public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SendNotif.this, "Notification Sent", Toast.LENGTH_SHORT).show();

                        } else {
                            Log.e("SendNotif", "Notification sending failed. Error code: " + response.code());
                            Toast.makeText(SendNotif.this, "Notification sending failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PushNotification> call, Throwable t) {
                        Toast.makeText(SendNotif.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
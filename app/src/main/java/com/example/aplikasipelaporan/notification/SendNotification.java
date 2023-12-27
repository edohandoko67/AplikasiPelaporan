package com.example.aplikasipelaporan.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.example.aplikasipelaporan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.messaging.FirebaseMessaging;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SendNotification extends AppCompatActivity {
    SendNotification binding;
    MaterialButton send_notif, signOut;
    TextInputEditText notifMessage, notifEmail;
    private FirebaseAuth AuthUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        AuthUI = FirebaseAuth.getInstance();
        notifMessage = findViewById(R.id.notifMessage);
        notifEmail = findViewById(R.id.notifEmail);
        send_notif = findViewById(R.id.send_notif);
        signOut = findViewById(R.id.sign_out);
        setUpButtons();
    }

    private void setUpButtons() {
        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AuthUI.getInstance().signOut(SendNotification.this).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<Void> task) {
//                        startActivity(new Intent(SendNotification.this, FirebaseAuthUIActivity.class));
//                        finish();
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic("messaging");
//                    }
//                });
            }
        });
        binding.send_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.notifMessage.getText().toString().isEmpty() && (!binding.notifEmail.getText().toString().isEmpty())) {
                    new FCMSender().send(String.format(NotificationMessage.message, "messaging", binding.notifMessage.getText().toString(), binding.notifEmail.getText().toString()), new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            SendNotification.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.code() == 200) {
                                        Toast.makeText(SendNotification.this, "Notification sent", Toast.LENGTH_SHORT).show();
                                        hideKeyboard(SendNotification.this);
                                    }
                                }
                            });
                        }
                    });
                } else {
                    if (notifEmail.getText().toString().isEmpty()) {
                        notifEmail.setError("Please enter the mobile number");
                    }
                    if (notifMessage.getText().toString().isEmpty()) {
                        notifMessage.setError("Please enter the message you want to send");
                    }
                }
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
package com.example.aplikasipelaporan.Notif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.aplikasipelaporan.R;
import com.example.aplikasipelaporan.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivityUserSend extends AppCompatActivity {
    ActivityMainBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_main_user_send);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //msg();
    }

//    private void msg() {
//        if (FirebaseAuth.getInstance().getCurrentUser()==null){
//            FirebaseAuth.getInstance()
//                    .signInAnonymously()
//        }
//    }
}
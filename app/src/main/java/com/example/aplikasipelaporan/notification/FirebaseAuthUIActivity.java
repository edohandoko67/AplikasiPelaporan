package com.example.aplikasipelaporan.notification;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasipelaporan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class FirebaseAuthUIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth_uiactivity);
//        ActivityAuthUiBinding binding=ActivityAuthUiBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
//            Intent intent=new Intent(FirebaseAuthUIActivity.this,SendNotification.class);
//            startActivity(intent);
//            finish();
//        } else{
//            final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
//                    new FirebaseAuthUIActivityResultContract(),
//                    new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
//                        @Override
//                        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
//                            onSignInResult(result);
//                        }
//                    }
//            );
//            // Choose authentication providers
//            List<AuthUI.IdpConfig> providers = Arrays.asList(
//                    new AuthUI.IdpConfig.PhoneBuilder().build());
//
//// Create and launch sign-in intent
//            Intent signInIntent = AuthUI.getInstance()
//                    .createSignInIntentBuilder()
//                    .setAvailableProviders(providers)
//                    .setTheme(R.style.Theme_Helloworld)
//                    .build();
//            signInLauncher.launch(signInIntent);
//        }
//
//    }
//    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
//        IdpResponse response = result.getIdpResponse();
//        if (result.getResultCode() == RESULT_OK) {
//            // Successfully signed in
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            Intent intent=new Intent(FirebaseAuthUIActivity.this,SendNotification.class);
//            startActivity(intent);
//            finish();
//        }else{
//            finish();
//        }
//    }
    }
}
package com.darrenmoriarty.myfitnessapp.Login_Signup_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.darrenmoriarty.myfitnessapp.HomeScreen;
import com.darrenmoriarty.myfitnessapp.R;
import com.darrenmoriarty.myfitnessapp.pageractivities.MainHomePagerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {

    private Button mSignInButton;
    private Button mLoginButton;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setTitle("Welcome Activity");

        // initialize_auth
        mAuth = FirebaseAuth.getInstance();

        // Getting the current user
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // checking if the user is logged in currently
        if (firebaseUser != null) {

            // changing to the account activity
            startActivity(new Intent(WelcomeActivity.this, MainHomePagerActivity.class));

        }


        mLoginButton = (Button) findViewById(R.id.loginButton);
        mSignInButton = (Button) findViewById(R.id.signUpButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));

            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(WelcomeActivity.this, SignUpChoice.class));

            }
        });


        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // getting the current user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());

                    // changing to the account activity
                    startActivity(new Intent(WelcomeActivity.this, MainHomePagerActivity.class));

                } else {

                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");

                }
            }
        };
        // [END auth_state_listener]
    }
}

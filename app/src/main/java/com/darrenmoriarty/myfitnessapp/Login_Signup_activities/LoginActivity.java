package com.darrenmoriarty.myfitnessapp.Login_Signup_activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darrenmoriarty.myfitnessapp.HomeScreen;
import com.darrenmoriarty.myfitnessapp.R;
import com.darrenmoriarty.myfitnessapp.pageractivities.MainHomePagerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginButton;
    private Button mFaceBookLogin;
    private Button mGoogleLogin;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "EmailPassword";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login Activity");

        // initialize_auth
        mAuth = FirebaseAuth.getInstance();


        // Edit Text Fields
        mEmailField = (EditText) findViewById(R.id.emailEditText);
        mPasswordField = (EditText) findViewById(R.id.passwordEditText);

        // Buttons
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mFaceBookLogin = (Button) findViewById(R.id.facebookLoginButton);
        mGoogleLogin = (Button) findViewById(R.id.googleLoginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());

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
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    // changing to the account activity
                    startActivity(new Intent(LoginActivity.this, MainHomePagerActivity.class));

                } else {

                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");


                }
            }
        };
        // [END auth_state_listener]


    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    private void signIn(final String email, final String password) {


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){

            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();

        } else {

            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, "signInWithEmail:failed\n" + email + password,
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });
            // [END sign_in_with_email]



        }


    }
}

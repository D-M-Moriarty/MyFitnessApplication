package com.darrenmoriarty.myfitnessapp;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    // Fields
    private EditText mPasswordField;
    private EditText mEmailField;
    private EditText mUsername;

    private String username;
    private String weightGoals;
    private String currentActivity;
    private String gender;
    private String DOB;
    private String BMR;
    private String BMI;
    private String TDEE;
    private String calorieGoal;
    private String height;
    private String weight;
    private String emailAddress;
    private String fullname;
    private String password;

    private Button mSignUpButton;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog mProgressDialog;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        BMR = getIntent().getStringExtra("BMR");
//        BMI = getIntent().getStringExtra("BMI");
//        TDEE = getIntent().getStringExtra("TDEE");
//        calorieGoal = getIntent().getStringExtra("CalorieGoal");
//        weightGoals = getIntent().getStringExtra("WeightGoal");
//        currentActivity = getIntent().getStringExtra("CurrentActivity");
//        fullname = getIntent().getStringExtra("Fullname");
//        weight = getIntent().getStringExtra("Weight");
//        height = getIntent().getStringExtra("Height");
//        gender = getIntent().getStringExtra("Gender");


        setTitle("Sign Up Activity");

        mAuth = FirebaseAuth.getInstance();
        // Write a message to the database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Users");


        // Text Fields
        mPasswordField = (EditText) findViewById(R.id.passwordEditText);
        mEmailField = (EditText) findViewById(R.id.emailEditText);
        mUsername = (EditText) findViewById(R.id.usernameEditText);

        // Sign Up Button
        mSignUpButton  =(Button) findViewById(R.id.signUpButton);



        // Progress Dialog
        mProgressDialog = new ProgressDialog(this);


        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount(mEmailField.getText().toString().trim(), mPasswordField.getText().toString().trim());

            }
        });



    }



    private void createAccount(String email, String password) {

        final String name = mUsername.getText().toString().trim();


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name)) {

            // Register user process

            mProgressDialog.setMessage("Signing up....");
            mProgressDialog.show();

            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());


                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {

                                Toast.makeText(SignUpActivity.this, "Failed to create user",
                                        Toast.LENGTH_SHORT).show();

                            } else {

                                String userId = mAuth.getCurrentUser().getUid();

                                DatabaseReference currentUserDb = mDatabaseReference.child(userId);

                                currentUserDb.child("username").setValue(name);
                                currentUserDb.child("BMI").setValue(DetailsSignUpActivity.BMIString);
                                currentUserDb.child("TDEE").setValue(DetailsSignUpActivity.BMRString);
                                currentUserDb.child("Gender").setValue(DetailsSignUpActivity.gender);
                                currentUserDb.child("Height").setValue(DetailsSignUpActivity.height);
                                currentUserDb.child("Weight").setValue(DetailsSignUpActivity.weight);
                                currentUserDb.child("Fullname").setValue(DetailsSignUpActivity.fullName);
                                currentUserDb.child("Current Activity").setValue(DetailsSignUpActivity.currentActivity);
                                currentUserDb.child("Weight Goal").setValue(DetailsSignUpActivity.weight);
                                currentUserDb.child("Calorie Goal").setValue(DetailsSignUpActivity.calorieGoal);


                                mProgressDialog.dismiss();

                                Intent mainIntent = new Intent(SignUpActivity.this, HomeScreen.class);
                                startActivity(mainIntent);

                            }

                        }
                    });
            // [END create_user_with_email]

        } else {



        }
    }
}

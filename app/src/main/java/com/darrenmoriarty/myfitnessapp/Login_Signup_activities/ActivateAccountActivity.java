package com.darrenmoriarty.myfitnessapp.Login_Signup_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivateAccountActivity extends AppCompatActivity {

    private TextView BMRTextView;
    private TextView BMITextView;
    private TextView TDEETextView;
    private TextView calorieGoalTextView;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_account);

        setTitle("Activate Account Activity");

        BMRTextView = (TextView) findViewById(R.id.BMRTextView);
        BMITextView = (TextView) findViewById(R.id.BMITextView);
        TDEETextView = (TextView) findViewById(R.id.TDEETextView);
        calorieGoalTextView = (TextView) findViewById(R.id.calorieGoalTextView);


        BMRTextView.setText(DetailsSignUpActivity.BMRString);
        BMITextView.setText(DetailsSignUpActivity.BMIString);
        TDEETextView.setText(DetailsSignUpActivity.TDEEString);
        calorieGoalTextView.setText(DetailsSignUpActivity.calorieGoal);



        findViewById(R.id.activateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });



    }

    public void registerUserDetails() {

        mProgressDialog.setMessage("Registering Details....");
        mProgressDialog.show();

        String userId = mAuth.getCurrentUser().getUid();

        System.out.println("UID " + userId);

        DatabaseReference currentUserDb = mDatabaseReference.child(userId);

        currentUserDb.child("BMR").setValue(BMITextView.getText().toString().trim());
        currentUserDb.child("BMI").setValue(BMRTextView.getText().toString().trim());
        currentUserDb.child("TDEE").setValue(TDEETextView.getText().toString().trim());
        currentUserDb.child("Calorie Goal").setValue(calorieGoalTextView.getText().toString().trim());

        mProgressDialog.dismiss();

    }



}

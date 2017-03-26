package com.darrenmoriarty.myfitnessapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

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



        Bundle b =  getIntent().getExtras();

        if (b != null) {

            BMRTextView.setText(b.getString("BMR"));
            BMITextView.setText(b.getString("BMI"));
            TDEETextView.setText(b.getString("TDEE"));
            calorieGoalTextView.setText(b.getString("Calorie Goal"));

        }

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

package com.darrenmoriarty.myfitnessapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.darrenmoriarty.myfitnessapp.R.id.radio;

public class WeightGoalsActivity extends AppCompatActivity {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    // UI elements
    private RadioButton mLoseWeight;
    private RadioButton mMaintainWeight;
    private RadioButton mGainWeight;
    private RadioButton mSedentary;
    private RadioButton mLightEx;
    private RadioButton mModerateEx;
    private RadioButton mHardEx;
    private RadioButton mVeryHardEx;

    // Goal settings
    private String weightGoal;
    private String currentActivity;

    private Button mContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_goals);

        mAuth = FirebaseAuth.getInstance();
        // Write a message to the database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Users");

        mLoseWeight = (RadioButton) findViewById(R.id.loseWeightRadioButton);
        mMaintainWeight = (RadioButton) findViewById(R.id.maintainWeightRadioButton);
        mGainWeight = (RadioButton) findViewById(R.id.gainWeightRadioButton);
        mSedentary = (RadioButton) findViewById(R.id.sedentaryRadioButton);
        mLightEx = (RadioButton) findViewById(R.id.lightExerciseRadioButton);
        mModerateEx = (RadioButton) findViewById(R.id.moderateExerciseRadioButton);
        mHardEx = (RadioButton) findViewById(R.id.hardExerciseRadioButton);
        mVeryHardEx = (RadioButton) findViewById(R.id.veryHardExerciseRadioButton);

        final RadioGroup weightGroup = (RadioGroup) findViewById(R.id.weightRadioGroup);
        weightGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                View radioButton = weightGroup.findViewById(checkedId);
                int index = weightGroup.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button

                        Toast.makeText(getApplicationContext(), "Selected button number " + index, Toast.LENGTH_LONG).show();
                        weightGoal = "Lose Weight";
                        break;
                    case 1: // secondbutton

                        Toast.makeText(getApplicationContext(), "Selected button number " + index, Toast.LENGTH_LONG).show();
                        weightGoal = "Maintain Weight";
                        break;
                    case 2: // thirdbutton

                        Toast.makeText(getApplicationContext(), "Selected button number " + index, Toast.LENGTH_LONG).show();
                        weightGoal = "Gain Weight";
                        break;
                }

            }
        });

        final RadioGroup activityGroup = (RadioGroup) findViewById(R.id.activityRadioGroup);
        activityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                View radioButton = activityGroup.findViewById(checkedId);
                int index = activityGroup.indexOfChild(radioButton);

                // Add logic here

                switch (index) {
                    case 0: // first button

                        currentActivity = "Sedentary";
                        break;
                    case 1: // secondbutton

                        currentActivity = "Light Exercise";
                        break;
                    case 2: // thirdbutton

                        currentActivity = "Moderate Exercise";
                        break;
                    case 3:

                        currentActivity = "Hard Exercise";
                        break;
                    case 4:


                        currentActivity = "Very Moderate Exercise";
                        break;


                }

            }
        });


    }



}

package com.darrenmoriarty.myfitnessapp.Login_Signup_activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.darrenmoriarty.myfitnessapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.darrenmoriarty.myfitnessapp.R.id.gainWeightRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.hardExerciseRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.lightExerciseRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.loseWeightRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.maintainWeightRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.moderateExerciseRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.sedentaryRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.veryHardExerciseRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.weightEditText;
import static com.darrenmoriarty.myfitnessapp.R.id.weightRadioGroup;

public class WeightGoalsActivity extends AppCompatActivity {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;


    // Goal settings
    private String weightGoal;
    private String currentActivity;

    // Radio Groups
    RadioGroup weightGroup;
    RadioGroup activityGroup;

    private Button mContinue;

    private RadioButton loseWeightRadio;
    private RadioButton maintainWeightRadio;
    private RadioButton gainWeightRadio;
    private RadioButton sedentaryWeightRadio;
    private RadioButton lightWeightRadio;
    private RadioButton hardWeightRadio;
    private RadioButton moderateWeightRadio;
    private RadioButton veryHardExerciseRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_goals);

        setTitle("Weight Goals Activity");

        mAuth = FirebaseAuth.getInstance();
        // Write a message to the database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Users");

        // Radio Groups
        weightGroup = (RadioGroup) findViewById(R.id.weightRadioGroup);
        activityGroup = (RadioGroup) findViewById(R.id.activityRadioGroup);

        loseWeightRadio = (RadioButton) findViewById(R.id.loseWeightRadioButton);
        maintainWeightRadio = (RadioButton) findViewById(R.id.maintainWeightRadioButton);
        gainWeightRadio = (RadioButton) findViewById(R.id.gainWeightRadioButton);
        sedentaryWeightRadio = (RadioButton) findViewById(R.id.sedentaryRadioButton);
        lightWeightRadio = (RadioButton) findViewById(R.id.lightExerciseRadioButton);
        moderateWeightRadio = (RadioButton) findViewById(R.id.moderateExerciseRadioButton);
        veryHardExerciseRadio = (RadioButton) findViewById(R.id.veryHardExerciseRadioButton);
        hardWeightRadio = (RadioButton) findViewById(R.id.hardExerciseRadioButton);

        // Checked Lister
        weightGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == loseWeightRadioButton) {

                    weightGoal = "Lose Weight";
                    System.out.println(weightGoal);

                } else if (checkedId == maintainWeightRadioButton) {

                    weightGoal = "Maintain Weight";
                    System.out.println(weightGoal);

                } else if (checkedId == gainWeightRadioButton) {

                    weightGoal = "Gain Weight";
                    System.out.println(weightGoal);

                }


            }
        });

        activityGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == sedentaryRadioButton) {

                    currentActivity = "Sedentary";
                    System.out.println(currentActivity);

                } else if (checkedId == lightExerciseRadioButton) {

                    currentActivity = "Light Exercise";
                    System.out.println(currentActivity);

                } else if (checkedId == moderateExerciseRadioButton) {


                    currentActivity = "Moderate Exercise";
                    System.out.println(currentActivity);

                } else if (checkedId == hardExerciseRadioButton) {

                    currentActivity = "Hard Exercise";
                    System.out.println(currentActivity);

                } else if (checkedId == veryHardExerciseRadioButton) {

                    currentActivity = "Very Moderate Exercise";
                    System.out.println(currentActivity);

                }
            }

        });

    }




    public void toDetailsScreen(View view) {

        if ((loseWeightRadio.isChecked() || maintainWeightRadio.isChecked() ||
                gainWeightRadio.isChecked()) &&
                        (sedentaryWeightRadio.isChecked() || lightWeightRadio.isChecked() ||
                                moderateWeightRadio.isChecked() || hardWeightRadio.isChecked() ||
                                veryHardExerciseRadio.isChecked())) {

            Intent nextScreen = new Intent(WeightGoalsActivity.this, DetailsSignUpActivity.class);


            nextScreen.putExtra("WeightGoal", weightGoal);
            nextScreen.putExtra("CurrentActivity", currentActivity);

            startActivity(nextScreen);

        } else {

            Snackbar.make(view, "please select two radio buttons", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

        }


    }
}

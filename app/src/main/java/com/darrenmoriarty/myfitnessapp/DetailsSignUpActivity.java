package com.darrenmoriarty.myfitnessapp;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import static com.darrenmoriarty.myfitnessapp.R.id.femaleRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.maleRadioButton;

public class DetailsSignUpActivity extends AppCompatActivity {

    private Button mContinueButton;
    private EditText mFullNameField;
    private EditText mBirthdayField;
    private Spinner mHeightSpinner;
    private Spinner mWeightSpinner;
    private RadioGroup mGenderGroup;
    public static String gender;
    public static String BMRString;
    public static String BMIString;
    public static String TDEEString;
    public static String calorieGoal;
    public static String weightGoals;
    public static String currentActivity;
    public static String fullName;
    public static String weight;
    public static String height;

    private String selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_sign_up);

        setTitle("Details Sign up");

        Bundle b =  getIntent().getExtras();

        if (b != null) {

            weightGoals = b.getString("WeightGoal");
            currentActivity = b.getString("CurrentActivity");

            System.out.println(weightGoals + "   " + currentActivity);

        }

        System.out.println("Weight goals" + weightGoals + "   " + "Current" + currentActivity);



        mContinueButton = (Button) findViewById(R.id.continueButton);
        mFullNameField = (EditText) findViewById(R.id.fullnameEditText);
        mBirthdayField = (EditText) findViewById(R.id.birthdateEditText);
        mHeightSpinner = (Spinner) findViewById(R.id.heightSpinner);
        mWeightSpinner = (Spinner) findViewById(R.id.weightSpinner);
        mGenderGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);


        mGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == femaleRadioButton) {

                    selectedGender = "Female";

                } else if (checkedId == maleRadioButton) {

                    selectedGender = "Male";

                }

                gender = selectedGender;

            }
        });



    }

    public int calcBMR(int weightKG, int heightCm, int ageYears, String gender) {


        if (gender.equals("Male")) {

            return (int) (88.362 + (13.397 * weightKG) + (4.799 * heightCm) - (5.677 * ageYears));

        } else {

            return (int) (447.593 + (9.247 * weightKG) + (3.098 * heightCm) - (4.330 * ageYears));
        }

    }

    public int calcBMI(int weightKg, int heightCm) {

        return (weightKg / (heightCm / 10) *  (heightCm / 10));

    }


    public int calcTDEE(String currentActivity, int BMR) {

        if (currentActivity.equals("Sedentary")) {

            return (int) (BMR * 1.2);

        } else if (currentActivity.equals("Light Exercise")) {

            return (int) (BMR * 1.375);

        } else if (currentActivity.equals("Moderate Exercise")) {

            return (int) (BMR * 1.55);

        } else if (currentActivity.equals("Hard Exercise")) {

            return (int) (BMR * 1.725);

        } else {

            return (int) (BMR * 1.9);
        }

    }

    public double calcCalorieGoal(int TDEE, String weightGoals) {

        if (weightGoals.equals("Lose Weight")) {

            return TDEE * .85;

        } else if (weightGoals.equals("Maintain Weight")) {

            return TDEE;

        } else {

            return TDEE * 1.15;

        }

    }


    public int convertToKG(int weight) {

        return (int) (weight / 2.2);

    }

    public int convertToCM(int height) {


        return (int) (height * 2.54);

    }

    public void toActivate(View view) {

        fullName = mFullNameField.getText().toString().trim();
        String birthdate = mBirthdayField.getText().toString().trim();
        height = "6.0";
        weight = "200";
        int BMR = calcBMR(100, 182, 25, gender);
        BMRString = Integer.toString(BMR);
        int BMI = calcBMI(100, 182);
        BMIString = Integer.toString(BMI);
        int TDEE = calcTDEE(currentActivity, BMR);
        TDEEString = Integer.toString(TDEE);
        calorieGoal = Integer.toString((int) calcCalorieGoal(TDEE, weightGoals));


        Intent intent = new Intent(DetailsSignUpActivity.this, ActivateAccountActivity.class);




        System.out.println(BMRString);
        System.out.println(BMIString);
        System.out.println(TDEEString);
        System.out.println(calorieGoal);
        System.out.println(fullName);
        System.out.println(weight);
        System.out.println(height);
        System.out.println(gender);
        System.out.println(currentActivity);
        System.out.println(weightGoals);
        startActivity(intent);

    }
}

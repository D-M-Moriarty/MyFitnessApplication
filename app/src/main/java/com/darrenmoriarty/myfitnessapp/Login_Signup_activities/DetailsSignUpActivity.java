package com.darrenmoriarty.myfitnessapp.Login_Signup_activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.darrenmoriarty.myfitnessapp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import static com.darrenmoriarty.myfitnessapp.R.id.birthdateEditText;
import static com.darrenmoriarty.myfitnessapp.R.id.breakfastRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.femaleRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.heightEditText;
import static com.darrenmoriarty.myfitnessapp.R.id.maleRadioButton;
import static com.darrenmoriarty.myfitnessapp.R.id.setTextView;
import static com.darrenmoriarty.myfitnessapp.R.id.weightEditText;

public class DetailsSignUpActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Button mContinueButton;
    private EditText mFullNameField;
    private EditText mBirthdayField;
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
    public static String birthdate;

    private EditText mWeightEditText;
    private EditText mHeightEditText;

    private Calendar myCalendar;

    private String selectedGender;

    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private View mView;
    private TextView heightWeightTextView;
    private EditText firstEdit;
    private TextView firstTextView;
    private EditText secondEditText;
    private TextView secondTextView;
    private TextView setText;
    private Spinner spinner2;
    private ArrayList<String> metrics = new ArrayList<>();
    private ArrayAdapter mArrayAdapter;

    private boolean isBothFields = false;
    private boolean isHeightField = false;
    private boolean isKg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_sign_up);

        setTitle("Details Sign up");

        Bundle b = getIntent().getExtras();

        if (b != null) {

            weightGoals = b.getString("WeightGoal");
            currentActivity = b.getString("CurrentActivity");

            System.out.println(weightGoals + "   " + currentActivity);

        }

        System.out.println("Weight goals" + weightGoals + "   " + "Current" + currentActivity);

        // TODO make sure the weight and height is validated correctly


        mContinueButton = (Button) findViewById(R.id.continueButton);
        mFullNameField = (EditText) findViewById(R.id.fullnameEditText);
        mBirthdayField = (EditText) findViewById(R.id.birthdateEditText);
        mGenderGroup = (RadioGroup) findViewById(R.id.genderRadioGroup);
        mWeightEditText = (EditText) findViewById(R.id.weightEditText);
        mHeightEditText = (EditText) findViewById(R.id.heightEditText);

        mBirthdayField.setOnClickListener(this);
        mWeightEditText.setOnClickListener(this);
        mHeightEditText.setOnClickListener(this);


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

        mView = getLayoutInflater().inflate(R.layout.height_dialog_layout, null);
        builder = new AlertDialog.Builder(DetailsSignUpActivity.this);
        heightWeightTextView = (TextView) mView.findViewById(R.id.heightWeightTextView);
        firstEdit = (EditText) mView.findViewById(R.id.firstEdit);
        firstTextView = (TextView) mView.findViewById(R.id.firstTextView);
        secondEditText = (EditText) mView.findViewById(R.id.secondEditText);
        secondTextView = (TextView) mView.findViewById(R.id.secondTextView);
        setText = (TextView) mView.findViewById(R.id.setTextView);
        //setText.setOnClickListener(this);
        spinner2 = (Spinner) mView.findViewById(R.id.spinner2);
        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metrics);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(mArrayAdapter);

        builder.setView(mView);
        alert = builder.create();


    }

    public int calcBMR(int weightKG, int heightCm, int ageYears, String gender) {


        if (gender.equals("Male")) {

            return (int) (88.362 + (13.397 * weightKG) + (4.799 * heightCm) - (5.677 * ageYears));

        } else {

            return (int) (447.593 + (9.247 * weightKG) + (3.098 * heightCm) - (4.330 * ageYears));
        }

    }

    public int calcBMI(int weightKg, int heightCm) {

        return (weightKg / (heightCm / 10) * (heightCm / 10));

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

    public String convertWeighttoKg(String weight) {

        if (weight.length() > 8) {

            String stonePart = "";

            int pos = 0;

            for (int i = 0; i < weight.length(); i++) {

                if (!Character.isDigit(weight.charAt(i))) {
                    pos = i;
                    break;
                }

            }

            for (int i = 0; i < pos; i++) {

                stonePart += weight.charAt(i);

            }

            System.out.println(stonePart);

            String lbsPart = "";

            if (stonePart.length() > 1) {
                lbsPart += weight.charAt(10);

                if (Character.isDigit(weight.charAt(11)))
                    lbsPart += weight.charAt(11);

            } else {

                lbsPart += weight.charAt(9);

                if (Character.isDigit(weight.charAt(10)))
                    lbsPart += weight.charAt(10);


            }

            if (lbsPart.length() > 1) {

                int stone = Integer.parseInt(stonePart);

                int stoneTolbs = stone * 14;

                int lbs = Integer.parseInt(lbsPart);

                System.out.println(stoneTolbs);

                System.out.println(lbs);

                lbs += stoneTolbs;

                System.out.println(lbs);

                double kg = lbs / 2.2;

                System.out.println((int)kg);

                return Integer.toString((int) kg);

            } else {

                int stone = Integer.parseInt(stonePart);

                int stoneTolbs = stone * 14;

                int lbs = Integer.parseInt(String.valueOf(lbsPart.charAt(0)));

                System.out.println(stoneTolbs);

                System.out.println(lbs);

                lbs += stoneTolbs;

                System.out.println(lbs);

                double kg = lbs / 2.2;

                System.out.println((int)kg);

                return Integer.toString((int) kg);

            }





        } else if (weight.charAt(weight.length() - 1) == 's') {

            String lbsS = "";

            for (int i = 0; i < weight.length(); i++) {

                if (Character.isDigit(weight.charAt(i))) {

                    lbsS += weight.charAt(i);

                }

            }

            int lbs = Integer.parseInt(lbsS);

            System.out.println(lbs);

            double kg = lbs / 2.2;


            return Integer.toString((int) kg);


        } else {

            String kgS = "";

            for (int i = 0; i < weight.length(); i++) {

                if (Character.isDigit(weight.charAt(i))) {

                    kgS += weight.charAt(i);

                }

            }

            int kg = Integer.parseInt(kgS);

            System.out.println(kg);

            return Integer.toString(kg);


        }


    }

    public String convertHeighttoCm(String height) {

        String num = "";

        if (height.charAt(height.length() - 1) == 'n') {

            //0 ft, 0 in

            String in = "";

            char feet = height.charAt(0);
            in += height.charAt(6);

            if (height.length() > 10) {
                in += height.charAt(7);
            }

            int inches = Integer.parseInt(in);

            int feetInch = Character.getNumericValue(feet);

            int feetConverted = feetInch * 12;

            int allInches = feetConverted + inches;


            double allInchtoCm = allInches * 2.54;


            return Integer.toString((int) allInchtoCm);



        } else {

            int pos = 0;

            for (int i = 0; i < height.length(); i++) {

                if (height.charAt(i) == ' ') {

                    pos = i;

                }

            }

            String numCm = "";

            for (int i = 0; i < pos; i++) {

                numCm += height.charAt(i);

            }

            return numCm;

        }

    }


    public int convertToKG(int weight) {

        return (int) (weight / 2.2);

    }

    public int convertToCM(int height) {


        return (int) (height * 2.54);

    }

    public void toActivate(View view) {

        int realWeight = Integer.parseInt(convertWeighttoKg(mWeightEditText.getText().toString()));
        int realHeight = Integer.parseInt(convertHeighttoCm(mHeightEditText.getText().toString()));

        System.out.println(realHeight + " real height");
        System.out.println(realWeight + " real weight");

        fullName = mFullNameField.getText().toString().trim();
        birthdate = mBirthdayField.getText().toString().trim();
        height = convertHeighttoCm(mHeightEditText.getText().toString());
        weight = convertWeighttoKg(mWeightEditText.getText().toString());
        int BMR = calcBMR(realWeight, realHeight, 25, gender);
        BMRString = Integer.toString(BMR);
        int BMI = calcBMI(realWeight, realHeight);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String date = dayOfMonth + "/" + (month + 1) + "/" + year;

        mBirthdayField.setText(date);

    }

    public void setClick(View view) {

        if (alert != null && alert.isShowing())
        {

            if (isHeightField) {

                if (isBothFields) {

                    if (!firstEdit.getText().toString().equals("") && !secondEditText.getText().toString().equals("")) {

                        mHeightEditText.setText(firstEdit.getText().toString() + " ft, " + secondEditText.getText().toString() + " in");

                    } else if (secondEditText.getText().toString().equals("")) {

                        mHeightEditText.setText(firstEdit.getText().toString() + " ft, 0 in");

                    } else if (firstEdit.getText().toString().equals("")){

                        mHeightEditText.setText("0 ft, " + secondEditText.getText().toString() + " in");

                    }



                } else {

                    mHeightEditText.setText(firstEdit.getText().toString() + " cm");

                }

            } else {


                if (isBothFields) {

                    if (!firstEdit.getText().toString().equals("") && !secondEditText.getText().toString().equals("")) {

                        mWeightEditText.setText(firstEdit.getText().toString() + " stone, " +
                                secondEditText.getText().toString() + " lbs");

                    } else if (secondEditText.getText().toString().equals("")) {

                        mWeightEditText.setText(firstEdit.getText().toString() + " stone, 0 lbs");

                    } else {

                        mWeightEditText.setText("0 stone, " +
                                secondEditText.getText().toString() + " lbs");

                    }



                } else {

                    if (isKg) {


                        mWeightEditText.setText(firstEdit.getText().toString() + " kg");

                    } else {

                        mWeightEditText.setText(firstEdit.getText().toString() + " lbs");

                    }


                }

            }


            alert.cancel();
        }


    }

    @Override
    public void onClick(View v) {

        firstEdit.setText("");
        secondEditText.setText("");


        switch (v.getId()) {

            case birthdateEditText:
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
                myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
                myCalendar.set(Calendar.DAY_OF_MONTH, myCalendar.get(Calendar.DAY_OF_MONTH));

                DatePickerDialog dialog = new DatePickerDialog(this, this, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                break;

            // when the weight edit text is selected the alert dialog i shown
            case weightEditText:

                heightWeightTextView.setText("Weight");

                isHeightField = false;

                metrics.clear();
                metrics.add("lbs");
                metrics.add("kg");
                metrics.add("stone");

                spinner2.setAdapter(mArrayAdapter);

                // Spinner click listener
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {

                            case 0:
                                secondEditText.setVisibility(View.INVISIBLE);
                                secondTextView.setVisibility(View.INVISIBLE);
                                firstTextView.setText("lbs");

                                firstEdit.setText("");
                                secondEditText.setText("");

                                int maxLength = 3;
                                InputFilter[] FilterArray = new InputFilter[1];
                                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                                firstEdit.setFilters(FilterArray);
                                isBothFields = true;

                                isBothFields = false;

                                isKg = false;

                                if (!firstEdit.getText().toString().equals("")) {

                                    mWeightEditText.setText(firstEdit.getText().toString() + " lbs");

                                } else {

                                    mWeightEditText.setText("0 lbs");

                                }

                                break;
                            case 1:
                                secondEditText.setVisibility(View.INVISIBLE);
                                secondTextView.setVisibility(View.INVISIBLE);
                                firstTextView.setText("kg");

                                firstEdit.setText("");
                                secondEditText.setText("");

                                maxLength = 3;
                                FilterArray = new InputFilter[1];
                                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                                firstEdit.setFilters(FilterArray);
                                isBothFields = true;

                                isBothFields = false;

                                isKg = true;

                                if (!firstEdit.getText().toString().equals("")) {

                                    mWeightEditText.setText(firstEdit.getText().toString() + " kg");

                                } else {

                                    mWeightEditText.setText("0 kg");

                                }

                                break;
                            case 2:
                                secondEditText.setVisibility(View.VISIBLE);
                                secondTextView.setVisibility(View.VISIBLE);
                                firstTextView.setText("stone");
                                secondTextView.setText("lbs");

                                firstEdit.setText("");
                                secondEditText.setText("");

                                maxLength = 2;
                                FilterArray = new InputFilter[1];
                                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                                firstEdit.setFilters(FilterArray);
                                isBothFields = true;

                                isBothFields = true;

                                isKg = false;

                                if (!firstEdit.getText().toString().equals("")) {

                                    mWeightEditText.setText(firstEdit.getText().toString() + " stone" +
                                            secondEditText.getText().toString() + " lbs");

                                } else {

                                    mWeightEditText.setText("0 stone, 0 lbs");

                                }


                                break;


                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                alert.show();

                break;

            case heightEditText:

                isHeightField = true;

                heightWeightTextView.setText("Height");

                metrics.clear();
                metrics.add("feet & inches");
                metrics.add("cm");

                isKg = false;

                spinner2.setAdapter(mArrayAdapter);

                // Spinner click listener
                spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        switch (position) {

                            case 0:
                                secondEditText.setVisibility(View.VISIBLE);
                                secondTextView.setVisibility(View.VISIBLE);

                                firstEdit.setText("");
                                secondEditText.setText("");

                                firstTextView.setText("ft");

                                secondTextView.setText("in");

                                int maxLength = 1;
                                InputFilter[] FilterArray = new InputFilter[1];
                                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                                firstEdit.setFilters(FilterArray);
                                isBothFields = true;

                                if (!secondEditText.getText().toString().equals("")) {

                                    mHeightEditText.setText(firstEdit + " ft," + secondEditText + " in");

                                } else {

                                    mHeightEditText.setText("0 ft, 0 in");

                                }

                                break;
                            case 1:
                                secondEditText.setVisibility(View.INVISIBLE);
                                secondTextView.setVisibility(View.INVISIBLE);
                                firstTextView.setText("cm");

                                firstEdit.setText("");
                                secondEditText.setText("");

                                maxLength = 3;
                                FilterArray = new InputFilter[1];
                                FilterArray[0] = new InputFilter.LengthFilter(maxLength);
                                firstEdit.setFilters(FilterArray);
                                isBothFields = true;

                                isBothFields = false;

                                if (!secondEditText.getText().toString().equals("")) {

                                    mHeightEditText.setText(firstEdit + " cm");

                                } else {

                                    mHeightEditText.setText("0 cm");

                                }

                                break;


                        }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {



                    }
                });


                alert.show();

                break;


        }



    }


}

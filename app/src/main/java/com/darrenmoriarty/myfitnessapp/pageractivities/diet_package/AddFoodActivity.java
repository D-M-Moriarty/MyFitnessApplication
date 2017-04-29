package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.R;

import java.util.ArrayList;
import java.util.List;

public class AddFoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> values;
    private String name;
    private TextView nameTextView;
    private Spinner spinner;
    // Spinner Drop down elements
    private List<String> servingSizes;
    private ArrayAdapter mArrayAdapter;
    private TextView energyValue;
    private TextView proteinValue;
    private TextView fatValue;
    private TextView carbValue;
    private EditText qtyEditText;

    private int energyInt;
    private double proteinDouble;
    private double fatDouble;
    private double carbDouble;
    private int qtyMultiplier;

    private Button logThisButton;
    private Button logMoreButton;

    private String meal;

    private boolean divideBy100 = false;

    // TODO fix the issue with selecting 2 radio buttons


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        logThisButton = (Button) findViewById(R.id.logFoodButton);
        logMoreButton = (Button) findViewById(R.id.logAddMoreButton);

        try {

            Bundle extras = getIntent().getExtras();

            if (extras != null) {

                meal = extras.getString("meal");

                System.out.println("Add food activity " + meal);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        values = (ArrayList<String>) getIntent().getSerializableExtra("valueArray");
        name = getIntent().getStringExtra("name");

        nameTextView = (TextView) findViewById(R.id.foodNameTextView);
        qtyEditText = (EditText) findViewById(R.id.qtyEditText);

        qtyEditText.setText("1");
        qtyMultiplier = Integer.parseInt(String.valueOf(qtyEditText.getText()));



        // listening for text change
        qtyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!divideBy100) {

                    // setting numeric values to change later
                    energyInt = Integer.parseInt(values.get(0));
                    proteinDouble = Double.parseDouble(values.get(1));
                    fatDouble = Double.parseDouble(values.get(3));
                    carbDouble = Double.parseDouble(values.get(4));

                } else {

                    // setting numeric values to change later
                    energyInt = Integer.parseInt(values.get(0));
                    proteinDouble = Double.parseDouble(values.get(1));
                    fatDouble = Double.parseDouble(values.get(3));
                    carbDouble = Double.parseDouble(values.get(4));

                    energyInt /= 100;
                    proteinDouble /= 100;
                    fatDouble /= 100;
                    carbDouble /= 100;

                }


                if (!String.valueOf(qtyEditText.getText()).equals("")) {

                    qtyMultiplier = Integer.parseInt(String.valueOf(qtyEditText.getText()));
                    energyInt *= qtyMultiplier;
                    proteinDouble *= qtyMultiplier;
                    fatDouble *= qtyMultiplier;
                    carbDouble *= qtyMultiplier;

                    energyValue.setText(energyInt + " cals");
                    proteinValue.setText(String.format("%.2f",proteinDouble) + "\nProtein(g)");
                    //sugarValue.setText(values.get(2));
                    fatValue.setText(String.format("%.2f",fatDouble) + "\nFat(g)");
                    carbValue.setText(String.format("%.2f",carbDouble) + "\nCarbs(g)");


                }




            }
        });



        nameTextView.setText(name);

        // spinner
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        servingSizes = new ArrayList<>();
        servingSizes.add("100g");
        servingSizes.add("1g");

        mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, servingSizes);

        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(mArrayAdapter);

        energyValue = (TextView) findViewById(R.id.caloriestextView);
        proteinValue = (TextView) findViewById(R.id.proteinTextView);
        //TextView sugarValue = (TextView) findViewById(R.id.sugarValue);
        fatValue = (TextView) findViewById(R.id.fatTextView);
        carbValue = (TextView) findViewById(R.id.carbTextView);

        energyValue.setText(values.get(0) + " cals");
        proteinValue.setText(values.get(1) + "\nProtein(g)");
        //sugarValue.setText(values.get(2));
        fatValue.setText(values.get(3) + "\nFat(g)");
        carbValue.setText(values.get(4) + "\nCarbs(g)");

        // setting numeric values to change later
        energyInt = Integer.parseInt(values.get(0));
        proteinDouble = Double.parseDouble(values.get(1));
        fatDouble = Double.parseDouble(values.get(3));
        carbDouble = Double.parseDouble(values.get(4));

        spinner.setSelection(0);

    }

    // clicking off the editText
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    // log the selected food
    public void logThisFood(View view) {


        Intent intent = new Intent(getApplicationContext(), DietDiaryActivity.class);

        intent.putExtra("FoodName", name);
        intent.putExtra("Calories", Integer.toString(energyInt));
        intent.putExtra("meal", meal);

        startActivity(intent);

        finish();


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:

                divideBy100 = false;

                // setting numeric values to change later
                energyInt = Integer.parseInt(values.get(0));
                proteinDouble = Double.parseDouble(values.get(1));
                fatDouble = Double.parseDouble(values.get(3));
                carbDouble = Double.parseDouble(values.get(4));
                energyInt *= qtyMultiplier;
                proteinDouble *= qtyMultiplier;
                fatDouble *= qtyMultiplier;
                carbDouble *= qtyMultiplier;
                energyValue.setText(energyInt + " cals");
                proteinValue.setText(String.format("%.2f",proteinDouble) + "\nProtein(g)");
                //sugarValue.setText(values.get(2));
                fatValue.setText(String.format("%.2f",fatDouble) + "\nFat(g)");
                carbValue.setText(String.format("%.2f",carbDouble) + "\nCarbs(g)");


                break;
            case 1:

                divideBy100 = true;

                energyInt /= 100;
                proteinDouble /= 100;
                fatDouble /= 100;
                carbDouble /= 100;
                energyValue.setText(energyInt + " cals");
                proteinValue.setText(String.format("%.2f",proteinDouble) + "\nProtein(g)");
                //sugarValue.setText(values.get(2));
                fatValue.setText(String.format("%.2f",fatDouble) + "\nFat(g)");
                carbValue.setText(String.format("%.2f",carbDouble) + "\nCarbs(g)");

                break;


        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

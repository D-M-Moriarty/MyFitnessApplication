package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.R;
import com.darrenmoriarty.myfitnessapp.pageractivities.MainHomePagerActivity;

import java.util.ArrayList;

public class DietDiaryActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView breakfastListView;
    private ListView lunchListView;
    private ListView dinnerListView;
    private ListView morningSnackListView;
    private TextView calorieGoalTextView;
    private TextView consumedCaloriesTextView;
    private TextView remainingCaloriesTextView;
    private TextView addBreakfastTextView;
    private TextView addLunchTextView;
    private TextView addDinnerTextView;
    private TextView addMorningSnackTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_diary);


        breakfastListView = (ListView) findViewById(R.id.breakfastListView);
        lunchListView = (ListView) findViewById(R.id.lunchListView);
        dinnerListView = (ListView) findViewById(R.id.dinnerListView);
        morningSnackListView = (ListView) findViewById(R.id.morningSnackListView);
        calorieGoalTextView = (TextView) findViewById(R.id.goalCaloriesTextView);
        consumedCaloriesTextView  = (TextView) findViewById(R.id.consumedCaloriesTextView);
        remainingCaloriesTextView = (TextView) findViewById(R.id.remainingCaloriesTextView);
        addBreakfastTextView = (TextView) findViewById(R.id.AddBreakfast);
        addLunchTextView = (TextView) findViewById(R.id.addLunch);
        addDinnerTextView = (TextView) findViewById(R.id.addDinner);
        addMorningSnackTextView = (TextView) findViewById(R.id.addMorningSnack);

        addBreakfastTextView.setOnClickListener(this);
        addLunchTextView.setOnClickListener(this);
        addDinnerTextView.setOnClickListener(this);
        addMorningSnackTextView.setOnClickListener(this);

        ArrayList foods = new ArrayList();

        foods.add("White Bread");

        ArrayAdapter mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foods);

        breakfastListView.setAdapter(mArrayAdapter);
        lunchListView.setAdapter(mArrayAdapter);
        dinnerListView.setAdapter(mArrayAdapter);
        morningSnackListView.setAdapter(mArrayAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            System.out.println(calorieGoalTextView.getText().toString());

            calorieGoalTextView.setText(calorieGoalTextView.getText().toString());
        }
        catch (Exception e) {

            e.printStackTrace();

        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.AddBreakfast:

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class));

                break;

            case R.id.addLunch:

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class));

                break;

            case R.id.addDinner:

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class));

                break;

            case R.id.addMorningSnack:

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class));

                break;


        }

    }
}

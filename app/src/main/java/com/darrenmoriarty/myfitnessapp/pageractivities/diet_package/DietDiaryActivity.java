package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

        ArrayList foods = new ArrayList();

        try {

            Bundle extras = getIntent().getExtras();

            System.out.println("Extras " + extras);

            System.out.println("Inside the try");
            if (extras != null) {
                String name = extras.getString("FoodName");
                //The key argument here must match that used in the other activity

                String calories = extras.getString("Calories");

                System.out.println("The food name is " + name);

                FoodList.foodList.add(name);

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, FoodList.foodList);

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

        //System.out.println(breakfastListView.get);

        // probably not a great way
        ViewGroup.LayoutParams params = breakfastListView.getLayoutParams();
        params.height *= FoodList.foodList.size();
        breakfastListView.setLayoutParams(params);
        breakfastListView.requestLayout();

        addBreakfastTextView.setOnClickListener(this);
        addLunchTextView.setOnClickListener(this);
        addDinnerTextView.setOnClickListener(this);
        addMorningSnackTextView.setOnClickListener(this);

        breakfastListView.setAdapter(mArrayAdapter);
        lunchListView.setAdapter(mArrayAdapter);
        dinnerListView.setAdapter(mArrayAdapter);
        morningSnackListView.setAdapter(mArrayAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            calorieGoalTextView.setText(MainHomePagerActivity.calorieGoal);
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

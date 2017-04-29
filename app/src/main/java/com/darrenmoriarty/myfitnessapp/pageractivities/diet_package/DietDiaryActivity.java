package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.Login_Signup_activities.MyUser;
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

    private ArrayAdapter mArrayAdapter;
    private ArrayAdapter mArrayAdapter2;
    private ArrayAdapter mArrayAdapter3;
    private ArrayAdapter mArrayAdapter4;




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
                String foodName = extras.getString("FoodName");
                //The key argument here must match that used in the other activity

                // limiting the size of the string
                if (foodName.length() > 30)
                    foodName = foodName.substring(0, 30);

                String calories = extras.getString("Calories");

                int cal = Integer.parseInt(calories);

                FoodList.calories += cal;

                System.out.println(calories);

                String meal = extras.getString("meal");

                System.out.println("The food name is " + foodName);

                System.out.println(meal);

                if (meal.equals("breakfast")) {

                    FoodList.breakfastList.add(foodName);
                    mArrayAdapter.notifyDataSetChanged();

                } else if (meal.equals("lunch")) {

                    FoodList.lunchList.add(foodName);

                } else if (meal.equals("dinner")) {

                    FoodList.dinnerList.add(foodName);

                } else {

                    FoodList.morningSnackList.add(foodName);

                }

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, FoodList.breakfastList);
        mArrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, FoodList.lunchList);
        mArrayAdapter3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, FoodList.dinnerList);
        mArrayAdapter4 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, FoodList.morningSnackList);



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
        params.height *= FoodList.breakfastList.size();
        breakfastListView.setLayoutParams(params);
        breakfastListView.requestLayout();

        params = lunchListView.getLayoutParams();
        params.height *= FoodList.lunchList.size();
        lunchListView.setLayoutParams(params);
        lunchListView.requestLayout();

        params = dinnerListView.getLayoutParams();
        params.height *= FoodList.dinnerList.size();
        dinnerListView.setLayoutParams(params);
        dinnerListView.requestLayout();

        params = morningSnackListView.getLayoutParams();
        params.height *= FoodList.morningSnackList.size();
        morningSnackListView.setLayoutParams(params);
        morningSnackListView.requestLayout();

        addBreakfastTextView.setOnClickListener(this);
        addLunchTextView.setOnClickListener(this);
        addDinnerTextView.setOnClickListener(this);
        addMorningSnackTextView.setOnClickListener(this);

        breakfastListView.setAdapter(mArrayAdapter);
        lunchListView.setAdapter(mArrayAdapter2);
        dinnerListView.setAdapter(mArrayAdapter3);
        morningSnackListView.setAdapter(mArrayAdapter4);


    }

    @Override
    protected void onStart() {
        super.onStart();

        try {

            int goalCals = Integer.parseInt(MainHomePagerActivity.calorieGoal);

            int remaining = goalCals - FoodList.calories;

            if (remaining < 0) {
                remainingCaloriesTextView.setTextColor(Color.RED);
            }

            remainingCaloriesTextView.setText(Integer.toString(remaining));

            consumedCaloriesTextView.setText(Integer.toString(FoodList.calories));

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

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class).putExtra("meal", "breakfast"));

                break;

            case R.id.addLunch:

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class).putExtra("meal", "lunch"));

                break;

            case R.id.addDinner:

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class).putExtra("meal", "dinner"));

                break;

            case R.id.addMorningSnack:

                startActivity(new Intent(getApplicationContext(), FoodSearchActivity.class).putExtra("meal", "morningSnack"));

                break;


        }

        finish();

    }
}

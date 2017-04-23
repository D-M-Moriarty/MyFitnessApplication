package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.R;
import com.darrenmoriarty.myfitnessapp.pageractivities.MainHomePagerActivity;

import java.util.ArrayList;

public class DietDiaryActivity extends AppCompatActivity {

    private ListView breakfastListView;
    private ListView lunchListView;
    private ListView dinnerListView;
    private ListView morningSnackListView;
    private TextView calorieGoalTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_diary);


        breakfastListView = (ListView) findViewById(R.id.breakfastListView);
        lunchListView = (ListView) findViewById(R.id.lunchListView);
        dinnerListView = (ListView) findViewById(R.id.dinnerListView);
        morningSnackListView = (ListView) findViewById(R.id.morningSnackListView);
        calorieGoalTextView = (TextView) findViewById(R.id.goalCaloriesTextView);

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
}

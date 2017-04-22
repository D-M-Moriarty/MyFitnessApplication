package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darrenmoriarty.myfitnessapp.R;

import java.util.ArrayList;

public class DietDiaryActivity extends AppCompatActivity {

    private ListView breakfastListView;
    private ListView lunchListView;
    private ListView dinnerListView;
    private ListView morningSnackListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_diary);


        breakfastListView = (ListView) findViewById(R.id.breakfastListView);
        lunchListView = (ListView) findViewById(R.id.lunchListView);
        dinnerListView = (ListView) findViewById(R.id.dinnerListView);
        morningSnackListView = (ListView) findViewById(R.id.morningSnackListView);

        ArrayList foods = new ArrayList();

        foods.add("White Bread");

        ArrayAdapter mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, foods);

        breakfastListView.setAdapter(mArrayAdapter);
        lunchListView.setAdapter(mArrayAdapter);
        dinnerListView.setAdapter(mArrayAdapter);
        morningSnackListView.setAdapter(mArrayAdapter);


    }
}

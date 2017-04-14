package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.R;

import java.util.ArrayList;

public class AddFoodActivity extends AppCompatActivity {

    private ArrayList<String> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);


        values = (ArrayList<String>) getIntent().getSerializableExtra("valueArray");
        String name = getIntent().getStringExtra("name");

        TextView nameTextView = (TextView) findViewById(R.id.foodNameTextView);

        nameTextView.setText(name);

        TextView energyValue = (TextView) findViewById(R.id.caloriestextView);
        TextView proteinValue = (TextView) findViewById(R.id.proteinTextView);
        //TextView sugarValue = (TextView) findViewById(R.id.sugarValue);
        TextView fatValue = (TextView) findViewById(R.id.fatTextView);
        TextView carbValue = (TextView) findViewById(R.id.carbTextView);

        energyValue.setText(values.get(0) + " cals");
        proteinValue.setText(values.get(1) + "\nProtein(g)");
        //sugarValue.setText(values.get(2));
        fatValue.setText(values.get(3) + "\nFat(g)");
        carbValue.setText(values.get(4) + "\nCarbs(g)");



    }



}

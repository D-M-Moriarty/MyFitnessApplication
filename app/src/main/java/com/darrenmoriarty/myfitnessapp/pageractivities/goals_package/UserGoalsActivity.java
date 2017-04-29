package com.darrenmoriarty.myfitnessapp.pageractivities.goals_package;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darrenmoriarty.myfitnessapp.Login_Signup_activities.MyUser;
import com.darrenmoriarty.myfitnessapp.R;

import java.util.ArrayList;

public class UserGoalsActivity extends AppCompatActivity {

    private ListView weightGoalsListView;
    private ArrayList<String> weightGoalsArrayList;
    private ArrayAdapter mArrayAdapterWeight;
    private ListView nutritionGoalsListView;
    private ArrayList<String> nutritionGoalsArrayList;
    private ArrayAdapter mArrayAdapterNutrition;

    private MyUser mMyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_goals);
        setTitle("Goals");

        weightGoalsListView = (ListView) findViewById(R.id.weightGoalsListView);
        nutritionGoalsListView = (ListView) findViewById(R.id.nutritionGoalsListView);

        mMyUser = (MyUser) getIntent().getSerializableExtra("MyUser");

        // weight arraylist values
        weightGoalsArrayList = new ArrayList<>();
        weightGoalsArrayList.add("Starting Weight - \t" + mMyUser.weight + "Kg");
        weightGoalsArrayList.add("Current Weight - \t" + mMyUser.weight + "Kg");
        weightGoalsArrayList.add("Weight Goal - \t" + mMyUser.weightGoal);
        weightGoalsArrayList.add("Activity Level - \t" + mMyUser.currentActivity);

        mArrayAdapterWeight = new ArrayAdapter(this, android.R.layout.simple_list_item_1, weightGoalsArrayList);

        weightGoalsListView.setAdapter(mArrayAdapterWeight);


        nutritionGoalsArrayList = new ArrayList<>();

        String calories = mMyUser.calorieGoal;

        int calorieInt = Integer.parseInt(calories);

        int carbsInt = (int) (calorieInt * .4) / 4;
        int proteinInt = (int) (calorieInt * .3) / 4;
        int fatInt = (int) (calorieInt * .3) / 9;

        // nutrition arraylist values
        nutritionGoalsArrayList.add("Calorie Goal - \t" + mMyUser.calorieGoal + " kCal");
        nutritionGoalsArrayList.add("Protein - \t" + proteinInt + " g");
        nutritionGoalsArrayList.add("Carbohydrates - \t" + carbsInt + " g");
        nutritionGoalsArrayList.add("Fat - \t" + fatInt + " g");

        mArrayAdapterNutrition = new ArrayAdapter(this, android.R.layout.simple_list_item_1, nutritionGoalsArrayList);


        nutritionGoalsListView.setAdapter(mArrayAdapterNutrition);
    }
}

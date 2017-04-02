package com.darrenmoriarty.myfitnessapp.Login_Signup_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.darrenmoriarty.myfitnessapp.R;

public class SignUpChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_choice);


        findViewById(R.id.signUpWithEmailButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignUpChoice.this, WeightGoalsActivity.class));

            }
        });

    }
}

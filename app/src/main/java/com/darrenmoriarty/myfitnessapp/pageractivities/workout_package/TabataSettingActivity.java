package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.darrenmoriarty.myfitnessapp.R;

public class TabataSettingActivity extends AppCompatActivity implements View.OnClickListener {

    // GUI elements
    private ImageView prepareMinus;
    private ImageView preparePlus;
    private ImageView workMinus;
    private ImageView workPlus;
    private ImageView restMinus;
    private ImageView restPlus;
    private ImageView cyclesMinus;
    private ImageView cyclesPlus;

    private TextView prepareValue;
    private TextView workValue;
    private TextView restValue;
    private TextView cyclesValue;

    private int intPrepareVal = 10;
    private int intWorkVal = 10;
    private int intRestVal = 10;
    private int intCyclesVal = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata_setting);

        prepareMinus = (ImageView) findViewById(R.id.prepareMinusImg);
        prepareMinus.setOnClickListener(this);

        preparePlus = (ImageView) findViewById(R.id.preparePlusImg);
        preparePlus.setOnClickListener(this);

        workMinus = (ImageView) findViewById(R.id.workMinusImg);
        workMinus.setOnClickListener(this);

        workPlus = (ImageView) findViewById(R.id.workPlusImg);
        workPlus.setOnClickListener(this);

        restPlus = (ImageView) findViewById(R.id.restMinusImg);
        restPlus.setOnClickListener(this);

        restMinus = (ImageView) findViewById(R.id.restPlusImg);
        restMinus.setOnClickListener(this);

        cyclesPlus = (ImageView) findViewById(R.id.cyclesMinusImg);
        cyclesPlus.setOnClickListener(this);

        cyclesMinus = (ImageView) findViewById(R.id.cyclesPlusImg);
        cyclesMinus.setOnClickListener(this);

        prepareValue = (TextView) findViewById(R.id.prepareSecondValue);
        workValue = (TextView) findViewById(R.id.workSecondValue);
        restValue = (TextView) findViewById(R.id.restSecondValue);
        cyclesValue = (TextView) findViewById(R.id.cyclesSecondValue);

        prepareValue.setText(Integer.toString(intPrepareVal));
        workValue.setText(Integer.toString(intWorkVal));
        restValue.setText(Integer.toString(intRestVal));
        cyclesValue.setText(Integer.toString(intCyclesVal));

        // start tabata timer button
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TabataSettingActivity.this, TabataMainActivity.class);
                intent.putExtra("prepareValue", intPrepareVal);
                intent.putExtra("workValue", intWorkVal);
                intent.putExtra("restValue", intRestVal);
                intent.putExtra("cyclesValue", intCyclesVal);

                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {

        // adjusting the values for the timer
        switch (view.getId()) {

            case R.id.prepareMinusImg:
                if (intPrepareVal > 0)
                    intPrepareVal--;
                prepareValue.setText(Integer.toString(intPrepareVal));
                break;

            case R.id.preparePlusImg:
                intPrepareVal++;
                prepareValue.setText(Integer.toString(intPrepareVal));
                break;

            case R.id.workMinusImg:
                if (intWorkVal > 0)
                    intWorkVal--;
                workValue.setText(Integer.toString(intWorkVal));
                break;

            case R.id.workPlusImg:
                intWorkVal++;
                workValue.setText(Integer.toString(intWorkVal));
                break;

            case R.id.restMinusImg:
                if (intRestVal > 0)
                    intRestVal--;
                restValue.setText(Integer.toString(intRestVal));
                break;

            case R.id.restPlusImg:
                intRestVal++;
                restValue.setText(Integer.toString(intRestVal));
                break;

            case R.id.cyclesMinusImg:
                if (intCyclesVal > 0)
                    intCyclesVal--;
                cyclesValue.setText(Integer.toString(intCyclesVal));
                break;

            case R.id.cyclesPlusImg:
                intCyclesVal++;
                cyclesValue.setText(Integer.toString(intCyclesVal));
                break;

        }


    }
}

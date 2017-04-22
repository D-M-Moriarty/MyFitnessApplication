package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.darrenmoriarty.myfitnessapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class RecordFinishActivity extends AppCompatActivity {

    private Button saveWorkoutButton;
    private Button discardButton;
    private ArrayList<LatLng> points;
    private String durationS;
    private double distance;
    private EditText runTitle;
    private TextView durationText;
    private TextView distanceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_finish);

        setTitle("Record Finish");

        saveWorkoutButton = (Button) findViewById(R.id.saveButton);
        discardButton = (Button) findViewById(R.id.discardButton);
        runTitle = (EditText) findViewById(R.id.titleEditText);
        durationText = (TextView) findViewById(R.id.theDuration);
        distanceText = (TextView) findViewById(R.id.distanceValue);

        points = getIntent().getParcelableArrayListExtra("points");

        durationS = RunningTracker.durationT;
        distance = getIntent().getDoubleExtra("distance", distance);

        System.out.println("The distance " + distance);
        System.out.println("The duration " + durationS);

        String dist = Double.toString(distance);

        // TODO fix the issue with the string format of the double value
        //dist = String.format(Locale.UK, "%.2f m", dist);

        //System.out.println(String.format(Locale.UK, "%.2f", dist));

        System.out.println(points);

        durationText.setText(durationS);
        distanceText.setText(dist);

        // save the workout details
        saveWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(LatLng latLng : points) {

                    System.out.println(latLng.toString());

                }

                System.out.println(points.toString());

                try {

                    SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("RoutesInfo", MODE_PRIVATE, null);

                    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Routes (RunTitle VARCHAR, Duration VARCHAR, Distance REAL, Coordinates VARCHAR)");

                    sqLiteDatabase.execSQL("INSERT INTO Routes (RunTitle, Duration, Distance, Coordinates) VALUES ('"
                            + runTitle.getText().toString() + "', '" +
                            durationS + "', " + distance + ", '" + points.toString() + "')");

                    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Routes", null);

                    int runTitleInt = cursor.getColumnIndex("RunTitle");
                    int durationInt = cursor.getColumnIndex("Duration");
                    int distanceInt = cursor.getColumnIndex("Distance");
                    int coordinatesInt = cursor.getColumnIndex("Coordinates");

                    cursor.moveToFirst();

                    int i = 1;

                    // checking to see that the information is in the database
                    if (cursor != null && cursor.moveToNext()) {
                        do {
                            System.out.println("This is set " + i);
                            System.out.println("Run title " + cursor.getString(runTitleInt));
                            System.out.println("Duration " + cursor.getString(durationInt));
                            System.out.println("Distance " + cursor.getString(distanceInt));
                            System.out.println("Coordinates " + cursor.getString(coordinatesInt));
                            i++;
                        } while (cursor.moveToNext());
                    }

                    startActivity(new Intent(getApplicationContext(), RunningTracker.class));


                }
                catch (Exception e) {

                    e.printStackTrace();
                }


            }
        });

        // discard the information
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RunningTracker.class));
            }
        });

    }
}

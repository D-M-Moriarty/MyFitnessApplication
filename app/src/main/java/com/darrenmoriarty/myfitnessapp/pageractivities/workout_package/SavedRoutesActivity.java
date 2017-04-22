package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.darrenmoriarty.myfitnessapp.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SavedRoutesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView savedRoutesListView;
    private ArrayAdapter mArrayAdapter;
    private ArrayList<LatLng> loadedRoutes;

    private ArrayList<String> runTitles = new ArrayList<>();
    private ArrayList<String> durations = new ArrayList<>();
    private ArrayList<String> distances = new ArrayList<>();
    private ArrayList<String> coordinates = new ArrayList<>();

    public static String runTitleS;
    public static String durationS;
    public static String distanceS;
    public static String coordinateS;
    private boolean delete;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_routes);

        savedRoutesListView = (ListView) findViewById(R.id.savedRoutesListView);

        loadedRoutes = new ArrayList<>();

//        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, loadedRoutes);
//
//        savedRoutesListView.setAdapter(mArrayAdapter);

        try {

            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("RoutesInfo", MODE_PRIVATE, null);

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
                    //System.out.println("Run title " + cursor.getString(runTitleInt));
                    runTitles.add(cursor.getString(runTitleInt));
                    //System.out.println("Duration " + cursor.getString(durationInt));
                    durations.add(cursor.getString(durationInt));
                    //System.out.println("Distance " + cursor.getString(distanceInt));
                    distances.add(cursor.getString(distanceInt));
                    //System.out.println("Coordinates " + cursor.getString(coordinatesInt));
                    coordinates.add(cursor.getString(coordinatesInt));
                    i++;
                } while (cursor.moveToNext());
            }


        }
        catch (Exception e) {

            e.printStackTrace();
        }

        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, runTitles);

        savedRoutesListView.setAdapter(mArrayAdapter);

        savedRoutesListView.setOnItemClickListener(this);
        savedRoutesListView.setOnItemLongClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println(runTitles.get(position));
        System.out.println(durations.get(position));
        System.out.println(distances.get(position));
        System.out.println(coordinates.get(position));

        Intent intent = new Intent(getApplicationContext(), RouteDetailsMapsActivity.class);

        runTitleS = runTitles.get(position).toString();
        durationS = durations.get(position);
        distanceS = distances.get(position);
        coordinateS = coordinates.get(position);



//
//        intent.putExtra("runTitle", runTitle);
//        intent.putExtra("duration", );
//        intent.putExtra("distance", );
//        intent.putExtra("coordinates", coordinates.get(position));

        startActivity(intent);

    }

    public void makeDelete() {

        delete = true;

        checkToDelete();

    }

    public void makeBuilder(final int position) {


        new AlertDialog.Builder(SavedRoutesActivity.this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Delete")
                .setMessage("Do you want to delete " + runTitles.get(position))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        try {

                            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("RoutesInfo", MODE_PRIVATE, null);

                            System.out.println("Trying to delete " + runTitles.get(position));

                            sqLiteDatabase.execSQL("DELETE FROM Routes WHERE RunTitle = '" + runTitles.get(position) + "'");

                            sqLiteDatabase.close();
                        }
                        catch (Exception e) {

                            e.printStackTrace();
                        }

                        makeDelete();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }

    public void checkToDelete() {

        if (delete) {

            runTitles.remove(pos);

            mArrayAdapter.notifyDataSetChanged();

        }

        delete = false;

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        pos = position;

        makeBuilder(position);

        return true;
    }
}

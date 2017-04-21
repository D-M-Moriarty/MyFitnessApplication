package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.darrenmoriarty.myfitnessapp.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class SavedRoutesActivity extends AppCompatActivity {

    private ListView savedRoutesListView;
    private ArrayAdapter mArrayAdapter;
    private ArrayList<LatLng> loadedRoutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_routes);

        savedRoutesListView = (ListView) findViewById(R.id.savedRoutesListView);

        loadedRoutes = new ArrayList<>();

        mArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, loadedRoutes);

        savedRoutesListView.setAdapter(mArrayAdapter);

    }
}

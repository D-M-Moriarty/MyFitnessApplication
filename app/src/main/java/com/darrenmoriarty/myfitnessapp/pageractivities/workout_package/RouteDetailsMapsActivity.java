package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.darrenmoriarty.myfitnessapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class RouteDetailsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String runTitle;
    private String duration;
    private String distance;
    private String coordinates;
    private ArrayList<LatLng> points = new ArrayList<>();
    private Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        runTitle = SavedRoutesActivity.runTitleS;
        duration = SavedRoutesActivity.durationS;
        distance = SavedRoutesActivity.distanceS;
        coordinates = SavedRoutesActivity.coordinateS;

        ArrayList<String> arrayList = extractLatLngs(coordinates);

        latLngMaker(arrayList);



    }

    // splits the arrayList items and adds them to new latlng arrayList
    private void latLngMaker(ArrayList<String> extract) {

        for (String latlong : extract) {

            String[] latitLongit =  latlong.split(",");
            double latitude = Double.parseDouble(latitLongit[0].trim());
            double longitude = Double.parseDouble(latitLongit[1].trim());

            LatLng latLng = new LatLng(latitude, longitude);

            points.add(latLng);

        }



    }


    // extracts the LatLngs from the String from the database
    private ArrayList<String> extractLatLngs(String coordinates) {

        ArrayList<String> lats = new ArrayList<>();

        String single = "";

        String latLng = coordinates;

        for (int i = 1; i < latLng.length(); i++) {

            if (latLng.charAt(i - 1) == '(') {

                int j = i;

                while (latLng.charAt(j) != ')') {
                    single += latLng.charAt(j);
                    j++;
                }

                lats.add(single);
                single = "";

            }

        }

        return lats;

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // always redraw the line
        redrawLine();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 18));

        mMap.addMarker(new MarkerOptions()
                .position(points.get(0)));

        mMap.addMarker(new MarkerOptions()
                .position(points.get(points.size() - 1)));

    }

    private void redrawLine(){

        //mMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(10).color(Color.GREEN).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        line = mMap.addPolyline(options); //add Polyline
    }
}

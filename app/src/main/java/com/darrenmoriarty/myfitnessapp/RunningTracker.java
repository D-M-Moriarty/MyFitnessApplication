package com.darrenmoriarty.myfitnessapp;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.rotation;

public class RunningTracker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    // GUI elements
    private TextView durationText;
    private TextView distanceText;
    private Button startDurationTimerBtn;
    private Chronometer mChronometer;
    private TextView distanceValueText;

    // Timer attributes
    private Timer timer;
    private int incrementValue = 1;

    // boolean to start drawing the polylines
    private boolean isDrawing = false;

    //distance
    private double distance = 0;
    private String distString;

    // Array list for storing the LatLngs and plotting the polylines
    private ArrayList<LatLng> points;
    private Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_tracker);

        points = new ArrayList<LatLng>(); //added

        durationText = (TextView) findViewById(R.id.durationTextView);
        distanceText = (TextView) findViewById(R.id.distanceTextView);
        startDurationTimerBtn = (Button) findViewById(R.id.startWorkoutButton);
        mChronometer = (Chronometer) findViewById(R.id.chronometerTimer);
        distanceValueText = (TextView) findViewById(R.id.distanceValueTextView);

        distanceValueText.setText("0.00 m");


        // setting the value of the chronometer
        mChronometer.setBase(SystemClock.elapsedRealtime());

        startDurationTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isDrawing) {

                    mChronometer.start();

                    //trackDuration();
                    startDurationTimerBtn.setText("Pause Workout");

                    isDrawing = true;

                } else {

                    isDrawing = false;
                    mChronometer.stop();
                    startDurationTimerBtn.setText("Resume Workout");

                }


            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //
    public void centerMapOnLocation(Location location, String title) {

        LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.clear();

        if (title != "Your location") {

            //mMap.addMarker(new MarkerOptions().position(userLocation).title(title).anchor(0.5f,0.5f)
                    //.flat(true)).setRotation(rotation);


        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17));

    }



    // A stopwatch to track users exercise duration
    public void trackDuration() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                incrementValue++;
                durationText.setText(Integer.toString(incrementValue) + "");
            }
        }, 0 , 1000);


    }

    // calculating the distance travelled
    //http://stackoverflow.com/questions/14394366/find-distance-between-two-points-on-map-using-google-map-api-v2
    // calculate the distance between each update and add to arraylist
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);

        return km;
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

        // found this after loads of work !!!!!
        mMap.setMyLocationEnabled(true);
        mMap.addPolyline(new PolylineOptions().width(10).color(Color.BLUE).geodesic(true));

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(lastKnownLocation != null) {

            LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

            mMap.clear();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

            centerMapOnLocation(lastKnownLocation, "Your location");

        }



        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                // Add a marker in Sydney and move the camera
                LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());




                // clearing the map
                mMap.clear();
               // mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location")).setRotation(rotation);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

//                Circle circle = mMap.addCircle(new CircleOptions()
//                        .center(userLocation)
//                        .radius(10)
//                        .strokeColor(Color.rgb(201, 239, 246))
//                        .fillColor(Color.argb(100, 201, 239, 246)));

                // checking if workout has started
                if (isDrawing) {

                    // add new points to the arraylist while tracking
                    points.add(userLocation);

                    // if there are more than 2 points start calculating the distance travelled
                    if (points.size() > 1)  {

                        distance += CalculationByDistance(points.get(points.size() - 2), points.get(points.size() - 1));

                        System.out.println(String.format("%.2f",distance));

                        //distString = String.format("%.2f",Double.toString(distance));

                        distanceValueText.setText(String.format(Locale.UK, "%.2f m", distance));

                    }

                }

                // always redraw the line
                redrawLine();


                //System.out.println((int) CalculationByDistance(points.get(0), userLocation));

                //Toast.makeText(RunningTracker.this, (int) CalculationByDistance(points.get(0), userLocation), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

                lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(lastKnownLocation != null) {

                    LatLng userLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                    mMap.clear();  //to remove last known location
                    //  mMap.setMyLocationEnabled(true);
//                    mMap.addMarker(new MarkerOptions().position(userLocation).title("My Location")
//                            .anchor(0.5f,0.5f)
//                            .flat(true)).setRotation(rotation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

                }
            }
        }

    }

    private void redrawLine(){

        //mMap.clear();  //clears all Markers and Polylines

        PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        line = mMap.addPolyline(options); //add Polyline
    }

}

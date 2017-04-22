package com.darrenmoriarty.myfitnessapp.pageractivities.workout_package;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.darrenmoriarty.myfitnessapp.*;
import com.darrenmoriarty.myfitnessapp.R;
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
import static android.R.attr.start;
import static android.R.attr.streamType;

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
    private Button holdToFinishButton;
    private Button resumeButton;
    private CardView loadRoutesCardView;

    public static String durationT = "";

    // Timer attributes
    private Timer timer;
    private int incrementValue = 1;
    private boolean timerStarted = false;
    private long timeWhenStopped = 0;

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
        setContentView(com.darrenmoriarty.myfitnessapp.R.layout.activity_running_tracker);

        points = new ArrayList<LatLng>(); //added

        durationText = (TextView) findViewById(R.id.durationTextView);
        distanceText = (TextView) findViewById(R.id.distanceTextView);
        startDurationTimerBtn = (Button) findViewById(R.id.startWorkoutButton);
        mChronometer = (Chronometer) findViewById(R.id.chronometerTimer);
        distanceValueText = (TextView) findViewById(R.id.distanceValueTextView);
        holdToFinishButton = (Button) findViewById(R.id.holdFinishButton);
        resumeButton = (Button) findViewById(R.id.resumeWorkoutButton);
        loadRoutesCardView = (CardView) findViewById(R.id.loadRoutesCardView);

        distanceValueText.setText("0.00 m");


        startDurationTimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // only sets the basetime when the button is pressed but not for any other click
                if (!timerStarted) {

                    // setting the value of the chronometer
                    mChronometer.setBase(SystemClock.elapsedRealtime());

                    timerStarted = true;

                }

                System.out.println("Drwaing " + isDrawing);

                if (!isDrawing) {

                    mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    mChronometer.start();

                    //trackDuration();
                    startDurationTimerBtn.setText("Pause Workout");

                    isDrawing = true;

                } else {

                    isDrawing = false;
                    timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
                    mChronometer.stop();
                    startDurationTimerBtn.setVisibility(View.INVISIBLE);
                    holdToFinishButton.setVisibility(View.VISIBLE);
                    resumeButton.setVisibility(View.VISIBLE);

                }


            }
        });

//        startDurationTimerBtn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                // TODO reset all the attributes
//                // TODO save the users data for the activity
//                // TODO give the option to save the details
//                // TODO save favourite runs
//
//                isDrawing = false;
//                mChronometer.setBase(SystemClock.elapsedRealtime());
//                timeWhenStopped = 0;
//                mChronometer.stop();
//                mChronometer.setText("00:00");
//
//                distanceValueText.setText("0.00 m");
//
//                points.clear();
//
//
//                startDurationTimerBtn.setText("start workout");
//
//
//
//                return true;
//            }
//        });

        // resuming the workout
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startDurationTimerBtn.setVisibility(View.VISIBLE);
                holdToFinishButton.setVisibility(View.INVISIBLE);
                resumeButton.setVisibility(View.INVISIBLE);

                mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                mChronometer.start();

                isDrawing = true;

            }
        });

        // finish the workout
        holdToFinishButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // TODO reset all the attributes
                // TODO save the users data for the activity
                // TODO give the option to save the details
                // TODO save favourite runs

                durationT = mChronometer.getText().toString();

                System.out.println("The duration is " + durationT);
                System.out.println("The distance is " + distance);

                isDrawing = false;
                mChronometer.setBase(SystemClock.elapsedRealtime());
                timeWhenStopped = 0;
                mChronometer.stop();
                mChronometer.setText("00:00");

                Intent intent = new Intent(getApplicationContext(), RecordFinishActivity.class);

                //intent.putParcelableArrayListExtra("points", points);
                intent.putExtra("duration", durationT);
                intent.putExtra("distance", distance);

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("points", points);

                intent.putExtras(bundle);

                if (points.size() > 1 && distance > 0)
                    startActivity(intent);
                else
                    Toast.makeText(RunningTracker.this, "Cannot save this activiity", Toast.LENGTH_SHORT).show();


                distanceValueText.setText("0.00 m");

                points.clear();


                startDurationTimerBtn.setText("start workout");

                startDurationTimerBtn.setVisibility(View.VISIBLE);
                holdToFinishButton.setVisibility(View.INVISIBLE);
                resumeButton.setVisibility(View.INVISIBLE);



                return true;
            }
        });

        loadRoutesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SavedRoutesActivity.class));
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


//                Circle circle = mMap.addCircle(new CircleOptions()
//                        .center(userLocation)
//                        .radius(10)
//                        .strokeColor(Color.rgb(201, 239, 246))
//                        .fillColor(Color.argb(100, 201, 239, 246)));

                // checking if workout has started
                if (isDrawing) {

                    // TODO make sure the camera isn't following the user while not drawing
                    // clearing the map
                    mMap.clear();
                    // mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location")).setRotation(rotation);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

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



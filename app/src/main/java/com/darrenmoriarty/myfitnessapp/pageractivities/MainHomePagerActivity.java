package com.darrenmoriarty.myfitnessapp.pageractivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.darrenmoriarty.myfitnessapp.Login_Signup_activities.MyUser;
import com.darrenmoriarty.myfitnessapp.Login_Signup_activities.WelcomeActivity;
import com.darrenmoriarty.myfitnessapp.R;
import com.darrenmoriarty.myfitnessapp.pageractivities.diet_package.DietDiaryActivity;
import com.darrenmoriarty.myfitnessapp.pageractivities.diet_package.DietTab;
import com.darrenmoriarty.myfitnessapp.pageractivities.diet_package.FoodSearchActivity;
import com.darrenmoriarty.myfitnessapp.pageractivities.goals_package.GoalsTab;
import com.darrenmoriarty.myfitnessapp.pageractivities.goals_package.UserGoalsActivity;
import com.darrenmoriarty.myfitnessapp.pageractivities.workout_package.RunningTracker;
import com.darrenmoriarty.myfitnessapp.pageractivities.workout_package.TabataSettingActivity;
import com.darrenmoriarty.myfitnessapp.pageractivities.workout_package.WorkoutsTab;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static android.R.attr.name;
import static android.R.attr.start;
import static com.darrenmoriarty.myfitnessapp.R.string.email;

public class MainHomePagerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Button startTracking;
    private Button startTabataTimer;
    private Button testDatabase;
    private View header;
    private TextView name;
    private TextView email;


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [START declare_auth_listener]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog mProgressDialog;


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private String userID;
    public static String calorieGoal;
    public String fullname;
    private MyUser myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home_pager);


        mAuth = FirebaseAuth.getInstance();

        // Write a message to the database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        //mDatabaseReference = mFirebaseDatabase.getReference();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Users");


        // [START auth_state_listener]
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                // getting the current user
                FirebaseUser user = firebaseAuth.getCurrentUser();

                // set userid out here
                userID = user.getUid();

                if (user != null) {
                    // User is signed in
                    Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());

                    // changing to the account activity
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    // User is signed out
                    Log.d("TAG", "onAuthStateChanged:signed_out");

                    startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                }
            }
        };
        // [END auth_state_listener]

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myUser = dataSnapshot.child(userID).child("userinfo").getValue(MyUser.class);
                System.out.println(myUser.toString());

                calorieGoal = myUser.calorieGoal;

                // drawer items
                name.setText(myUser.username);
                email.setText(myUser.emailAddress);
                // end drawer items

                Toast.makeText(MainHomePagerActivity.this, "Hey Good to see you " + myUser.username, Toast.LENGTH_SHORT).show();

                //System.out.println(myUser.username);

                //String value = dataSnapshot.child(userID).child("userinfo").child("username").getValue(String.class);

                //System.out.println(value);


//                Map<String, String> userMap = dataSnapshot.getValue(Map.class);
//
//                String fullName = userMap.get(userID);
////
//                Log.d("TAG", "The users BMI is: " + fullName);

                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//
//                String BMI = dataSnapshot.child(userID).child("userinfo").child("BMI").getValue(String.class);
//                fullname = dataSnapshot.child(userID).child("userinfo").child("Fullname").getValue(String.class);
//                String Birthdate = dataSnapshot.child(userID).child("userinfo").child("Birthdate").getValue(String.class);
//                String gender = dataSnapshot.child(userID).child("userinfo").child("Gender").getValue(String.class);
//                calorieGoal = dataSnapshot.child(userID).child("userinfo").child("Calorie Goal").getValue(String.class);
//
//                String username = dataSnapshot.child(userID).child("userinfo").child("username").getValue(String.class);
//                String weightGoal = dataSnapshot.child(userID).child("userinfo").child("Weight Goal").getValue(String.class);
//                String currentActivity = dataSnapshot.child(userID).child("userinfo").child("Current Activity").getValue(String.class);
//                String DOB = dataSnapshot.child(userID).child("userinfo").child("Birthdate").getValue(String.class);
//                String BMR = dataSnapshot.child(userID).child("userinfo").child("BMR").getValue(String.class);
//                String TDEE = dataSnapshot.child(userID).child("userinfo").child("TDEE").getValue(String.class);
//                String height = dataSnapshot.child(userID).child("userinfo").child("Height CM").getValue(String.class);
//                String weight = dataSnapshot.child(userID).child("userinfo").child("Weight KG").getValue(String.class);
//
//
//
//                Log.d("TAG", "The users BMI is: " + BMI);
//                Log.d("TAG", "The users fullname is: " + fullname);
//                Log.d("TAG", "The users Birthdate is: " + Birthdate);
//                Log.d("TAG", "The users weightGoal is: " + weightGoal);
//                Log.d("TAG", "The users gender is: " + gender);
//                Log.d("TAG", "The users calorieGoal Goal is: " + calorieGoal);
//                Log.d("TAG", "The users username is: " + username);
//                Log.d("TAG", "The users birthdate is: " + Birthdate);
//                Log.d("TAG", "The users current Activity is: " + currentActivity);
//                Log.d("TAG", "The users DOB is: " + DOB);
//                Log.d("TAG", "The users BMR is: " + BMR);
//                Log.d("TAG", "The users TDEE is: " + TDEE);
//                Log.d("TAG", "The users height in cm is: " + height);
//                Log.d("TAG", "The users weight in kg  is: " + weight);



                //
//                MyUser.username = username;
//                MyUser.weightGoal =weightGoal;
//                MyUser.currentActivity = currentActivity;
//                MyUser.gender = gender;
//                MyUser.DOB = DOB;
//                MyUser.BMR = BMR;
//                MyUser.BMI = BMI;
//                MyUser.TDEE = TDEE;
//                MyUser.calorieGoal = calorieGoal;
//                MyUser.height = height;
//                MyUser.weight = weight;

                //System.out.println(MyUser.toStringS());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setTitle("");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Barcode Scanner Not Implemented!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        header = navigationView.getHeaderView(0);
        name = (TextView)header.findViewById(R.id.nav_username);
        email = (TextView)header.findViewById(R.id.nav_Email);




    }

    // view goals
    public void viewGoals(View view) {

        startActivity(new Intent(MainHomePagerActivity.this, UserGoalsActivity.class).putExtra("MyUser", myUser));

    }

    // log food click
    public void logFood(View view) {

        startActivity(new Intent(MainHomePagerActivity.this, FoodSearchActivity.class));
    }

    // view food diary
    public void viewDiary(View view) {

        startActivity(new Intent(MainHomePagerActivity.this, DietDiaryActivity.class));

    }

    // start the running tracker activity
    public void startTracking(View view) {

        startActivity(new Intent(MainHomePagerActivity.this, RunningTracker.class));
    }

    // starting the tabata timer activity
    public void tabataTimerClick(View view) {

        startActivity(new Intent(MainHomePagerActivity.this, TabataSettingActivity.class));

    }

    // [START on_start_add_listener]
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    // [END on_start_add_listener]

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    // [END on_stop_remove_listener]

    private void logout() {

        mAuth.signOut();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;

        } else if (item.getItemId() == R.id.logout) {

            logout();

        }

        return super.onOptionsItemSelected(item);
    }

    // method was taken from Navigation drawer activity
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "nav_gallery", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    GoalsTab tab1 = new GoalsTab();
                    return tab1;
                case 1:
                    DietTab tab2 = new DietTab();
                    return tab2;
                case 2:
                    WorkoutsTab tab3 = new WorkoutsTab();
                    return tab3;
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Goals";
                case 1:
                    return "Diet";
                case 2:
                    return "Workouts";
            }
            return null;
        }
    }
}
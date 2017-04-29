package com.darrenmoriarty.myfitnessapp.pageractivities.diet_package;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.darrenmoriarty.myfitnessapp.R;
import com.darrenmoriarty.myfitnessapp.pageractivities.MainHomePagerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FoodSearchActivity extends AppCompatActivity {


    private EditText foodSearchEditText;
    private ListView foodList;
    private Intent nextScreen;
    private String meal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");

        try {

            Bundle extras = getIntent().getExtras();

            if (extras != null) {

                meal = extras.getString("meal");

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        foodList = (ListView) findViewById(R.id.foodListView);

        nextScreen = new Intent(FoodSearchActivity.this, AddFoodActivity.class);


        foodSearchEditText = (EditText) findViewById(R.id.foodSearchEditText);




//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DownloadTask task = new DownloadTask();
//                task.execute("https://api.nal.usda.gov/ndb/search/?format=json&q=" + queryEditText.getText().toString() + "&sort=r&max=25&sr&offset=0&api_key=n9S63MmDB62TayeDC9x1y8gxYDoQyTU3PIG6jQ9f");
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DownloadTask task = new DownloadTask();
                task.execute("https://api.nal.usda.gov/ndb/search/?format=json&q=" + foodSearchEditText.getText().toString() + "&sort=r&max=25&sr&offset=0&api_key=n9S63MmDB62TayeDC9x1y8gxYDoQyTU3PIG6jQ9f");

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        private ArrayList<String> names = new ArrayList<>();
        private ArrayList<String> ndbnos = new ArrayList<>();
        public ArrayList<String> nutrientsList = new ArrayList<>();
        public ArrayList<String> values = new ArrayList<>();

        public ArrayList<String> getNames() {
            return names;
        }

        public void setNames(ArrayList<String> names) {
            this.names = names;
        }

        public ArrayList<String> getNdbnos() {
            return ndbnos;
        }

        public void setNdbnos(ArrayList<String> ndbnos) {
            this.ndbnos = ndbnos;
        }

        public ArrayList<String> getNutrientsList() {
            return nutrientsList;
        }

        public ArrayList<String> getValues() {
            return values;
        }

        public void setNutrientsList(ArrayList<String> nutrientsList) {
            this.nutrientsList = nutrientsList;
        }

        public void setValues(ArrayList<String> values) {
            this.values = values;
        }

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        } // end doInBackground method

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                String list = jsonObject.getString("list");

                Log.i("List content", list);

                JSONObject itemCont = new JSONObject(list);

                //Log.i("item content", itemCont.getString("item"));

                // creating a JSON array to loop through the received data
                JSONArray jsonArray = new JSONArray(itemCont.getString("item"));



                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonPart = jsonArray.getJSONObject(i);

                    //Log.i("Name: ", jsonPart.getString("name"));
                    //Log.i("NDBNO: ", jsonPart.getString("ndbno"));

                    names.add(jsonPart.getString("name"));
                    ndbnos.add(jsonPart.getString("ndbno"));

                    // TODO try and make 2 new methods to do the same as the methods in this class to make a new api call and then populate the arraylists

////                    // creating the new task to download the current items details
//                    DownloadNDBNO downloadNDBNO = new DownloadNDBNO();
//                    downloadNDBNO.execute("https://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=n9S63MmDB62TayeDC9x1y8gxYDoQyTU3PIG6jQ9f&nutrients=205&nutrients=203&nutrients=204&nutrients=208&nutrients=269&ndbno=" + jsonPart.getString("ndbno"));

//                    setNutrientsList(downloadNDBNO.getNutrientsList());
//                    setValues(downloadNDBNO.getValues());

                }

                System.out.println(names);
                System.out.println(ndbnos);

                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, names);

                foodList.setAdapter(arrayAdapter);

                foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        try {
                            // creating the new task to download the current items details
                            DownloadNDBNO downloadNDBNO = new DownloadNDBNO();
                            downloadNDBNO.execute("https://api.nal.usda.gov/ndb/nutrients/?format=json&api_key=n9S63MmDB62TayeDC9x1y8gxYDoQyTU3PIG6jQ9f&nutrients=205&nutrients=203&nutrients=204&nutrients=208&nutrients=269&gm&ndbno=" + ndbnos.get(position));

                            nextScreen.putExtra("name", names.get(position));

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

//                final Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
//                intent.putExtra("names", names);
//                intent.putExtra("ndbno", ndbnos);
//
//




            } catch (Exception e) {

                e.printStackTrace();

            }
        } // end onPostExecute method

    } // end DownloadTask class

    // class for downloading the nutrient content
    public class DownloadNDBNO extends AsyncTask<String, Void, String> {

//        public ArrayList<String> names = new ArrayList<>();
//        public ArrayList<String> ndbnos = new ArrayList<>();
//        public ArrayList<String> nutrientsList = new ArrayList<>();
//        public ArrayList<String> values = new ArrayList<>();
//
//        public ArrayList<String> getNames() {
//            return names;
//        }
//
//        public void setNames(ArrayList<String> names) {
//            this.names = names;
//        }
//
//        public ArrayList<String> getNdbnos() {
//            return ndbnos;
//        }
//
//        public void setNdbnos(ArrayList<String> ndbnos) {
//            this.ndbnos = ndbnos;
//        }
//
//        public ArrayList<String> getNutrientsList() {
//            return nutrientsList;
//        }
//
//        public ArrayList<String> getValues() {
//            return values;
//        }
//
//        public void setNutrientsList(ArrayList<String> nutrientsList) {
//            this.nutrientsList = nutrientsList;
//        }
//
//        public void setValues(ArrayList<String> values) {
//            this.values = values;
//        }

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }  // end doInBackground method

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //Log.i("result", s);

            try {

                // the original report
                JSONObject jsonObjectReport = new JSONObject(s);

                String report = jsonObjectReport.getString("report");

                Log.i("Report content" , report);

                // making an object of the foods
                JSONObject jsonObjectFoods = new JSONObject(report);

                String foods = jsonObjectFoods.getString("foods");

                //Log.i("Foods content" , foods);

                String foodsObj = "";

                for (int i = 1; i < foods.length() - 1; i++) {
                    foodsObj += foods.charAt(i);
                }

                // making an object of the nutrients
                JSONObject jsonObjectNutrients = new JSONObject(foodsObj);

                String nutrients = jsonObjectNutrients.getString("nutrients");

                //Log.i("nutrients content" , nutrients);

                JSONArray array = new JSONArray(nutrients);

                ArrayList<String> nutrientArray = new ArrayList<>();
                ArrayList<String> valueArray = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {

                    JSONObject jsonPart = array.getJSONObject(i);

                    //Log.i("Part ", jsonPart.toString());
                    //Log.i("nutrient ", jsonPart.getString("nutrient"));
                    //Log.i("value ", jsonPart.getString("value"));

                    //Toast.makeText(MainActivity.this, "nutrient " + jsonPart.getString("nutrient"), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(MainActivity.this, "value " + jsonPart.getString("value"), Toast.LENGTH_SHORT).show();

                    nutrientArray.add(jsonPart.getString("nutrient"));
                    valueArray.add(jsonPart.getString("gm"));

//                    nutrientsList.add(jsonPart.getString("nutrient"));
//                    values.add(jsonPart.getString("value"));

                }

                //System.out.println(nutrientArray);
                //System.out.println(valueArray);

                nextScreen.putExtra("nutrientArray", nutrientArray);
                nextScreen.putExtra("valueArray", valueArray);
                nextScreen.putExtra("meal", meal);

                startActivity(nextScreen);

                finish();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        } // end onPostExecute method
    }// end DownloadNDBNO class

}

package com.example.coronatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TextView totalCaseView;
    TextView totalDeathsView;
    TextView confirmedView;
    TextView deathsView;
    TextView recoveredView;
    TextView countryView;
    TextView updateView;

    public static Double latitude;
    public static Double longitude;


   public JSONArray jsonArray;
    public JSONArray jsonMapArray;
   public JSONObject jsonObject;
   public JSONObject jsonMapobject;
   public JSONObject jsonMapObject1;
   public JSONObject jsonMapObject2;
   public JSONObject jsonObject1;
   public JSONObject jsonObject2;
   public JSONObject jsonObject3;
   public JSONObject jsonObject4;


    public static ArrayList<String> listItems=new ArrayList<>();
    public static ArrayList<Double> latitudeItems=new ArrayList<>();
    public static ArrayList<Double> longitudeItems=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalDeathsView = findViewById(R.id.totalDeathsView);
        totalCaseView = findViewById(R.id.totalCaseView);
        confirmedView = findViewById(R.id.confirmedView);
        deathsView = findViewById(R.id.deathsView);
        recoveredView = findViewById(R.id.recoveredView);
        updateView = findViewById(R.id.updateView);
        countryView = findViewById(R.id.countryTextView);

        listItems.add("Please Select Country");
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, listItems);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    setCoronaVirusDetailInfoToView(position);
                }else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });




        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://coronavirus-tracker-api.herokuapp.com/v2/locations"  ;


            downloadData.execute(url);
        }catch (Exception e){

        }


    }

    public void uptade(View view){

        Intent intent = new Intent(MainActivity.this,CoronaTrackerMapsActivity.class);
        startActivity(intent);



    }


    public class DownloadData extends AsyncTask<String,Void,String>{
        ArrayList<String> list;
    protected void onPreExecute(){
        super.onPreExecute();
        list=new ArrayList<>();
    }

        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;

            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();
                while (data > 0 ){
                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }

                return result;

            }catch (Exception e){

                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                 jsonObject = new JSONObject(s);
                String latest = jsonObject.getString("latest");
                 jsonObject1 = new JSONObject(latest);
                String totalDeaths = jsonObject1.getString("deaths");
                totalDeathsView.setText("Deaths : " + totalDeaths);

                String totalCases = jsonObject1.getString("confirmed");
                totalCaseView.setText("Coronavirus Cases : " + totalCases);

                String idlocation = jsonObject.getString("locations");

                 jsonArray = new JSONArray(idlocation);

                 for(int i=0;i<jsonArray.length();i++){
                     JSONObject jsonObject=jsonArray.getJSONObject(i);
                     String   country = jsonObject.getString("country");
                     listItems.add(jsonObject.getString("country"));

                 }
            }catch (Exception e){
                e.printStackTrace();
            }

         try {
             jsonMapobject = new JSONObject(s);
             String idcoordinate = jsonMapobject.getString("locations");
             jsonMapArray = new JSONArray(idcoordinate);

             for (int a=0; a<jsonMapArray.length();a++){
                 JSONObject jsonObject=jsonArray.getJSONObject(a);
                 String  countryCoordinates = jsonObject.getString("coordinates");
                 JSONObject  jsonCoordinateObject = new JSONObject(countryCoordinates);
         //        latitude = jsonCoordinateObject.getDouble("latitude");
         //        longitude = jsonCoordinateObject.getDouble("longitude");
                 latitudeItems.add(jsonCoordinateObject.getDouble("latitude"));
                 longitudeItems.add(jsonCoordinateObject.getDouble("longitude"));
             }
         }catch (Exception e){
             e.printStackTrace();
         }




        }


    }

    private void setCoronaVirusDetailInfoToView(int i){
        try{
            CoronaDetailInfoFields.CoronaDetailInfoFieldsBuilder builder=new CoronaDetailInfoFields.CoronaDetailInfoFieldsBuilder();

            if(i==0){
                setDetailInfosToTextView(builder.build());
            }else{
                String id = jsonArray.getString(i-1);
                jsonObject2 = new JSONObject(id);
                String       lastUp = jsonObject2.getString("last_updated");
                String       latest2 = jsonObject2.getString("latest");
                String      countryPopulation = jsonObject2.getString("country_population");
                String  countryCoordinates = jsonObject2.getString("coordinates");
                jsonObject3 = new JSONObject(latest2);
                String       confirmed = jsonObject3.getString("confirmed");
                String      deaths = jsonObject3.getString("deaths");
                String      recovered = jsonObject3.getString("recovered");

//              for (int a=0;a<jsonObject4.length();a++){
//                  latitude = jsonObject4.getDouble("latitude");
//                  longitude = jsonObject4.getDouble("longitude");
//                  System.out.println("deneme"+latitude);
//              }



                setDetailInfosToTextView(
                        builder.setLastUp(lastUp)
                                .setConfirmed(confirmed)
                                .setDeaths(deaths)
                                .setRecovered(recovered)
                                .setId(countryPopulation)
                                .build()
                        );
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setDetailInfosToTextView(CoronaDetailInfoFields coronaDetailInfoFields) {
        updateView.setText("Last Update : " + coronaDetailInfoFields.getLastUp());
        confirmedView.setText("Confirmed : " + coronaDetailInfoFields.getConfirmed());
        deathsView.setText("Deaths : " + coronaDetailInfoFields.getDeaths());
        recoveredView.setText("Recovered : " + coronaDetailInfoFields.getRecovered());
        countryView.setText("Country Population:"+coronaDetailInfoFields.getId());
    }

}

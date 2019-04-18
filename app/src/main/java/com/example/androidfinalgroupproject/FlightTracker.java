package com.example.androidfinalgroupproject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class FlightTracker extends AppCompatActivity {

    private ArrayList<String> searchResultList = new ArrayList<String>();
    private Button searchByDepBtn;
    private Button searchByArrBtn;
    private EditText airportCodeET;
    private SharedPreferences ftsharedpref;
    private Boolean isDep = false;
    private String airportCodeString;

    private ProgressBar ftProgressBar;

    private String flightResult;

    private FTListAdapter ftArrayAdapter = null;
    private ListView ftListview;
    ;

//Arrays storing flight info found in Asynch Task query
final ArrayList<String> flightInfo = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_tracker_activity);

        Toolbar ftToolbar = findViewById(R.id.ftToolbar);
        setSupportActionBar(ftToolbar);
        ftListview = findViewById(R.id.ftresultslistview);
        ftArrayAdapter = new FTListAdapter(this);
        ftListview.setAdapter(ftArrayAdapter);


        ftProgressBar = findViewById(R.id.ftProgressBar);

        searchByDepBtn = (Button) findViewById(R.id.searchDepBtn);
        searchByArrBtn = (Button) findViewById(R.id.searchArrBtn);

        airportCodeET = (EditText) findViewById(R.id.ETSearchCity);
        ftsharedpref = getSharedPreferences("SearchCity", Context.MODE_PRIVATE);
        String savedCityString = ftsharedpref.getString("SearchCityReserveName", "");
        airportCodeET.setText((savedCityString));

//        ftListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              Intent frag = new Intent(FlightTracker.this,)
//            }
//        });

        searchByDepBtn.setOnClickListener(b -> {
            isDep = true;
            if (airportCodeET.getText().toString().equals("") || airportCodeET.getText().toString().length() < 3) {
                Toast.makeText(getApplicationContext(), "Airport Code must be 3 letters",
                        Toast.LENGTH_LONG).show();
            } else {
                ftProgressBar.setVisibility(View.VISIBLE);
                airportCodeString = airportCodeET.getText().toString();

                FTgetFlight query = new FTgetFlight();
                query.execute(airportCodeET.getText().toString());

                ftArrayAdapter.notifyDataSetChanged();
                searchResultList.clear();


            }

        });
        searchByArrBtn.setOnClickListener(b -> {
            if (airportCodeET.getText().toString().equals("") || airportCodeET.getText().toString().length() < 3) {
                Toast.makeText(getApplicationContext(), "Airport Code must be 3 letters",
                        Toast.LENGTH_LONG).show();
            } else {
                ftProgressBar.setVisibility(View.VISIBLE);
                airportCodeString = airportCodeET.getText().toString();

                FTgetFlight query = new FTgetFlight();
                query.execute();
                //airportCodeET.setText("");
                ftArrayAdapter.notifyDataSetChanged();
                searchResultList.clear();


            }

        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about:
                Intent intentFTabout = new Intent(FlightTracker.this, FlightTrackerAbout.class);
                startActivity(intentFTabout);
                break;
            case R.id.savedItems:
                Intent intentFTsaved = new Intent(FlightTracker.this, FlightTrackerFragment.class);
                startActivity(intentFTsaved);
                break;
        }

        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = ftsharedpref.edit();

        String whatWasTyped = airportCodeET.getText().toString();
        editor.putString("SearchCityReserveName", whatWasTyped);

        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater ftMenuInflater = getMenuInflater();
        ftMenuInflater.inflate(R.menu.flight_tracker_menu, menu);
        return true;
    }



    private class FTgetFlight extends AsyncTask<String, Integer, String> {
        JSONArray ftSearchResultsArray;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Searching Flights",
                    Toast.LENGTH_LONG).show();

        }

        @Override
        protected String doInBackground(String... params) {



            InputStream ftInputStream;
            HttpURLConnection ftUrlConnection;
            URL url;
            try {
                if (isDep == true) {
                    url = new URL(searchDep(airportCodeString));
                } else {
                    url = new URL(searchArr(airportCodeString));
                }

                ftUrlConnection = (HttpURLConnection) url.openConnection();


                ftUrlConnection.setReadTimeout(10000);
                ftUrlConnection.setConnectTimeout(15000);
                ftUrlConnection.setRequestMethod("GET");
                ftUrlConnection.setDoInput(true);

                ftInputStream = ftUrlConnection.getInputStream();

                BufferedReader flightReader = new BufferedReader(new InputStreamReader(ftInputStream, "UTF-8"), 8);
                StringBuilder flightSB = new StringBuilder();
                String line = null;

                publishProgress(50);
                //Thread.sleep(500);

                while ((line = flightReader.readLine()) != null) {
                    flightSB.append(line + "\n");
                }
                flightResult = flightSB.toString();

                //[] array {} object

                ftSearchResultsArray = new JSONArray(flightResult);


                publishProgress(50);
                //Thread.sleep(500);


            } catch (Exception e) {
                Log.i("Crash", "crash");

            }
            return "Finished Task";
        }

        @Override
        protected void onPostExecute(String result) {
            double fltSpeed;
            String fltNbr;
            String fltStatus;
            String fltLong;
            String fltLat;
            String fltDep;
            String fltArr;
            FTFlightData ftFlightData = new FTFlightData();
                try {
                    for (int index = 0; index < ftSearchResultsArray.length(); index++) {


                        JSONObject jObjFltInfo;
                        jObjFltInfo = ftSearchResultsArray.getJSONObject(index).getJSONObject("flight");
                        fltNbr = jObjFltInfo.getString("iataNumber");

                        ftFlightData.setFltNbr(fltNbr);
                        jObjFltInfo = ftSearchResultsArray.getJSONObject(index).getJSONObject("departure");
                        fltDep = jObjFltInfo.getString("iataCode");
                        ftFlightData.setFltDep(fltDep);

                        jObjFltInfo = ftSearchResultsArray.getJSONObject(index).getJSONObject("arrival");
                        fltArr = jObjFltInfo.getString("iataCode");
                        ftFlightData.setFltArr(fltArr);

                        jObjFltInfo = ftSearchResultsArray.getJSONObject(index).getJSONObject("speed");
                        fltSpeed = jObjFltInfo.getDouble("horizontal");
                        ftFlightData.setFltSpeed(fltSpeed);

                        jObjFltInfo = ftSearchResultsArray.getJSONObject(index).getJSONObject("geography");
                        fltLong = jObjFltInfo.getString("longitude");
                        ftFlightData.setFltLong(fltLong);
                        fltLat = jObjFltInfo.getString("latitude");
                        ftFlightData.setFltLat(fltLat);

                        fltStatus = jObjFltInfo.getString("status");
                        ftFlightData.setFltStatus(fltStatus);
                        ftArrayAdapter.add(ftFlightData);
                        ftProgressBar.setVisibility(View.INVISIBLE);
                    }

                } catch (JSONException e) {
                    Log.i("JSON Exception", "crash");
                }


            Snackbar snackbar = Snackbar.make(searchByDepBtn, "Search Complete", Snackbar.LENGTH_SHORT);

            snackbar.show();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            ftProgressBar.setVisibility(View.VISIBLE);
            ftProgressBar.setProgress(values[0]);
        }

    }

    protected class FTListAdapter extends ArrayAdapter<FTFlightData> {
        ArrayList<FTFlightData> flightDataArrayList = null;

        public FTListAdapter(Context context){
          this(context,new ArrayList<>());

        }


        public FTListAdapter(Context context, ArrayList<FTFlightData> flightDataArrayList) {
            super(context, 0,flightDataArrayList);
            this.flightDataArrayList=flightDataArrayList;
        }


        /**
         * Return the size of the list,
         *
         * @return size of searchResultsData.
         */
        public int getCount() {

            return flightDataArrayList.size();
        }

        /**
         * Get suggestion at requested position.
         *
         * @param position index to be used.
         * @return word at given index.
         */
        public FTFlightData getItem(int position) {

            return flightDataArrayList.get(position);
        }


        /**
         * Returns passed position until database implementation.
         *
         * @param position word position in list.
         * @return position.
         */
//        public long getItemId(int position) {
//            return flightDataArrayList.get(position);
//
//        }

        /**
         * Inflates a row of the ListView based on a defined layout file. If a old view is found,
         * it is used instead.
         *
         * @param position Item's position in list.
         * @param old      Old view to be recycled.
         * @param parent   ViewGroup
         * @return root - New view that has been inflated.
         */
        public View getView(int position, View old, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            FTFlightData oneFlight = getItem(position);
            View root = old;
            root = inflater.inflate(R.layout.flight_tracker_activity,parent,false);
            TextView flight = root.findViewById(R.id.flight);
            flight.setText(oneFlight.getFltNbr());
            TextView depApt = root.findViewById(R.id.depApt);
            flight.setText(oneFlight.getFltDep());
            TextView arrApt = root.findViewById(R.id.arrAprt);
            flight.setText(oneFlight.getFltArr());
            return root;
        }


    }

    public String searchDep(String city) {

        airportCodeString = city;

        return "http://aviation-edge.com/v2/public/flights?key=4fbbe8-570515&depIata=" + city;
    }

    public String searchArr(String city) {

        airportCodeString = city;

        return ("http://aviation-edge.com/v2/public/flights?key=4fbbe8-570515&arrIata=" + city);

    }

}

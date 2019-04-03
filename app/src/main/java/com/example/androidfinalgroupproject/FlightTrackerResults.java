package com.example.androidfinalgroupproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class FlightTrackerResults extends AppCompatActivity {
//shows the location, speed, altitude of flight and status of flight
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_tracker_results);
        Toolbar ftToolbar = (Toolbar)findViewById(R.id.ftToolbar);
        setSupportActionBar(ftToolbar);
    }
}

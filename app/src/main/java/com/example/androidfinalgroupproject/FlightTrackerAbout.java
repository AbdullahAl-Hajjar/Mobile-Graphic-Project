package com.example.androidfinalgroupproject;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;


public class FlightTrackerAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_tracker_about_activity);
        Toolbar ftToolbar = findViewById(R.id.ftToolbar);
        setSupportActionBar(ftToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.flight_tracker_menu,menu);
        return true;
    }
}

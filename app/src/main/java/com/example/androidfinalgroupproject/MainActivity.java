package com.example.androidfinalgroupproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * This activity's purpose is to load the application home screen, giving users the choice of
 * dictionary, new york times feed, news feed, and flight tracker. Clicking an ImageButton loads
 * the appropriate activity.
 *
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    MenuInflater inflater;

    /**
     * This method initializes the main activity. The setContentView() method is
     * used to define the layout resource to be used. Button background colors are set, and click
     * listeners are set for each ImageButton to start the appropriate activity.
     *
     * @param savedInstanceState Bundle object containing activity's previously saved state. If
     *                           activity is new, value will be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This boolean method onCreateOptionsMenu is used to inflate the menu items for use in the action bar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        return true;
    }

    /**
     * This boolean method onOptionsItemSelected is used when the menu item is selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //what to do when the menu item is selected:
            case R.id.dictionaryButton:
                Intent intent = new Intent(this, Dictionary.class);
                startActivity(intent);
                break;
            case R.id.newsFeedButton:
                Intent intent1 = new Intent(this, NewsFeed.class);
                startActivity(intent1);
                break;
            case R.id.newYorkButton:
                Intent intent2 = new Intent(this, NewYorkTimes.class);
                startActivity(intent2);
                break;
            case R.id.airplaneButton:
                Intent intent3 = new Intent(this, FlightTracker.class);
                startActivity(intent3);
                break;
        }
        return false;

    }
}

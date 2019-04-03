package com.example.androidfinalgroupproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * This activity's purpose is to load the application home screen, giving users the choice of
 * dictionary, new york times feed, news feed, and flight tracker. Clicking an ImageButton loads
 * the appropriate activity.
 *
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity
{
    /**
     * ImageButton variables for each activity.
     */
    ImageButton newsFeedButton;
    ImageButton airplaneButton;
    ImageButton newYorkButton;
    ImageButton dictionaryButton;

    /**
     * This method initializes the main activity. The setContentView() method is
     * used to define the layout resource to be used. Button background colors are set, and click
     * listeners are set for each ImageButton to start the appropriate activity.
     *
     * @param savedInstanceState Bundle object containing activity's previously saved state. If
     * activity is new, value will be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsFeedButton = findViewById(R.id.newsFeedButton);
        airplaneButton = findViewById(R.id.airplaneButton);
        newYorkButton = findViewById(R.id.newYorkButton);
        dictionaryButton = findViewById(R.id.dictionaryButton);

        newsFeedButton.setBackgroundColor(Color.parseColor("#0000ffff"));
        airplaneButton .setBackgroundColor(Color.parseColor("#0000ffff"));
        newYorkButton.setBackgroundColor(Color.parseColor("#0000ffff"));
        dictionaryButton.setBackgroundColor(Color.parseColor("#0000ffff"));

        /**
         * News Feed button listener.
         */
        newsFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewsFeed.class);
                startActivity(intent);
            }
        });
        /**
         * Flight Tracker button listener.
         */
        airplaneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  PlaneTracker.class);
                startActivity(intent);
            }
        });
        /**
         * New York Times button listener.
         */
        newYorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  NewYorkTimes.class);
            startActivity(intent);
        }
        });
        /**
         * Dictionary button listener.
         */
        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  Dictionary.class);
                startActivity(intent);
            }
        });



    }
}

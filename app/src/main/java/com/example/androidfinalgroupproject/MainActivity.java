package com.example.androidfinalgroupproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity
{
    ImageButton newsFeedButton;
    ImageButton airplaneButton;
    ImageButton newYorkButton;
    ImageButton dictionaryButton;

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

        newsFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewsFeed.class);
                startActivity(intent);
            }
        });

        airplaneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  PlaneTracker.class);
                startActivity(intent);
            }
        });

        newYorkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  NewYorkTimes.class);
            startActivity(intent);
        }
        });

        dictionaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),  Dictionary.class);
                startActivity(intent);
            }
        });



    }
}

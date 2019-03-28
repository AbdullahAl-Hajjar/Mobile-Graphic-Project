package com.example.androidfinalgroupproject;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class DictionaryInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_info);

        Toolbar toolbar = findViewById(R.id.dictionaryInfoTb);
        toolbar.setTitle(R.string.dictionaryTitle);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(toolbar);

        // code for toolbar back arrow
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(btn -> finish());
    }
}

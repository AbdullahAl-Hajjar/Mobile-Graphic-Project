package com.example.androidfinalgroupproject;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Dictionary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar tBar = (Toolbar)findViewById(R.id.my_toolbar);
        tBar.setTitle(R.string.dictionaryTitle);
        setSupportActionBar(tBar);
        //tBar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        tBar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dictionary_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item1:
                Toast.makeText(this,R.string.noSavedWords, Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                break;
        }
        return true;
    }
}
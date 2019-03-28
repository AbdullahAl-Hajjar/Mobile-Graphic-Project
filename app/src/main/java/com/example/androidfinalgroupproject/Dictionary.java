package com.example.androidfinalgroupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Dictionary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar tBar = (Toolbar)findViewById(R.id.my_toolbar);
        tBar.setTitle(R.string.dictionaryTitle);
        setSupportActionBar(tBar);
        tBar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(btn -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            LinearLayout l = findViewById(R.id.customDialog);
            View v = inflater.inflate(R.layout.custom_dialog, l);
            builder.setView( v );
            builder.setPositiveButton(R.string.positive, (dialog, which) -> {
                dialog.dismiss();
            });
            builder.setNegativeButton(R.string.negative, (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
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
                Intent nextPage = new Intent(Dictionary.this, DictionaryInfo.class);
                startActivity(nextPage);
                break;
        }
        return true;
    }
}
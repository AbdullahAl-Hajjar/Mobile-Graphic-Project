package com.example.androidfinalgroupproject;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class DictionaryWordDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_word_detail);
        Toolbar toolbar = findViewById(R.id.wordDetailTb);
        toolbar.setTitle(R.string.savedWordsTitle);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(toolbar);

        // code for toolbar back arrow
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(btn -> finish());
        Bundle dataToPass = getIntent().getExtras();
        TextView word = findViewById(R.id.wordText);
        TextView pos = findViewById(R.id.wordPosition);
        word.setText("Word: " + dataToPass.getString("word"));
        pos.setText("Position: " + Integer.toString(dataToPass.getInt("position")));
    }
}

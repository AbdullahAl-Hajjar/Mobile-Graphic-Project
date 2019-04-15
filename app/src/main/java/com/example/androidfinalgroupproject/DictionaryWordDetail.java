package com.example.androidfinalgroupproject;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * This class is used to show the definition of a locally stored word on a phone. Tablets will use
 * the DictionaryFragment activity instead.
 *
 * @author Nick Hallarn
 */
public class DictionaryWordDetail extends AppCompatActivity {

    /**
     * Initializes the activity. Gets data from extras passed by intent, then updates the GUI
     * @param savedInstanceState Bundle object containing activity's previously saved state. If
     *  activity is new, value will be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict_word_detail);
        setTheme(R.style.DictionaryTheme);

        String word = getIntent().getStringExtra("word");
        String pron = getIntent().getStringExtra("pron");
        String type = getIntent().getStringExtra("type");
        String def = getIntent().getStringExtra("def");

        Toolbar toolbar = findViewById(R.id.wordTB);
        toolbar.setTitle(word);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(toolbar);

        // code for toolbar back arrow
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(btn -> finish());


        TextView wd = findViewById(R.id.headWord);
        TextView pr = findViewById(R.id.pronunciation);
        TextView wt = findViewById(R.id.wordType);
        TextView df = findViewById(R.id.definition);

        wd.setText(word);
        pr.setText(pron);
        wt.setText(type);
        df.setText(def);
    }
}

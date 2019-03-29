package com.example.androidfinalgroupproject;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
/**
 * This activity displays information about a single word from the saved words list. This activity
 * is called by the saved words list click listener.
 *
 * @author Nick Hallarn.
 * @version 1.0.
 */
public class DictionaryWordDetail extends AppCompatActivity {

    /**
     * This method initializes the DictionaryWordDetail activity. The setContentView() method is
     * used to define the layout resource to be used. Toolbar is initialized. Information from
     * selected word is retrieved and set to the layout's TextView fields.
     *
     * @param savedInstanceState Bundle object containing activity's previously saved state. If
     * activity is new, value will be null.
     */
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
        if(dataToPass != null) {
            TextView word = findViewById(R.id.wordText);
            TextView pos = findViewById(R.id.wordPosition);
            word.setText("Word: " + dataToPass.getString("word"));
            pos.setText("Position: " + Integer.toString(dataToPass.getInt("position")));
        }
    }
}

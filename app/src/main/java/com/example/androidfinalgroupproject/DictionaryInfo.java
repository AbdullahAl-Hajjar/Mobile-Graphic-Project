package com.example.androidfinalgroupproject;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
/**
 * This activity displays information about the Dictionary application, such as author, version,
 * version date, and instructions for using the application.
 *
 * @author Nick Hallarn.
 * @version 1.0.
 */
public class DictionaryInfo extends AppCompatActivity {

    /**
     * This method initializes the DictionaryInfo activity. The setContentView() method is
     * used to define the layout resource to be used. Toolbar is initialized.
     *
     * @param savedInstanceState Bundle object containing activity's previously saved state. If
     * activity is new, value will be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_info);
        setTheme(R.style.DictionaryTheme);
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

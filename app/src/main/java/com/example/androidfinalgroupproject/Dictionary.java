package com.example.androidfinalgroupproject;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
/**
 * The Dictionary class is the main activity that drives the dictionary application. This class
 * extends AppCompatActivity. Users have the options to search the dictionary for a word, view the
 * definition, save the current word, view saved words list, and view info pane.
 *
 * @author Nick Hallarn.
 * @version 1.0.
 */
public class Dictionary extends AppCompatActivity {
    /**
     * This method initializes the Dictionary activity. The setContentView() method is used to
     * define the layout resource to be used. Toolbar and ProgressBar are initialized, and a click
     * listener is defined for the save button.
     *
     * @param savedInstanceState Bundle object containing activity's previously saved state. If
     * activity is new, value will be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        setToolbar();
        createProgressBar();
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(btn -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            LinearLayout l = findViewById(R.id.customDialog);
            View v = inflater.inflate(R.layout.custom_dialog, l);
            builder.setView( v );
            builder.setPositiveButton(R.string.positive, (dialog, which) -> {
                Intent nextPage = new Intent(Dictionary.this, DictionarySavedWords.class);
                nextPage.putExtra("dictionary", 1);
                startActivity(nextPage);
            });
            builder.setNegativeButton(R.string.negative, (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
    }

    /**
     * Called automatically when activity is started. Initializes the options menu (Toolbar) for
     * the activity using the custom menu file dictionary_menu. This menu file consists of two
     * items, which are set to always show their icons. The first menu item is to access the saved
     * words list, and the second menu item shows the application info.
     *
     * @param menu The options menu to be used.
     * @return True to display menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dictionary_menu, menu);
        return true;
    }

    /**
     * This method is called when a user clicks an item in the options menu (Toolbar). A switch
     * statement is used to determine which menu item was clicked, and the appropriate action is
     * taken.
     *
     * @param item The menu item that was selected.
     * @return True if item is successfully handled, false if item is not handled.
     */
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

    /**
     * This method is used to set the Toolbar for the activity. It is called by the onCreate()
     * method.
     */
    private void setToolbar(){
        Toolbar tBar = findViewById(R.id.my_toolbar);
        tBar.setTitle(R.string.dictionaryTitle);
        tBar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(tBar);
    }

    /**
     * This method is used to create the ProgressBar for the activity. It is called by the
     * onCreate() method.
     *
     * Note: ProgressBar is set at 50% as a placeholder until full functionality is implemented.
     */
    private void createProgressBar(){
        ProgressBar pb = findViewById(R.id.dictionaryProgress);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(50);
    }
}
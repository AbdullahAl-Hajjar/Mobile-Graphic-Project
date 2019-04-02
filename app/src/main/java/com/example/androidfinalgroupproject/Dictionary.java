package com.example.androidfinalgroupproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * The Dictionary class is the main activity that drives the dictionary application. This class
 * extends AppCompatActivity. Users have the options to search the dictionary for a word, view the
 * definition, save the current word, view saved words list, and view info pane.
 *
 * @author Nick Hallarn.
 * @version 1.0.
 */
public class Dictionary extends AppCompatActivity {

    private ProgressBar pb;
    private String searchTerm;



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
        setTheme(R.style.DictionaryTheme);
        setToolbar();
        createProgressBar();
        Button saveButton = findViewById(R.id.saveButton);
        Button searchButton = findViewById(R.id.searchButton);
        EditText searchBar = findViewById(R.id.searchBar);

        searchButton.setOnClickListener(btn -> {
            View v = this.getCurrentFocus();
            InputMethodManager mm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            mm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            try {
                searchTerm = searchBar.getText().toString().trim();
                String param = URLEncoder.encode(searchTerm, "UTF-8");
                DataFetcher networkThread = new DataFetcher();
                networkThread.execute("https://www.dictionaryapi.com/api/v1/references/sd3/xml/" + param
                        + "?key=4556541c-b8ed-4674-9620-b6cba447184f");
            }catch(Exception e){
                Log.e("Program crashed", e.getMessage());
            }
        });


        saveButton.setOnClickListener(btn -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            LinearLayout l = findViewById(R.id.customDialog);
            View v = inflater.inflate(R.layout.activity_dictionary_dialog, l);
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
        pb = findViewById(R.id.dictionaryProgress);
        pb.getProgressDrawable().setColorFilter(
                getResources().getColor(R.color.colorAccentD),
                android.graphics.PorterDuff.Mode.SRC_IN);
        pb.setVisibility(View.INVISIBLE);
    }

    private class DataFetcher extends AsyncTask<String, Integer, String> {

        List<String> headWord = new ArrayList<>();      //<hw>
        List<String> pronunciation = new ArrayList<>(); //<pr>
        List<String> wordType = new ArrayList<>();      //<fl>
        List<String> inflections = new ArrayList<>();   //<if>
        List<String> senseNumber = new ArrayList<>();   //<sn>
        List<String> definition = new ArrayList<>();    //<dt>
        List<String> references = new ArrayList<>();    //<sx>

        @Override
        protected String doInBackground(String... strings) {

            try{

                String myUrl = strings[0];
                URL url = new URL(myUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                InputStream i = urlConnection.getInputStream();
                XmlPullParser pp = Xml.newPullParser();
                pp.setInput(i, "UTF-8");
                while(pp.getEventType() != XmlPullParser.END_DOCUMENT){
                    if (pp.getEventType() == XmlPullParser.START_TAG){
                        String tagName = pp.getName();
                        switch (tagName) {
                            case "hw":

                                headWord.add(pp.nextText());
                                publishProgress(10);

                                break;
                            case "pr":

                                pronunciation.add('\\' + pp.nextText() + "\\");
                                publishProgress(20);

                                break;
                            case "fl":

                                wordType.add(pp.getAttributeValue(null, "fl"));
                                publishProgress(30);

                                break;
                            case "if":

                                inflections.add(pp.getAttributeValue(null, "if"));
                                publishProgress(40);

                                break;
                            case "sn":

                                senseNumber.add(pp.getAttributeValue(null, "sn"));
                                publishProgress(60);

                                break;
                            case "dt":

                                definition.add(pp.getAttributeValue(null, "dt"));
                                publishProgress(75);
                                references.add(pp.getAttributeValue(null, "sx"));
                                publishProgress(100);

                                break;
                        }
                    }
                    pp.next();
                }
                i.close();

            }catch(Exception e){
                Log.e("Program crashed", e.getStackTrace().toString());
            }
            return "Finished Task";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pb.setVisibility(View.INVISIBLE);
            TextView hw = findViewById(R.id.headWord);
            TextView pr = findViewById(R.id.pronunciation);

            hw.setText(headWord.get(0));
            pr.setText(pronunciation.get(0));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(values[0]);
        }
    }
}
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
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
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
 * @version 1.2.
 */
public class Dictionary extends AppCompatActivity {
    /**
     * Global parameters used to interact with the saved words list, interact with the GUI, and a
     *  boolean to track if a word was found via the search process.
     */
    private ProgressBar pb;
    private String searchTerm;
    private EditText searchBar;
    private TextView hw;
    private TextView pr;
    private TextView sg;
    private TextView dt;
    private TextView wt;
    private boolean notFound =false;
    private ListView theList;

    /**
     * Lists to hold different variations and parts of a definition. Different words have a
     * varying number of meanings and uses, and while this application currently only loads the most
     * relevant, these lists were coded to allow expandability.
     * Note: Suggestions is only used if a word is not in the dictionary but is recognized.
     */
    List<String> headWord = new ArrayList<>();      //<hw>
    List<String> pronunciation = new ArrayList<>(); //<pr>
    List<String> wordType = new ArrayList<>();      //<fl>
    List<String> senseNumber = new ArrayList<>();   //<sn>
    List<String> definition = new ArrayList<>();    //<dt>
    List<String> suggestions = new ArrayList<>();   //<suggestion>


    /**
     * This method initializes the Dictionary activity. The setContentView() method is used to
     * define the layout resource to be used. Toolbar and ProgressBar are initialized, and click
     * listeners are defined for the search and save buttons.
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
        searchBar = findViewById(R.id.searchBar);
        theList = findViewById(R.id.dict_sugg_list);
        hw = findViewById(R.id.headWord);
        pr = findViewById(R.id.pronunciation);
        wt = findViewById(R.id.wordType);
        dt = findViewById(R.id.definition);
        sg = findViewById(R.id.suggestTitle);


        int searchCode = getIntent().getIntExtra("savedWords",0);
        if(searchCode != 0){
            String wordPassed = getIntent().getStringExtra("word");
            search(wordPassed);
        }

        searchButton.setOnClickListener(btn -> {
            clearFields();
            searchTerm = searchBar.getText().toString().trim();
            search(searchTerm);
        });



        saveButton.setOnClickListener(btn -> {
            if (!headWord.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                LinearLayout l = findViewById(R.id.customDialog);
                View v = inflater.inflate(R.layout.activity_dictionary_dialog, l);
                builder.setView(v);
                builder.setPositiveButton(R.string.positive, (dialog, which) -> {
                    Intent nextPage = new Intent(Dictionary.this, DictionarySavedWords.class);
                    nextPage.putExtra("dictionary", 1);
                    nextPage.putExtra("word", searchTerm);
                    startActivity(nextPage);
                });
                builder.setNegativeButton(R.string.negative, (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }else{
                Toast.makeText(this, R.string.noWordToSave, Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * This method is used to hide the keyboard, clear array lists, store search term,
     * and finally call execute on the AsyncTask thread, passing the URL with the encoded
     * search term.
     *
     * @param term String - The word to be searched.
     */
    public void search(String term){
        View v = this.getCurrentFocus();
        InputMethodManager mm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        headWord.clear();
        pronunciation.clear();
        wordType.clear();
        definition.clear();
        suggestions.clear();
        searchTerm = term;
        try {
            if(v != null)
                mm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            String param = URLEncoder.encode(term, "UTF-8");
            DataFetcher networkThread = new DataFetcher();
            networkThread.execute("https://www.dictionaryapi.com/api/v1/references/sd3/xml/" + param
                    + "?key=4556541c-b8ed-4674-9620-b6cba447184f");
        }catch(Exception e){
            Log.e("Program crashed", e.getMessage());
        }
    }

    /**
     * Method specifically used to set all fields to blank and clear the stored search term.
     */
    public void clearFields(){
        searchTerm = "";
        hw.setText("");
        pr.setText("");
        dt.setText("");
        wt.setText("");
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
                Intent swPage = new Intent(Dictionary.this, DictionarySavedWords.class);
                startActivity(swPage);
                break;
            case R.id.item2:
                Intent infoPage = new Intent(Dictionary.this, DictionaryInfo.class);
                startActivity(infoPage);
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
                ContextCompat.getColor(this, R.color.colorAccentD),
                android.graphics.PorterDuff.Mode.SRC_IN);
        pb.setVisibility(View.INVISIBLE);
    }

    /**
     * This class extends AsyncThread to allow access to server data on a seperate thread from
     *  the GUI.
     */
    private class DataFetcher extends AsyncTask<String, Integer, String> {


        /**
         * This method does all the work of retrieving and parsing xml data from the requested link.
         * This data is then identified and sorted to the correct location using a for loop and a
         * switch statement. PublishProgress() is called as each item is retrieved.
         *
         * @param strings Array of strings, used to pass URL from execute().
         * @return String task finished message.
         */
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

                                wordType.add(pp.nextText());
                                publishProgress(30);

                                break;
                            case "sn":

                                senseNumber.add(pp.nextText());
                                publishProgress(60);

                                break;
                            case "dt":
                                if (pp.next() == XmlPullParser.TEXT)
                                    definition.add(pp.getText());

                                //definition.add(pp.nextText());
                                publishProgress(75);


                                break;
                            case "suggestion":
                                suggestions.add(pp.nextText());
                                notFound = true;
                                break;
                        }
                    }
                    pp.next();
                }
                publishProgress(100);
                i.close();


            }catch(Exception e){
                Log.e("Program crashed", e.getMessage());
            }
            return "Finished Task";
        }

        /**
         * Used to update GUI elements after the data has been received. If the word is found,
         * typical activity is used. If the word is not found and suggestions are returned,
         * suggestion list is made visible and populated.
         *
         * @param s String returned by doInBackground.
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pb.setVisibility(View.INVISIBLE);
            TextView hw = findViewById(R.id.headWord);
            TextView pr = findViewById(R.id.pronunciation);
            TextView wt = findViewById(R.id.wordType);
            TextView df = findViewById(R.id.definition);
            if (!headWord.isEmpty()) {
                notFound=false;
                sg.setVisibility(View.INVISIBLE);
                theList.setVisibility(View.INVISIBLE);
                hw.setText(headWord.get(0));

                if (!pronunciation.isEmpty()) {
                    pr.setText(pronunciation.get(0));
                }
                if (!wordType.isEmpty()) {
                    wt.setText(wordType.get(0));
                }
                if (!definition.isEmpty()) {
                    df.setText(definition.get(0));
                }

            }else {
                hw.setText(R.string.wordNotFound);
                pr.setText("");
                wt.setText("");
                df.setText("");
                sg.setVisibility(View.VISIBLE);
                theList.setVisibility(View.VISIBLE);
                if (notFound) {
                    ListAdapter adt = new MyArrayAdapter<>(suggestions);
                    ListView theList = findViewById(R.id.dict_sugg_list);
                    theList.setAdapter(adt);
                    theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
                        search(suggestions.get(position));
                        ((MyArrayAdapter) adt).notifyDataSetChanged();
                    });
                }
            }

        }

        /**
         * Sets progress bar to visible and sets the appropriate value.
         *
         * @param values Value to update progress bar.
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(values[0]);
        }
    }

    /**
     * This class extends BaseAdapter and is used as the adapter for the ListView of suggestions if
     * suggestions exist. It takes an array and populates the ListView with the given data.
     *
     */
    protected class MyArrayAdapter<E> extends BaseAdapter
    {
        private List<E> dataCopy;

        /**
         * Constructor, stores a copy of original data passed.
         *
         * @param originalData data to be copied.
         */
        MyArrayAdapter(List<E> originalData)
        {
            dataCopy = originalData;
        }

        /**
         * Return the size of the list,
         *
         * @return size of dataCopy.
         */
        public int getCount()
        {
            return dataCopy.size();
        }

        /**
         * Get suggestion at requested position.
         * @param position index to be used.
         * @return word at given index.
         */
        public E getItem(int position){
            return dataCopy.get(position);
        }

        /**
         * Inflates a row of the ListView based on a defined layout file. If a old view is found,
         * it is used instead.
         * @param position Item's position in list.
         * @param old Old view to be recycled.
         * @param parent ViewGroup
         * @return root - New view that has been inflated.
         */
        public View getView(int position, View old, ViewGroup parent)
        {
            LayoutInflater inflater = getLayoutInflater();
            View root = old;
            if (old == null)
                root = inflater.inflate(R.layout.activity_dict_sugg_row, parent, false);

            TextView tv = root.findViewById(R.id.textOnRow);
            tv.setText(suggestions.get(position));
            return root;
        }


        /**
         * Returns passed position until database implementation.
         *
         * @param position word position in list.
         * @return position.
         */
        public long getItemId(int position) {
            return position;
        }
    }
}
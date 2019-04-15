package com.example.androidfinalgroupproject;

/**
 *  <<<<<<<<<<<<<Some of the code was provided by Webhose I configured most of the code to my likening >>>>>>>>>
 *https://github.com/Webhose/webhoseio-java-sdk  040656012
 *
 * MIT License
 *
 * Copyright (c) 2017 Webhose.io
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;


public class NewsFeed extends AppCompatActivity {
    /**
     * JsonObject to store strings from the xml 0
     */
    private JSONObject result;
    /**
     * TextView in the ojectsLayout 4
     */
    private TextView textView;
    /**
     * JSONarray to stor JSONobjects 0
     */
    private JSONArray postArray;
    /**
     * Object of the WebhoseIOClient class 6
     */
    private WebhoseIOClient webhoseClient;
    /**
     * An Array adapter to linflate the listview with the arraylist items 5
     */
  private ArrayAdapter<String> arrayAdapter;
    /**
     * Listeview object form the newsfeed layout 6
     */
    private  ListView listView;
    /**
     * Arraylist that store the items from the JSONarray 0
     */
    private ArrayList<String> items;
    /**
     * Button object from the newsfeed layout 1
     */
    private ImageButton button;
    /**
     * Progressbar for the newsfeed laytut 2
     */
    private ProgressBar bar;
    /**
     * The speed of the progress bar
     */
    private int progress;
    /**
     * Editbox variable
     */
    private EditText editText;
    private SharedPreferences sharedPref;
  private TextView text;
    private View view;

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    private  int idNumber;

    private SQLiteDatabase dbn;
    private NewsFeedDatabaseOpener opener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Instantiate the variable and call upo jsonArr()
         * Configure the progressbar
         * Onclick for the Button check if item has completed collecting the data
         * Otherwise will throw a toast
         * if items is not null then the items will displayed on the listeview
         * Onclick for the items will open a snackbar
         * the allow the user to click yes
         * if yes was clicked then alert diologe box will appear
         * dialoge box will allow user to open a web page for more information
         * This will be modified
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        result = new JSONObject();
        postArray = new JSONArray();
        webhoseClient = new WebhoseIOClient().getInstance("134a79ef-4a76-48e8-a627-fc9240845d29");
        listView = findViewById(R.id.newslistview);
        button = findViewById(R.id.button);
        bar = findViewById(R.id.simpleProgressBar);
        bar.setVisibility(View.INVISIBLE);
        editText = findViewById(R.id.newsfeedsearchbar);
        opener = new NewsFeedDatabaseOpener(this);
        dbn = opener.getWritableDatabase();
        items = new ArrayList<>();
        sharedPref = getSharedPreferences("PREF_FILE", Context.MODE_PRIVATE);
        String keyword = sharedPref.getString("keyword", "");
        if(keyword != null)
        {
            editText.setText(keyword);
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // Initialize a TextView for ListView each Item
                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text color of TextView (ListView Item)
                tv.setTextColor(Color.DKGRAY);

                // Generate ListView Item using TextView
                return view;
            }
        };

    listDisplay();
    dbn.execSQL("delete from "+ NewsFeedDatabaseOpener.TABLE_NAME);
        String [] columns = {NewsFeedDatabaseOpener.COL_ID, NewsFeedDatabaseOpener.COL_TITLE,  NewsFeedDatabaseOpener.COL_CONTENT};
        Cursor results = dbn.query(false, NewsFeedDatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        printCursor(results);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("keyword", editText.getText().toString());
                        editor.commit();
                        bar.setVisibility(View.VISIBLE);
                        jsonArr();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });






        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = listView.getItemAtPosition(position).toString();
                idNumber = (int)id;
                Bundle dataToPass = new Bundle();
                dataToPass.putString(ITEM_SELECTED, title);
                dataToPass.putLong(ITEM_ID, id);
                    Intent nextActivity = new Intent(getApplicationContext(),  NewsFeedEmptyActivity.class);
                    nextActivity.putExtras(dataToPass); //send data to next activity
                    startActivityForResult(nextActivity, EMPTY_ACTIVITY);//make the transition

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_feed_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.help:
            helpBox();
                break;
            case R.id.favorites_newsfeed:
                    Intent intent = new Intent(this, NewsFeedFavorites.class);
                    startActivity(intent);
                    break;
        }
        return false;

    }



    public void helpBox() {
        AlertDialog alertDialog = new AlertDialog.Builder(NewsFeed.this).create();
        alertDialog.setTitle("Abdullah Al-Hajjar");
       String language = Locale.getDefault().getLanguage();
       System.out.println(language);
       if(language.equals("en")) {
           alertDialog.setMessage("News Feed Acitvity v2.0\nHow to Use\nType in any keyword to search your desired topic then select that topic from the list to access its content you then have the option to save that specific article");
       }else if(language.equals("fr"))
           {
               alertDialog.setMessage("Fil De Nouvelles v2.0\nComment utiliser\n" +
                       "Tapez n'importe quel mot clé pour rechercher le sujet de votre choix, puis sélectionnez ce sujet dans la liste pour accéder à son contenu. Vous avez ensuite la possibilité d'enregistrer cet article");
           }
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    public void jsonArr() throws JSONException{
        /**
         * Inserts the API Token
         * instert the Search KeyWord
         * This will be modified
         */

            //webhoseClient = getInstance("134a79ef-4a76-48e8-a627-fc9240845d29");
            String queries = editText.getText().toString();
            webhoseClient.query("filterWebContent", queries);
            System.out.println("JSONARR>>>>>>>>>>>ON");
    }

    public void listDisplay()
    {
                System.out.println("LISTDISPLAY>>>>>>>>>>>WAIT");


        String [] columns = {NewsFeedDatabaseOpener.COL_ID, NewsFeedDatabaseOpener.COL_TITLE,  NewsFeedDatabaseOpener.COL_CONTENT};
        Cursor results = dbn.query(false, NewsFeedDatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        int titleColIndex = results.getColumnIndex(NewsFeedDatabaseOpener.COL_TITLE);



        while(results.moveToNext())
        {
            String title= results.getString(titleColIndex );

            if(title != null)
            items.add(title);
        }
        if (items.size() > 0) {

            listView.setAdapter(arrayAdapter);
            bar.setVisibility(View.INVISIBLE);
        }

}


public void setDataBase(String title, String content)
{

            ContentValues newRow = new ContentValues();
            newRow.put(NewsFeedDatabaseOpener.COL_TITLE, title);
            newRow.put(NewsFeedDatabaseOpener.COL_CONTENT, content);
            dbn.insert(NewsFeedDatabaseOpener.TABLE_NAME, null, newRow);

}

    public void printCursor(Cursor c)
    {
        String[] columns = c.getColumnNames();
        int colId = c.getColumnIndex(NewsFeedDatabaseOpener.COL_ID);
        int colIndex = c.getColumnIndex(NewsFeedDatabaseOpener.COL_TITLE );
        int colIndexRec = c.getColumnIndex(NewsFeedDatabaseOpener.COL_CONTENT);


        Log.v("Test ", "database version number............................     " +NewsFeedDatabaseOpener.VERSION_NUM);
        Log.v("Test ", "number of columns in the cursor............................     " + c.getColumnCount());
        Log.v("Test ", "number of results in the cursor............................     " + c.getCount());
        Log.v("Test ", "name of the columns in the cursor............................     " + Arrays.toString(columns));

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++)
        {
            String id = c.getString(colId);
            String  title = c.getString(colIndex);
            String content = c.getString(colIndexRec);
            Log.v("Test ", "each row of results in the cursor............................     " + id+ "           "+title+"            "+content);

            c.moveToNext();
        }
    }
class WebhoseIOClient extends AsyncTask<String, Integer, String> {
        /**
         * This is the Json object that will be used to store the Api Content
         */
        private JSONObject jObject;
        /**
         * This main webpage for the news feed api
         */
        private static final String WEBHOSE_BASE_URL = "http://webhose.io";
        /**
         * This will store  the  api key
         */
        private  WebhoseIOClient mClient;
        /**
         * This Stores the Token Key
         */
        private String mApiKey;
        /**
         * This  Stores the item from te JSONarray
         */
        private ArrayList<String> items;

        private ProgressBar progressBar;

        public WebhoseIOClient()
        {

        }

        public WebhoseIOClient(ProgressBar pb)
        {
            this.progressBar = pb;
        }

        @Override
        protected String doInBackground(String... strings) {

            /**
             * Use the string that was build to connect to the specific wbepage
             * Will then append and save all the string into an JSONobject
             */
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"), 8);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();


                System.out.println("............DONE THE APPENDING");

                String result = response.toString();

                jObject = new JSONObject(result);

                setjObject(jObject);

                JSONArray postArray = jObject.getJSONArray("posts");
                items = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    if(postArray.getJSONObject(i).getString("title").isEmpty()) {
                    }else{items.add(postArray.getJSONObject(i).getString("title"));}
                }
                setItems(items);
                System.out.println("............DONE ARRAY");


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Mission Acomplished";
        }

        private WebhoseIOClient(String apiKey) {
            /**
             * private Constructor
             */
            //this.progressbar
            this.mApiKey = apiKey;
        }

       public  WebhoseIOClient getInstance(String apiKey) {
            /**
             * Gets the Token
             * private Constructor
             */
            if (mClient == null) {
                mClient = new WebhoseIOClient(apiKey);
            }

            return mClient;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                //040656012
        }


        public void query(String endpoint, String queries) {
            /**
             * Builds the URL
             */
            //  http://webhose.io/filterWebContent?token=134a79ef-4a76-48e8-a627-fc9240845d29&format=json&sort=relevancy&q=stock%20market%20language%3Aenglish040656012
            String builder = String.format("%s/%s?token=%s&format=json&sort=relevancy&q=%s&language=english", WEBHOSE_BASE_URL, endpoint, mApiKey, queries);
            try {
                mClient = new WebhoseIOClient(mApiKey);
                mClient.execute(builder);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }



        public JSONObject getjObject() {
            /**
             * Returns JSONobject
             */
            return jObject;
        }




        public void setItems(ArrayList<String> items) {
            /**
             * Sets the  items array list
             */
            this.items = items;
        }

        public ArrayList<String> getItems() {
            /**
             * Returns the items arrayslist
             */
            return items;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray postArray = jObject.getJSONArray("posts");
                for (int i = 0; i < 20; i++) {
                    if (postArray.getJSONObject(i).getString("title").isEmpty())
                    {
                    }
                    else
                    {
                        setDataBase(postArray.getJSONObject(i).getString("title"), postArray.getJSONObject(i).getString("text"));
                    }
                }


            }catch (Exception e)
            {
                e.printStackTrace();
            }
            listDisplay();

        }




        public void setjObject(JSONObject jObject) {
            this.jObject = jObject;
        }
    }


}



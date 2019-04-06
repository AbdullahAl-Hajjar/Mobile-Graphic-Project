package com.example.androidfinalgroupproject;

/**
 *  <<<<<<<<<<<<<Some of the code was provided by Webhose I configured most of the code to my likening >>>>>>>>>
 *https://github.com/Webhose/webhoseio-java-sdk
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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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

import java.util.ArrayList;
import java.util.Locale;


public class NewsFeed extends AppCompatActivity {
    /**
     * JsonObject to store strings from the xml
     */
    private JSONObject result;
    /**
     * TextView in the ojectsLayout
     */
    private TextView textView;
    /**
     * JSONarray to stor JSONobjects
     */
    private JSONArray postArray;
    /**
     * Object of the WebhoseIOClient class
     */
    private WebhoseIOClient webhoseClient;
    /**
     * An Array adapter to linflate the listview with the arraylist items
     */
    private ArrayAdapter<String> arrayAdapter;
    /**
     * Listeview object form the newsfeed layout
     */
    private ListView listView;
    /**
     * Arraylist that store the items from the JSONarray
     */
    private ArrayList<String> items;
    /**
     * Button object from the newsfeed layout
     */
    private ImageButton button;
    /**
     * Progressbar for the newsfeed laytut
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
        webhoseClient = new WebhoseIOClient();
        listView = findViewById(R.id.newslistview);
        button = findViewById(R.id.button);
        bar = findViewById(R.id.simpleProgressBar);
        bar.setVisibility(View.INVISIBLE);
        editText = findViewById(R.id.newsfeedsearchbar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                jsonArr();
                listDisplay();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar sb = Snackbar
                        .make(findViewById(R.id.newsfeedlayout), "Open Dialog", Snackbar.LENGTH_LONG);

                sb.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog alertDialog = new AlertDialog.Builder(NewsFeed.this).create();
                        alertDialog.setTitle("URL");
                        alertDialog.setMessage("For More Information Click ok\n" + "https://www.cbc.ca/");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url = "https://www.cbc.ca/";
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    }
                });

                sb.show();

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
        bar.setVisibility(View.VISIBLE);
        bar.setProgress(10);
            webhoseClient = WebhoseIOClient.getInstance("134a79ef-4a76-48e8-a627-fc9240845d29");
            String queries = editText.getText().toString();
            webhoseClient.query("filterWebContent", queries);
            System.out.println("JSONARR>>>>>>>>>>>ON");
    }

    public void listDisplay()
    {

        System.out.println("LISTDISPLAY>>>>>>>>>>>WAIT");


        try {
            Toast.makeText(getApplicationContext(), "Loading Data Please Wait", Toast.LENGTH_LONG).show();
Thread.sleep(8000);
        }catch (Exception e){}
        bar.setVisibility(View.INVISIBLE);
    System.out.println("LISTDISPLAY>>>>>>>>>>>ON");
        webhoseClient = WebhoseIOClient.getInstance("134a79ef-4a76-48e8-a627-fc9240845d29");
        items = webhoseClient.getItems();
    if (items != null) {
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
        listView.setAdapter(arrayAdapter);
        bar.setVisibility(View.INVISIBLE);
    } else {
        Toast.makeText(getApplicationContext(), "Still Loading Data", Toast.LENGTH_SHORT).show();

    }
}





}



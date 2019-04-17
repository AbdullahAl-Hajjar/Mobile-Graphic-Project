package com.example.androidfinalgroupproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class NewsFeedFavorites extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> items;
    private ArrayAdapter<String> arrayAdapter;
    private NewsFeedDetailFragment newsFragment;
    private int idNumber;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    private NewsFeedEmptyActivity empty;
    private SQLiteDatabase dbn;
    private NewsFeedFavOpener opener;
    private Cursor results;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_favorites);
        listView = findViewById(R.id.newsfeedfavlist);
        newsFragment = new NewsFeedDetailFragment();
        empty = new NewsFeedEmptyActivity();
        items = new ArrayList<>();
        opener = new NewsFeedFavOpener(this);
        dbn = opener.getWritableDatabase();
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
   String[] columns = {NewsFeedFavOpener.COL_ID, NewsFeedFavOpener.COL_TITLE, NewsFeedFavOpener.COL_CONTENT};
        results = dbn.query(false, NewsFeedFavOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        listDisplay();
printCursor(results);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * send data to next activity
                 * make the transition
                 */
                String title = listView.getItemAtPosition(position).toString();
                idNumber = (int) id;
                Bundle dataToPass = new Bundle();
                dataToPass.putString(ITEM_SELECTED, title);
                dataToPass.putLong(ITEM_ID, id);
                Intent nextActivity = new Intent(getApplicationContext(), NewsFeedEmptyActivity2.class);
                nextActivity.putExtras(dataToPass);
                startActivityForResult(nextActivity, EMPTY_ACTIVITY);

            }

        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        /**
         * Ends the Activity
         */
        finish();
    }

    public void listDisplay() {

        /**
         * Lists the Favorite objects saved by the user
         */
        int titleColIndex = results.getColumnIndex(NewsFeedFavOpener.COL_TITLE);


        results.moveToFirst();
        for (int i = 0; i < results.getCount(); i++)
        {
            String title = results.getString(titleColIndex);
            if (title != null)
                items.add(title);
            results.moveToNext();
        }

        if (items.size() > 0) {

            listView.setAdapter(arrayAdapter);
        }
        else {
                Toast.makeText(getApplicationContext(), "Empty List", Toast.LENGTH_SHORT).show();

            }
        }




    public void printCursor(Cursor c)
    {

        /**
         * prints the  cursors of the newsfeedfav Table
         */
        String[] columns = c.getColumnNames();
        int colId = c.getColumnIndex(NewsFeedFavOpener.COL_ID);
        int colIndex = c.getColumnIndex(NewsFeedFavOpener.COL_TITLE );
        int colIndexRec = c.getColumnIndex(NewsFeedFavOpener.COL_CONTENT);


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
            Log.v("Test ", "each row of results in the cursor............................     " + id+ "           "+title+"            "+content);                                                                                                                                                                                                                                                                                  //040656012

            c.moveToNext();
        }
    }


    }


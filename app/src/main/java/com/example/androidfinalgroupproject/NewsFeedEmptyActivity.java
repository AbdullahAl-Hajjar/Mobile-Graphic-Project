package com.example.androidfinalgroupproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsFeedEmptyActivity extends AppCompatActivity {

    private ArrayList<String> items;
    public static SQLiteDatabase dbn;
    private NewsFeedFavOpener opener;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_empty);
        items = new ArrayList<>();
        opener = new NewsFeedFavOpener(this);
        dbn = opener.getWritableDatabase();
        textView = findViewById(R.id.newsfeedtitle);


        String [] columns = {NewsFeedFavOpener.COL_ID, NewsFeedFavOpener.COL_TITLE,  NewsFeedFavOpener.COL_CONTENT};
        //Cursor results = dbn.query(false, NewsFeedFavOpener.TABLE_NAME, columns, null, null, null, null, null, null);


        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        NewsFeedDetailFragment dFragment = new NewsFeedDetailFragment();
        dFragment.setArguments( dataToPass ); //pass data to the the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                .addToBackStack("AnyName")
                .commit();

        System.out.println("ONCREATHEERE");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.news_feed_save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NewsFeedDetailFragment dFragment = new NewsFeedDetailFragment();
        switch (item.getItemId()) {
            case R.id.newsfeedsave:
                TextView textView = findViewById(R.id.newsfeedtitle);
                TextView textView1 = findViewById(R.id.newsfeecontent);
               String title = textView.getText().toString();
               String content = textView1.getText().toString();
                ContentValues newRow = new ContentValues();
                newRow.put(NewsFeedFavOpener.COL_TITLE, title);
                newRow.put(NewsFeedFavOpener.COL_CONTENT, content);
                dbn.insert(NewsFeedFavOpener.TABLE_NAME, null, newRow);
                Toast.makeText(getApplicationContext(), "Article was Saved to Favorites", Toast.LENGTH_SHORT).show();
                break;

        }
        return false;

    }


    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}

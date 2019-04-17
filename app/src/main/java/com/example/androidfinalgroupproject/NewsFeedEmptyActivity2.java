package com.example.androidfinalgroupproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsFeedEmptyActivity2 extends AppCompatActivity {

    /**
     *  Emtpy activity to pass data from bundle
     */

    private ArrayList<String> items;
    private NewsFeedFavOpener opener;
   private  SQLiteDatabase db;
   private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Cursor results = dbn.query(false, NewsFeedFavOpener.TABLE_NAME, columns, null, null, null, null, null, null);
         * get the data that was passed from FragmentExample
         * pass data to the the fragment
         */


        setContentView(R.layout.activity_news_feed_empty2);
        items = new ArrayList<>();
        opener = new NewsFeedFavOpener(this);
        db =  opener.getWritableDatabase();

        Bundle dataToPass = getIntent().getExtras();


        NewsFeedDetailFragment2 dFragment = new NewsFeedDetailFragment2();
        dFragment.setArguments( dataToPass );
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation2, dFragment)
                .addToBackStack("AnyName")
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * Inflates the toolbar
         */
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.new_feed_favorites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            /**
             * Removes the deleted item from the NewsFeedFav Table
             */
            case R.id.removefavorites:

                String [] columns = {NewsFeedFavOpener.COL_ID, NewsFeedFavOpener.COL_TITLE,  NewsFeedFavOpener.COL_CONTENT};
               Cursor results = db.query(false, NewsFeedFavOpener.TABLE_NAME, columns, null, null, null, null, null, null);
                TextView textView = findViewById(R.id.newsfeedtitle2);
                String title = textView.getText().toString();
                int colIndexTitle = results.getColumnIndex(NewsFeedFavOpener.COL_TITLE );
                int id =  results.getColumnIndex(NewsFeedFavOpener.COL_ID );
                String indexid="";
                results.moveToFirst();
                for (int i = 0; i < results.getCount(); i++)
                {
                    String dbtitle = results.getString(colIndexTitle);
                    if (title.equals(dbtitle))
                    {
                        indexid = results.getString(id);
                    }
                    results.moveToNext();
                }
                db.delete(NewsFeedFavOpener.TABLE_NAME, NewsFeedFavOpener.COL_ID+ "=" + indexid,null);
                Toast.makeText(getApplicationContext(), "Article was Removed From Favorites", Toast.LENGTH_SHORT).show();
                finish();
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

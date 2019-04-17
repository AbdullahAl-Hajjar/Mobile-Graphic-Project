package com.example.androidfinalgroupproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class NewsFeedDetailFragment extends Fragment {

    /**
     * Fragment displays the content of the click item
     */
    private Bundle dataFromActivity;
    private long id;
    private  String message;
    private  int  position;
    private NewsFeedDatabaseOpener opener;
    public static SQLiteDatabase dbn;
    private   String indexContent;
    private View result;
    private Cursor results;
    private int colIndexTitle;
   private  int colIndexContent;
   private ArrayList<String> item;
    private String title ;
    private  TextView trueTitle;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      opener= new NewsFeedDatabaseOpener(getActivity());
        dbn = opener.getWritableDatabase();
        item = new ArrayList<>();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(NewsFeed.ITEM_ID );
        message = dataFromActivity.getString(NewsFeed.ITEM_SELECTED);
        position = dataFromActivity.getInt(NewsFeed.ITEM_POSITION);

        final SQLiteDatabase dbn = opener.getWritableDatabase();
        String [] columns = {NewsFeedDatabaseOpener.COL_ID, NewsFeedDatabaseOpener.COL_TITLE,  NewsFeedDatabaseOpener.COL_CONTENT};
        results = dbn.query(false, NewsFeedDatabaseOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        colIndexTitle = results.getColumnIndex(NewsFeedDatabaseOpener.COL_TITLE );
        colIndexContent = results.getColumnIndex(NewsFeedDatabaseOpener.COL_CONTENT);

        result =  inflater.inflate(R.layout.activity_news_feed_detail_fragment, container, false);


        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**
         * Checks which item was clicked then compares it to the database to retreive its content
         */
        title = dataFromActivity.getString(NewsFeed.ITEM_SELECTED);
        trueTitle = result.findViewById(R.id.newsfeedtitle);
        trueTitle.setText(title);
        results.moveToFirst();
        for (int i = 0; i < results.getCount(); i++)
        {
            String indexTitle = results.getString(colIndexTitle);
            if (title.equals(indexTitle))
            {
                indexContent = results.getString(colIndexContent);
            }
            results.moveToNext();
        }
        TextView contentView = (TextView)result.findViewById(R.id.newsfeecontent);
        contentView.setText(title+"\n\n"+indexContent);
        contentView.setTextSize(18);
    }

    @Override
    public void onPause() {
        super.onPause();
        /**
         *
         */
        NewsFeedEmptyActivity parent = (NewsFeedEmptyActivity) getActivity();
        parent.finish();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

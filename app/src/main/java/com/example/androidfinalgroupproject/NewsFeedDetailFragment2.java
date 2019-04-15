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

public class NewsFeedDetailFragment2 extends Fragment {
    private Bundle dataFromActivity;
    private long id;
    private  String message;
    private  int  position;
    private NewsFeedFavOpener opener;
    private SQLiteDatabase dbn;
    private   String indexContent;
    private View result;
    private Cursor results;
    private int colIndexTitle;
    private  int colIndexContent;
    private ArrayList<String> item;
    private String title ;
    private TextView textView;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        opener= new NewsFeedFavOpener(getActivity());
        dbn = opener.getWritableDatabase();
        item = new ArrayList<>();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(NewsFeedFavorites.ITEM_ID );
        message = dataFromActivity.getString(NewsFeedFavorites.ITEM_SELECTED);

        final SQLiteDatabase dbn = opener.getWritableDatabase();
        String [] columns = {NewsFeedFavOpener.COL_ID, NewsFeedFavOpener.COL_TITLE,  NewsFeedFavOpener.COL_CONTENT};
        results = dbn.query(false, NewsFeedFavOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        colIndexTitle = results.getColumnIndex(NewsFeedFavOpener.COL_TITLE );
        colIndexContent = results.getColumnIndex(NewsFeedFavOpener.COL_CONTENT);

        result =  inflater.inflate(R.layout.activity_news_feed_detail_fragment2, container, false);


        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        message = dataFromActivity.getString(NewsFeedFavorites.ITEM_SELECTED);
        textView = result.findViewById(R.id.newsfeedtitle2);
        textView.setText(message);
        results.moveToFirst();
        for (int i = 0; i < results.getCount(); i++)
        {
            String indexTitle = results.getString(colIndexTitle);
            if (message.equals(indexTitle))
            {
                indexContent = results.getString(colIndexContent);
            }
            results.moveToNext();
        }
        TextView contentView = result.findViewById(R.id.newsfeedcontent2);
        contentView.setText(indexContent);
        contentView.setTextSize(18);
    }

    @Override
    public void onPause() {
        super.onPause();
       NewsFeedEmptyActivity2 parent = (NewsFeedEmptyActivity2) getActivity();
        parent.finish();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

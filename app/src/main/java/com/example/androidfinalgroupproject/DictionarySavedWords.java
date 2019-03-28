package com.example.androidfinalgroupproject;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class DictionarySavedWords extends AppCompatActivity {

    int numWords = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_words);
        Toolbar toolbar = findViewById(R.id.savedWordsTb);
        toolbar.setTitle(R.string.savedWordsTitle);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        setSupportActionBar(toolbar);

        // code for toolbar back arrow
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(btn -> finish());

        ListAdapter adt = new MyArrayAdapter(new String[] {"Word 1", "Word 2", "Word 3"});
        ListView theList = (ListView)findViewById(R.id.wordList);
        theList.setAdapter(adt);
        //This listens for items being clicked in the list view
        theList.setOnItemClickListener(( parent,  view,  position,  id) -> {
            Log.e("you clicked on :" , "item "+ position);

            numWords = 20;
            ((MyArrayAdapter) adt).notifyDataSetChanged();
        });
    }

    //A copy of ArrayAdapter. You just give it an array and it will do the rest of the work.
    protected class MyArrayAdapter<E> extends BaseAdapter
    {
        private List<E> dataCopy = null;

        //Keep a reference to the data:
        public MyArrayAdapter(List<E> originalData)
        {
            dataCopy = originalData;
        }

        //You can give it an array
        public MyArrayAdapter(E [] array)
        {
            dataCopy = Arrays.asList(array);
        }


        //Tells the list how many elements to display:
        public int getCount()
        {
            return dataCopy.size();
        }


        public E getItem(int position){
            return dataCopy.get(position);
        }

        public View getView(int position, View old, ViewGroup parent)
        {
            //get an object to load a layout:
            LayoutInflater inflater = getLayoutInflater();

            //Recycle views if possible:
            TextView root = (TextView)old;
            //If there are no spare layouts, load a new one:
            if(old == null)
                root = (TextView)inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

            //Get the string to go in row: position
            String toDisplay = getItem(position).toString();

            //Set the text of the text view
            root.setText(toDisplay);

            //Return the text view:
            return root;
        }


        //Return 0 for now. We will change this when using databases
        public long getItemId(int position)
        {
            return 0;
        }
    }
}
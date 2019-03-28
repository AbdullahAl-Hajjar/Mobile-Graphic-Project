package com.example.androidfinalgroupproject;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DictionarySavedWords extends AppCompatActivity {
    ArrayList<String> wordList = new ArrayList<>();

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

        // Add words to saved words array list, and set adapter.
        wordList.add("Word 1");
        wordList.add("Word 2");
        wordList.add("Word 3");
        ListAdapter adt = new MyArrayAdapter(wordList);
        ListView theList = findViewById(R.id.wordList);
        theList.setAdapter(adt);

        //Check if list was accessed by adding a word, if so, show Snackbar to confirm
        int addCode = getIntent().getIntExtra("dictionary",0);
        if(addCode != 0){
            CoordinatorLayout cl = findViewById(R.id.savedWordsLayout);
            Snackbar sb = Snackbar.make(cl,R.string.dictSnackbarText, Snackbar.LENGTH_INDEFINITE);
            sb.setAction(R.string.dictSnackbarDismiss, e-> sb.dismiss());
            sb.show();
        }

        //This listens for items being clicked in the list view
        theList.setOnItemClickListener((list, item, position, id)->{
            Bundle dataToPass = new Bundle();
            dataToPass.putString("word", wordList.get(position) );
            dataToPass.putInt("position", position);
            Intent nextActivity = new Intent(DictionarySavedWords.this, DictionaryWordDetail.class);
            nextActivity.putExtras(dataToPass); //send data to next activity
            startActivity(nextActivity); //make the transition
        });
    }

    //A copy of ArrayAdapter. You just give it an array and it will do the rest of the work.
    protected class MyArrayAdapter<E> extends BaseAdapter
    {
        private List<E> dataCopy;

        //Keep a reference to the data:
        MyArrayAdapter(List<E> originalData)
        {
            dataCopy = originalData;
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
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
/**
 * This class is used to interact with the list of user saved words. This class extends
 * AppCompatActivity. Current functionality is a placeholder to fulfill milestone 1 requirements.
 *
 * @author Nick Hallarn.
 * @version 1.0.
 */
public class DictionarySavedWords extends AppCompatActivity {
    /**
     * ArrayList that holds the list of user saved words.
     */
    ArrayList<String> wordList = new ArrayList<>();

    /**
     * This method initializes the DictionarySavedWords activity. The setContentView() method is
     * used to define the layout resource to be used. Toolbar, wordList and ArrayAdapter are
     * initialized. This activity checks to see if it was called by adding a new word, and if so,
     * shows a Snackbar to confirm the addition. Click listener for the word list is also defined.
     *
     * @param savedInstanceState Bundle object containing activity's previously saved state. If
     * activity is new, value will be null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_words);
        setTheme(R.style.DictionaryTheme);
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
        ListAdapter adt = new MyArrayAdapter<>(wordList);
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

    /**
     * This inner class extends BaseAdapter. It's purpose is to provide data to fill the saved words
     * ListView.
     *
     * @param <E> Object type of ArrayList
     */
    protected class MyArrayAdapter<E> extends BaseAdapter
    {
        /**
         * List object used to store a copy of the data passed to the adapter.
         */
        private List<E> dataCopy;

        /**
         * Constructor method. Takes a list of objects and creates a duplicate list to work with.
         *
         * @param originalData List of objects to be used for ListView.
         */
        MyArrayAdapter(List<E> originalData)
        {
            dataCopy = originalData;
        }

        /**
         * This method tells the list how many elements to display.
         *
         * @return Size of the ArrayList
         */
        public int getCount()
        {
            return dataCopy.size();
        }

        /**
         * This method takes an integer index, and returns the item at the designated position in
         * the list. Called in getView() function.
         *
         * @param position Index of requested item.
         * @return Requested item.
         */
        public E getItem(int position){
            return dataCopy.get(position);
        }

        /**
         * This method specifies how each row of the list will be displayed. Requires a
         * LayoutInflater to load the required layout file.
         *
         * @param position Index of item to be displayed.
         * @param old View used to recycle old views if possible.
         * @param parent ViewGroup to inflate view into.
         * @return The new TextView for the row.
         */
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

        /**
         * Function used to return database ID if necessary. At this time, it is not implemented .
         *
         * @param position Index of requested item.
         * @return No database in use, so return 0.
         */
        public long getItemId(int position)
        {
            return 0;
        }
    }
}
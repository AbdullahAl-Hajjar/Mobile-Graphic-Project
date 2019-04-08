package com.example.androidfinalgroupproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/**
 * This class is used to interact with the list of user saved words. This class extends
 * AppCompatActivity. Current functionality is a placeholder to fulfill milestone 1 requirements.
 *
 * @author Nick Hallarn.
 * @version 1.2.
 */
public class DictionarySavedWords extends AppCompatActivity {
    /**
     * ArrayList that holds the list of user saved words.
     */
    ArrayList<DictionaryWords> wordList = new ArrayList<>();
    ListAdapter adt = new MyArrayAdapter<>(wordList);
    SQLiteDatabase db;
    DictionaryFragment dFragment;

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

        DictionaryDbOpener dbOpener = new DictionaryDbOpener(this);
        db = dbOpener.getWritableDatabase();
        String [] columns = {DictionaryDbOpener.COL_ID, DictionaryDbOpener.COL_WORD};
        Cursor results = db.query(false, DictionaryDbOpener.TABLE_NAME, columns,
                null, null, null, null, null, null);
        int wordColIndex = results.getColumnIndex(DictionaryDbOpener.COL_WORD);
        int idColIndex = results.getColumnIndex(DictionaryDbOpener.COL_ID);
        wordList.clear();
        while(results.moveToNext())
        {
            String word = results.getString(wordColIndex);
            long id = results.getLong(idColIndex);
            Log.i("Word", "\nSaved Word :" + word + "\nID :" + id);
            wordList.add(new DictionaryWords(word, id));
        }
        results.close();


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

        ListView theList = findViewById(R.id.wordList);
        theList.setAdapter(adt);
        //Check if list was accessed by adding a word, if so, show Snackbar to confirm
        int addCode = getIntent().getIntExtra("dictionary",0);
        if(addCode != 0){
            String word = getIntent().getStringExtra("word");
            addWord(word);
            CoordinatorLayout cl = findViewById(R.id.savedWordsLayout);
            Snackbar sb = Snackbar.make(cl,R.string.dictSnackbarText, Snackbar.LENGTH_INDEFINITE);
            sb.setAction(R.string.dictSnackbarDismiss, e-> sb.dismiss());
            sb.show();
        }else{
            if (wordList.isEmpty()){
                Toast.makeText(this, R.string.noSavedWords, Toast.LENGTH_LONG).show();
            }
        }

        boolean isTablet = findViewById(R.id.fragmentLocation) != null;

        //This listens for items being clicked in the list view
        theList.setOnItemClickListener((list, item, position, id)->{

            Bundle dataToPass = new Bundle();

            dataToPass.putString("word", wordList.get(position).getWord());
            dataToPass.putInt("savedWords", 1);
            if (isTablet){
                 dFragment = new DictionaryFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout
                        .addToBackStack("AnyName") //make the back button undo the transaction
                        .commit(); //actually load the fragment.
            }else {
                Intent nextPage = new Intent(DictionarySavedWords.this, Dictionary.class);
                nextPage.putExtras(dataToPass); //send data to next activity
                startActivity(nextPage); //make the transition
            }
            });
    }

    /**
     * Adds a word to the database and word list and notifies the adapter of the change.
     * @param word word to be added.
     */
    public void addWord(String word){

        ContentValues row = new ContentValues();
        row.put(DictionaryDbOpener.COL_WORD, word);
        long id = db.insert(DictionaryDbOpener.TABLE_NAME, null, row);
        Log.i("add","Word =" + word);
        wordList.add(new DictionaryWords(word, id));
        ((MyArrayAdapter) adt).notifyDataSetChanged();
    }

    /**
     * Deletes a word from the database and word list and notifies the adapter of the change.
     * @param id Id of the word to be deleted.
     */
    public void deleteWord(int id){
        String w = wordList.get(id).getWord();
        long _id = wordList.get(id).getWordID();
        db.delete(DictionaryDbOpener.TABLE_NAME, "_id=?", new String[] {Long.toString(_id)});
        wordList.remove(id);
        ((MyArrayAdapter) adt).notifyDataSetChanged();
        getSupportFragmentManager().beginTransaction().remove(dFragment).commit();
        CoordinatorLayout cl = findViewById(R.id.savedWordsLayout);
        Snackbar sb = Snackbar.make(cl,w + " " +
                        getApplicationContext().getResources().getString(R.string.wordDeleted),
                Snackbar.LENGTH_INDEFINITE);
        sb.setAction(R.string.dictSnackbarDismiss, e-> sb.dismiss());
        sb.show();
    }

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
            View root = old;
            //If there are no spare layouts, load a new one:
            if(old == null)
                root = inflater.inflate(R.layout.activity_dict_saved_row, parent, false);

            //Get the string to go in row: position
            String toDisplay = getItem(position).toString();


            //Set the text of the text view
            TextView tv = root.findViewById(R.id.savedRow);
            tv.setText(toDisplay);

            ImageButton delButton = root.findViewById(R.id.deleteWordButton);
            delButton.setOnClickListener(v -> deleteWord((int) adt.getItemId(position)));

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
            return position;
        }
    }
}
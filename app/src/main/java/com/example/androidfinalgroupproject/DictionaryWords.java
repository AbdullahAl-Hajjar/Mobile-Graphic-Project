package com.example.androidfinalgroupproject;

/**
 * Simple object class to store words for the dictionary saved word list.
 * Word variable is the saved word.
 * id variable is the word's database id.
 * @author Nick Hallarn
 * @version 1.3.
 */
public class DictionaryWords {
    private String word;
    private long id;

    /**
     * Constructor for word class
     * @param word Saved word
     * @param id Database id
     */
    DictionaryWords(String word, long id){
        this.word = word;
        this.id = id;
    }

    /**
     * Get the saved word
     * @return saved word
     */
    String getWord(){
        return this.word;
    }

    /**
     * Get the id of the saved word
     * @return database id
     */
    long getWordID() { return this.id; }

    @Override
    public String toString() {
        return getWord();
    }
}

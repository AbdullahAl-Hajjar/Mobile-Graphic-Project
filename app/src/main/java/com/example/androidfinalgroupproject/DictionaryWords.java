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
    private String pron;
    private String type;
    private String def;
    private long id;

    /**
     * Constructor for word class
     * @param word Saved word
     * @param id Database id
     * @param pron Pronunciation
     * @param type Word Type
     * @param def Definition
     */
    DictionaryWords(String word, String pron, String type, String def, long id){
        this.word = word;
        this.id = id;
        this.pron = pron;
        this.type = type;
        this.def = def;
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

    /**
     * Get the word pronunciation
     * @return pronunciation
     */
    String getPron() { return this.pron; }

    /**
     * Get the word type
     * @return word type
     */
    String getType() { return this.type; }

    /**
     * Get the words definition
     * @return definition
     */
    String getDef() { return this.def; }

    /**
     * to String method
     * @return Word
     */
    @Override
    public String toString() {
        return getWord();
    }
}

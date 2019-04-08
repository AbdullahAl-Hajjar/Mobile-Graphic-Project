package com.example.androidfinalgroupproject;

public class DictionaryWords {
    private String word;
    private long id;

    DictionaryWords(String word, long id){
        this.word = word;
        this.id = id;
    }

    String getWord(){
        return this.word;
    }

    long getWordID() { return this.id; }

    @Override
    public String toString() {
        return getWord();
    }
}

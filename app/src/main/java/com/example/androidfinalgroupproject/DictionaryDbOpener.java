package com.example.androidfinalgroupproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Nick Hallarn
 * @version 1.3.
 */
public class DictionaryDbOpener extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "WordsDB";
    private static final int VERSION_NUM = 1;
    static final String TABLE_NAME = "Words";
    static final String COL_ID = "_id";
    static final String COL_WORD = "WORD";
    static final String COL_PRON = "PRON";
    static final String COL_TYPE = "TYPE";
    static final String COL_DEF = "DEF";

    /**
     * Constructor, uses parent class to initialize the database.
     * @param ctx The Activity where the database is being opened.
     */
    DictionaryDbOpener(Activity ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * Create the new database if it does not exist yet. Executes an SQL create statement if called.
     * @param db Database object for running SQL queries
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_WORD + " TEXT," + COL_PRON + " TEXT,"
                + COL_TYPE + " TEXT," + COL_DEF + " TEXT)");
    }

    /**
     * Called if the database exists and the constructor version is newer than the saved version.
     * @param db Database object
     * @param oldVersion Saved version
     * @param newVersion New version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:
        onCreate(db);
    }

    /**
     * Called if the database exists and the constructor version is older than the saved version.
     * @param db Database object
     * @param oldVersion Saved version
     * @param newVersion New Version
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        //Delete the old table:
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create a new table:

        onCreate(db);
    }
}

package com.example.androidfinalgroupproject;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NewsFeedDatabaseOpener extends SQLiteOpenHelper {

    /**
     * NewsFeed Table Sqlite
     */

    public static final String DATABASE_NAME = "MyDatabaseFile";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "NewsFeed";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "TITLE";
    public static final String COL_CONTENT = "CONTENT";


    public NewsFeedDatabaseOpener(Activity ctx){
        /**
         * The factory parameter should be null, unless you know a lot about Database Memory management
         */
        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }

    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "
                + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_TITLE + " TEXT, " + COL_CONTENT + " TEXT)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        /**
         * Delete the old table:
         * Create a new table:
         */
        Log.i("Database upgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);


        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i("Database downgrade", "Old version:" + oldVersion + " newVersion:"+newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

}



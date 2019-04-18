package com.example.androidfinalgroupproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class FlightTrackerDatabase extends SQLiteOpenHelper {


    /**
     * DB elements
     */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE = "FlightTrackerDatabase";
    public static final String TABLE = "SavedFlights";
    public static final String KEY_ID = "id";
    public static final String KEY_FLT_NBR = "fltNbr";


    /**
     * creates database
     * @param context  current context
     */
    FlightTrackerDatabase(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
    }


    /**
     * method used for creating the application table
     *
     * @param db current db in use
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_FLT_NBR + " TEXT NOT NULL );");
    }

    /**
     * method modifies the database
     *
     * @param db         current db in use
     * @param oldVersion previous version of db
     * @param newVersion most recent version of db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    /**
     * **find website got help from and cite it here
     *
     * @return cursor object with the query to get the information row from the table
     */
    public Cursor getAllSavedFlights() {
        final SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE;
        return db.rawQuery(query, null);
    }
}



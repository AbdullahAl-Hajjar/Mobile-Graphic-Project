package com.example.androidfinalgroupproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class FlightTrackerFragment extends Fragment {
    private Bundle ftBundle;
    private Context parent;
    private String flightNumber;

//need to adjust this so that when user clicks it will direct them to the individual flight details fragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ftBundle = this.getArguments();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View saveResults = inflater.inflate(R.layout.flight_tracker_frag_saved_list_activity, container,false);
        TextView fltNbr = saveResults.findViewById(R.id.FlightNumber); //this isnt being used - need to fix code once running


        Button saveButton = saveResults.findViewById(R.id.saveButton);
        flightNumber = ftBundle.getString("fltNbr");

        saveButton.setOnClickListener((v -> {

            FlightTrackerDatabase ftDatabaseHelper = new FlightTrackerDatabase(parent);
            SQLiteDatabase ftdatabase = ftDatabaseHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(FlightTrackerDatabase.KEY_FLT_NBR,flightNumber);
            ftdatabase.insert(FlightTrackerDatabase.TABLE,null,contentValues);
            ftdatabase.close();
            //maybe insert toast here
        }));
        return saveResults;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ftBundle = this.getArguments();
        parent = context;
    }
}

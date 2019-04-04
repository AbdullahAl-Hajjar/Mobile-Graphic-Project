package com.example.androidfinalgroupproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PlaneTracker extends AppCompatActivity {

    private TextView tvsearchCity;
    private Button tvsearchBtn;
    private ProgressBar progressBar;
    private EditText etSearchCity;
    private SharedPreferences ftsharedpref;
    // need to adjust url/variables "http://aviation-edge.com/v2/public/flights?key=4fbbe8-570515&arrIata=YOW";
    // to change to the airport code to whatever person selected
    //using these variables for testing
    private static final String URL_DEPART_FLTS = "http://aviation-edge.com/v2/public/flights?key=4fbbe8-570515&depIata=YOW";
    private static final String URL_ARR_FLT = "http://aviation-edge.com/v2/public/flights?key=4fbbe8-570515&arrIata=YOW";
    private static final String FLT_TRK_API_KEY= "4fbbe8-570515";
 //   private ftDialogListener dialogListener;

   // private Fragment resultsFragment;
    /**
     * onCreate is responsible for initializing the flight status tracker
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_tracker);
        Toolbar ftToolbar = (Toolbar)findViewById(R.id.ftToolbar);
        setSupportActionBar(ftToolbar);
        progressBar = findViewById(R.id.progressBar);
        tvsearchCity = (TextView) findViewById(R.id.searchBtn);
        tvsearchBtn = (Button) findViewById(R.id.searchBtn);
        tvsearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomDialog();
            }
        });
        //resultsFragment = (Fragment) findViewById(R.id.fragment);

//        ftsharedpref = getSharedPreferences("SearchCity", Context.MODE_PRIVATE );
//        String savedCityString = ftsharedpref.getString("SearchCityReserveName"," ")   ;
//        etSearchCity.setText((savedCityString));
    }

    public void openCustomDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        View customDialogueView = inflater.inflate(R.layout.flight_tracker_custom_dialog,null);




        //Add citation here, some ideas from from coding in flow customer dialog video
        // https://www.youtube.com/watch?v=ARezg1D9Zd0

        dialogBuilder.setView(customDialogueView);

        final EditText input = (EditText) customDialogueView.findViewById(R.id.cdETSearchCity);
        ftsharedpref = getSharedPreferences("SearchCity", Context.MODE_PRIVATE );
        dialogBuilder.setPositiveButton("Search as Departure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //etSearchCity.setText(input.getText());

                mySharedPref(); //prob wont work; need to fix

                //start search using city code entered as departure city
            }
        })
                .setNegativeButton("Search as Arrival", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      //  etSearchCity.setText(input.getText());
                        mySharedPref(); //prob wont work, need to fix
                        //start search using city code entered as arrival city
                    }
                });

            dialogBuilder.create().show();

    }

    public void applyTexts(String searchCity){
        tvsearchCity.setText((CharSequence) etSearchCity);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater ftMenuInflater = getMenuInflater();
        ftMenuInflater.inflate(R.menu.flight_tracker_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.about:
                Intent intent = new Intent(PlaneTracker.this, FTAbout.class);
                startActivity(intent);
                break;
            case R.id.savedItems:
                Intent intent2 = new Intent(PlaneTracker.this, FlightTrackerSaved.class );
                startActivity(intent2);
                break;
        }

      return true;
    }

public void mySharedPref(){
//    ftsharedpref = getSharedPreferences("SearchCity", Context.MODE_PRIVATE );
    String savedCityString = ftsharedpref.getString("SearchCityReserveName"," ")   ;
    etSearchCity.setText((savedCityString));

    //gets an editor object
    SharedPreferences.Editor editor = ftsharedpref.edit();

    //save what was typed under the name "ReserveName"
    String whatWasTyped = etSearchCity.getText().toString();
    editor.putString("SearchCityReserveName", whatWasTyped);

    //write it to disk:
    editor.commit();
}
}

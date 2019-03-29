package com.example.androidfinalgroupproject;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class NewsFeed extends AppCompatActivity {
    private JSONObject result;
    private  TextView textView;
    private  JSONArray postArray;
    private  WebhoseIOClient webhoseClient;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;
    private ArrayList<String> items;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        result = new JSONObject();
        postArray = new JSONArray();
        webhoseClient = new WebhoseIOClient();
        listView = findViewById(R.id.newslistview);
        button = findViewById(R.id.button);
        try {
            jsonArr();
        }catch (Exception e)
        {}

        System.out.println("************************************************************");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
                    items = webhoseClient.getItems();
                    arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, items){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent){
                            // Get the Item from ListView
                            View view = super.getView(position, convertView, parent);

                            // Initialize a TextView for ListView each Item
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Set the text color of TextView (ListView Item)
                            tv.setTextColor(Color.RED);

                            // Generate ListView Item using TextView
                            return view;
                        }
                    };
                    listView.setAdapter(arrayAdapter);
            }
        });

    }


    public void jsonArr() throws JSONException
    {
        webhoseClient = WebhoseIOClient.getInstance("134a79ef-4a76-48e8-a627-fc9240845d29");
        String queries = "stockmarket";
        webhoseClient.query("filterWebData", queries);

    }



}



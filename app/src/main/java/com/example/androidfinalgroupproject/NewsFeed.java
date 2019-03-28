package com.example.androidfinalgroupproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
        result = new JSONObject();
        postArray = new JSONArray();
        webhoseClient = new WebhoseIOClient();
        listView = findViewById(R.id.newslistview);
        try {
            jsonArr();
        }catch (Exception e)
        {}
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void jsonArr() throws JSONException
    {
        webhoseClient = WebhoseIOClient.getInstance("86940a5c-b094-4465-942e-81ce096fe5c9");
        String queries = "stockmarket";
        webhoseClient.query("filterWebData", queries);

    }

    public void jsonList() throws  JSONException
    {
        JSONArray postArray = result.getJSONArray("posts");
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < postArray.length(); i++)
        {
            items.add(postArray.get(i).toString());
        }

    }

    public void setResult(JSONObject result)
        {
        this.result = result;
    }

}



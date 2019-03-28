package com.example.androidfinalgroupproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;
public class WebhoseIOClient extends AsyncTask<String, Integer, String> {

    private JSONObject jObject;
    private static final String WEBHOSE_BASE_URL = "http://webhose.io";
    private static WebhoseIOClient mClient;
    private String mApiKey;

    public WebhoseIOClient() {

    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"), 8);
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
            }
            rd.close();

            String result = response.toString();

            jObject = new JSONObject(result);

        } catch (Exception e) {

        }
        return "Mission Acomplished";
    }

    private WebhoseIOClient(String apiKey) {
        this.mApiKey = apiKey;
    }

    public static WebhoseIOClient getInstance(String apiKey) {
        if (mClient == null) {
            mClient = new WebhoseIOClient(apiKey);
        }

        return mClient;
    }


    public void query(String endpoint, String queries) {

        String builder = String.format("%s/%s?token=%s&format=json&q=%s&language=english", WEBHOSE_BASE_URL, endpoint, mApiKey, queries);
        execute(builder);

    }

    public JSONObject getjObject() {
        return jObject;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    NewsFeed news = new NewsFeed();
    try {
        news.setResult(jObject);
        news.jsonList();
    }
    catch (Exception e)
    {
            e.printStackTrace();;
    }

    }
}
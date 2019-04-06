package com.example.androidfinalgroupproject;

/**
 * <<<<<<<<<<<<<Some of the code was provided by Webhose I configured most of the code to my likening >>>>>>>>>
 *https://github.com/Webhose/webhoseio-java-sdk
 *
 * MIT License
 *
 * Copyright (c) 2017 Webhose.io
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


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
    /**
     * This is the Json object that will be used to store the Api Content
     */
    private JSONObject jObject;
    /**
     * This main webpage for the news feed api
     */
    private static final String WEBHOSE_BASE_URL = "http://webhose.io";
    /**
     * This will store  the  api key
     */
    private static WebhoseIOClient mClient;
    /**
     * This Stores the Token Key
     */
    private String mApiKey;
    /**
     * This  Stores the item from te JSONarray
     */
    private ArrayList<String> items;


    public WebhoseIOClient()
    {

    }

    @Override
    protected String doInBackground(String... strings) {

        /**
         * Use the string that was build to connect to the specific wbepage
         * Will then append and save all the string into an JSONobject
         */
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


System.out.println("............DONE THE APPENDING");

            String result = response.toString();

            jObject = new JSONObject(result);


                JSONArray postArray = jObject.getJSONArray("posts");
                items = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    if(postArray.getJSONObject(i).getString("title").isEmpty()) {
                    }else{items.add(postArray.getJSONObject(i).getString("title"));}
                }
                setItems(items);
            System.out.println("............DONE ARRAY");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Mission Acomplished";
    }

    private WebhoseIOClient(String apiKey) {
        /**
         * private Constructor
         */
        this.mApiKey = apiKey;
    }

    public static WebhoseIOClient getInstance(String apiKey) {
        /**
         * Gets the Token
         * private Constructor
         */
        if (mClient == null) {
            mClient = new WebhoseIOClient(apiKey);
        }

        return mClient;
    }


    public void query(String endpoint, String queries) {
        /**
         * Builds the URL
         */
      //  http://webhose.io/filterWebContent?token=134a79ef-4a76-48e8-a627-fc9240845d29&format=json&sort=relevancy&q=stock%20market%20language%3Aenglish
        String builder = String.format("%s/%s?token=%s&format=json&sort=relevancy&q=%s&language=english", WEBHOSE_BASE_URL, endpoint, mApiKey, queries);
        try {
            mClient = new WebhoseIOClient(mApiKey);
            mClient.execute(builder);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public JSONObject getjObject() {
        /**
         * Returns JSONobject
         */
        return jObject;
    }




    public void setItems(ArrayList<String> items) {
        /**
         * Sets the  items array list
         */
        this.items = items;
    }

    public ArrayList<String> getItems() {
        /**
         * Returns the items arrayslist
         */
        return items;
    }
}
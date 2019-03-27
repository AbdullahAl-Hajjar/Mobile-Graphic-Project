package com.example.androidfinalgroupproject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NewYorkTimes_Article implements Serializable {
    String webUrl;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    String headline;
    String thumbnail;

    public NewYorkTimes_Article(JSONObject jsonObject) {
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                this.thumbnail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<NewYorkTimes_Article> fromJSONArray(JSONArray array) {
        ArrayList<NewYorkTimes_Article> results = new ArrayList<>();

        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new NewYorkTimes_Article(array.getJSONObject(x)));
            } catch (JSONException e){
                e.printStackTrace();
            }
        }

        return results;
    }
}

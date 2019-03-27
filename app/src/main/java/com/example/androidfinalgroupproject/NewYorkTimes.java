package com.example.androidfinalgroupproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class NewYorkTimes extends AppCompatActivity {
    ProgressBar progressBar;
    EditText etQuery;
    GridView gvResults;
    TextView emptyText;
    ArrayList<NewYorkTimes_Article> articles;
    NewYorkTimes_ArticleArrayAdapter adapter;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newyorktimes_activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setupViews();
        Toast.makeText(this, "Searching for Articles", Toast.LENGTH_LONG).show();

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create intents
                Intent intent = new Intent(getApplicationContext(), NewYorktimes_ArticleActivity.class);
                NewYorkTimes_Article article = articles.get(position);
                // pass article
                intent.putExtra("article", article);
                // display article
                startActivity(intent);

    }
});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    public void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new NewYorkTimes_ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);
    }
    public void onArticleSearch(View view){
        String query = etQuery.getText().toString();

        //Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        Log.v("stuff", url);
        RequestParams params = new RequestParams();
        params.put("api-key", "nGhORsp4W6LhNZnA1DtcYdeVv2Kp0l8r");
        params.put("page", "0");
        params.put("q", query);

        Log.v("stuff", url);
//        client.setTimeout(15000);
        client.setConnectTimeout(15000);
        client.setMaxRetriesAndTimeout(3,10000);
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                //Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    //Log.d("DEBUG", response.toString());
                    adapter.addAll(NewYorkTimes_Article.fromJSONArray(articleJsonResults));
                    //adapter.notifyDataSetChanged();
                    Log.d("DEBUG", articles.toString());
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } );
    }
}


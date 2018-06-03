package com.sanitcode.cataloguemovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //deklarasi variabel
    private EditText fieldSearch;
    private Button search;
    private ImageButton clear;
    private ListView listView;
    private String API_KEY = "97fa2289a0d003ceacd20d40dc795312";
    String TAG = "CatalogueMovie";
    private ProgressDialog progress;
    private TextView status;

    private Adapter adapter;

    private JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldSearch = (EditText) findViewById(R.id.editTextsearch);
        search = (Button) findViewById(R.id.btn_search);
        listView = (ListView) findViewById(R.id.list_movie);
        status = (TextView) findViewById(R.id.status_list);

        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        progress.setTitle("Catalogue Movie");
        progress.setMessage("Sedang mengambil data...");

        adapter = new Adapter(this);

        search.setOnClickListener(this);

        //mengambil database movie dari variabel API_KEY
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&sort_by=popularity.desc";
        progress.show();
        getMovie(url);

        //Pindah ke DetailActivity ketika mengklik listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent detailMovie = new Intent(getApplicationContext(), DetailActivity.class);
                String send = "";
                try {
                    send = json.getJSONArray("results").getJSONObject(position).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                detailMovie.putExtra(DetailActivity.EXTRAS_JSON, send);
                startActivity(detailMovie);
            }
        });

    }

    //fungsi ketika menekan Button dengan id btn_search
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_search) {
            String query = fieldSearch.getText().toString();
            if (!TextUtils.isEmpty(query)) {
                String url = "https://api.themoviedb.org/3/search/movie?api_key=" + API_KEY;
                progress.show();
                getMovie(url, query);
            }
        }
    }

    private void getMovie(String alamat, final String query) {
        adapter.clearData();
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list_movie = new ArrayList<>();
        Log.d(TAG, "getFilmByQuery: Running");
        String url = alamat + "&language=en-US&query=" + query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.d(TAG, "result: " + result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");
                    json = responseObject;

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json = getResult.getJSONObject(i);
                        Movie movie = new Movie(json);
                        list_movie.add(movie);
                        Log.d(TAG, "year: " + list_movie.get(i).getYear());
                        Log.d(TAG, "backdrop: " + list_movie.get(i).getBackdrop());
                    }
                    adapter.setData(list_movie);
                    Log.d(TAG, "size adapter: " + adapter.getCount());
                    listView.setAdapter(adapter);
                    status.setText("Displaying result for " + query);
                    progress.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void getMovie(String url) {
        adapter.clearData();
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list_movie = new ArrayList<>();
        Log.d(TAG, "getFilmByQuery: Running");

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    Log.d(TAG, "result: " + result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");
                    json = responseObject;

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json = getResult.getJSONObject(i);
                        Movie movie = new Movie(json);
                        list_movie.add(movie);
                        Log.d(TAG, "year: " + list_movie.get(i).getYear());
                        Log.d(TAG, "backdrop: " + list_movie.get(i).getBackdrop());
                    }
                    adapter.setData(list_movie);
                    Log.d(TAG, "size adapter: " + adapter.getCount());
                    listView.setAdapter(adapter);
                    progress.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}

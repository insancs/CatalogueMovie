package com.sanitcode.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    //deklarasi variabel
    public static String EXTRAS_JSON = "";

    private JSONObject json;

    private TextView title, year, overview;
    private ImageView backdrop, poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = (TextView) findViewById(R.id.movie_title);
        year = (TextView) findViewById(R.id.movie_year);
        overview = (TextView) findViewById(R.id.overview);

        backdrop = (ImageView) findViewById(R.id.backdrop_image);
        poster = (ImageView) findViewById(R.id.poster_image);

        EXTRAS_JSON = getIntent().getStringExtra(EXTRAS_JSON);

        //menampilkan berdasarkan ID
        if (!EXTRAS_JSON.equals("")){
            try {
                json = new JSONObject(EXTRAS_JSON);
                Movie movie = new Movie(json);
                getImage(movie);
                title.setText(movie.getJudul());
                year.setText(movie.getYear());
                overview.setText(movie.getOverview());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    //mengambil gambar
    private void getImage(Movie movie){
        //mengambil gambar poster dari url
        if (movie.getUrl_poster()!=null){
            String url_poster = "https://image.tmdb.org/t/p/w92/"+movie.getUrl_poster();
            Picasso.with(this).load(url_poster).into(poster);
        }
        //mengambil gambar backdrop dari url
        if (movie.getBackdrop()!=null){
            String url_backdrop = "https://image.tmdb.org/t/p/w300/"+movie.getBackdrop();
            Picasso.with(this).load(url_backdrop).into(backdrop);
        }
    }
}

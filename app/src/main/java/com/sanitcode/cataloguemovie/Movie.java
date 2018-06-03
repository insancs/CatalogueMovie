package com.sanitcode.cataloguemovie;

import org.json.JSONObject;
import  java.lang.String;

/**
 * Created by USER on 04/06/2018.
 */

public class Movie {
    private String judul;
    private String overview;
    private String url_poster;
    private String year;
    private String backdrop;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getUrl_poster() {
        return url_poster;
    }

    public void setUrl_poster(String url_poster) {
        this.url_poster = url_poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Movie (JSONObject object){
        try{
            String title = object.getString("original_title");
            String ulasan = object.getString("overview");
            String url = object.getString("poster_path");
            String tahun = object.getString("release_date");
            String background = object.getString("backdrop_path");
            this.judul = title;
            this.overview = ulasan;
            this.url_poster = url;
            if (!tahun.equals("")){
                this.year = tahun.substring(0,4);
            } else{
                year = "";
            }
            this.backdrop = background;

        } catch(Exception e){
            e.printStackTrace();
        }
    }


}

package com.example.yts;

import java.io.Serializable;

public class Movie implements Serializable {
    private int id;
    private String title;
    private String posterUrl;

    public Movie(int id, String title, String posterUrl) {
        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}

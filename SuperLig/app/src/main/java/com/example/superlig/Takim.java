package com.example.superlig;

import java.io.Serializable;

public class Takim implements Serializable {
    private int id;
    private String ad;
    private String stat;
    private int logo;

    public Takim(int id, String title, String stat, int logo) {
        this.id = id;
        this.ad = title;
        this.stat = stat;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return ad;
    }

    public String getStat() {
        return stat;
    }

    public int getImage() {
        return logo;
    }
}

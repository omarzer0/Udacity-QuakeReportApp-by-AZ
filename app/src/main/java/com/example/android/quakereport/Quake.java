package com.example.android.quakereport;

import java.net.URL;

public class Quake {
    private String  location ;
    private Double magnitude;
    private long TimeInMilliseconds;
    private String url;

    public Quake(Double magnitude, String location, long TimeInMilliseconds , String url) {
        this.magnitude = magnitude;
        this.location= location;
        this.TimeInMilliseconds = TimeInMilliseconds;
        this.url = url;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public long getTimeInMilliseconds() {
        return TimeInMilliseconds;
    }

    public String getUrl() {
        return url;
    }
}

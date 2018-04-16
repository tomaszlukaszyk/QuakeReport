package com.example.android.quakereport;

/**
 * Stores all information about a single earthquake
 */

public class Earthquake {

    private final double mag;
    private final String place;
    private final long dateUnix;
    private final String url;

    /**
     * Create a new Earthquake object
     */
    Earthquake(double mag, String place, long dateUnix, String url) {
        this.mag = mag;
        this.place = place;
        this.dateUnix = dateUnix;
        this.url = url;
    }

    public double getMag() {
        return mag;
    }

    public String getPlace() {
        return place;
    }

    public long getDateUnix() {
        return dateUnix;
    }

    public String getUrl() {
        return url;
    }
}

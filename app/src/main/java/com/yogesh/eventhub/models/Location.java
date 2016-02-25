package com.yogesh.eventhub.models;

import java.io.Serializable;

/**
 * Created by yogesh on 9/2/16.
 */
public class Location implements Serializable {
    double lat;
    double lng;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

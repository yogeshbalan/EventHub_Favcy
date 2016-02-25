package com.yogesh.eventhub.config;

import com.yogesh.eventhub.models.Artist;
import com.yogesh.eventhub.models.Event;

import java.util.ArrayList;

/**
 * Created by Aron on 10/23/2015.
 */
public class TestData {
    public static ArrayList<Event> events = new ArrayList<>();
    public static ArrayList<Artist> artists = new ArrayList<>();

    public static ArrayList<Artist> getArtists() {
        for (int i = 0; i < 10; i++) {
            Artist artist = new Artist();
            artist.setName("Nicki");
            artist.setId(i);
            artist.setDescription("Boss lady");
            artist.setDescription_source("New yotk times");
            artist.setDiscovery(15.15);
            artist.setFamiliarity(10.024);
            artist.setHotttnesss(10.00);
            artists.add(artist);
        }
        return artists;
    }

    public ArrayList<Event> getEvents() {
        return events;

    }
}

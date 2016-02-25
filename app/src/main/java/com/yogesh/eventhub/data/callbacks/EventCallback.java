package com.yogesh.eventhub.data.callbacks;

import com.yogesh.eventhub.models.Events;

import java.util.List;

/**
 * Created by Aron on 10/24/2015.
 */
public class EventCallback {
    private int numFound;
    private int limit;
    private int offset;
    private List<Events> events;

    public int getNumFound() {
        return numFound;
    }

    public void setNumFound(int numFound) {
        this.numFound = numFound;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }
}

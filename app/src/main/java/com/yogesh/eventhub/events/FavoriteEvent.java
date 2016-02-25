package com.yogesh.eventhub.events;

import com.yogesh.eventhub.models.Events;

/**
 * Created by Gilbert on 28/10/2015.
 */
public class FavoriteEvent {
    private Events event;

    public FavoriteEvent(Events event) {
        this.event = event;
    }

    public Events getEvent() {
        return event;
    }

    public void setEvent(Events event) {
        this.event = event;
    }
}

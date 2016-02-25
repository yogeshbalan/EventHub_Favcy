package com.yogesh.eventhub.events;

import android.content.Context;

import com.yogesh.eventhub.models.Event;

/**
 * Created by Aron on 1/4/2016.
 */
public interface onUnFavoriteEvent {
    void unFavorite(Context context,Event event);
}

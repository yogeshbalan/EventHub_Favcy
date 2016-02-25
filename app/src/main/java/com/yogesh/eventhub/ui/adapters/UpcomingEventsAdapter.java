package com.yogesh.eventhub.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.yogesh.eventhub.models.Events;
import com.yogesh.eventhub.ui.adapters.ViewHolders.EventHeader;
import com.yogesh.eventhub.ui.adapters.ViewHolders.UpcomingViewHolder;

import java.util.ArrayList;

import me.gilo.eventhub.R;

/**
 * Created by Aron on 10/31/2015.
 */
public class UpcomingEventsAdapter extends RecyclerView.Adapter<UpcomingViewHolder> implements StickyRecyclerHeadersAdapter<EventHeader> {
    private ArrayList<Events> events;
    private Context context;

    public UpcomingEventsAdapter(ArrayList<Events> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @Override
    public UpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UpcomingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_event_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(UpcomingViewHolder holder, int position) {
        holder.renderView(context, events.get(position));
    }

    @Override
    public int getItemCount() {
        return events.size() == 0 ? 0 : events.size();
    }

    @Override
    public long getHeaderId(int position) {
        return position;
    }

    @Override
    public EventHeader onCreateHeaderViewHolder(ViewGroup parent) {
        return new EventHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.event_sticky_header, parent, false));
    }

    @Override
    public void onBindHeaderViewHolder(EventHeader holder, int position) {
        //String date = DataFormatter.formatDate(events.get(position).getEventDateLocal());
        holder.renderView(events.get(position).getEventReleaseDate());
    }
}

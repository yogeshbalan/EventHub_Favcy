package com.yogesh.eventhub.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.yogesh.eventhub.models.Events;
import com.yogesh.eventhub.ui.adapters.ViewHolders.EventHeader;
import com.yogesh.eventhub.ui.adapters.ViewHolders.FavoritesViewHolder;
import com.yogesh.eventhub.ui.fragments.TabFavoritesFragment;

import java.util.List;

import me.gilo.eventhub.R;

/**
 * Created by Aron on 10/28/2015.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder> implements StickyRecyclerHeadersAdapter<EventHeader> {
    private List<Events> events;
    private Context context;
    private TabFavoritesFragment tabFavoritesFragment;

    public FavoritesAdapter(List<Events> events, Context context, TabFavoritesFragment tabFavoritesFragment) {
        this.events = events;
        this.context = context;
        this.tabFavoritesFragment = tabFavoritesFragment;
    }

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoritesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.events_single_event, parent, false));
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        if (events.get(position) != null) {
            holder.renderView(events.get(position), context, tabFavoritesFragment);
        } else {
            Log.d("Favorites error: ", "null event");
        }
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

    @Override
    public int getItemCount() {
        return events.size() == 0 ? 0 : events.size();
    }

}

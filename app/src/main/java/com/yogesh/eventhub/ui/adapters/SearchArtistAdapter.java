package com.yogesh.eventhub.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import me.gilo.eventhub.R;
import com.yogesh.eventhub.models.Performer;
import com.yogesh.eventhub.ui.adapters.ViewHolders.SearchArtistViewHolder;

/**
 * Created by Aron on 11/6/2015.
 */
public class SearchArtistAdapter extends RecyclerView.Adapter<SearchArtistViewHolder> {
    private ArrayList<Performer> performers;
    private Context context;

    public SearchArtistAdapter(ArrayList<Performer> performers, Context context) {
        this.performers = performers;
        this.context = context;
    }

    @Override
    public SearchArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchArtistViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_search_single_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SearchArtistViewHolder holder, int position) {
        holder.renderView(performers.get(position), context);
    }

    @Override
    public int getItemCount() {
        return performers.size() == 0 ? 0 : performers.size();
    }
}

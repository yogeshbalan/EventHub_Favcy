package com.yogesh.eventhub.ui.adapters.ViewHolders;

/**
 * Created by Gilbert on 28/10/2015.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colintmiller.simplenosql.NoSQL;
import com.colintmiller.simplenosql.NoSQLEntity;
import com.squareup.picasso.Picasso;
import com.yogesh.eventhub.events.FavoriteEvent;
import com.yogesh.eventhub.models.Events;
import com.yogesh.eventhub.ui.activities.EventDetailsActivity;
import com.yogesh.eventhub.utils.DataFormatter;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import me.gilo.eventhub.R;

public class EventItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.ivEventsSingleEvent_favorite_image)
    ImageView ivFavorite;
    @Bind(R.id.ivEventImage)
    ImageView eventImage;
    @Bind(R.id.tvEventTitle)
    TextView eventTitle;
    @Bind(R.id.tvEventDesc)
    TextView eventDesc;
    @Bind(R.id.tvEventTime)
    TextView eventTime;
    @Bind(R.id.tvEventVenue)
    TextView eventVenue;
    @Bind(R.id.tvEventCity)
    TextView eventCity;
    @Bind(R.id.ivShare)
    ImageView shareEvent;
    String favcyapp_id="A9FKZEJCMKADFGZ";
    String favcyapp_secret="FRTTKCD27J8DEQS";
    String actionCode = "pL2cT1eU4kYoTkE";

    SharedPreferences myPrefs;

    View itemView;
    Context context;

    public EventItemHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
        this.context = context;
    }

    public void renderView(final Events event) {
        eventTitle.setText(event.getEventTitle());
        eventCity.setText("Delhi");
        eventDesc.setText(event.getEventSynopsis().substring(0, 50) + "...");
        Picasso.with(context).load(event.getBannerURL()).placeholder(R.drawable.placeholder).into(eventImage);
        eventTime.setText(event.getEventReleaseDate());
        eventVenue.setText(event.getGenre());
        String time = DataFormatter.formatTime(event.getReleaseDateCode());
        eventTime.setText(event.getEventReleaseDate());

        //Log.v("test", "memberID = " + String.valueOf(myPrefs.getString("favcy_member", "")));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* PassPoints passPoints = new PassPoints((Activity) context);
                passPoints.setListener(new HttpCallListener() {

                    @Override
                    public void asyncComplete(JSONObject json) {
                        Log.i("NewsDetails", "json async complete" + json);
                        Log.v("test" , String.valueOf(json));
                    }
                });

                String favcy_member = myPrefs.getString("favcy_member", "");
                if(favcy_member!="") {

                    // if(DBUtility.isNetworkAvailable(mActContext)) {

                    passPoints.execute(new String[]{favcyapp_id, favcyapp_secret, favcy_member, "", actionCode});
                    // }

                }*/



                Intent intent = new Intent(context, EventDetailsActivity.class);
                intent.putExtra("event", event);
                context.startActivity(intent);
                Toast.makeText(context, "added 50 points", Toast.LENGTH_SHORT);
            }
        });

        /*String performers = "";

        if (event.getPerformers() != null) {
            for (Performer performer : event.getPerformers()) {
                performers += performer.getName() + ", ";
            }
        }

        if(performers.length() != 0) {
            performers = performers.trim().substring(0, performers.length() - 1);
        }else{
            performers = "";
        }

       // Log.d("performers", performers);

        if(performers.length() != 0) {
            eventDesc.setText(performers);
            eventDesc.setVisibility(View.VISIBLE);
        }else{
            eventDesc.setText(performers);
            eventDesc.setVisibility(View.GONE);
        }*/

        ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if (ivFavorite.getTag().equals("unfavorite")) {
                if (!event.is_favorite()) {
                    ivFavorite.setImageDrawable(context.getResources().getDrawable(R.drawable.starred_star));
                    favorite(event);
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Added 50pts ;) ", Toast.LENGTH_LONG).show();
                    ivFavorite.setTag("favorite");
                    event.setIs_favorite(true);
                } else {
                    ivFavorite.setImageDrawable(context.getResources().getDrawable(R.drawable.star_unstarred));
                    unfavorite(event);
                    Toast.makeText(context, "Removed from Favorites", Toast.LENGTH_LONG).show();
                    ivFavorite.setTag("unfavorite");
                    event.setIs_favorite(false);
                    //save(event);

                }
                EventBus.getDefault().post(new FavoriteEvent(event));
            }

        });
    }

    public void favorite(Events event) {
        NoSQLEntity<Events> entity = new NoSQLEntity<Events>("favorites", event.getEventifierID() + "");
        entity.setData(event);
        NoSQL.with(context).using(Events.class).save(entity);
    }

    public void unfavorite(Events event) {
        NoSQL.with(context).using(Events.class)
                .bucketId("favorites")
                .entityId(event.getEventifierID() + "")
                .delete();
    }

}
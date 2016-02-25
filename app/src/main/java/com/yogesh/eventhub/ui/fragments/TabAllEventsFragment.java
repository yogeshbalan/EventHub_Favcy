package com.yogesh.eventhub.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.colintmiller.simplenosql.NoSQL;
import com.colintmiller.simplenosql.NoSQLEntity;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.yogesh.eventhub.Volley.VolleySingleton;
import com.yogesh.eventhub.events.FavoriteEvent;
import com.yogesh.eventhub.models.Event;
import com.yogesh.eventhub.models.Events;
import com.yogesh.eventhub.models.Location;
import com.yogesh.eventhub.ui.adapters.AllEventsAdapter;
import com.yogesh.eventhub.utils.DividerDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.gilo.eventhub.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabAllEventsFragment extends Fragment {
    @Bind(R.id.popularRecycler)
    RecyclerView popularRecycler;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.tvSearchToolBar_title)
    TextView loadTitle;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tvTryAgain)
    TextView tryAgain;
    AllEventsAdapter allEventsAdapter;
    List<Events> events;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    public static final String TAG = "test";
    private String jsondata;
    public TabAllEventsFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.tvTryAgain)
    void retry() {
        getEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.tab_events, container, false);
        ButterKnife.bind(this, view);

        events = new ArrayList<>();
        //addDummyEvents();
        getEvents();

       // events();
        return view;
    }

    private void getEvents() {
        imageView.setVisibility(ImageView.GONE);
        progressBar.setVisibility(ProgressBar.GONE);
        loadTitle.setVisibility(TextView.GONE);
        tryAgain.setVisibility(TextView.GONE);
        Log.v(TAG, "fetchData fo Courses is called");
        volleySingleton = VolleySingleton.getMyInstance();
        requestQueue = volleySingleton.getRequestQueue();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, "http://192.168.1.19:5000/PUNE", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                jsondata=response;
                events = getEventList(response.toString());
                popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                allEventsAdapter = new AllEventsAdapter(events, getContext());
                popularRecycler.setAdapter(allEventsAdapter);
                final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(allEventsAdapter);
                popularRecycler.addItemDecoration(headersDecor);
                popularRecycler.addItemDecoration(new DividerDecoration(getActivity()));
                allEventsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        headersDecor.invalidateHeaders();
                    }
                });

                if (response == null) {
                    Log.v(TAG, "fetchData is not giving a fuck");
                }
                Log.v(TAG, "response = " + response);
                imageView.setVisibility(ImageView.GONE);
                progressBar.setVisibility(ProgressBar.GONE);
                loadTitle.setVisibility(TextView.GONE);
                tryAgain.setVisibility(TextView.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.v(TAG, "Response = " + "timeOut");
                } else if (error instanceof AuthFailureError) {
                    Log.v(TAG, "Response = " + "AuthFail");
                } else if (error instanceof ServerError) {
                    Log.v(TAG, "Response = " + "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.v(TAG, "Response = " + "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.v(TAG, "Response = " + "ParseError");
                }
            }
        });

        requestQueue.add(jsonObjectRequest);

        /*imageView.setVisibility(ImageView.VISIBLE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        loadTitle.setVisibility(TextView.VISIBLE);
        tryAgain.setVisibility(TextView.GONE);*/

        /*String error = String.format("%s", "Loading your events...");
        loadTitle.setText(error);*/
        /*imageView.setVisibility(ImageView.GONE);
        progressBar.setVisibility(ProgressBar.GONE);
        loadTitle.setVisibility(TextView.GONE);
        tryAgain.setVisibility(TextView.GONE);*/
        //progressBar.setVisibility(ProgressBar.INVISIBLE);
       /* events = getEventList();
        popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        allEventsAdapter = new AllEventsAdapter(events, getContext());
        popularRecycler.setAdapter(allEventsAdapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(allEventsAdapter);
        popularRecycler.addItemDecoration(headersDecor);
        popularRecycler.addItemDecoration(new DividerDecoration(getActivity()));
        allEventsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                headersDecor.invalidateHeaders();
            }
        });
*/
        //API
        /*API api = RetrofitAdapter.createAPI();
        Observable<EventCallback> eventCallbackObservable = api.getEvents();*/



/*
        eventCallbackObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .filter(new Func1<EventCallback, Boolean>() {
                    @Override
                    public Boolean call(EventCallback eventCallback) {
                        return eventCallback.getLimit() != 0;
                    }
                })
                .subscribe(new Subscriber<EventCallback>() {
                    @Override
                    public void onCompleted() {
                        imageView.setVisibility(ImageView.GONE);
                        progressBar.setVisibility(ProgressBar.GONE);
                        loadTitle.setVisibility(TextView.GONE);

                        Log.d("getting events...", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  Toast.makeText(getActivity(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("Allevents Frag error", e.toString());
                        String error = String.format("%s", "Error occurred!");
                        loadTitle.setText(error);
                        loadTitle.setVisibility(TextView.VISIBLE);
                        tryAgain.setVisibility(TextView.VISIBLE);
                        progressBar.setVisibility(ProgressBar.GONE);

                    }

                    @Override
                    public void onNext(EventCallback eventCallback) {

                        */
/*events = eventCallback.getEvents();
                        popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        allEventsAdapter = new AllEventsAdapter(events, getContext());
                        popularRecycler.setAdapter(allEventsAdapter);
                        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(allEventsAdapter);
                        popularRecycler.addItemDecoration(headersDecor);
                        popularRecycler.addItemDecoration(new DividerDecoration(getActivity()));
                        allEventsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onChanged() {
                                super.onChanged();
                                headersDecor.invalidateHeaders();
                            }
                        });*//*

                        events = eventCallback.getEvents();
                        popularRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                        allEventsAdapter = new AllEventsAdapter(events, getContext());
                        popularRecycler.setAdapter(allEventsAdapter);
                        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(allEventsAdapter);
                        popularRecycler.addItemDecoration(headersDecor);
                        popularRecycler.addItemDecoration(new DividerDecoration(getActivity()));
                        allEventsAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                            @Override
                            public void onChanged() {
                                super.onChanged();
                                headersDecor.invalidateHeaders();
                            }
                        });

                    }
                });
*/
    }

    public void save(Event event) {
        NoSQLEntity<Event> entity = new NoSQLEntity<Event>("events", event.getId() + "");
        entity.setData(event);
        NoSQL.with(getActivity()).using(Event.class).save(entity);
    }

/*
    public void events() {
        imageView.setVisibility(ImageView.VISIBLE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        loadTitle.setVisibility(TextView.VISIBLE);
        tryAgain.setVisibility(TextView.GONE);
        NoSQL.with(getActivity()).using(Event.class)
                .bucketId("events")
                .retrieve(new RetrievalCallback<Event>() {
                    @Override
                    public void retrievedResults(List<NoSQLEntity<Event>> entities) {
                        if (entities.size() > 0) {
                            events.clear();
                            for (NoSQLEntity<Event> entity : entities) {
                                Event event = entity.getData();
                                events.add(entity.getData());
                            }
                            allEventsAdapter.notifyDataSetChanged();
                            //getEvents();
                        }

                        imageView.setVisibility(ImageView.GONE);
                        progressBar.setVisibility(ProgressBar.GONE);
                        loadTitle.setVisibility(TextView.GONE);
                        tryAgain.setVisibility(TextView.GONE);
                    }
                });
    }
*/

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // This method will be called when a MessageEvent is posted
    public void onEvent(FavoriteEvent event) {
        //events();
    }

    public List<Events> getEventList(String response){
        List<Events> eventsList = new ArrayList<>();
        // Reading json file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open(
                    "model.json")));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String myjsonstring = response;
        // Try to parse JSON
        try {
            // Creating JSONObject from String
            JSONArray jsonArray = new JSONArray(myjsonstring);

            // Creating JSONArray from JSONObject
            //JSONObject jsonEvent = jsonObjMain.getJSONObject("eventsDetails");
            //Events events = new Events();

            // JSONArray has x JSONObject
            for (int i = 0; i < jsonArray.length(); i++) {
                Events events = new Events();
                JSONObject jsonEvent = jsonArray.getJSONObject(i);
                events.setBannerURL(jsonEvent.getString("banner_url"));
                events.setEventTitle(jsonEvent.getString("title"));
                events.setEventSynopsis(jsonEvent.getString("event_synopsis"));
                events.setEventReleaseDate(jsonEvent.getString("event_release_date"));
                events.setGenre(jsonEvent.getString("venue"));
                events.setFShareURL(jsonEvent.getString("share_url"));
                events.setReleaseDateCode(jsonEvent.getString("time"));
                events.setEventifierID(jsonEvent.getString("id"));

                JSONObject locationObject = jsonEvent.getJSONObject("venue_coords");
                Location location = new Location();
                location.setLat(locationObject.getDouble("lat"));
                location.setLng(locationObject.getDouble("lng"));
                events.setLocation(location);

                eventsList.add(events);
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.v("test", "list of events = " + String.valueOf(eventsList));
        return eventsList;
    }


}

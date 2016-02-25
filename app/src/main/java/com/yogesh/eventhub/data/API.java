package com.yogesh.eventhub.data;

import com.yogesh.eventhub.data.callbacks.EventCallback;
import com.yogesh.eventhub.models.Artist;
import com.yogesh.eventhub.models.AutoSuggestSearchResult;
import com.yogesh.eventhub.models.Event;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Aron on 10/23/2015.
 */
public interface API {
    String BASE_URL = "http://192.168.1.24:5000/";
    static int EVENTS_LIMIT = 20;

    @GET("http://192.168.1.24:5000/PUNE")
    Observable<EventCallback> getEvents();

    @GET("events")
    Observable<EventCallback> getPopularEvents();

    @GET("events?limit=6")
    Observable<EventCallback> getRecommendedEvents();

    @GET("autoSuggest/artist")
    Observable<AutoSuggestSearchResult> getUpcomingEvents(@Query("term") String artist);

    @POST("events/{id}")
    Observable<Event> getEvent(@Path("id") int id);

    @GET("artists/{id}")
    Observable<Artist> getArtist(@Path("id") int id);

    @GET("artists/")
    Observable<EventCallback> getArtists();

    @GET("artists/")
    Observable<Artist> searchArtists(@Query("artist") String artist);

    @GET("autoSuggest/artist")
    Observable<AutoSuggestSearchResult> autoSuggestArtist(@Query("term") String artist);

    @GET("autoSuggest/event")
    Observable<AutoSuggestSearchResult> autoSuggestEvent(@Query("term") String event);

    @GET("autoSuggest/event")
    Observable<AutoSuggestSearchResult> getMyArtistEvents2(@Query("term") String event);

    @GET("autoSuggest/venue")
    Observable<AutoSuggestSearchResult> autoSuggestVenue(@Query("term") String venue);

    @GET("autoSuggest/location")
    Observable<AutoSuggestSearchResult> autoSuggestLocation(@Query("term") String venue);

}

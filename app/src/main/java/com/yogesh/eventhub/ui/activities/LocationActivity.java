package com.yogesh.eventhub.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import me.gilo.eventhub.R;

public class LocationActivity extends AppCompatActivity {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        setUpMapIfNeeded();

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("");

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.setMyLocationEnabled(true);

        UiSettings settings = mMap.getUiSettings();
        settings.setAllGesturesEnabled(true);
        settings.setMyLocationButtonEnabled(false);
        settings.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        final LatLng delhi = new LatLng(28.6139, 77.2090);
        mMap.addMarker(new MarkerOptions().position(delhi).title("Marker in Delhi"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));
        if (mMap!=null){
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(delhi, 10.0f));
                    return false;
                }
            });
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(delhi, 13.0f));
        }

/*

        LatLng ecevt = new LatLng(28.6166, 77.299);
        mMap.addMarker(new MarkerOptions().position(delhi).title("Event 1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ecevt));
        if (mMap!=null){
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    return false;
                }
            });
        }

        LatLng ecesvt = new LatLng(28.516, 77.223);
        mMap.addMarker(new MarkerOptions().position(delhi).title("Event 2"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ecesvt));
        if (mMap!=null){
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    return false;
                }
            });
        }

        LatLng eceddvt = new LatLng(28.677, 77.266);
        mMap.addMarker(new MarkerOptions().position(delhi).title("Event 3"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(eceddvt));
        if (mMap!=null){
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    return false;
                }
            });
        }
*/


    }
}

package com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.varun.exoplayer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class AnimationMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    double latitude,longitude;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        latitude=getIntent().getDoubleExtra("lat1",0.0);
        longitude=getIntent().getDoubleExtra("long1",0.0);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Current Position"));
       mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));


    }


}

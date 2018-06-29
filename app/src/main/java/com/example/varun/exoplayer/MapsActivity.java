package com.example.varun.exoplayer;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;
import java.util.Locale;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,LocationListener {

    private GoogleMap mMap;
    double lat1, long1;
    LatLng  Search_location_latlng;

    LocationManager locationManager;





    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(

            new LatLng(-34.041458, 150.790100), new LatLng(-33.682247, 151.383362));

    protected GeoDataClient mGeoDataClient;
    private PlaceAutocompleteAdapter mAdapter;
    String srchh;

    AutoCompleteTextView e7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGeoDataClient = Places.getGeoDataClient(this, null);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        e7=(AutoCompleteTextView)findViewById(R.id.serach_address);

        mAdapter = new PlaceAutocompleteAdapter(this, mGeoDataClient, BOUNDS_GREATER_SYDNEY, null);

        e7.setAdapter(mAdapter);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    public void Address(View v)
    {

       // srchh = e7.getText().toString();
        getLocation();
        //Toast.makeText(getApplicationContext(),"search="+srchh,Toast.LENGTH_LONG).show();

    }


    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }





    private void movemap1() {
        try {




            mMap.addMarker(new MarkerOptions()
                    .position(Search_location_latlng) //setting position
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                    .title("serch location")); //Adding a title

            mMap.moveCamera(CameraUpdateFactory.newLatLng(Search_location_latlng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            mMap.getUiSettings().setZoomControlsEnabled(true);



        } catch (Exception e) {
            //  Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "error " + e.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    @Override
    public void onLocationChanged(Location location) {

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            Address address = addresses.get(0);
            lat1 = address.getLatitude();
            long1 = address.getLongitude();
            Search_location_latlng = new LatLng(lat1, long1);
            movemap1();

           // Toast.makeText(getApplicationContext(),"lat="+lat+" lng="+lng,Toast.LENGTH_LONG).show();

        }catch(Exception e)
        {

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

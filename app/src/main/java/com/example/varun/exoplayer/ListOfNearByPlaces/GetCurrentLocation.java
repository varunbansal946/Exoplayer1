package com.example.varun.exoplayer.ListOfNearByPlaces;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.varun.exoplayer.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class GetCurrentLocation extends AppCompatActivity implements LocationListener{

    Button btn;
    double lat,lng;
    LocationManager locationManager;
    LatLng Search_location_latlng;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_current_location);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }



    }


    public void GetCurrentLocation(View v)
    {
        getLocation();


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



    public void onLocationChanged(Location location) {



        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            Address address = addresses.get(0);
            lat = address.getLatitude();
            lng = address.getLongitude();
            Search_location_latlng = new LatLng(lat, lng);

//            Toast.makeText(getApplicationContext(),"lat="+lat+" lng="+lng,Toast.LENGTH_LONG).show();


            Intent i=new Intent(getApplicationContext(),ListOfNearByPlaces.class);
            i.putExtra("latitude",lat);
            i.putExtra("longitude",lng);
            startActivity(i);

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

package com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.example.varun.exoplayer.ListOfNearByPlaces.ListOfNearByPlaces;
import com.example.varun.exoplayer.ListOfNearByPlaces.RecyclerViewAdapter;
import com.example.varun.exoplayer.R;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;
import android.support.v7.widget.SearchView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentLocationRetrofit extends AppCompatActivity implements LocationListener,AdapterClassForRecyclerViewRerofit.OnItemClickListener {

    double lat,lng;
    LocationManager locationManager;
    LatLng Search_location_latlng;
    RecyclerView recyclerView;
    SearchView sv;
    Result r11;
        GetSetDistance getSetDistance;
        ArrayList<GetSetDistance> array=new ArrayList<>();
    public   List<Result> s;
  AdapterClassForRecyclerViewRerofit adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location_retrofit);

        sv= (SearchView) findViewById(R.id.mSearch);
        recyclerView=(RecyclerView)findViewById(R.id.recylist);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
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
    @Override
    public void onLocationChanged(Location location) {

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            Address address = addresses.get(0);
            lat = address.getLatitude();
            lng = address.getLongitude();
            Search_location_latlng = new LatLng(lat, lng);
            build_retrofit_and_get_response();

//            Toast.makeText(getApplicationContext(),"lat="+lat+" lng="+lng,Toast.LENGTH_LONG).show();



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



    private void build_retrofit_and_get_response() {

        String url = "https://maps.googleapis.com/maps/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

        Call<BaseClass> call = service.getNearbyPlaces( lat + "," + lng, 500);
        ///Toast.makeText(getApplicationContext(),"lat="+lat+" lng="+lng,Toast.LENGTH_LONG).show();

      call.enqueue(new Callback<BaseClass>() {
          @Override
          public void onResponse(Call<BaseClass> call, Response<BaseClass> response) {

               s = response.body().getResults();
                 for (int i = 0; i < response.body().getResults().size(); i++) {
                  Double latlocation = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                  Double longlocation = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                     double earthRadius = 6371000; //meters
                     double dLat = Math.toRadians(latlocation-lat);
                     double dLng = Math.toRadians(longlocation-lng);
                     double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                             Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(latlocation)) *
                                     Math.sin(dLng/2) * Math.sin(dLng/2);
                     double c1 = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                     float dist = (float) (earthRadius * c1);

                     double kilodistace=(dist)/1000;
                     DecimalFormat dtime = new DecimalFormat("#.##");
                     double i2= Double.valueOf(dtime.format(kilodistace));
                     String k=String.valueOf(i2)+" km";
                     s.get(i).setDistacnce(k);


              }

              array.add(getSetDistance);




              recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

               adapter = new AdapterClassForRecyclerViewRerofit(CurrentLocationRetrofit.this,s,lat,lng);




              recyclerView.setAdapter(adapter);



              sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                  @Override
                  public boolean onQueryTextSubmit(String query) {
                      return false;
                  }

                  @Override
                  public boolean onQueryTextChange(String query) {
                      //FILTER AS YOU TYPE
                      adapter.getFilter().filter(query);
                      return false;
                  }
              });


        adapter.setOnItemClickListener(CurrentLocationRetrofit.this);







          }

          @Override
          public void onFailure(Call<BaseClass> call, Throwable t) {

          }
      });
        }

    @Override
    public void OnItemClick(int position) {
    
     double lat=s.get(position).getGeometry().getLocation().getLat();
     double lng=s.get(position).getGeometry().getLocation().getLng();

      Intent i=new Intent(getApplicationContext(),NextOnClickRecyclerView.class);
        i.putExtra("lat1",lat);
        i.putExtra("long1",lng);
        startActivity(i);



    }
}
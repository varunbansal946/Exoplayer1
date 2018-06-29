package com.example.varun.exoplayer.ListOfNearByPlaces;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varun.exoplayer.R;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListOfNearByPlaces extends AppCompatActivity  implements LocationListener{


    double mLatitude=0;
    double mLongitude=0;
    RecyclerView recyclerView;
    GetterSetterForAdapter getterSetterForAdapter;
    JSONObject image12;

    double latplacesearch,longplaceseacrh;

    ArrayList<GetterSetterForAdapter> arr=new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_near_by_places);

        mLatitude=getIntent().getDoubleExtra("latitude",0.0);
        mLongitude=getIntent().getDoubleExtra("longitude",0.0);

        recyclerView=(RecyclerView)findViewById(R.id.recy);
    ///    Toast.makeText(getApplicationContext(),"latitude="+mLatitude+" longi="+mLongitude,Toast.LENGTH_LONG).show();
        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location="+mLatitude+","+mLongitude);
        sb.append("&radius=5000");
        sb.append("&sensor=true");
        sb.append("&key=AIzaSyDqVKB6pItzXimGHuQRnzEtkX-AWz7bzkY");


       //// Toast.makeText(getApplicationContext(),"sb="+sb.toString(),Toast.LENGTH_LONG).show();

        PlacesTask placesTask = new PlacesTask();

        placesTask.execute(sb.toString());

    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

// Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

// Connecting to url
            urlConnection.connect();

// Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception while ", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }

        return data;
    }





    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);

                System.out.println(data);


            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        protected void onPostExecute(String result){
         ///   ParserTask parserTask = new ParserTask();
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray array = obj.getJSONArray("results");
                for(int i=0;i<array.length();i++)
                {
                    JSONObject o = array.getJSONObject(i);

                    getterSetterForAdapter=new GetterSetterForAdapter();
                    String latPlace,lngplace;
                    latPlace= o.getJSONObject("geometry").getJSONObject("location").getString("lat");
                    lngplace= o.getJSONObject("geometry").getJSONObject("location").getString("lng");
                    latplacesearch=Double.parseDouble(latPlace);
                    longplaceseacrh=Double.parseDouble(lngplace);
                  double distance=  distFrom(mLatitude,mLongitude,latplacesearch,longplaceseacrh);
                  double kilodistace=(distance)/1000;


        //   Toast.makeText(getApplicationContext(),"outside",Toast.LENGTH_LONG).show();
                /*  JSONArray on=o.getJSONArray("photos");



                        for(int i1=1;i1<=on.length();i1++) {

                            JSONObject obk = on.getJSONObject(i1);
                            String h = obk.getString("height");
                            Toast.makeText(getApplicationContext(), "height=" + h, Toast.LENGTH_LONG).show();

                            JSONArray objforpic = obk.getJSONArray("html_attributions");
                            Toast.makeText(getApplicationContext(), "obj=" + objforpic, Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "size=" + objforpic.length(), Toast.LENGTH_LONG).show();

                            for (int ik = 0; ik < objforpic.length(); ik++) {
                                JSONObject obkref = objforpic.getJSONObject(ik);
                                Toast.makeText(getApplicationContext(), "obj1000=" + obkref, Toast.LENGTH_LONG).show();
                                String urll = obkref.getString("href");
                                Toast.makeText(getApplicationContext(), "urll=" + urll, Toast.LENGTH_LONG).show();
                            }


                        }*/




                    getterSetterForAdapter.setName1(o.getString("name"));
                    getterSetterForAdapter.setIconn(o.getString("icon"));
                    getterSetterForAdapter.setDist(kilodistace);
                    arr.add(getterSetterForAdapter);
                }

                        RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(arr,getApplicationContext());
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            }
            catch (Exception e)
            {
            }
     ///   Toast.makeText(getApplicationContext(),"return="+result,Toast.LENGTH_LONG).show();
// Start parsing the Google places in JSON format
// Invokes the "doInBackground()" method of the class ParseTask




           // parserTask.execute(result);
        }









        }




















        @Override
    public void onLocationChanged(Location location) {

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

    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}

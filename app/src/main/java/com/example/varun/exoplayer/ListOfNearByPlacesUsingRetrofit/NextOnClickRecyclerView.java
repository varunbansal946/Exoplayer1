package com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.varun.exoplayer.R;
import com.squareup.picasso.Picasso;

public class NextOnClickRecyclerView extends AppCompatActivity {

   ImageView imageView;
   WebView webView;
    double lat;
    double longi;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_on_click_recycler_view);
        overridePendingTransition(0, 0);

       lat =getIntent().getDoubleExtra("lat1",0.0);
       longi =getIntent().getDoubleExtra("long1",0.0);
        imageView=(ImageView)findViewById(R.id.imh12);

        String url="https://maps.googleapis.com/maps/api/staticmap?center="+lat+","+longi+"&zoom=12&size=400x200";
        Picasso.with(getApplicationContext()).load(url).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),AnimationMapActivity.class);
                i.putExtra("lat1",lat);
                i.putExtra("long1",longi);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
            }
        });



    }
}

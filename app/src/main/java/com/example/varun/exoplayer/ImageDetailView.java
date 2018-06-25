 package com.example.varun.exoplayer;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

 public class ImageDetailView extends AppCompatActivity {

//     @BindView(R.id.viewpager)
     ViewPager viewPager;

    public static ArrayList<ImageData> data=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_detail_view);
//        ButterKnife.bind(this);

        SlidrConfig config = new SlidrConfig.Builder()
                               .position(SlidrPosition.BOTTOM)
                                .build();

        Slidr.attach(this,config);
        viewPager=findViewById(R.id.viewpager);
        data.clear();

        ImageData sample1=new ImageData(R.drawable.sample1+"",true);
        ImageData sample2=new ImageData(R.drawable.sample2+"",true);
        ImageData sample3=new ImageData(R.drawable.sample3+"",true);
        ImageData sample4=new ImageData(R.drawable.sample4+"",true);
        ImageData sample5=new ImageData("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",false);


        data.add(sample1);
        data.add(sample2);
        data.add(sample3);
        data.add(sample4);
        data.add(sample5);
        viewPager.setAdapter(new adapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        viewPager.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                Log.d("TAG",dragEvent.getAction()+"");
                return true;
            }
        });

//viewPager.setAdapter(new SamplePagerAdapter());






    }



     class adapter extends FragmentPagerAdapter
    {

        public adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("TAG",data.get(position).checkImageVideo+"");
            if (data.get(position).checkImageVideo) {
                Log.d("TAG","IN IMAGE");

                ImageDisplay imageDisplay=ImageDisplay.newInstance(position);

                return imageDisplay;
            }
            else {
                Log.d("TAG","IN VIDEO");

                VideoDisplay videoDisplay=VideoDisplay.newInstance(position);

                return videoDisplay;
            }
        }

        @Override
        public int getCount() {
            return data.size();
        }
    }


 }

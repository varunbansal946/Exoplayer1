package com.example.varun.exoplayer;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImageDisplay extends Fragment {
    int position;


    PhotoView imageView;

    public ImageDisplay() {
        // Required empty public constructor
    }

    public static ImageDisplay newInstance(int position)
    {
        ImageDisplay imageDisplay=new ImageDisplay();
        Bundle args=new Bundle();
        args.putInt("position",position);
        imageDisplay.setArguments(args);
        return  imageDisplay;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_image_display, container, false);

        imageView=(PhotoView) view.findViewById(R.id.image);



        Log.d("TAG",position+"");
        Log.d("TAG",ImageDetailView.data.get(position).data+"");


        try {

            imageView.setImageResource(Integer.parseInt(ImageDetailView.data.get(position).data));
        }
        catch (Exception e)
        {

        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        position=getArguments().getInt("position");
        super.onCreate(savedInstanceState);


    }







}

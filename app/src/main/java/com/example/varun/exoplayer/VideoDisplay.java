package com.example.varun.exoplayer;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoDisplay extends Fragment {
    int position;
    String url;

    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer player;


    public VideoDisplay() {
        // Required empty public constructor
    }

    public static VideoDisplay newInstance(int position)
    {
        VideoDisplay videoDisplay=new VideoDisplay();
        Bundle args=new Bundle();
        args.putInt("position",position);
        videoDisplay.setArguments(args);
        return  videoDisplay;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_video_display, container, false);



        try {
            url=ImageDetailView.data.get(position).data;
            simpleExoPlayerView = (SimpleExoPlayerView) (view.findViewById(R.id.exo_view));
            BandwidthMeter meter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(meter));
            player = ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), trackSelector);
            Uri videouri = Uri.parse(url);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory factory = new DefaultExtractorsFactory();


            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, factory, null, null);
            simpleExoPlayerView.setPlayer(player);
            player.prepare(mediaSource);

//            player.setPlayWhenReady(true);

        }
        catch (Exception e)
        {
            Log.d("exoerror","error"+e.toString());
        }


        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        position=getArguments().getInt("position");
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(!isVisibleToUser&&player!=null)
        {
            player.setPlayWhenReady(false);
            Log.d("TAG","NOT VISIBLE");
        }
    }
}

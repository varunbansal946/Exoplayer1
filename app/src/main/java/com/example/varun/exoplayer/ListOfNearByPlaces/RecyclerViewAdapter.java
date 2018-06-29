package com.example.varun.exoplayer.ListOfNearByPlaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.varun.exoplayer.R;
import com.squareup.picasso.Picasso;


import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {



    ArrayList<GetterSetterForAdapter> getterSetterForAdapters;
    Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView ttx,txt2;


        public MyViewHolder(View view) {
            super(view);

            imageView=(ImageView)view.findViewById(R.id.imgicon1111);
            ttx=(TextView)view.findViewById(R.id.placename);
            txt2=(TextView)view.findViewById(R.id.distance);
        }
    }


    public RecyclerViewAdapter(ArrayList<GetterSetterForAdapter> getterSetterForAdapters, Context c) {
        this.getterSetterForAdapters = getterSetterForAdapters;
        this.c=c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerlayoutadapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        GetterSetterForAdapter gette=getterSetterForAdapters.get(position);




        holder.ttx.setText(gette.getName1());
        DecimalFormat dtime = new DecimalFormat("#.##");
        double i2= Double.valueOf(dtime.format(gette.getDist()));

        holder.txt2.setText(String.valueOf(i2)+" Km");

        Picasso.with(c).load(gette.getIconn()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return getterSetterForAdapters.size();
    }
}
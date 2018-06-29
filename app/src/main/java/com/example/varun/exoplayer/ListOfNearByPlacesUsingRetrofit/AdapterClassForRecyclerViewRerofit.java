package com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit.Result;
import com.example.varun.exoplayer.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class AdapterClassForRecyclerViewRerofit extends RecyclerView.Adapter<AdapterClassForRecyclerViewRerofit.MyViewHolder>
        implements Filterable
{
    List<Result> s ,filterList;
    Context c;
    double latcurrent,lngcurrent;
    double latlocation,longlocation;
    private OnItemClickListener onItemClickListener;
    CustomFilter filter;


    public void setOnItemClickListener(OnItemClickListener itemClickListener)
    {
        onItemClickListener=itemClickListener;
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new CustomFilter(this, filterList);
        }

        return filter;
    }

    public interface OnItemClickListener{

        void OnItemClick(int position);

    }
    AdapterClassForRecyclerViewRerofit(CurrentLocationRetrofit currentLocationRetrofit, List<Result> s,double lat,double lng)
    {
        this.s = s;
        this.c=currentLocationRetrofit;
        this.latcurrent=lat;
        this.lngcurrent=lng;
        this.filterList=s;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerlayoutadapter, parent, false);

        return new AdapterClassForRecyclerViewRerofit.MyViewHolder(itemView);





    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


    ///    Toast.makeText(c,"lat="+latcurrent+" lng="+lngcurrent,Toast.LENGTH_LONG).show();

      latlocation=s.get(position).getGeometry().getLocation().getLat();

        longlocation=s.get(position).getGeometry().getLocation().getLng();
        ////Toast.makeText(c,"lat111111="+latlocation+" lng11="+longlocation,Toast.LENGTH_LONG).show();

     /*  double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(latlocation-latcurrent);
        double dLng = Math.toRadians(longlocation-lngcurrent);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(latcurrent)) * Math.cos(Math.toRadians(latlocation)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c1 = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c1);

        double kilodistace=(dist)/1000;


  //      Toast.makeText(c,"killo="+kilodistace,Toast.LENGTH_LONG).show();


        DecimalFormat dtime = new DecimalFormat("#.##");
        double i2= Double.valueOf(dtime.format(kilodistace));*/

//        Toast.makeText(c,"kilogramme="+i2,Toast.LENGTH_LONG).show();

        holder.ttx.setText(s.get(position).getName());
        Picasso.with(c).load(s.get(position).getIcon()).into(holder.imageView);

      //// holder.txt2.setText(String.valueOf(i2)+" Km");

       holder.txt2.setText(s.get(position).getDistacnce());

// String url=s.get(position).getPhotos().get(position).getPhotoReference();


        ///Toast.makeText(c,"url="+url,Toast.LENGTH_LONG).show();


     //   s.set(position,dist).setDistance(kilodistace);


           }

    @Override
    public int getItemCount() {
        return s.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView ttx,txt2;


        public MyViewHolder(View view) {
            super(view);

            imageView=(ImageView)view.findViewById(R.id.imgicon1111);
            ttx=(TextView)view.findViewById(R.id.placename);
            txt2=(TextView)view.findViewById(R.id.distance);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            onItemClickListener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

}

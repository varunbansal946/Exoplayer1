package com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {

    AdapterClassForRecyclerViewRerofit adptr;
    List<Result> filterList;

    public CustomFilter(AdapterClassForRecyclerViewRerofit adptr, List<Result> filterList) {
        this.adptr = adptr;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults resultsF=new FilterResults();

        if(constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();

            List<Result> filteredPlayers=new ArrayList<>();


            for (int i=0;i<filterList.size();i++)
            {
                //CHECK
                if(filterList.get(i).getName().toUpperCase().contains(constraint)|filterList.get(i).getDistacnce().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(filterList.get(i));


                }
            }


            resultsF.count=filteredPlayers.size();
            resultsF.values=filteredPlayers;
        }

        else {


            resultsF.count=filterList.size();
            resultsF.values=filterList;

        }


        return resultsF;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adptr.s= (ArrayList<Result>) results.values;


        //REFRESH
        adptr.notifyDataSetChanged();
    }
}

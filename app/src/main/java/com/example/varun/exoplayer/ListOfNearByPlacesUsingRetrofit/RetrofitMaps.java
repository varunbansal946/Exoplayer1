package com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitMaps {

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyDqVKB6pItzXimGHuQRnzEtkX-AWz7bzkY")
    Call<BaseClass> getNearbyPlaces( @Query("location") String location, @Query("radius") int radius);

///    @GET("/maps/api/place/autocomplete/json")
    /*Call<GoogleAutocompleteResponse> getAutoCompleteSearchResults(@Query("key") String apiKey,
                                                                  @Query("input") String searchTerm,
                                                                  @Query("location") String location,
                                                                  @Query("radius") long radius);
*/
}

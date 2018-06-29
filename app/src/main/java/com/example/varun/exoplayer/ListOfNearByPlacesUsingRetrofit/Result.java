package com.example.varun.exoplayer.ListOfNearByPlacesUsingRetrofit;



import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("icon")
    @Expose
    private String icon;



    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

    @SerializedName("plus_code")
    @Expose
    private PlusCode plusCode;

   String distacnce;

    public String getDistacnce() {
        return distacnce;
    }

    public void setDistacnce(String distacnce) {
        this.distacnce = distacnce;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }


    public PlusCode getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(PlusCode plusCode) {
        this.plusCode = plusCode;
    }




}

package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 10/12/2016.
 */

public class Location {
    @SerializedName(Constant.KEY_ID)
    private int id;
    @SerializedName(Constant.KEY_LONGITUDE)
    private double longitude;
    @SerializedName(Constant.KEY_LATITUDE)
    private double latitude;
    @SerializedName(Constant.KEY_COUNTRY)
    private String country;
    @SerializedName(Constant.KEY_CITY)
    private String city;
    @SerializedName(Constant.KEY_DISTRICT)
    private String district;
    @SerializedName(Constant.KEY_STREET)
    private String street;

    public int getId() {
        return id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }
}
package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 26/11/2016.
 */

public class Store extends User {
    @SerializedName(Constant.KEY_STORE_TYPE)
    private String storeType;

    @SerializedName(Constant.KEY_STREET)
    private String street;

    @SerializedName(Constant.KEY_DISTRICT)
    private String district;

    @SerializedName(Constant.KEY_CITY)
    private String city;

    @SerializedName(Constant.KEY_LONGITUDE)
    private double longitude;

    @SerializedName(Constant.KEY_LATITUDE)
    private double latitude;

    @SerializedName(Constant.KEY_COUNTRY)
    private String country ;



    public Store(int id, String email, String password, String name, String phoneNumber,
                 String storeType, String street, String district, String city,
                 double longitude, double latitude, String country) {
        super(id, email, password, Constant.STORE_ROLE, name, phoneNumber);
        this.storeType = storeType;
        this.street = street;
        this.district = district;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
    }

    public Store(String email, String password, String name, String phoneNumber,
                 String storeType, String street, String district, String city,
                 double longitude, double latitude, String country) {
        super(email, password, Constant.STORE_ROLE, name, phoneNumber);
        this.storeType = storeType;
        this.street = street;
        this.district = district;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
    }

    public Store() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 26/11/2016.
 */

public class Shipper extends User {

    @SerializedName(Constant.KEY_ADDRESS)
    private String address;

    @SerializedName(Constant.KEY_LONGITUDE)
    private double longitude;

    @SerializedName(Constant.KEY_LATITUDE)
    private double latitude;

    @SerializedName(Constant.KEY_BIRTHDAY)
    private String birthday;

    @SerializedName(Constant.KEY_AVATAR)
    private String avatar;

    public Shipper(int id, String email, String password, int role, String name, String phoneNumber, int status, int rating, int vote, String createTime, String updateTime, String address, double longitude, double latitude, String birthday, String avatar) {
        super(id, email, password, role, name, phoneNumber, status, rating, vote, createTime, updateTime);
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.birthday = birthday;
        this.avatar = avatar;
    }

    public Shipper(int id, String email, String password, String name, String phoneNumber, String address, double longitude, double latitude, String birthday) {
        super(id, email, password, Constant.SHIPPER_ROLE, name, phoneNumber);
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.birthday = birthday;
    }

    public Shipper(String email, String password, String name, String phoneNumber, String address, double longitude, double latitude, String birthday) {
        super(email, password, Constant.SHIPPER_ROLE, name, phoneNumber);
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.birthday = birthday;
    }

    public Shipper() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

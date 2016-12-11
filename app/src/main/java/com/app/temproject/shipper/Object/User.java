package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 26/11/2016.
 */

public abstract class User {
    @SerializedName(Constant.KEY_ID)
    protected int id;

    @SerializedName(Constant.KEY_EMAIL)
    protected String email;

    @SerializedName(Constant.KEY_PASSWORD)
    protected String password;

    @SerializedName(Constant.KEY_ROLE)
    protected int role;

    @SerializedName(Constant.KEY_NAME)
    protected String name;

    @SerializedName(Constant.KEY_PHONE_NUMBER)
    protected String phoneNumber;

    @SerializedName(Constant.KEY_STATUS)
    protected int status;

    @SerializedName(Constant.KEY_RATING)
    protected double rating;

    @SerializedName(Constant.KEY_VOTE)
    protected int vote;

    @SerializedName(Constant.KEY_CREATED_TIME)
    protected String createTime;

    @SerializedName(Constant.KEY_UPDATED_TIME)
    protected String updateTime;

    public User() {
    }

    public User(int id, String email, String password, int role, String name, String phoneNumber) {

        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User(String email, String password, int role, String name, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public User(int id, String email, String password, int role, String name, String phoneNumber, int status, double rating, int vote, String createTime, String updateTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.rating = rating;
        this.vote = vote;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

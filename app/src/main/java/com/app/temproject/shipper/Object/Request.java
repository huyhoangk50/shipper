package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 30/11/2016.
 */

public class Request {

    @SerializedName(Constant.KEY_ID)
    private int id;
    @SerializedName(Constant.KEY_DEPOSIT)
    private int deposit;
    @SerializedName(Constant.KEY_DISTANCE)
    private double distance;
    @SerializedName(Constant.KEY_START_TIME)
    private String startTime;
    @SerializedName(Constant.KEY_END_TIME)
    private String endTime;
    @SerializedName(Constant.KEY_STORE_ID)
    private int storeId;
    @SerializedName(Constant.KEY_DESTINATION)
    private String destination;
    @SerializedName(Constant.KEY_PRICE)
    private int price;
    @SerializedName(Constant.KEY_PRODUCT_ID)
    private int productId;
    @SerializedName(Constant.KEY_PRODUCT_NAME)
    private String productName;
    @SerializedName(Constant.KEY_PHONE_NUMBER)
    private String phoneNumber;
    @SerializedName(Constant.KEY_LONGITUDE)
    private double longitude;
    @SerializedName(Constant.KEY_LATITUDE)
    private double latitude;
    @SerializedName(Constant.KEY_STATUS)
    private int status;
    @SerializedName(Constant.KEY_CREATED_TIME)
    private String createTime;
    @SerializedName(Constant.KEY_STORE_NAME)
    private String storeName;
    @SerializedName(Constant.KEY_STORE_POSITION)
    private String storePosition;
    @SerializedName(Constant.KEY_UPDATED_TIME)
    private String updateTime;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @SerializedName(Constant.KEY_CUSTOMER_NAME)
    private String customerName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePosition() {
        return storePosition;
    }

    public void setStorePosition(String storePosition) {
        this.storePosition = storePosition;
    }

    public Request(int id, int deposit, double distance,
                   String startTime, String endTime, int storeId,
                   String destination, int price, int productId,
                   String productName, String phoneNumber, double longitude,
                   double latitude, int status, String createTime, String updateTime) {
        this.id = id;
        this.deposit = deposit;
        this.distance = distance;
        this.startTime = startTime;
        this.endTime = endTime;
        this.storeId = storeId;
        this.destination = destination;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

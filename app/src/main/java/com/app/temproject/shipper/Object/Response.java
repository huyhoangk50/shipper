package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 10/12/2016.
 */

public class Response {
    @SerializedName(Constant.KEY_ID)
    private int id;
    @SerializedName(Constant.KEY_STATUS)
    private int status;
    @SerializedName(Constant.KEY_REQUEST_ID)
    private int requestId;
    @SerializedName(Constant.KEY_SHIPPER_ID)
    private int shipperId;

    public int getShipperId() {
        return shipperId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public int getRequestId() {
        return requestId;
    }
}

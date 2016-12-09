package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hieppso194 on 04/12/2016.
 */

public class Notification {
    @SerializedName(Constant.KEY_USER_ID)
    private int UserID;

    @SerializedName(Constant.KEY_ROLE)
    private int Role;

    @SerializedName(Constant.KEY_TYPE)
    private int Type;

    @SerializedName(Constant.KEY_CREATED_TIME)
    private String CreatedTime;

    @SerializedName(Constant.KEY_UPDATED_TIME)
    private String UpdatedTime;




    public Notification(int UserID, int Type, int Role, String CreatedTime, String UpdatedTime ) {
        //super(UserID, Type, Role, CreatedTime, UpdatedTime);
        this.UserID = UserID;
        this. Type  = Type;
        this.Role = Role;
        this.CreatedTime = CreatedTime;
        this.UpdatedTime = UpdatedTime;

    }

    public Notification() {

    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        UpdatedTime = updatedTime;
    }

}

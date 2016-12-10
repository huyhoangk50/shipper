package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hieppso194 on 04/12/2016.
 */

public class Notification {
    @SerializedName(Constant.KEY_ACTOR_NAME)
    private String ActorName;

    @SerializedName(Constant.KEY_ROLE)
    private int Role;

    @SerializedName(Constant.KEY_CODE)
    private int Code;

    @SerializedName(Constant.KEY_CREATED_TIME)
    private String CreatedTime;

    public Notification() {

    }

    public Notification(String ActorName, int Role, int Code,String CreatedTime) {
        //super(UserID, Type, Role, CreatedTime, UpdatedTime);
        this.ActorName = ActorName;
        this.Role = Role;
        this.Code = Code;
        this.CreatedTime = CreatedTime;

    }

    public String getActorName() {
        return ActorName;
    }

    public void setActorName(String actorName) {
        ActorName = actorName;
    }

    public int getRole() {
        return Role;
    }

    public void setRole(int role) {
        Role = role;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }
}

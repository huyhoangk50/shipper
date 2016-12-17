package com.app.temproject.shipper.Object;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hieppso194 on 04/12/2016.
 */

public class Notification {

    public static final int SHIPPER_APPLY_REQUEST_NOTIFICATION_CODE = 1;
    public static final int SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_CODE = 2;
    public static final int SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_CODE = 3;
    public static final int STORE_ACCEPT_SHIPPER_NOTIFICATION_CODE = 4;
    public static final int STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_CODE = 5;
    public static final int STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_CODE = 6;
    public static final int STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_CODE = 7;

    private static final String SHIPPER_APPLY_REQUEST_NOTIFICATION_CONTENT = " đã nhận đơn hàng của bạn";
    private static final String SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_CONTENT = " yêu cầu hoàn thành đơn hàng";
    private static final String SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_CONTENT = " đã hủy đơn hàng của bạn";
    private static final String STORE_ACCEPT_SHIPPER_NOTIFICATION_CONTENT = " đã chấp nhận bạn thực hiện đơn hàng";
    private static final String STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_CONTENT = " đã xác nhận hoàn thành đơn hàng";
    private static final String STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_CONTENT = " đã hủy đơn hàng mà bạn đang thực hiện";
    private static final String STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_CONTENT = " đã hủy đơn hàng mà bạn đã đang ký";

    private static final String SHIPPER_APPLY_REQUEST_NOTIFICATION_TITLE = "Shipper mới";
    private static final String SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_TITLE = "Yêu cầu xác nhận";
    private static final String SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_TITLE = "Đơn hàng bị hủy";
    private static final String STORE_ACCEPT_SHIPPER_NOTIFICATION_TITLE = "Đơn hàng mới";
    private static final String STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_TITLE = "Hoàn tất đơn hàng";
    private static final String STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_TITLE = "Đơn hàng bị hủy";
    private static final String STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_TITLE = "Đơn hàng bị hủy";



    private String title;
    private String message;
    @SerializedName(Constant.KEY_ACTOR_ID)
    private int actorId;
    @SerializedName(Constant.KEY_ACTOR_ROLE)
    private int actorRole;
    @SerializedName(Constant.KEY_RECEIVER_NAME)
    private String receiverName;
    @SerializedName(Constant.KEY_RECEIVER_ID)
    private String receiverId;
    @SerializedName(Constant.KEY_RECEIVER_ROLE)
    private int receiverRole;
    @SerializedName(Constant.KEY_REQUEST_ID)
    private int requestId;
    @SerializedName(Constant.KEY_UPDATED_TIME)
    private String updatedTime;
    @SerializedName(Constant.KEY_ID)
    private int id;
    @SerializedName(Constant.KEY_ACTOR_NAME)
    private String actorName;

    @SerializedName(Constant.KEY_ROLE)
    private int role;

    @SerializedName(Constant.KEY_CODE)
    private int code;

    @SerializedName(Constant.KEY_CREATED_TIME)
    private String createdTime;

    public Notification() {

    }

    public Notification(String ActorName, int Role, int Code, String CreatedTime) {
        //super(UserID, Type, role, createdTime, UpdatedTime);
        this.actorName = ActorName;
        this.role = Role;
        this.code = Code;
        this.createdTime = CreatedTime;

    }

    public int getActorId() {
        return actorId;
    }

    public int getActorRole() {
        return actorRole;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public int getReceiverRole() {
        return receiverRole;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public int getId() {
        return id;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }



    public String getTitle() {
        return title;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage() {

        switch (code) {
            case SHIPPER_APPLY_REQUEST_NOTIFICATION_CODE:
                message = Constant.SHIPPER + " " + actorName + SHIPPER_APPLY_REQUEST_NOTIFICATION_CONTENT;
                break;
            case SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_CODE:
                message = Constant.SHIPPER + " " + actorName + SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_CONTENT;
                break;
            case SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_CODE:
                message = Constant.SHIPPER + " " + actorName + SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_CONTENT;
                break;
            case STORE_ACCEPT_SHIPPER_NOTIFICATION_CODE:
                message = Constant.STORE + " " + actorName + STORE_ACCEPT_SHIPPER_NOTIFICATION_CONTENT;
                break;
            case STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_CODE:
                message = Constant.STORE + " " + actorName + STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_CONTENT;
                break;
            case STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_CODE:
                message = Constant.STORE + " " + actorName + STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_CONTENT;
                break;
            case STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_CODE:
                message = Constant.STORE + " " + actorName + STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_CONTENT;
                break;
        }

    }

    public void setTitle() {
        switch (code) {
            case SHIPPER_APPLY_REQUEST_NOTIFICATION_CODE:
                title = SHIPPER_APPLY_REQUEST_NOTIFICATION_TITLE;
                break;
            case SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_CODE:
                title = SHIPPER_REQUIRE_COMPLETE_REQUEST_NOTIFICATION_TITLE;
                break;
            case SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_CODE:
                title = SHIPPER_CANCEL_ACCEPTED_RESPONSE_NOTIFICATION_TITLE;
                break;
            case STORE_ACCEPT_SHIPPER_NOTIFICATION_CODE:
                title = STORE_ACCEPT_SHIPPER_NOTIFICATION_TITLE;
                break;
            case STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_CODE:
                title = STORE_CONFIRM_COMPLETED_REQUEST_NOTIFICATION_TITLE;
                break;
            case STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_CODE:
                title = STORE_CANCEL_ACCEPTED_REQUEST_NOTIFICATION_TITLE;
                break;
            case STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_CODE:
                title = STORE_ACCEPT_ANOTHER_SHIPPER_NOTIFICATION_TITLE;
                break;
        }
    }
}

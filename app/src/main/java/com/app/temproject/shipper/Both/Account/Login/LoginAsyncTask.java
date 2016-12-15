package com.app.temproject.shipper.Both.Account.Login;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.app.temproject.shipper.Both.Account.ActiveAccount.ActiveAccountActivity;
import com.app.temproject.shipper.Libs.FileProcessing;
import com.app.temproject.shipper.Object.SocketConnection;
import com.app.temproject.shipper.Shipper.SPHomeActivity;
import com.app.temproject.shipper.Store.STHomeActivity;
import com.app.temproject.shipper.Object.Shipper;
import com.app.temproject.shipper.Object.Store;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.Libs.ServiceAsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by paladin on 10/12/2016.
 */

public class LoginAsyncTask extends ServiceAsyncTask {
    private String email;
    private String password;
    private int role;
    private Activity activity;

    public LoginAsyncTask(Activity activity, String email, String password, int role) {
        super(activity);
        this.activity = activity;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    protected void processData(boolean error, String message, String data) {
        if (error) {
            Toast.makeText(activity, Constant.INCORRECT_INFORMATION, Toast.LENGTH_LONG).show();
        } else {
            JsonObject loginJSONObject = new JsonObject();
            loginJSONObject.addProperty(Constant.KEY_EMAIL, email);
            loginJSONObject.addProperty(Constant.KEY_PASSWORD, password);
            loginJSONObject.addProperty(Constant.KEY_ROLE, role);

            FileProcessing.writeToInternalStorageFile(loginJSONObject.toString(), Constant.PATH_TO_LOGIN_INFORMATION_FILE, activity);

            if (role == Constant.STORE_ROLE) {
                Store store = new Gson().fromJson(data, Store.class);
                store.setPassword(password);
                ProjectManagement.store = store;
                ProjectManagement.socketConnection = new SocketConnection(ProjectManagement.baseUrl
                        + "?" + Constant.KEY_USER_ID + "=" + store.getId()
                        + "&" + Constant.KEY_ROLE + "=" + Constant.STORE_ROLE,
                        Constant.STORE_ROLE);
                if (store.getStatus() == Constant.ACTIVE_STATUS) {
                    Intent intent = new Intent(activity, STHomeActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else {
                    Intent intent = new Intent(activity, ActiveAccountActivity.class);
                    intent.putExtra(Constant.KEY_ROLE, role);
                    intent.putExtra(Constant.KEY_ID_ACCOUNT, store.getId());
                    activity.startActivity(intent);
                    activity.finish();
                }
            } else {
                Shipper shipper = new Gson().fromJson(data, Shipper.class);
                shipper.setPassword(password);
                ProjectManagement.shipper = shipper;
                ProjectManagement.socketConnection = new SocketConnection(ProjectManagement.baseUrl
                        + "?" + Constant.KEY_USER_ID + "=" + shipper.getId()
                        + "&" + Constant.KEY_ROLE + "=" + Constant.SHIPPER_ROLE,
                        Constant.SHIPPER_ROLE);
                if (shipper.getStatus() == Constant.ACTIVE_STATUS) {

                    Intent intent = new Intent(activity, SPHomeActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                } else {
                    Intent intent = new Intent(activity, ActiveAccountActivity.class);
                    intent.putExtra(Constant.KEY_ROLE, role);
                    intent.putExtra(Constant.KEY_ID_ACCOUNT, shipper.getId());
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        }
    }
}

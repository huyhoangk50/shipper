package com.app.temproject.shipper.Both.Account.Login;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.app.temproject.shipper.Both.Account.ActiveAccount.ActiveAccountActivity;
import com.app.temproject.shipper.Libs.FileProcessing;
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

            FileProcessing.writeToInternalStorageFile(loginJSONObject.toString(), Constant.PATH_TO_LOGIN_INFORMATION_FILE,activity);

            if (role == Constant.STORE_ROLE) {
                Store store = new Gson().fromJson(data, Store.class);
                store.setPassword(password);
//                    Store store = new Store(1, "huyhaongk4", "h32o", "nguyen huy hoang", "033884", "Tap hóa", "số 3 tân mai", "hoàng mai", "hà nội", 12.134, 124.2344, "Việt nam");
//                    store.setStatus(Constant.NOT_ACTIVE_STATUS);
//                    store.setStatus(Constant.ACTIVE_STATUS);
                ProjectManagement.store = store;
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

//                    Shipper shipper = new Shipper(1, "huyhoangk40@gmail.com", "1233435", Constant.SHIPPER_ROLE, "Nguyen Huy Hoang", "098765", Constant.NOT_ACTIVE_STATUS, 0, 0, "22/12/2015", "23/23/1345", "Hoang mai ha noi", 12.133,143.2413, "29/2/1994", "conaten.jpg");
//                    shipper.setStatus(Constant.NOT_ACTIVE_STATUS);
//                    shipper.setStatus(Constant.ACTIVE_STATUS);
                ProjectManagement.shipper = shipper;
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

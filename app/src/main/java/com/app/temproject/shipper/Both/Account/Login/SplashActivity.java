package com.app.temproject.shipper.Both.Account.Login;

/**
 * Created by Admin on 11/12/2016.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.temproject.shipper.Both.HomeActivity;
import com.app.temproject.shipper.Libs.FileProcessing;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;

import org.json.JSONObject;


public class SplashActivity extends Activity {

    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        String res = FileProcessing.readFileFromExternalStorage(Constant.PATH_TO_CONFIG_FILE);
        ProjectManagement.baseUrl = res;
        ProjectManagement.changeURL(res);

//        Thread autoLogin = new Thread(){
//            @Override
//            public void run() {
//
//            }
//        };
//        autoLogin.start();
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    data = FileProcessing.readFileFromInternalStorage(Constant.PATH_TO_LOGIN_INFORMATION_FILE, SplashActivity.this);
//                    if(data.equals("")) {
                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
//                    }else {
//                        login(data);
//                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
    private void login(String loginInformation){
        try {
            JSONObject jsonObject = new JSONObject(loginInformation);
            String email = jsonObject.getString(Constant.KEY_EMAIL);
            String password = jsonObject.getString(Constant.KEY_PASSWORD);
            int role = jsonObject.getInt(Constant.KEY_ROLE);
            new LoginAsyncTask(SplashActivity.this, email,password,role).execute(ProjectManagement.urlLogin, Constant.POST_METHOD, jsonObject.toString());
        }catch (Exception e){
            Log.e("Home Activity", e.toString());
        }
    }
}

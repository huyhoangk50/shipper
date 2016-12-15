package com.app.temproject.shipper.Both;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.temproject.shipper.Libs.FileProcessing;
import com.app.temproject.shipper.Shipper.SPRegisterActivity;
import com.app.temproject.shipper.Store.STRegisterActivity;
import com.app.temproject.shipper.Both.Account.Login.LoginActivity;
import com.app.temproject.shipper.Both.Account.Login.LoginAsyncTask;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.MyLocationListener;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private Button btnLogin;
//    private Button btnRatingBarCheck;
    private Button btnSignUpAsStore;
    private Button btnSignUpAsShipper;
    private String data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String res = FileProcessing.readFileFromExternalStorage(Constant.PATH_TO_CONFIG_FILE);
        ProjectManagement.baseUrl = res;
        ProjectManagement.changeURL(res);
        //test
//        String stringStr = "{\"err\":false,\"message\":\"Email and Password are valid! Account Not Active!\",\"data\":{\"id\":25,\"email\":\"1@gmail.com\",\"name\":\"jaybo\",\"phone_number\":\"0123456789\",\"address\":\"Tan Mai, Hoang Mai, Ha Noi\",\"avatar\":\"userdefault.jpg\",\"birthday\":\"1994-02-17T17:00:00.000Z\",\"longitude\":100.5,\"latitude\":10.6,\"rating\":0,\"vote\":0,\"created_time\":\"2016-11-26T09:29:33.085Z\",\"updated_time\":null,\"active_code\":\"c917d592\",\"status\":0,\"reset_code\":\"5a42e3c4\"}}";
//        String storeStr = " {\"err\":false,\"message\":\"Email and Password are valid! Account Not Active!\",\"data\":{\"id\":5,\"email\":\"1@gmail.com\",\"name\":\"jaybo\",\"phone_number\":\"0123456789\",\"store_type\":\"Clothes\",\"location_id\":5,\"address\":\"Tan Mai, Hoang Mai, Ha Noi\",\"rating\":0,\"vote\":0,\"created_time\":\"2016-11-26T09:27:39.004Z\",\"updated_time\":null,\"avatar\":\"userdefault.jpg\",\"active_code\":\"6d403ed0\",\"status\":0,\"reset_code\":\"a4833fd9\"}}";
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new_icon JSONObject(stringStr);
//            boolean error = jsonObject.getBoolean(Constant.KEY_ERROR);
//            String message = jsonObject.getString(Constant.KEY_MESSAGE);
//            String body = jsonObject.getString(Constant.KEY_DATA);
//            Shipper shipper = new_icon Gson().fromJson(body, Shipper.class);
//
//
//            jsonObject = new_icon JSONObject(storeStr);
//             error = jsonObject.getBoolean(Constant.KEY_ERROR);
//             message = jsonObject.getString(Constant.KEY_MESSAGE);
//             body = jsonObject.getString(Constant.KEY_DATA);
//            Store store = new_icon Gson().fromJson(body, Store.class);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        data = FileProcessing.readFileFromInternalStorage(Constant.PATH_TO_LOGIN_INFORMATION_FILE, HomeActivity.this);
        if(data.equals("")) {
            initViews();
            setEvents();
        }else {
            login(data);
        }
    }

    /**
     * init views
     */
    private void initViews() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUpAsShipper = (Button) findViewById(R.id.btnSignUpAsShipper);
        btnSignUpAsStore = (Button) findViewById(R.id.btnSignUpAsStore);
        new MyLocationListener();
    }


    /**
     * set events
     */
    private void setEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUpAsStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, STRegisterActivity.class);
                startActivity(intent);
            }
        });

        btnSignUpAsShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SPRegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void login(String loginInformation){
        try {
            JSONObject jsonObject = new JSONObject(loginInformation);
            String email = jsonObject.getString(Constant.KEY_EMAIL);
            String password = jsonObject.getString(Constant.KEY_PASSWORD);
            int role = jsonObject.getInt(Constant.KEY_ROLE);
            new LoginAsyncTask(HomeActivity.this, email,password,role).execute(ProjectManagement.urlLogin, Constant.POST_METHOD, jsonObject.toString());
        }catch (Exception e){
            Log.e("Home Activity", e.toString());
        }
    }

}
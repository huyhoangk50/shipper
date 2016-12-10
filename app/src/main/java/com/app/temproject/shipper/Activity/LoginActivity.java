package com.app.temproject.shipper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Activity.Shipper.SPHomeActivity;
import com.app.temproject.shipper.Activity.Store.STHomeActivity;
import com.app.temproject.shipper.CheckingInformation;
import com.app.temproject.shipper.LoginAsyncTask;
import com.app.temproject.shipper.Object.Shipper;
import com.app.temproject.shipper.Object.Store;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by huyhoang on 12/08/2016.
 */
public class LoginActivity extends Activity {

    private Button btnLogin;
    private EditText etEmail;
    private TextView tvCheckEmail;
    private EditText etPassword;
    private TextView tvCheckPassword;
    private Spinner spRole;
    private TextView tvForgotPassword;
    private Toolbar toolbar;

    private int role;
    private String email, password;

    private boolean isValidEmail;
    private boolean isValidPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        setEvent();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.register));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        tvCheckEmail = (TextView) findViewById(R.id.tvCheckEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvCheckPassword = (TextView) findViewById(R.id.tvCheckPassword);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
        spRole = (Spinner) findViewById(R.id.spRole);
        String[] roles = getResources().getStringArray(R.array.roles);
        ArrayAdapter<String> roleAdapter = new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_spinner_dropdown_item, roles);
        spRole.setAdapter(roleAdapter);
    }

//    private class LoginAsyncTask extends ServiceAsyncTask {
//
//        public LoginAsyncTask(Activity activity) {
//            super(activity);
//        }
//
//        @Override
//        protected void processData(boolean error, String message, String data) {
//            if (error) {
//                Toast.makeText(LoginActivity.this, getString(R.string.incorrect_information), Toast.LENGTH_LONG).show();
//            } else {
//                JsonObject loginJSONObject = new JsonObject();
//                loginJSONObject.addProperty(Constant.KEY_EMAIL, email);
//                loginJSONObject.addProperty(Constant.KEY_PASSWORD, password);
//                loginJSONObject.addProperty(Constant.KEY_ROLE, role);
//
//                FileProcessing.writeToInternalStorageFile(loginJSONObject.toString(), "loginInformation.txt",LoginActivity.this);
//                Log.e("login", FileProcessing.readFileFromInternalStorage("loginInformation.txt", LoginActivity.this));
//
//                if (role == Constant.STORE_ROLE) {
//                    Store store = new Gson().fromJson(data, Store.class);
//                    store.setPassword(password);
////                    Store store = new Store(1, "huyhaongk4", "h32o", "nguyen huy hoang", "033884", "Tap hóa", "số 3 tân mai", "hoàng mai", "hà nội", 12.134, 124.2344, "Việt nam");
////                    store.setStatus(Constant.NOT_ACTIVE_STATUS);
////                    store.setStatus(Constant.ACTIVE_STATUS);
//                    ProjectManagement.store = store;
//                    if (store.getStatus() == Constant.ACTIVE_STATUS) {
//                        Intent intent = new Intent(LoginActivity.this, STHomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(LoginActivity.this, ActiveAccountActivity.class);
//                        intent.putExtra(Constant.KEY_ROLE, role);
//                        intent.putExtra(Constant.KEY_ID_ACCOUNT, store.getId());
//                        startActivity(intent);
//                        finish();
//                    }
//                } else {
//                    Shipper shipper = new Gson().fromJson(data, Shipper.class);
//                    shipper.setPassword(password);
//
////                    Shipper shipper = new Shipper(1, "huyhoangk40@gmail.com", "1233435", Constant.SHIPPER_ROLE, "Nguyen Huy Hoang", "098765", Constant.NOT_ACTIVE_STATUS, 0, 0, "22/12/2015", "23/23/1345", "Hoang mai ha noi", 12.133,143.2413, "29/2/1994", "conaten.jpg");
////                    shipper.setStatus(Constant.NOT_ACTIVE_STATUS);
////                    shipper.setStatus(Constant.ACTIVE_STATUS);
//                    ProjectManagement.shipper = shipper;
//                    if (shipper.getStatus() == Constant.ACTIVE_STATUS) {
//
//                        Intent intent = new Intent(LoginActivity.this, SPHomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent intent = new Intent(LoginActivity.this, ActiveAccountActivity.class);
//                        intent.putExtra(Constant.KEY_ROLE, role);
//                        intent.putExtra(Constant.KEY_ID_ACCOUNT, shipper.getId());
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            }
//        }
//    }

    private class LoginAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            BufferedReader reader = null;
            HttpURLConnection urlConnection = null;

            try {
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(strings[2], "UTF-8");
                data += "&" + URLEncoder.encode("role", "UTF-8") + "=" + URLEncoder.encode(strings[3], "UTF-8");

                URL url = new URL(strings[0]);

                // Send POST data request
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStreamWriter.write(data);
                outputStreamWriter.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                response = sb.toString();
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }

    private void setEvent() {
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ConfirmEmailActivity.class);
                intent.putExtra(Constant.KEY_ROLE, role);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//   shipper {"err":false,"message":"Email and Password are valid! Account Not Active!","data":{"id":25,"email":"1@gmail.com","name":"jaybo","phone_number":"0123456789","address":"Tan Mai, Hoang Mai, Ha Noi","avatar":"userdefault.jpg","birthday":"1994-02-17T17:00:00.000Z","longitude":100.5,"latitude":10.6,"rating":0,"vote":0,"created_time":"2016-11-26T09:29:33.085Z","updated_time":null,"active_code":"c917d592","status":0,"reset_code":"5a42e3c4"}}
//store {"err":false,"message":"Email and Password are valid! Account Not Active!","data":{"id":5,"email":"1@gmail.com","name":"jaybo","phone_number":"0123456789","store_type":"Clothes","location_id":5,"address":"Tan Mai, Hoang Mai, Ha Noi","rating":0,"vote":0,"created_time":"2016-11-26T09:27:39.004Z","updated_time":null,"avatar":"userdefault.jpg","active_code":"6d403ed0","status":0,"reset_code":"a4833fd9"}}

//                Store store = new Store();
//                String test = new Gson().toJson(store);
                //fake code
//                if (role == Constant.STORE_ROLE) {
//                    Store store = new Store(1, "huyhaongk4", "h32o", "nguyen huy hoang", "033884", "Tap hóa", "số 3 tân mai", "hoàng mai", "hà nội", 12.134, 124.2344, "Việt nam");
////                    store.setStatus(Constant.NOT_ACTIVE_STATUS);
////                    store.setStatus(Constant.ACTIVE_STATUS);
//                    ProjectManagement.store = store;
//                        Intent intent = new Intent(LoginActivity.this, STHomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                } else {
//
//                    Shipper shipper = new Shipper(1, "huyhoangk40@gmail.com", "1233435", Constant.SHIPPER_ROLE, "Nguyen Huy Hoang", "098765", Constant.NOT_ACTIVE_STATUS, 0, 0, "22/12/2015", "23/23/1345", "Hoang mai ha noi", 12.133,143.2413, "29/2/1994", "conaten.jpg");
////                    shipper.setStatus(Constant.NOT_ACTIVE_STATUS);
////                    shipper.setStatus(Constant.ACTIVE_STATUS);
//                    ProjectManagement.shipper = shipper;
//                        Intent intent = new Intent(LoginActivity.this, SPHomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                }


                setInformationFromUser();
                checkInformationCorrectness();

                if (isAllInformationCorrect()) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(Constant.KEY_EMAIL, email);
                    jsonObject.addProperty(Constant.KEY_PASSWORD, password);
                    jsonObject.addProperty(Constant.KEY_ROLE, role);
                    new LoginAsyncTask(LoginActivity.this,email,password,role).execute(ProjectManagement.urlLogin, Constant.POST_METHOD, jsonObject.toString());
                }else {
                    notifyToUser();
                }

            }
        });

        spRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spRole.getSelectedItem().toString().equals("Shipper")) {
                    role = Constant.SHIPPER_ROLE;
                } else {
                    role = Constant.STORE_ROLE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                role = Constant.SHIPPER_ROLE;
            }
        });


//        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }

    private void setInformationFromUser(){
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();
    }

    private void checkInformationCorrectness(){
        isValidEmail = CheckingInformation.isValidEmail(email);
        isValidPassword = CheckingInformation.isValidPassword(password);
    }
    private boolean isAllInformationCorrect(){
        if(!isValidEmail) return false;
        if(!isValidPassword) return false;
        return true;
    }

    private void notifyToUser(){
        if(!(isValidEmail)){
            tvCheckEmail.setText(Constant.INVALID_EMAIL);
        }else{
            tvCheckEmail.setText("");
        }
        if(!isValidPassword){
            tvCheckPassword.setText(Constant.INVALID_PASSWORD);
        }else{
            tvCheckPassword.setText("");
        }
    }


//    String response = "{\"err\":false,\"message\":\"Email and Password are valid! Account Not Active!\",\"data\":{\"id\":19,\"email\":\"123@gmail.com\",\"password\":\"8604968e69fafb4e65e8bd952dbddd122fc600cd05bb5b48ffdf5dfb462888e505325e542a276f5898842d2458af6991c22918c9852ddeff34f63b667ddd6059\",\"salt\":\"aca4ad29034dddea\",\"name\":\"AnhTu\",\"phone_number\":\"1234567890\",\"address\":\"Tan Mai, Hoang Mai, Ha Noi\",\"avatar\":\"userdefault.jpg\",\"birthday\":\"1994-02-17T17:00:00.000Z\",\"longitude\":12,\"latitude\":12,\"rating\":0,\"vote\":0,\"created_time\":\"2016-11-30T02:57:39.462Z\",\"updated_time\":null,\"status\":0,\"reset_code\":\"3a69ba91\",\"active_code\":\"82b29bd5\"}}\n"
}

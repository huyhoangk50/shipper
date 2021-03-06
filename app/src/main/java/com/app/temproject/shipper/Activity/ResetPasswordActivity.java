package com.app.temproject.shipper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.temproject.shipper.Activity.Shipper.SPHomeActivity;
import com.app.temproject.shipper.Activity.Store.STHomeActivity;
import com.app.temproject.shipper.Object.Shipper;
import com.app.temproject.shipper.Object.Store;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ResetPasswordActivity extends AppCompatActivity {

    private int role;
    private String email;
    private String password;
    private String resetCode;

    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etResetCode;
    private TextView tvCheckPassword;
    private TextView tvCheckConfirmPassword;
    private TextView tvCheckResetCode;
    private Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passwrod);
        Intent intent = getIntent();
        role = intent.getIntExtra(Constant.KEY_ROLE, 0);
        email = intent.getStringExtra(Constant.KEY_EMAIL);

        initView();
        setEvent();


    }

    private void initView(){
        tvCheckPassword = (TextView) findViewById(R.id.tvCheckPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etResetCode = (EditText) findViewById(R.id.etResetCode);
        tvCheckConfirmPassword = (TextView) findViewById(R.id.tvCheckConfirmPassword);
        tvCheckResetCode = (TextView) findViewById(R.id.tvCheckResetCode);
        btnReset = (Button) findViewById(R.id.btnReset);
    }

    private void setEvent(){
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = etPassword.getText().toString();
                resetCode = etResetCode.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                if(isValid(password, confirmPassword)){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(Constant.KEY_EMAIL, email);
                    jsonObject.addProperty(Constant.KEY_PASSWORD, password);
                    jsonObject.addProperty(Constant.KEY_ROLE, role);
                    jsonObject.addProperty(Constant.KEY_RESET_CODE, resetCode);

                    new ResetPasswordAsyncTask(ResetPasswordActivity.this).execute(Constant.URL_RESET_PASSWORD, Constant.POST_METHOD, jsonObject.toString());
                }
            }
        });
    }
    private class ResetPasswordAsyncTask extends ServiceAsyncTask {

        public ResetPasswordAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(error){
                tvCheckResetCode.setText(getString(R.string.incorrect_reset_code));
            } else {
                if(role == Constant.SHIPPER_ROLE){
                    Shipper shipper = new Gson().fromJson(data, Shipper.class);
                    shipper.setPassword(password);
                    ProjectManagement.shipper = shipper;
                    if (shipper.getStatus() == Constant.ACTIVE_STATUS) {
                        Intent intent = new Intent(ResetPasswordActivity.this, SPHomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(ResetPasswordActivity.this, ActiveAccountActivity.class);
                        intent.putExtra(Constant.KEY_ROLE, role);
                        startActivity(intent);
                        finish();
                    }
                } else  {
                    Store store = new Gson().fromJson(data, Store.class);
                    store.setPassword(password);
                    ProjectManagement.store = store;
                    if (store.getStatus() == Constant.ACTIVE_STATUS) {
                        Intent intent = new Intent(ResetPasswordActivity.this, STHomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(ResetPasswordActivity.this, ActiveAccountActivity.class);
                        intent.putExtra(Constant.KEY_ROLE, role);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }
    }

    private boolean isValid(String password, String confirmPassword){
        return  true;
    }
}

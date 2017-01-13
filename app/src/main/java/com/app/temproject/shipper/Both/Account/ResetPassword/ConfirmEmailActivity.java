package com.app.temproject.shipper.Both.Account.ResetPassword;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Libs.CheckingInformation;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.Libs.ServiceAsyncTask;
import com.google.gson.JsonObject;

public class ConfirmEmailActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSubmit;
    private TextView tvDescription;
    private TextView tvCheckEmail;
    private Button btnContinue;
    private Toolbar toolbar;
    private String email;

    private boolean isValidEmail;
    private int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_email);
        Intent intent = getIntent();
        role = intent.getIntExtra(Constant.KEY_ROLE, 0);
        initView();
        setEvent();
    }

    private void  initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.find_your_account));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        tvCheckEmail = (TextView) findViewById(R.id.tvCheckEmail);
    }

    private void setEvent(){

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInformationFromUser();
                checkInformationCorrectness();
                if(isAllInformationCorrect()){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(Constant.KEY_ROLE, role);
                    jsonObject.addProperty(Constant.KEY_EMAIL, email);

                    new ConfirmEmailAsyncTask(ConfirmEmailActivity.this).execute(ProjectManagement.urlConfirmEmail, Constant.POST_METHOD, jsonObject.toString());
                }else {
                    notifyToUser();
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmEmailActivity.this, ResetPasswordActivity.class);
                intent.putExtra(Constant.KEY_ROLE, role);
                intent.putExtra(Constant.KEY_EMAIL, email);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setInformationFromUser(){
        email = etEmail.getText().toString();
    }

    private void checkInformationCorrectness(){
        isValidEmail = CheckingInformation.isValidEmail(email);
    }

    private boolean isAllInformationCorrect(){
        if(!isValidEmail) return  false;
        return true;
    }

    private void notifyToUser(){
        if(!isValidEmail){
            tvCheckEmail.setText(Constant.INVALID_EMAIL);
        }else {
            tvCheckEmail.setText("");
        }
    }

    private class ConfirmEmailAsyncTask extends ServiceAsyncTask{

        public ConfirmEmailAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(error){
                Toast.makeText(ConfirmEmailActivity.this, Constant.NOT_EXISTED_EMAIL, Toast.LENGTH_LONG).show();
            } else {
                etEmail.setHint(getString(R.string.type_reset_code));
                etEmail.setEnabled(false);
                tvCheckEmail.setText("");
                tvDescription.setText(getString(R.string.reset_password_notification));
                btnSubmit.setVisibility(View.GONE);
                btnContinue.setVisibility(View.VISIBLE);
            }
        }
    }

}

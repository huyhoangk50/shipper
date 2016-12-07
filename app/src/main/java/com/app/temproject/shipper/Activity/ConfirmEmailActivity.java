package com.app.temproject.shipper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.JsonObject;

public class ConfirmEmailActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnSubmit;
    private TextView tvDescription;
    private Button btnContinue;
    private String email;

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
        etEmail = (EditText) findViewById(R.id.etEmail);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        btnContinue = (Button) findViewById(R.id.btnContinue);
    }

    private void setEvent(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = etEmail.getText().toString();
                if(isValid(email)){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(Constant.KEY_ROLE, role);
                    jsonObject.addProperty(Constant.KEY_EMAIL, email);

                    new ConfirmEmailAsyncTask(ConfirmEmailActivity.this).execute(Constant.URL_CONFIRM_EMAIL, Constant.POST_METHOD, jsonObject.toString());
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
            }
        });
    }

    private boolean isValid(String activeCode){
        return true;
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
                tvDescription.setText(getString(R.string.reset_password_notification));
                btnSubmit.setVisibility(View.GONE);
                btnContinue.setVisibility(View.VISIBLE);
            }
        }
    }

}

package com.app.temproject.shipper.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.temproject.shipper.Activity.Store.STHomeActivity;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.JsonObject;

public class ActiveAccountActivity extends AppCompatActivity {

    private EditText etActiveAccount;
    private Button btnSubmit;

    private int role;
    private int idAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_account);
        Intent intent = getIntent();
        role = intent.getIntExtra(Constant.KEY_ROLE, 0);
        idAccount = intent.getIntExtra(Constant.KEY_ID_ACCOUNT, 0);

        initView();
        setEvent();
    }

    private void  initView(){
        etActiveAccount = (EditText) findViewById(R.id.etActiveAccount);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
    }

    private void setEvent(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activeCode = etActiveAccount.getText().toString();
                if(isValid(activeCode)){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(Constant.KEY_ROLE, role);
                    jsonObject.addProperty(Constant.KEY_ID_ACCOUNT, idAccount);
                    jsonObject.addProperty(Constant.KEY_ACTIVE_CODE, activeCode);

                    new ActiveCodeAsyncTask(ActiveAccountActivity.this).execute(Constant.URL_ACTIVE_ACCOUNT, Constant.POST_METHOD, jsonObject.toString());
                }
            }
        });
    }

    private boolean isValid(String activeCode){
        return true;
    }

    private  class ActiveCodeAsyncTask extends ServiceAsyncTask{

        public ActiveCodeAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (error) {
                Toast.makeText(ActiveAccountActivity.this, getString(R.string.incorrect_active_code), Toast.LENGTH_LONG).show();
            } else {
                if (role == Constant.STORE_ROLE) {
                    ProjectManagement.store.setStatus(Constant.ACTIVE_STATUS);
                    Intent intent = new Intent(ActiveAccountActivity.this, STHomeActivity.class);
                    startActivity(intent);
                } else {
                    ProjectManagement.shipper.setStatus(Constant.ACTIVE_STATUS);
                    Intent intent = new Intent(ActiveAccountActivity.this, STHomeActivity.class);
                    startActivity(intent);
                }
            }
        }
    }
}

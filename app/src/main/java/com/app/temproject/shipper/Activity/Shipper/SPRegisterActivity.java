package com.app.temproject.shipper.Activity.Shipper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.app.temproject.shipper.R;

/**
 * Created by huyhoang on 12/08/2016.
 */
public class SPRegisterActivity extends Activity {

    Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_activity_register);

        initView();
        setEvent();
    }

    private void initView(){
    }

    private void setEvent(){
    }
}

package com.app.temproject.shipper.Activity;

/**
 * Created by Admin on 11/12/2016.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.app.temproject.shipper.R;


public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        timer.start();
    }
}

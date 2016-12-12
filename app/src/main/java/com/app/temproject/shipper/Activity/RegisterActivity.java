package com.app.temproject.shipper.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.temproject.shipper.Adapter.RegisterViewPagerAdapter;
import com.app.temproject.shipper.Adapter.Shipper.SPRequestPagerAdapter;
import com.app.temproject.shipper.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterViewPagerAdapter registerViewPagerAdapter = new RegisterViewPagerAdapter(this.getSupportFragmentManager(),this);
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpRegister);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tlRegister);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(registerViewPagerAdapter);
    }
}

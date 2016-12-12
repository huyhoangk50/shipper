package com.app.temproject.shipper.Both.Account.Register;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.app.temproject.shipper.R;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        setEvent();
    }

    private void initView(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.register));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        RegisterViewPagerAdapter registerViewPagerAdapter = new RegisterViewPagerAdapter(this.getSupportFragmentManager(),this);
        viewPager = (ViewPager) findViewById(R.id.vpRegister);
        tabLayout = (TabLayout) findViewById(R.id.tlRegister);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(registerViewPagerAdapter);


    }

    private void setEvent(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
}

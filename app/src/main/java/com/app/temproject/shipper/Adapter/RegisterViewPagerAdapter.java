package com.app.temproject.shipper.Adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.temproject.shipper.Fragment.Shipper.SpRegisterFragment;
import com.app.temproject.shipper.Fragment.Store.StRegisterFragment;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;

/**
 * Created by Admin on 12/12/2016.
 */

public class RegisterViewPagerAdapter extends FragmentStatePagerAdapter {
    private Activity activity;
    public RegisterViewPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SpRegisterFragment();
            default:
                return new StRegisterFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return activity.getString(R.string.shipper);
        else return activity.getString(R.string.store);
    }
}

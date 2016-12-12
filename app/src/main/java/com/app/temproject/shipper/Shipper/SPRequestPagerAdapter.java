package com.app.temproject.shipper.Shipper;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.app.temproject.shipper.Shipper.SPRequestsFragment;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.R;

/**
 * Created by Admin on 28/11/2016.
 */
public class SPRequestPagerAdapter extends FragmentStatePagerAdapter {
    private Context context;

    public SPRequestPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        SPRequestsFragment spRequestsFragment = new SPRequestsFragment();
        switch (position){
            case 0:
                bundle.putInt(Constant.KEY_STATUS, Constant.NEW_REQUEST);
                break;
            case 1:
                bundle.putInt(Constant.KEY_STATUS, Constant.WAITING_REQUEST);
                break;
            case 2:
                bundle.putInt(Constant.KEY_STATUS, Constant.PROCESSING_REQUEST);
                break;
            case 3:
                bundle.putInt(Constant.KEY_STATUS, Constant.COMPLETED_REQUEST);
                break;
            default:
                bundle.putInt(Constant.KEY_STATUS, Constant.NEW_REQUEST);
                break;
        }
        spRequestsFragment.setArguments(bundle);

        return spRequestsFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return context.getString(R.string.all);
        if(position==1) return context.getString(R.string.pending);
        if(position==2) return context.getString(R.string.on_going);
        else return context.getString(R.string.completed);
    }

    @Override
    public int getCount() {
        return 4;
    }
}

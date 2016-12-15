package com.app.temproject.shipper.Shipper;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.R;

public class SPHomeFragment extends Fragment {

    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        View rootView = inflater.inflate(R.layout.sp_fragment_home, container, false);
        SPRequestPagerAdapter spRequestPagerAdapter = new SPRequestPagerAdapter(getActivity().getSupportFragmentManager(),getContext());
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.vpRequest);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tlRequest);

//        for(int i = 0; i < viewPager)
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(spRequestPagerAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.new_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.waiting);
        tabLayout.getTabAt(2).setIcon(R.drawable.processing);
        tabLayout.getTabAt(3).setIcon(R.drawable.complete);
        int a = viewPager.getChildCount();
        return rootView;
    }
}

package com.app.temproject.shipper.Fragment.Shipper;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.Adapter.SPRequestPagerAdapter;
import com.app.temproject.shipper.R;

public class SPHomeFragment extends Fragment {
    public SPHomeFragment(){}

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
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(spRequestPagerAdapter);
        return rootView;
    }
}

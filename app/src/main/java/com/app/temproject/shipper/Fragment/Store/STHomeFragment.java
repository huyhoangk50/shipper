package com.app.temproject.shipper.Fragment.Store;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.Adapter.Store.STRequestPagerAdapter;
import com.app.temproject.shipper.R;

public class STHomeFragment extends Fragment {
    public STHomeFragment(){}

    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        View rootView = inflater.inflate(R.layout.st_fragment_home, container, false);
//       View rootView = inflater.inflate(R.layout.sp_fragment_home, container, false);
        STRequestPagerAdapter stRequestPagerAdapter = new STRequestPagerAdapter(getActivity().getSupportFragmentManager(),getContext());
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.vpRequest);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tlRequest);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(stRequestPagerAdapter);
        return rootView;
    }
}

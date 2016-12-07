package com.app.temproject.shipper.Fragment.Shipper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.R;

public class SPRequestFragment extends Fragment {
    public SPRequestFragment(){}

    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        View rootView = inflater.inflate(R.layout.sp_fragment_request, container, false);
        return rootView;
    }
}

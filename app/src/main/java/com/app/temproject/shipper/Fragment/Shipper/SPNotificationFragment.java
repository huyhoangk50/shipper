package com.app.temproject.shipper.Fragment.Shipper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SPNotificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SPNotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SPNotificationFragment extends Fragment {
    public SPNotificationFragment(){}

    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        View rootView = inflater.inflate(R.layout.sp_fragment_notification, container, false);
        return rootView;
    }
}

package com.app.temproject.shipper.Fragment.Shipper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;


import java.util.ArrayList;
import java.util.List;

import com.app.temproject.shipper.Adapter.Shipper.SPNotificationAdapter;
import com.app.temproject.shipper.Object.Notification;
import com.app.temproject.shipper.R;


public class SPNotificationFragment extends Fragment {
    private List<Notification> spNotificatiolist = new ArrayList<>();
    private RecyclerView recyclerView;
    private SPNotificationAdapter spNotificationAdapter;
    private Notification notification;

    public SPNotificationFragment(){}

    @Override
    public void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState )
    {
        View rootView = inflater.inflate(R.layout.sp_fragment_notification, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_SPNotification);

        spNotificationAdapter = new SPNotificationAdapter(spNotificatiolist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(spNotificationAdapter);

        LoadSPNotificationData();
        return rootView;


    }


    private void LoadSPNotificationData() {
        notification = new Notification("Nguyễn Huy Hoàng ",2,0,"14-11-2016");
        spNotificatiolist.add(notification);

        notification = new Notification("Nguyễn Anh Tu ",2,1,"14-11-2016");
        spNotificatiolist.add(notification);

        notification = new Notification("Nguyễn Văn Sang ",2,0,"14-11-2016");
        spNotificatiolist.add(notification);

        //SPNotificationAdapter.notifyDataSetChanged();
    }

}

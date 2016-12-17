package com.app.temproject.shipper.Shipper;

import android.app.Activity;
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
import android.widget.Toast;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.app.temproject.shipper.Libs.ServiceAsyncTask;
import com.app.temproject.shipper.Object.Notification;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class SPNotificationFragment extends Fragment {
    private List<Notification> notifications;
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


        loadNotifications();
        return rootView;


    }


    private void loadNotifications() {
        new LoadNotificationAsyncTask(getActivity())
                .execute(ProjectManagement.urlLoadNotifications
                    + ProjectManagement.shipper.getId()
                    + "/" + Constant.SHIPPER_ROLE,
                    Constant.GET_METHOD);
    }

    private class LoadNotificationAsyncTask extends ServiceAsyncTask{

        public LoadNotificationAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if(!error){
                Type token = new TypeToken<ArrayList<Notification>>() {
                }.getType();
                notifications = (new Gson().fromJson(data, token));
                if (notifications != null) {
                    setAdapter();
                }
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setAdapter() {
        spNotificationAdapter = new SPNotificationAdapter(notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(spNotificationAdapter);
    }

}

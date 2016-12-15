package com.app.temproject.shipper.Store;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.Libs.ServiceAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Admin on 09/12/2016.
 */

public class STRequestsFragment extends Fragment{
    protected RecyclerView recyclerView;
    protected ArrayList<Request> requests;
    private int status;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.st_request_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rcvRequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        status = getArguments().getInt(Constant.KEY_STATUS);
        loadData(status);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData(status);
    }

    protected void loadData(int status) {

        switch (status){
            case Constant.WAITING_REQUEST:
                new STRequestsFragment.LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlStLoadWaitingRequest + ProjectManagement.store.getId(), Constant.GET_METHOD);
                break;
            case Constant.COMPLETED_REQUEST:
                new STRequestsFragment.LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlStLoadCompletedRequest + ProjectManagement.store.getId(), Constant.GET_METHOD);
                break;
            case Constant.PROCESSING_REQUEST:
                new STRequestsFragment.LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlStLoadProcessingRequest + ProjectManagement.store.getId(), Constant.GET_METHOD);
                break;
            default:
                new STRequestsFragment.LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlStLoadWaitingRequest + ProjectManagement.store.getId(), Constant.GET_METHOD);
                break;
        }
    }


    private class LoadRequestAsyncTask extends ServiceAsyncTask {

        public LoadRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            //process data from server
            if (!error) {
                Type token = new TypeToken<ArrayList<Request>>() {
                }.getType();
                requests = (new Gson().fromJson(data, token));
                if (requests != null) {
                    updateUI();
                }
            }
        }

    }


//    private void fakeData(){
//        requests = new_icon ArrayList<>();
//        Request request = new_icon Request(1, 1000000, 5, "22/12/2016 08:30", "22/12/2016 : 17:30", 5, "Số 5 cù chính lan hà nội", 20000, 2, "Đồng hồ smart watch", "09876543332", 21.0024017, 105.8488915, 1, "12/12/2016 08:30", "12/12/2016 08:30");
//        request.setStoreName("Cửa hàng đồng hồ Huy Hoàng");
//        request.setStorePosition("Số 5 Minh khai hà nội");
//        request.setStatus(status);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//        requests.add(request);
//    }

    protected void updateUI() {
        STRequestAdapter requestAdapter = new STRequestAdapter(getActivity(), requests);
        recyclerView.setAdapter(requestAdapter);
    }
}
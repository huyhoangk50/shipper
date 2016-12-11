package com.app.temproject.shipper.Fragment.Shipper;

/**
 * Created by Admin on 28/11/2016.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.Adapter.Shipper.SPRequestAdapter;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SPRequestsFragment extends Fragment {
    protected RecyclerView recyclerView;
    protected ArrayList<Request> requests;
    private int status;
    //    private List<UserPlant> userPlantList;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.sp_request_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rcvRequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        status = getArguments().getInt(Constant.KEY_STATUS);
        loadData(status);


        return rootView;
    }

    protected void loadData(int status) {

//        String keys[] = {Constant.KEY_USER_ID, Constant.KEY_PASSWORD, Constant.KEY_STATUS};
//        String values[] = {ProjectManagement.shipper.getId() + "", ProjectManagement.shipper.getPassword(), status + ""};;
//        String requestPacket = HttpPacketProcessing.createBodyOfPacket(keys, values);


        switch (status){
            case Constant.WAITING_REQUEST:
                new LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlSpLoadWaitingRequest + ProjectManagement.shipper.getId(), Constant.GET_METHOD, "");
                break;
            case Constant.COMPLETED_REQUEST:
                new LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlSpLoadCompletedRequest + ProjectManagement.shipper.getId(), Constant.GET_METHOD, "");
                break;
            case Constant.PROCESSING_REQUEST:
                new LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlSpLoadProcessingRequest + ProjectManagement.shipper.getId(), Constant.GET_METHOD, "");
                break;
            case Constant.NEW_REQUEST:
                new LoadRequestAsyncTask(getActivity()).execute(ProjectManagement.urlSpLoadNewRequest + ProjectManagement.shipper.getId(), Constant.GET_METHOD, "");
                break;
        }
    }


    private class LoadRequestAsyncTask extends ServiceAsyncTask {

        public LoadRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                Type token = new TypeToken<ArrayList<Request>>() {
                }.getType();
                requests = (new Gson().fromJson(data, token));
                if (requests != null) {
                    setAdapter();
                }
            }

        }

        private void fakeData() {
            requests = new ArrayList<>();
            Request request = new Request(1, 1000000, 5, "22/12/2016 08:30", "22/12/2016 : 17:30", 5, "Số 5 cù chính lan hà nội", 20000, 2, "Đồng hồ smart watch", "09876543332", 21.0024017, 105.8488915, 1, "12/12/2016 08:30", "12/12/2016 08:30");
            request.setStoreName("Cửa hàng đồng hồ Huy Hoàng");
            request.setStorePosition("Số 5 Minh khai hà nội");
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
            requests.add(request);
        }
    }

    protected void setAdapter() {
        SPRequestAdapter requestAdapter = new SPRequestAdapter(getActivity(), requests);
        recyclerView.setAdapter(requestAdapter);
    }
}


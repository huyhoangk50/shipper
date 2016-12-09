package com.app.temproject.shipper.Fragment.Store;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.temproject.shipper.Adapter.Store.STRequestAdapter;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by Admin on 09/12/2016.
 */

public class STRequestsFragment extends Fragment{
    protected RecyclerView recyclerView;
    protected ArrayList<Request> requests;
    private int status;
    //    private List<UserPlant> userPlantList;
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

    protected void loadData(int status) {

//        String keys[] = {Constant.KEY_USER_ID, Constant.KEY_PASSWORD, Constant.KEY_STATUS};
//        String values[] = {ProjectManagement.shipper.getId() + "", ProjectManagement.shipper.getPassword(), status + ""};;
//        String requestPacket = HttpPacketProcessing.createBodyOfPacket(keys, values);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.KEY_USER_ID, ProjectManagement.store.getId());
        jsonObject.addProperty(Constant.KEY_PASSWORD, ProjectManagement.store.getPassword());
        jsonObject.addProperty(Constant.KEY_STATUS, status);
//        new LoadRequestAsyncTask(getActivity()).execute(Constant.urlStLoadRequest, Constant.POST_METHOD, jsonObject.toString());
        fakeData();
        setAdapter();

    }


    private class LoadRequestAsyncTask extends ServiceAsyncTask {

        public LoadRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            //process data from server

            setAdapter();
        }

    }


    private void fakeData(){
        requests = new ArrayList<>();
        Request request = new Request(1, 1000000, 5, "22/12/2016 08:30", "22/12/2016 : 17:30", 5, "Số 5 cù chính lan hà nội", 20000, 2, "Đồng hồ smart watch", "09876543332", 21.0024017, 105.8488915, 1, "12/12/2016 08:30", "12/12/2016 08:30");
        request.setStoreName("Cửa hàng đồng hồ Huy Hoàng");
        request.setStorePosition("Số 5 Minh khai hà nội");
        request.setStatus(status);
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
    protected void setAdapter() {
        STRequestAdapter requestAdapter = new STRequestAdapter(getActivity(), requests);
        recyclerView.setAdapter(requestAdapter);
    }
}

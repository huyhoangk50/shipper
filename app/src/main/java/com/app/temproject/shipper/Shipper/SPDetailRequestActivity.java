package com.app.temproject.shipper.Shipper;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Libs.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.Object.Location;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.Object.Response;
import com.app.temproject.shipper.Object.SocketConnection;
import com.app.temproject.shipper.Object.Store;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.Libs.ServiceAsyncTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class SPDetailRequestActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private WorkaroundMapFragment workaroundMapFragment;
    private CameraPosition cameraPosition;

    //View properties
    private Toolbar toolbar;
    private TextView tvProductName;
    private TextView tvStoreName;
    private TextView tvPrice;
    private TextView tvDeposit;
    private TextView tvStartTime;
    private TextView tvStorePlace;
    private TextView tvEndTime;
    private TextView tvCustomerName;
    private TextView tvDestination;
    private TextView tvCustomerPhone;
    private ScrollView svDetailRequest;
    private LinearLayout llApply;
    private Button btnCancel;
    private LinearLayout llDone;
    private TextView tvDescription;


    private Location location;
    private Store store;
    private Request request;
    private int requestId;
    private Response response;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sp_detail_request_layout);
        Intent intent = getIntent();
        requestId = intent.getIntExtra(Constant.KEY_REQUEST_ID, 0);
        initView();
        setEvent();
        loadData();
    }

    private void initView() {
        tvProductName = (TextView) findViewById(R.id.tvProductName);
        tvStoreName = (TextView) findViewById(R.id.tvStoreName);
        tvPrice = (TextView) findViewById(R.id.tvPrice);
        tvDeposit = (TextView) findViewById(R.id.tvDeposit);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvEndTime = (TextView) findViewById(R.id.tvEndTime);
        tvStorePlace = (TextView) findViewById(R.id.tvStorePlace);
        tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
        tvDestination = (TextView) findViewById(R.id.tvCustomerPlace);
        tvCustomerPhone = (TextView) findViewById(R.id.tvCustomerPhone);
        svDetailRequest = (ScrollView) findViewById(R.id.svDetailRequest);
        llApply = (LinearLayout) findViewById(R.id.llApply);
        llDone = (LinearLayout) findViewById(R.id.llDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        setSupportActionBar(toolbar);
        if (mMap == null) {
            workaroundMapFragment = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapSpDetailRequest));
            workaroundMapFragment.getMapAsync(this);
        }

        cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(20.9817847, 105.8554991)).zoom(20).build();

    }

    private void setEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        workaroundMapFragment.setListener(new WorkaroundMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                svDetailRequest.requestDisallowInterceptTouchEvent(true);
            }
        });

        llDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(SPDetailRequestActivity.this)
                        .setIcon(R.drawable.finish)
                        .setTitle(R.string.finish_request)
                        .setMessage(R.string.really_finish_request)
                        .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Dialog ratingDialog = new Dialog(SPDetailRequestActivity.this);
                                ratingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                ratingDialog.setContentView(R.layout.sp_rating_dialog);
                                Button btnSubmitRating = (Button) ratingDialog.findViewById(R.id.btnSubmitRating);
                                Button btnCancelRating = (Button) ratingDialog.findViewById(R.id.btnCancelRating);
                                final RatingBar rbRating = (RatingBar) ratingDialog.findViewById(R.id.ratingBar);
                                ratingDialog.show();

                                btnSubmitRating.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        rating = (int) rbRating.getRating();
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty(Constant.KEY_STORE_ID, store.getId());
                                        jsonObject.addProperty(Constant.KEY_NEW_RATING, rating);
                                        new CompleteRequestAsyncTask(SPDetailRequestActivity.this).execute(ProjectManagement.urlSpFinishRequest + request.getId(), Constant.PUT_METHOD, jsonObject.toString());
                                        ratingDialog.dismiss();
                                        pushShipperRequireToCompleteRequestNotification();
                                    }

                                });

                                btnCancelRating.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View arg0) {
                                        rating = 0;
                                        JsonObject jsonObject = new JsonObject();
                                        jsonObject.addProperty(Constant.KEY_STORE_ID, store.getId());
                                        jsonObject.addProperty(Constant.KEY_NEW_RATING, rating);
                                        new CompleteRequestAsyncTask(SPDetailRequestActivity.this).execute(ProjectManagement.urlSpFinishRequest, Constant.POST_METHOD, jsonObject.toString());
                                        ratingDialog.dismiss();
                                        pushShipperRequireToCompleteRequestNotification();
                                    }

                                });
                            }

                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        })
                        .show();
            }
        });

        llApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty(Constant.KEY_SHIPPER_ID, ProjectManagement.shipper.getId());
                jsonObject.addProperty(Constant.KEY_REQUEST_ID, requestId);
                new ApplyRequestAsyncTask(SPDetailRequestActivity.this).execute(ProjectManagement.urlSpApplyRequest, Constant.POST_METHOD, jsonObject.toString());
                pushShipperApplyRequestNotification();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder warningDialog = new AlertDialog.Builder(SPDetailRequestActivity.this);
                warningDialog.setIcon(R.drawable.delete);
                if (response.getStatus() == Constant.ACCEPTED_RESPONSE) {
                    warningDialog.setMessage(R.string.warninng_about_judgement);
                } else {
                    warningDialog.setMessage(R.string.really_want_to_cancel);
                }
                warningDialog.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty(Constant.KEY_SHIPPER_ID, ProjectManagement.shipper.getId());
                        jsonObject.addProperty(Constant.KEY_REQUEST_ID, requestId);
                        new CancelRequestAsyncTask(SPDetailRequestActivity.this).execute(ProjectManagement.urlSpCancelRequest, Constant.POST_METHOD, jsonObject.toString());
                        if(request.getStatus() == Constant.PROCESSING_REQUEST){
                            pushShipperCancelAcceptedResponseNotification();
                        }
                    }

                });
                warningDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }

                });
                warningDialog.show();
            }
        });
    }

    private void loadData() {
        new LoadDetailRequestAsyncTask(this).execute(ProjectManagement.urlSpLoadDetailRequest + requestId + "/" + ProjectManagement.shipper.getId(), Constant.GET_METHOD);
    }

    private void fakeData() {
        request = new Request(1, 1000000, 5, "22/12/2016 08:30", "22/12/2016 : 17:30", 5, "Số 5 cù chính lan hà nội", 20000, 2, "Đồng hồ smart watch", "09876543332", 105.8488915, 21.0024017, 1, "12/12/2016 08:30", "12/12/2016 08:30");
        request.setStoreName("Cửa hàng đồng hồ Huy Hoàng");
        request.setStorePosition("Số 5 Minh khai hà nội");
        request.setCustomerName("Nguyễn văn sang");
        Random random = new Random();
        int status = random.nextInt(4);
        request.setStatus(status);
        request.setStatus(Constant.WAITING_REQUEST);

        store = new Store(1, "123@gmail.com", "123@gmail.com", "Nguyễn Huy Hoàng", "35425343",
                "Thời trang", "số 3 tân mai", "Hoàng Mai", "Hà Nội", 105.8474236, 20.989865, "Việt Nam");
    }

    private void updateUI() {
        tvProductName.setText(request.getProductName());
        tvStoreName.setText(store.getName());
        tvPrice.setText(request.getPrice() + "");
        tvDeposit.setText(request.getDeposit() + "");
        tvStartTime.setText(request.getStartTime());
        tvEndTime.setText(request.getEndTime());
        tvCustomerName.setText(request.getCustomerName());
        tvDestination.setText(request.getDestination());
        tvStorePlace.setText(location.getStreet() + " - " + location.getDistrict() + " - " + location.getCity());
        tvCustomerPhone.setText(request.getPhoneNumber());

        if (mMap != null) {
            updateMap();
        }
        switch (response.getStatus()) {
            case Constant.NEW_RESPONSE:
            case Constant.CANCELED_RESPONSE:
                if (request.getStatus() == Constant.PROCESSING_REQUEST
                        || request.getStatus() == Constant.DONE_REQUEST
                        || request.getStatus() == Constant.COMPLETED_REQUEST
                        || request.getStatus() == Constant.CANCELED_REQUEST) {
                    tvDescription.setText(getString(R.string.can_not_apply_this_request));
                } else {
                    llApply.setVisibility(View.VISIBLE);
                    btnCancel.setVisibility(View.GONE);
                    llDone.setVisibility(View.GONE);
                    tvDescription.setVisibility(View.GONE);
                }
                break;
            case Constant.WAITING_RESPONSE:
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.waiting_for_vetification_from_store));
                btnCancel.setVisibility(View.VISIBLE);
                llApply.setVisibility(View.GONE);
                llDone.setVisibility(View.GONE);
                break;
            case Constant.BE_CANCELED_RESPONSE:
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.can_not_apply_this_request));
                llApply.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                llDone.setVisibility(View.GONE);
                break;
            case Constant.ACCEPTED_RESPONSE:
                switch (request.getStatus()) {
                    case Constant.PROCESSING_REQUEST:
                        tvDescription.setVisibility(View.VISIBLE);
                        tvDescription.setText(getString(R.string.request_is_processing));
                        btnCancel.setVisibility(View.VISIBLE);
                        llDone.setVisibility(View.VISIBLE);
                        llApply.setVisibility(View.GONE);
                        break;
                    case Constant.DONE_REQUEST:
                        tvDescription.setVisibility(View.VISIBLE);
                        tvDescription.setText(getString(R.string.waiting_for_vetification_to_be_completed_from_store));
                        llApply.setVisibility(View.GONE);
                        btnCancel.setVisibility(View.GONE);
                        llDone.setVisibility(View.GONE);
                        break;
                    case Constant.COMPLETED_REQUEST:
                        tvDescription.setVisibility(View.VISIBLE);
                        tvDescription.setText(getString(R.string.request_is_completed));
                        llApply.setVisibility(View.GONE);
                        btnCancel.setVisibility(View.GONE);
                        llDone.setVisibility(View.GONE);
                        break;
                }
                break;
        }

//        switch (request.getStatus()){
//
//            case Constant.NEW_REQUEST:
////                switch (response.getStatus()){
////                    case Constant.WAITING_REQUEST:
////                        break;
////                    default:
////                        llApply.setVisibility(View.VISIBLE);
////                        break;
////                }
////                break;
//
//            case  Constant.WAITING_REQUEST:
//                switch (response.getStatus()){
//                    case Constant.NEW_RESPONSE:
//                        tvDescription.setText(getString(R.string.can_not_apply_this_request));
//                        break;
//                    default:
//                        tvDescription.setText(getString(R.string.waiting_for_vetification_from_store));
//                        btnCancel.setVisibility(View.VISIBLE);
//                        break;
//                }
//                break;
//            case Constant.PROCESSING_REQUEST:
//                switch (response.getStatus()){
//                    case Constant.NEW_RESPONSE:
//                        tvDescription.setText(getString(R.string.can_not_apply_this_request));
//                        break;
//                    default:
//                        tvDescription.setText(getString(R.string.request_is_processing));
//                        btnCancel.setVisibility(View.VISIBLE);
//                        llDone.setVisibility(View.VISIBLE);
//                        break;
//                }
//                break;
//            case Constant.DONE_REQUEST:
//                switch (response.getStatus()){
//                    case Constant.NEW_RESPONSE:
//                        tvDescription.setText(getString(R.string.can_not_apply_this_request));
//                        break;
//                    default:
//                        tvDescription.setText(getString(R.string.waiting_for_vetification_to_be_completed_from_store));
//                        break;
//                }
//                break;
//            case Constant.COMPLETED_REQUEST:
//                switch (response.getStatus()){
//                    case Constant.NEW_RESPONSE:
//                        tvDescription.setText(getString(R.string.can_not_apply_this_request));
//                        break;
//                    default:
//                        tvDescription.setText(getString(R.string.request_is_completed));
//                        break;
//                }
//                break;
//            case Constant.CANCELED_REQUEST:
//                tvDescription.setText(getString(R.string.request_is_canceled));
//                break;
//        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        if (request != null && store != null) {
            updateMap();
        }
        // Add a marker in Sydney and move the camera
//        googleMap.animateCamera(CameraUpdateFactory
//                .newCameraPosition(cameraPosition));
    }

    private void updateMap() {

        cameraPosition = new CameraPosition.Builder()
                .target(new LatLng((request.getLatitude() + location.getLatitude()) / 2,
                        (request.getLongitude() + location.getLongitude()) / 2))
                .zoom(12).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.store));
        mMap.addMarker(new MarkerOptions().position(new LatLng(request.getLatitude(), request.getLongitude())))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.destination));
    }

    private class LoadDetailRequestAsyncTask extends ServiceAsyncTask {

        public LoadDetailRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                try {

                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(data);
                    JSONObject requestJson = jsonObject.getJSONObject(Constant.KEY_REQUEST);
                    request = gson.fromJson(requestJson.toString(), Request.class);

                    JSONObject storeJson = jsonObject.getJSONObject(Constant.KEY_STORE);
                    store = gson.fromJson(storeJson.toString(), Store.class);

                    JSONObject locationJson = jsonObject.getJSONObject(Constant.KEY_LOCATION);
                    location = gson.fromJson(locationJson.toString(), Location.class);


                    JSONObject responseJson = jsonObject.getJSONObject(Constant.KEY_RESPONSE);
                    response = gson.fromJson(responseJson.toString(), Response.class);
                    if (response.getRequestId() == 0) {
                        response.setStatus(Constant.NEW_RESPONSE);
                    }

//                    if (jsonObject.has(Constant.KEY_RESPONSE)) {
//                        JSONObject responseJson = jsonObject.getJSONObject(Constant.KEY_RESPONSE);
//                        response = gson.fromJson(responseJson.toString(), Response.class);
//                    }
                    updateUI();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }

    private void pushShipperCancelAcceptedResponseNotification(){
        JSONObject cancelResponseNotification = new JSONObject();
        try{
            cancelResponseNotification.put(Constant.KEY_SHIPPER_NAME, ProjectManagement.shipper.getName());
            cancelResponseNotification.put(Constant.KEY_SHIPPER_ID, ProjectManagement.shipper.getId());
            cancelResponseNotification.put(Constant.KEY_STORE_NAME, store.getName());
            cancelResponseNotification.put(Constant.KEY_STORE_ID, store.getId());
            cancelResponseNotification.put(Constant.KEY_REQUEST_ID, request.getId());
        }catch(JSONException e){
            e.printStackTrace();
        }
        ProjectManagement.socketConnection.getSocket().emit(SocketConnection.KEY_SHIPPER_CANCEL_ACCEPTED_RESPONSE_PORT, cancelResponseNotification);
    }

    private void pushShipperApplyRequestNotification(){
        JSONObject applyingNotification = new JSONObject();
        try{
            applyingNotification.put(Constant.KEY_SHIPPER_NAME, ProjectManagement.shipper.getName());
            applyingNotification.put(Constant.KEY_SHIPPER_ID, ProjectManagement.shipper.getId());
            applyingNotification.put(Constant.KEY_STORE_NAME, store.getName());
            applyingNotification.put(Constant.KEY_STORE_ID, store.getId());
            applyingNotification.put(Constant.KEY_REQUEST_ID, request.getId());
        }catch(JSONException e){
            e.printStackTrace();
        }
        ProjectManagement.socketConnection.getSocket().emit(SocketConnection.KEY_SHIPPER_APPLY_REQUEST_PORT, applyingNotification);
    }

    private void pushShipperRequireToCompleteRequestNotification(){
        JSONObject requirementNotification = new JSONObject();
        try{
            requirementNotification.put(Constant.KEY_SHIPPER_NAME, ProjectManagement.shipper.getName());
            requirementNotification.put(Constant.KEY_SHIPPER_ID, ProjectManagement.shipper.getId());
            requirementNotification.put(Constant.KEY_STORE_NAME, store.getName());
            requirementNotification.put(Constant.KEY_STORE_ID, store.getId());
            requirementNotification.put(Constant.KEY_REQUEST_ID, request.getId());
        }catch(JSONException e){
            e.printStackTrace();
        }
        ProjectManagement.socketConnection.getSocket().emit(SocketConnection.KEY_SHIPPER_REQUIRE_CONFIRM_REQUEST_PORT, requirementNotification);
    }
    private class CancelRequestAsyncTask extends ServiceAsyncTask {

        public CancelRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                loadData();
            } else {
                Toast.makeText(SPDetailRequestActivity.this, getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ApplyRequestAsyncTask extends ServiceAsyncTask {

        public ApplyRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                loadData();
            } else {
                Toast.makeText(SPDetailRequestActivity.this, getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class CompleteRequestAsyncTask extends ServiceAsyncTask {

        public CompleteRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                loadData();
            } else {
                Toast.makeText(SPDetailRequestActivity.this, getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
            }
        }
    }
}

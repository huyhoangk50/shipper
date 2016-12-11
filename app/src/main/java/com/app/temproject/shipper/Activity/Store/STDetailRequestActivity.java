package com.app.temproject.shipper.Activity.Store;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Activity.Shipper.SPDetailRequestActivity;
import com.app.temproject.shipper.Fragment.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.Object.Location;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.Object.Response;
import com.app.temproject.shipper.Object.Store;
import com.app.temproject.shipper.ProjectVariable.Constant;
import com.app.temproject.shipper.ProjectVariable.ProjectManagement;
import com.app.temproject.shipper.R;
import com.app.temproject.shipper.ServiceAsyncTask;
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

public class STDetailRequestActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private WorkaroundMapFragment workaroundMapFragment;
    private CameraPosition cameraPosition;

    private Toolbar toolbar;
    private TextView tvProductName;
    private TextView tvStoreName;
    private TextView tvPrice;
    private TextView tvDeposit;
    private TextView tvStartTime;
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
        setContentView(R.layout.st_activity_detail_request);

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
        tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
        tvDestination = (TextView) findViewById(R.id.tvCustomerPlace);
        tvCustomerPhone = (TextView) findViewById(R.id.tvCustomerPhone);
        svDetailRequest = (ScrollView) findViewById(R.id.svDetailRequest);
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder warningDialog = new AlertDialog.Builder(STDetailRequestActivity.this);
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
                        jsonObject.addProperty(Constant.KEY_STORE_ID, ProjectManagement.store.getId());
                        jsonObject.addProperty(Constant.KEY_REQUEST_ID, requestId);
                        new STDetailRequestActivity.CancelRequestAsyncTask(STDetailRequestActivity.this).execute(ProjectManagement.urlStCancelRequest, Constant.POST_METHOD, jsonObject.toString());
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
                .target(new LatLng((request.getLatitude() + store.getLatitude()) / 2,
                        (request.getLongitude() + store.getLongitude()) / 2))
                .zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.store));
        mMap.addMarker(new MarkerOptions().position(new LatLng(request.getLatitude(), request.getLongitude())))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.destination));
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
        tvCustomerPhone.setText(request.getPhoneNumber());

        if (mMap != null) {
            updateMap();
        }

        switch (request.getStatus()){
            case Constant.NEW_REQUEST:
            case Constant.WAITING_REQUEST:
                break;
            case Constant.PROCESSING_REQUEST:
                break;
            case Constant.COMPLETED_REQUEST:
                break;
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
                tvDescription.setText(getString(R.string.waiting_for_vetification_from_store));
                btnCancel.setVisibility(View.VISIBLE);
                llApply.setVisibility(View.GONE);
                llDone.setVisibility(View.GONE);
                break;
            case Constant.BE_CANCELED_RESPONSE:
                tvDescription.setText(getString(R.string.can_not_apply_this_request));
                llApply.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                llDone.setVisibility(View.GONE);
                break;
            case Constant.ACCEPTED_RESPONSE:
                switch (request.getStatus()) {
                    case Constant.PROCESSING_REQUEST:
                        tvDescription.setText(getString(R.string.request_is_processing));
                        btnCancel.setVisibility(View.VISIBLE);
                        llDone.setVisibility(View.VISIBLE);
                        llApply.setVisibility(View.GONE);
                        break;
                    case Constant.DONE_REQUEST:
                        tvDescription.setText(getString(R.string.waiting_for_vetification_to_be_completed_from_store));
                        llApply.setVisibility(View.GONE);
                        btnCancel.setVisibility(View.GONE);
                        llDone.setVisibility(View.GONE);
                        break;
                    case Constant.COMPLETED_REQUEST:
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

    private class CancelRequestAsyncTask extends ServiceAsyncTask {

        public CancelRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                loadData();
            } else {
                Toast.makeText(STDetailRequestActivity.this, getString(R.string.please_try_again), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void loadData() {

    }
}

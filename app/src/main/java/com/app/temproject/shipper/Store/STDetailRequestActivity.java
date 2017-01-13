package com.app.temproject.shipper.Store;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Libs.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.Libs.TimeProcessing;
import com.app.temproject.shipper.Object.Location;
import com.app.temproject.shipper.Object.Request;
import com.app.temproject.shipper.Object.Shipper;
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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class STDetailRequestActivity extends AppCompatActivity implements OnMapReadyCallback, OnAcceptShipperListener, OnCompleteRequestListener {

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
    private TextView tvStorePlace;
    private TextView tvCustomerPhone;
    private ScrollView svDetailRequest;
    private Button btnCancel;
    private TextView tvDescription;
    private RecyclerView rcvShipper;
    private BaseShipperAdapter baseShipperAdapter;

    private Location location;
    private Store store;
    private Request request;
    private int requestId;
    private int requestStatus;
    private int rating;
    private ArrayList<Shipper> shippers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_activity_detail_request);
        Intent intent = getIntent();
        requestId = intent.getIntExtra(Constant.KEY_REQUEST_ID, 0);
        requestStatus = intent.getIntExtra(Constant.KEY_REQUEST_STATUS, 0);
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
        tvStorePlace = (TextView) findViewById(R.id.tvStorePlace);
        tvCustomerPhone = (TextView) findViewById(R.id.tvCustomerPhone);
        svDetailRequest = (ScrollView) findViewById(R.id.svDetailRequest);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        rcvShipper = (RecyclerView) findViewById(R.id.rcvShipper);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        setSupportActionBar(toolbar);
        if (mMap == null) {
            workaroundMapFragment = ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapStDetailRequest));
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
                final AlertDialog.Builder warningDialog = new AlertDialog.Builder(STDetailRequestActivity.this);
                warningDialog.setIcon(R.drawable.delete);
                if (request.getStatus() == Constant.PROCESSING_REQUEST) {
                    warningDialog.setMessage(R.string.warninng_about_judgement);
                } else {
                    warningDialog.setMessage(R.string.really_want_to_cancel);
                }
                warningDialog.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty(Constant.KEY_STORE_ID, ProjectManagement.store.getId());
//                        jsonObject.addProperty(Constant.KEY_REQUEST_ID, requestId);
                        new CancelRequestAsyncTask(STDetailRequestActivity.this).execute(ProjectManagement.urlStCancelRequest + request.getId(),
                                Constant.PUT_METHOD, jsonObject.toString());
                        if(shippers.size()>0){
                            pushStoreCanceledRequestNotification(request);
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
        tvStartTime.setText(TimeProcessing.convertTime(request.getStartTime()));
        tvEndTime.setText(TimeProcessing.convertTime(request.getEndTime()));
        tvCustomerName.setText(request.getCustomerName());
        tvDestination.setText(request.getDestination());
        tvStorePlace.setText(location.getStreet() + " - " + location.getDistrict() + " - " + location.getCity());
        tvCustomerPhone.setText(request.getPhoneNumber());

        if (shippers != null) {
            rcvShipper.setLayoutManager(new LinearLayoutManager(STDetailRequestActivity.this));
            baseShipperAdapter = new BaseShipperAdapter(STDetailRequestActivity.this, shippers, request.getStatus(), this, this);
            rcvShipper.setAdapter(baseShipperAdapter);
        }

        switch (request.getStatus()) {
            case Constant.NEW_REQUEST:
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.no_shipper_accept));
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case Constant.WAITING_REQUEST:
                tvDescription.setVisibility(View.GONE);
                btnCancel.setVisibility(View.VISIBLE);
                break;
            case Constant.PROCESSING_REQUEST:
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.request_is_processing));
                btnCancel.setVisibility(View.GONE);
                break;
            case Constant.COMPLETED_REQUEST:
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.request_is_completed));
                btnCancel.setVisibility(View.GONE);
                break;
            case Constant.DONE_REQUEST:
                tvDescription.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.GONE);
                tvDescription.setText(getString(R.string.shipper_require_confirm_completion));
                break;
            case Constant.CANCELED_REQUEST:
                btnCancel.setVisibility(View.GONE);
                tvDescription.setVisibility(View.VISIBLE);
                tvDescription.setText(getString(R.string.request_is_canceled));
                break;
        }
        if (mMap != null) {
            updateMap();
        }

    }

    @Override
    public void onAcceptShipper(Shipper shipper) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.KEY_SHIPPER_ID, shipper.getId());
        jsonObject.addProperty(Constant.KEY_REQUEST_ID, request.getId());
        new AcceptShipperAsyncTask(STDetailRequestActivity.this).execute(ProjectManagement.urlStAcceptShipper, Constant.POST_METHOD, jsonObject.toString());
        pushAcceptedNotifications(shipper);
    }

    @Override
    public void onCompleteRequest(final Shipper shipper) {
        new AlertDialog.Builder(STDetailRequestActivity.this)
                .setIcon(R.drawable.finish)
                .setTitle(R.string.finish_request)
                .setMessage(R.string.really_finish_request)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final Dialog ratingDialog = new Dialog(STDetailRequestActivity.this);
                        ratingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        ratingDialog.setContentView(R.layout.sp_rating_dialog);
                        Button btnSubmitRating = (Button) ratingDialog.findViewById(R.id.btnSubmitRating);
                        Button btnCancelRating = (Button) ratingDialog.findViewById(R.id.btnCancelRating);
                        final RatingBar rbRating = (RatingBar) ratingDialog.findViewById(R.id.ratingBar);

                        ratingDialog.show();
                        pushCompletedNotification(shipper);
                        btnSubmitRating.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                rating = (int) rbRating.getRating();
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty(Constant.KEY_SHIPPER_ID, shippers.get(0).getId());
                                jsonObject.addProperty(Constant.KEY_NEW_RATING, rating);
                                new CompleteRequestAsyncTask(STDetailRequestActivity.this).execute(ProjectManagement.urlStConfirmCompletedRequest + request.getId(), Constant.PUT_METHOD, jsonObject.toString());
                                ratingDialog.dismiss();
                            }

                        });

                        btnCancelRating.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                rating = 0;
                                JsonObject jsonObject = new JsonObject();
                                jsonObject.addProperty(Constant.KEY_SHIPPER_ID, shippers.get(0).getId());
                                jsonObject.addProperty(Constant.KEY_NEW_RATING, rating);
                                new CompleteRequestAsyncTask(STDetailRequestActivity.this).execute(ProjectManagement.urlStConfirmCompletedRequest, Constant.POST_METHOD, jsonObject.toString());
                                ratingDialog.dismiss();
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

    private class CompleteRequestAsyncTask extends ServiceAsyncTask {

        public CompleteRequestAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                Toast.makeText(STDetailRequestActivity.this, getString(R.string.complete_request), Toast.LENGTH_LONG).show();
                requestStatus = Constant.COMPLETED_REQUEST;
                if (ProjectManagement.socketConnection != null) {

                }
                new LoadDetailRequestAsyncTask(STDetailRequestActivity.this).execute(ProjectManagement.urlStLoadDetailRequest + requestId, Constant.GET_METHOD);

            }
        }
    }

    private class AcceptShipperAsyncTask extends ServiceAsyncTask {

        public AcceptShipperAsyncTask(Activity activity) {
            super(activity);
        }

        @Override
        protected void processData(boolean error, String message, String data) {
            if (!error) {
                Toast.makeText(STDetailRequestActivity.this, getString(R.string.accept_shipper_successfully), Toast.LENGTH_LONG).show();
                requestStatus = Constant.PROCESSING_REQUEST;
                new LoadDetailRequestAsyncTask(STDetailRequestActivity.this).execute(ProjectManagement.urlStLoadDetailRequest + requestId, Constant.GET_METHOD);
            } else {

            }
        }
    }

    private void pushAcceptedNotifications(Shipper shipper) {

        JSONObject acceptedNotification = new JSONObject();
        try {
            acceptedNotification.put(Constant.KEY_SHIPPER_NAME, shipper.getName());
            acceptedNotification.put(Constant.KEY_SHIPPER_ID, shipper.getId());
            acceptedNotification.put(Constant.KEY_STORE_NAME, store.getName());
            acceptedNotification.put(Constant.KEY_STORE_ID, store.getId());
            acceptedNotification.put(Constant.KEY_REQUEST_ID, request.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ProjectManagement.socketConnection.getSocket().emit(SocketConnection.KEY_STORE_ACCEPT_SHIPPER_PORT, acceptedNotification);
    }

    private void pushCompletedNotification(Shipper shipper) {
        JSONObject completedNotification = new JSONObject();
        try {
            completedNotification.put(Constant.KEY_SHIPPER_NAME, shipper.getName());
            completedNotification.put(Constant.KEY_SHIPPER_ID, shipper.getId());
            completedNotification.put(Constant.KEY_STORE_NAME, store.getName());
            completedNotification.put(Constant.KEY_STORE_ID, store.getId());
            completedNotification.put(Constant.KEY_REQUEST_ID, request.getId());
            ProjectManagement.socketConnection.getSocket().emit(SocketConnection.KEY_STORE_CONFIRM_COMPLETED_REQUEST_PORT, completedNotification);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void pushStoreCanceledRequestNotification(Request mRequest) {
        for (Shipper shipper : shippers) {
            JSONObject canceledRequestNotification = new JSONObject();
            try {
                canceledRequestNotification.put(Constant.KEY_SHIPPER_NAME, shipper.getName());
                canceledRequestNotification.put(Constant.KEY_SHIPPER_ID, shipper.getId());
                canceledRequestNotification.put(Constant.KEY_STORE_NAME, store.getName());
                canceledRequestNotification.put(Constant.KEY_STORE_ID, store.getId());
                canceledRequestNotification.put(Constant.KEY_REQUEST_ID, mRequest.getId());
                ProjectManagement.socketConnection.getSocket().emit(SocketConnection.KEY_STORE_CANCEL_ACCEPTED_REQUEST_PORT, canceledRequestNotification);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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

                    JSONArray shippersJson = jsonObject.getJSONArray(Constant.KEY_SHIPPER);
                    Type token = new TypeToken<ArrayList<Shipper>>() {
                    }.getType();
                    shippers = (new Gson().fromJson(shippersJson.toString(), token));


//                    if (jsonObject.has(Constant.KEY_RESPONSE)) {
//                        JSONObject responseJson = jsonObject.getJSONObject(Constant.KEY_RESPONSE);
//                        response = gson.fromJson(responseJson.toString(), Response.class);
//                    }
                    updateUI();

                } catch (Exception e) {
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
        new LoadDetailRequestAsyncTask(this).execute(ProjectManagement.urlStLoadDetailRequest + requestId, Constant.GET_METHOD);

    }
}

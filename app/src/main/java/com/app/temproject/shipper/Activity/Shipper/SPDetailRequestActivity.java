package com.app.temproject.shipper.Activity.Shipper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.temproject.shipper.Fragment.Maps.WorkaroundMapFragment;
import com.app.temproject.shipper.Object.Request;
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
import com.google.gson.JsonObject;

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
    private TextView tvEndTime;
    private TextView tvCustomerName;
    private TextView tvDestination;
    private TextView tvCustomerPhone;
    private ScrollView svDetailRequest;
    private LinearLayout llApply;
    private Button btnDelete;


    private Store store;
    private Request request;
    private int requestId;

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
        tvCustomerName = (TextView) findViewById(R.id.tvCustomerName);
        tvDestination = (TextView) findViewById(R.id.tvCustomerPlace);
        tvCustomerPhone = (TextView) findViewById(R.id.tvCustomerPhone);
        svDetailRequest = (ScrollView) findViewById(R.id.svDetailRequest);

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
    }

    private void loadData() {
//        String keys[] = {Constant.KEY_USER_ID, Constant.KEY_PASSWORD, Constant.KEY_STATUS};
//        String values[] = {ProjectManagement.shipper.getId() + "", ProjectManagement.shipper.getPassword(), request.getId() + ""};
//        String httpBody = HttpPacketProcessing.createBodyOfPacket(keys, values);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.KEY_USER_ID, ProjectManagement.shipper.getId());
        jsonObject.addProperty(Constant.KEY_PASSWORD, ProjectManagement.shipper.getPassword());
        jsonObject.addProperty(Constant.KEY_STATUS, requestId);

//        new LoadDetailRequestAsyncTask(this).execute(Constant.urlSpLoadDetailRequest, Constant.POST_METHOD, jsonObject.toString());

        fakeData();
        updateUI();
    }

    private void fakeData(){
        request = new Request(1, 1000000, 5, "22/12/2016 08:30", "22/12/2016 : 17:30", 5, "Số 5 cù chính lan hà nội", 20000, 2, "Đồng hồ smart watch", "09876543332", 105.8488915,21.0024017 , 1, "12/12/2016 08:30", "12/12/2016 08:30");
        request.setStoreName("Cửa hàng đồng hồ Huy Hoàng");
        request.setStorePosition("Số 5 Minh khai hà nội");
        request.setCustomerName("Nguyễn văn sang");
        Random random = new Random();
        int status = random.nextInt(4);
        request.setStatus(status);
        request.setStatus(Constant.PENDING_STATUS);

        store = new Store(1, "123@gmail.com", "123@gmail.com", "Nguyễn Huy Hoàng", "35425343",
                "Thời trang", "số 3 tân mai", "Hoàng Mai", "Hà Nội",105.8474236 ,20.989865 , "Việt Nam");
    }

    private void updateUI(){
        tvProductName.setText(request.getProductName());
        tvStoreName.setText(store.getName());
        tvPrice.setText(request.getPrice() + "");
        tvDeposit.setText(request.getDeposit() + "");
        tvStartTime.setText(request.getStartTime());
        tvEndTime.setText(request.getEndTime());
        tvCustomerName.setText(request.getCustomerName());
        tvDestination.setText(request.getDestination());
        tvCustomerPhone.setText(request.getPhoneNumber());

        if(mMap!= null){
            updateMap();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        if(request!=null && store!=null){
            updateMap();
        }
        // Add a marker in Sydney and move the camera
//        googleMap.animateCamera(CameraUpdateFactory
//                .newCameraPosition(cameraPosition));
    }

    private void updateMap(){

        cameraPosition = new CameraPosition.Builder()
                .target(new LatLng((request.getLatitude() + store.getLatitude() )/2,
                        (request.getLongitude() + store.getLongitude() )/2))
                .zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(store.getLatitude(), store.getLongitude())))
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
            if(!error){

            } else {
//                Toast.makeText(SPDetailRequestActivity)
            }
        }

    }
}

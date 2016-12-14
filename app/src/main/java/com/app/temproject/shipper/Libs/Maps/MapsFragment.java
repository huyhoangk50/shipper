package com.app.temproject.shipper.Libs.Maps;

import android.Manifest;

import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.temproject.shipper.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 01/12/2016.
 */

public class MapsFragment extends Fragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private WorkaroundMapFragment workaroundMapFragment;
//    MapView mMapView;
    private Button btnSearch;
    private EditText etSearch;
    CameraPosition cameraPosition;
    private double latitude;
    private double longitude;
    private WorkaroundMapFragment.OnTouchListener listener;

    private View rootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.maps_layout, container, false);

        initView();
        setEvent();
        return rootView;
    }

    private void initView(){

        btnSearch = (Button) rootView.findViewById(R.id.btnSearch);
        etSearch = (EditText) rootView.findViewById(R.id.etSearch);
        if (mMap == null) {
            workaroundMapFragment = ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
            workaroundMapFragment.getMapAsync(this);
        }
        if(listener == null){
            ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                    .setListener(new WorkaroundMapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {

                        }
                    });
        }
    }

    private void setEvent(){
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder = new Geocoder(getActivity());
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocationName(etSearch.getText().toString(), 5);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        mMap.clear();
                        latitude = address.getLatitude();
                        longitude = address.getLongitude();

                        cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(latitude, longitude)).zoom(15).build();
                        mMap.animateCamera(CameraUpdateFactory
                                .newCameraPosition(cameraPosition));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));

                        loadData(latitude, longitude);
                        updateUI(mMap);
                    } else {
                        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                        adb.setTitle("Google Map");
                        adb.setMessage("Không có kết quả tìm kiếm!!");
                        adb.setPositiveButton("Đóng", null);
                        adb.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setListener(WorkaroundMapFragment.OnTouchListener listener) {
        workaroundMapFragment.setListener(listener);
    }
    @Override
    public void onResume() {
        super.onResume();
//        mMapView.onResume();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        return false;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        // Add a marker in Sydney and move the camera
        cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(20.9817847, 105.8554991)).zoom(20).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition arg0) {
                googleMap.clear();
                latitude = arg0.target.latitude;
                longitude = arg0.target.longitude;
                googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));

                loadData(latitude, longitude);
                updateUI(googleMap);
            }
        });
    }

    protected void loadData(double latitude, double longitude) {

    }

    protected void updateUI(GoogleMap googleMap) {

    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
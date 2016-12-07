package com.app.temproject.shipper.ProjectVariable;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Admin on 30/11/2016.
 */
public class MyLocationListener implements LocationListener {


    private String longitude;
    private String latitude;
    @Override
    public void onLocationChanged(Location loc) {
        longitude = "Longitude: " + loc.getLongitude();
        Log.v("long", longitude);
        latitude = "Latitude: " + loc.getLatitude();
        Log.v("lat", latitude);

    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}

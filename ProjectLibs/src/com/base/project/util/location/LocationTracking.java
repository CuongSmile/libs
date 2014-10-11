package com.base.project.util.location;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

/**
 * A process get current location by time Tracking
 * <p>
 * Usage: Call LocationTracking.getInstance().reConnect(); and implement code to
 * {@link #processUpdateLocation()}
 * 
 * 
 * <p>
 * Permission require: android.permission.ACCESS_COARSE_LOCATION" <br/>
 * android.permission.ACCESS_FINE_LOCATION" <br/>
 * <br/>
 * android.permission.ACCESS_NETWORK_STATE"<br/>
 * <br/>
 * android.permission.ACCESS_WIFI_STATE"<br/>
 * <p>
 * And import Google play Service lib.
 * 
 * 
 * @author TND Oct 17, 2013
 */
public class LocationTracking implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private static final String LOG_TAG = "LocationTracking";
    // hard code time tracking
    // private static int TIME_LOCATION_TRACKING = 5 * 60 * 1000;
    private static final int LOCATION_UPDATE_TIME = 1 * 60 * 1000;

    private static LocationTracking mInstance;

    private LocationTracking() {}

    public static LocationTracking getInstance() {
        if (mInstance == null) {
            mInstance = new LocationTracking();
        }
        return mInstance;
    }

    public void reConnect(Context pContext) {
        if (pContext != null) {
            if (mLocationClient != null && mLocationClient.isConnected()) {
                mLocationClient.disconnect();
            }
            this.setUpLocationClientIfNeeded(pContext);
        }
    }

    private boolean threadRunning = true;
    private Location mCurrentLocation;
    private LocationClient mLocationClient;
    // These settings are the same as the settings for the map.
    // They will in fact give you updates at
    // the maximal rates currently possible.
    // for each LOCATION_UPDATE_TIME time will be invoker onLocationChanged to be get current
    // location
    private static final LocationRequest REQUEST = LocationRequest.create().setInterval(LOCATION_UPDATE_TIME)
            .setFastestInterval(16).setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    private void setUpLocationClientIfNeeded(Context pContext) {
        if (mLocationClient == null) {
             mLocationClient = new LocationClient(pContext, this, this);
            mLocationClient.connect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("LOCATION", "onLocationChanged");
        mCurrentLocation = location;
        this.processUpdateLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {}

    @Override
    public void onConnected(Bundle arg0) {
        Log.d("LOCATION", "onConnected");

        // if (mCurrentLocation == null) {
        // // 1. using network and gps provider to get location
        // mCurrentLocation = LocationUtil.getCurrentLocation(Application.getAppContext());
        // }

        if (mCurrentLocation == null) {
            // 2. using Location Client request update location get location
            mLocationClient.requestLocationUpdates(REQUEST, this); // LocationListener
        }
        // // 3. using location Client
        // mCurrentLocation = mLocationClient.getLastLocation();
        // this.processUpdateLocation();
    }

    private boolean threadStarted = false;

    /***
     * TODO: implement code to this function, call after get current location successful.
     ***/
    private void processUpdateLocation() {}

    @Override
    public void onDisconnected() {}


    private void onDestroy() {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            mLocationClient.removeLocationUpdates(LocationTracking.this);
            mLocationClient.disconnect();
        }
        mCurrentLocation = null;
        mInstance = null;
    }
}

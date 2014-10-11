/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.base.project.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.base.project.util.connect.server.HttpUtils;

/**
 * receiver handle status when status network change Using action of intent filter to register new
 * IntentFilter( ConnectivityManager.CONNECTIVITY_ACTION)
 * 
 * @author CuongPG Jan 11, 2014
 */
public abstract class StatusNetworkReceiver extends BroadcastReceiver {

    public interface onHandleStatusNetworkConnection {
        public void onHandleConnectNetwork(boolean status);

    }

//    private onHandleStatusNetworkConnection mHandleInternetConnection;
//
//    public void setHandleInternetConnection(onHandleStatusNetworkConnection mInternetConnection) {
//        this.mHandleInternetConnection = mInternetConnection;
//    }

    @Override
    public final void onReceive(Context pContext, Intent intent) {
        ConnectivityManager conn =
                (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        // Checks the user prefs and the network connection. Based on the result, decides whether
        // to refresh the display or keep the current display.
        // If the userpref is Wi-Fi only, checks to see if the device has a Wi-Fi connection.
        // if ( networkInfo != null && networkInfo. ) {
        // Log.e("Netword", "netword");
        // mHandleInternetConnection.onHandleConnectNetwork(true);
        //
        // } else {
        // Log.e("Netword", "no netword");
        // if(HttpUtils.checkStatusNetwork(pContext)==true){
        // mHandleInternetConnection.onHandleConnectNetwork(true);
        // } else {
        // mHandleInternetConnection.onHandleConnectNetwork(false);
        // }
        // }

        if (networkInfo != null) {
            if (networkInfo.isAvailable()) {
                this.onConnected();
            } else if (!networkInfo.isAvailable()) {
                this.onDisconnected();
            }
        } else {
            if (HttpUtils.checkStatusNetwork(pContext) == true) {
                this.onConnected();
            } else {
                this.onDisconnected();
            }
        }

    }

    protected abstract void onDisconnected();

    protected abstract void onConnected();
    // private String getNetworkStateString(NetworkInfo.State state){
    // String stateString = "Unknown";
    //
    // switch(state)
    // {
    // case CONNECTED: stateString = CONNECTED; break;
    // case CONNECTING: stateString = CONNECTING; break;
    // case DISCONNECTED: stateString = DISCONNECTED; break;
    // case DISCONNECTING: stateString = DISCONNECTING; break;
    // case SUSPENDED: stateString = SUSPENDED; break;
    // default: stateString = UNKNOWN; break;
    // }
    //
    // return stateString;
    // }

}

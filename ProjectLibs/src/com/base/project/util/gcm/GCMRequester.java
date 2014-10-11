package com.base.project.util.gcm;


import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

import com.base.project.util.Constant;
import com.base.project.util.JsonUtil;
import com.base.project.util.connect.server.HttpUtils;
import com.base.project.util.connect.server.IURLDefine;
import com.base.project.util.sharereference.SharePreferencesLoader;
import com.base.project.util.task.BaseTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * @author CuongPG Jan 23, 2014
 */
public class GCMRequester {
    
    
    public interface IHandleRegisterGCMKey{
        public void onRegisterSuccess();
        public void onRegisterFail();
    }
    private static GCMRequester instance = null;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String PROPERTY_APP_VERSION = "appVersion";
    public static final String PROPERTY_REG_ID = "registration_id";
    // sender id of project
    public static final String SENDER_ID ="182387292657";
    private final String TAG = getClass().getSimpleName().toString();

    private GCMRequester() {}

    public static synchronized GCMRequester getInstance() {
        if (instance == null) {
            instance = new GCMRequester();
        }
        return instance;
    }

    public void onDestroy() {
        instance = null;
    }

    public void register(final Context pContext,final String userID, final IHandleRegisterGCMKey listener) {
    	if (pContext != null) {
    	BaseTask<Void, String> task = new BaseTask<Void, String>() {
			
			@Override
			public void onTaskPostExecute(String result) {
			    if(listener != null){
			     listener.onRegisterSuccess();   
			    }
			}

            @Override
            public String onTaskRunning(Void... params) throws TimeoutException, SocketException, IOException {

                String mRegistrationId ="";
//              if(checkPlayServices(pContext)){
                mRegistrationId = getRegistrationId(pContext);
                if (mRegistrationId != null && !mRegistrationId.isEmpty()) {
                        registerWithOwnServer(pContext, mRegistrationId,userID);
                } else if (mRegistrationId != null && mRegistrationId.isEmpty()) {
                    mRegistrationId = GoogleCloudMessaging.getInstance(pContext).register(SENDER_ID);
                    saveRegistrationID(pContext, mRegistrationId);
                    registerWithOwnServer(pContext, mRegistrationId,userID);
                }
//          }
            return mRegistrationId;
        
            }
            @Override
            protected void onNoNetwork(Context pContext) {
                super.onNoNetwork(pContext);
                if(listener != null){
                    listener.onRegisterFail();   
                   }
            }
            @Override
            protected void onTimeoutException() {
//                super.onTimeoutException();
                if(listener != null){
                    listener.onRegisterFail();   
                   }
            }
            @Override
            protected void onSocketTimeoutException() {
//                super.onSocketTimeoutException();
                if(listener != null){
                    listener.onRegisterFail();   
                   }
                }
            @Override
            protected void onIOException() {
//                super.onIOException();
                if(listener != null){
                    listener.onRegisterFail();   
                   }
            }
		};
		task.execute();
    }
        
        
    }

    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     * 
     * @return registration ID, or empty string if there is no existing registration ID.
     */
	private String getRegistrationId(Context context) {
        // String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        String registrationId = SharePreferencesLoader.getInstance().getValueFromPreferences(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion =
                SharePreferencesLoader.getInstance().getValueFromPreferences(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     * 
     * @param context application's context.
     * @param regId registration ID
     */
    private void saveRegistrationID(Context pContext, String regId) {
        if (pContext != null) {
            int appVersion = getAppVersion(pContext);
//            Log.i(TAG, "Saving regId on app version " + appVersion);
            SharePreferencesLoader.getInstance().saveValueToSharedPreferences(PROPERTY_REG_ID, regId);
            SharePreferencesLoader.getInstance().saveValueToSharedPreferences(PROPERTY_APP_VERSION, appVersion);
        }
    }
    /**
     * Method will send a message to Server
     */
    public void sendMessageToServer(Bundle bundle) {
    }

    public void unregister(final Context pContext) {
    	BaseTask<Void, Void> task =new BaseTask<Void, Void>() {
			
			
			@Override
			public void onTaskPostExecute(Void result) {
				
			}

            @Override
            public Void onTaskRunning(Void... params) throws TimeoutException, SocketException, IOException {
                clearRegistraionID(pContext);
                GoogleCloudMessaging.getInstance(pContext).unregister();
                unregisterWithOwnServer();
                return null;
            }
            @Override
            protected void onNoNetwork(Context pContext) {
//                super.onNoNetwork(pContext);
            }
            
            @Override
            protected void onSocketTimeoutException() {
//                super.onSocketTimeoutException();
            }
            
            @Override
            protected void onTimeoutException() {
//                super.onTimeoutException();
            }
            
            @Override
            protected void onIOException() {
//                super.onIOException();
            }
		};
    	task.execute();
    }
    /**
     * clear registration ID have save in Share reference and other data
     * @param pContext
     */
    private void clearRegistraionID(Context pContext){
        SharePreferencesLoader.getInstance().saveValueToSharedPreferences(PROPERTY_REG_ID, "");
        SharePreferencesLoader.getInstance().saveValueToSharedPreferences(PROPERTY_APP_VERSION, "");
    }

    /**
     * 
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages to a
     * server that echoes back the message using the 'from' address in the message.
     * 
     * 
     * @param pContext
     * @param pRegisterID
     * @return jsonObj
     * @throws TimeoutException 
     * @throws SocketException 
     * @throws IOException
     */
    public void registerWithOwnServer(Context pContext, String pRegisterID,String userID) throws SocketException, TimeoutException, IOException {
//        String contentReceive = null;
//        List<? extends NameValuePair> lstData = CommonUtil.genListParams(new String[]{
//                                    Constant.ID,Constant.DEVICE_TOKENT,Constant.DEVICE_TYPE
//        }, userID,pRegisterID,Constant.DEVICE_TYPE_ANDROID);
//        
//        
//        contentReceive = HttpUtils.postDataWithArgs(IURLDefine.REGISTER_GCM_DEVICE.toString(), lstData);
//        
////        return (ArrayList<BasicNameValuePair>) HttpPostUtil.genListParams(keys, values);
//        Log.e(GCMRequester.class.getSimpleName(), pRegisterID);
//        Log.e(GCMRequester.class.getSimpleName(), contentReceive);
//        if(contentReceive != null){
//        RegisterDeviceTokenModel model = JsonUtil.fromString(contentReceive, RegisterDeviceTokenModel.class);
//        }
//    	
    }

    private void updateTokenId(Context pContext, String pRegisterID) {}

    /**
     * 
     * @param pContext
     * @param pRegisterID
     * @return
     * @throws IOException
     */
    private void unregisterWithOwnServer() {
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * 
     * /** Check the device to make sure it has the Google Play Services APK. If it doesn't, display
     * a dialog that allows users to download the APK from the Google Play Store or enable it in the
     * device's system settings.
     */
    public boolean checkPlayServices(Context pContext) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(pContext);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) pContext,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
            }
            return false;
        }
        return true;
    }
}

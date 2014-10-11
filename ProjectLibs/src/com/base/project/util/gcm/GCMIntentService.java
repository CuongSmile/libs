package com.base.project.util.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * 
 * @author CuongPG
 * May 29, 2014
 */
public class GCMIntentService extends IntentService{
	public static final String NAME = "GCMIntentService";
	public GCMIntentService() {
		super(NAME);
	}
	
	@Override
	protected void onHandleIntent(Intent pIntent) {
	    // Method handle Intnet receive from GCMBroadcastReceiver
        Bundle extras = pIntent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(pIntent);
        
        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                // It's an error.
                GCMManagerMessage.getInstance().onMessageSendError(pIntent);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
            // If it's a regular GCM message, do some work.
                //Because the server deleted some pending messages because they were collapsible.
                GCMManagerMessage.getInstance().onMessageSendError(pIntent);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                GCMManagerMessage.getInstance().onMessageRegular(pIntent);
            }
        }
        // Release the wake lock provided by the GCMBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(pIntent);
    }


}

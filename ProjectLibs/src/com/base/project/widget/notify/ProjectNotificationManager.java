/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.base.project.widget.notify;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.projectlibs.R;

/**
 * @author CuongPG
 * Jan 17, 2014
 */
public class ProjectNotificationManager {
    private final String DEFAULT_TITLE ="Essilor";
    private static ProjectNotificationManager instance;
    private String title;
    private String content;
    private int count = 0;
    
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void showNotification(Context pContext){
        NotificationCompat.Builder buider = new NotificationCompat.Builder(pContext);
        buider.setContentTitle(DEFAULT_TITLE);
        buider.setContentText(this.content);
        buider.setSmallIcon(R.drawable.ic_launcher);
        buider.setAutoCancel(true);

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        buider.setSound(uri);
        
        NotificationManager notificationManager = getNotificationService(pContext);
        if(notificationManager != null){
            notificationManager.notify(count, buider.build());
            count=  count +1;
        }
    }
    
    private NotificationManager getNotificationService(Context pContext) {
        return (NotificationManager) pContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    
    private ProjectNotificationManager() {
    }
    
    public synchronized static ProjectNotificationManager getInstance(){
        if(instance == null){
            instance = new ProjectNotificationManager();
        }
        return instance;
    }
    
    /**
     * 
     */
    public void onDestroy() {
        count = 0;
       instance = null;
       
    }
    
    
}

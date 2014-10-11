package com.base.project.util.gcm;

import android.content.Intent;

/**
 * 
 * @author CuongPG
 * May 29, 2014
 */
public class GCMManagerMessage {
	
	private static GCMManagerMessage instance = null;
	
	private GCMManagerMessage() {
	}
	
	public static synchronized GCMManagerMessage getInstance(){
		if(instance == null){
			instance = new GCMManagerMessage();
		}
		return instance;
	}
	
	public void onDestroy(){
		instance = null;
	}
	
	public void onMessageDeleteByServer(Intent pIntent){
	    
	}
	public void onMessageSendError(Intent pIntent){
	    
	}
	
	public void onMessageRegular(Intent pIntent){
	    String content = pIntent.getStringExtra("message");
	    String conten1 = pIntent.getStringExtra("action");
	    String conten2 = pIntent.getStringExtra("data");
        System.err.println(content);
	}
	
}

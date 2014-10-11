package com.base.project.util.gcm;

import android.content.Intent;

public class GCMManager {
	
	private static GCMManager instance = null;
	
	private final String CODE_PROJECT ="";
	
	private GCMManager() {
	}
	
	public static synchronized GCMManager getInstance(){
		if(instance == null){
			instance = new GCMManager();
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
	    
	}
	
}

package com.base.project.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectlibs.R;

public class ToastUtil {
    
    public static void showLongToast(Context context, int content_id) {
    	if(context != null ){
    		showToast(context, context.getString(content_id), Toast.LENGTH_LONG);
    	}
    }
    
    public static void showLongToast(Context context, String content) {
        showToast(context, content, Toast.LENGTH_LONG);
    }
    
	public static void showToast(Context context, int content_id) {
		showToast(context, context.getString(content_id));
	}
	
	public static void showToast(Context context, String content) {
	    showToast(context, content, Toast.LENGTH_SHORT);
	}
	/**
	 * show toast with default of OS
	 * @param context
	 * @param content
	 * @param duration
	 */
	public static void showToast(Context context, String content, int duration){
		Toast.makeText(context, content,duration).show();
	}
	/**
	 * custom toast                   
	 */
//	public static void showToast(Context context, String content, int duration) {
////		Toast toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
//		Toast toast = new Toast(context);
//		toast.setDuration(duration);
//		TextView tv = new TextView(context);
//		tv.setText(content);
//		Resources res = context.getResources();
//		tv.setTextSize(res.getDimensionPixelSize(R.dimen.normalTextSize));
//		tv.setTextColor(Color.BLACK);
//		tv.setGravity(Gravity.CENTER);
//		tv.setBackgroundResource(R.drawable.bg_content_cell);
//		toast.setView(tv);
//		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.show();
//	}
}

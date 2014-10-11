package com.base.project.util;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * @author CuongPG
 * Sep 20, 2013
 */
public class ScreenDeviceUIUtil {
	public static final void setHtmlText(TextView tv, String source) {
		tv.setText((source == null ? null : Html.fromHtml(source)));
	}
	/**
	 * get and return screen size
	 * @param context
	 * @return Point
	 *********************************************************
	 */
	public static final Point getDisplaySize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
		Point ret = new Point();
		ret.x = dm.widthPixels;//display.getWidth();
		ret.y = dm.heightPixels;//display.getHeight();
		return ret;
	}
	
	public static void hideSoftwareInput(Context context, View v) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(
                 Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
	
}

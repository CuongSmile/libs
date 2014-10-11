/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 * 
 * CustomAdvancedDialogView.java
 * 
 * Created on: Feb 6, 2014 - 9:09:13 AM Author: VanNT
 */
package com.base.project.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author VanNT Feb 6, 2014
 */
public class CustomRelativeLayout extends RelativeLayout {

    private static final String LOG_TAG = CustomRelativeLayout.class.getSimpleName();
    private ViewDispatchTouchEventListener mDispatchTouchEventListener;


    public void registerViewDispatchTouchEventListener(
            ViewDispatchTouchEventListener pDispatchTouchEventListener) {
        this.mDispatchTouchEventListener = pDispatchTouchEventListener;
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param context
     */
    public CustomRelativeLayout(Context context) {
        super(context);
    }

//    final Rect frame = new Rect();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        final View cv = findViewById(R.id.txtKeyWord);
//        cv.getGlobalVisibleRect(frame);
//        if (frame.contains((int) ev.getX(), (int) ev.getY())) {
//            // DebugLog.e(LOG_TAG, "hit");
//        } else {
//            // DebugLog.e(LOG_TAG, "don't hit");
//            Utils.hideSoftKeyboard(cv);
//        }
        if (mDispatchTouchEventListener != null) {
            if (mDispatchTouchEventListener.dispatchTouchEventView(this, ev)) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static interface ViewDispatchTouchEventListener {
        public boolean dispatchTouchEventView(View pView, MotionEvent ev);
    }

}

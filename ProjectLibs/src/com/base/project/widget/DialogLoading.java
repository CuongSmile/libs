/*
 * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.example.projectlibs.R;

/**
 * @author CuongPG
 * Oct 4, 2013
 */
public class DialogLoading extends Dialog {
    /**
     * counter object ref to dialog at the time
     */
    protected volatile int countRef = 0;

    public DialogLoading(Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.loading_dialog_view);

        setCanceledOnTouchOutside(false);
    }
    
   
    @Override
    public final void show() {
        super.show();
    }
    
    @Override
    public final void dismiss() {
        super.dismiss();
    }
    
    public synchronized void controlShowDialog(){
        if(countRef == 0 && !this.isShowing()){
            this.show();
        }
        countRef = countRef + 1;
    }
    
    public synchronized void dismissDialog(){
        countRef = countRef - 1;
        if(countRef <= 0 && this.isShowing()){
            this.dismiss();
            countRef = 0;
        }
    }
}

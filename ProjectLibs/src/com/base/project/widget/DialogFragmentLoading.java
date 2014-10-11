/*
 * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.widget;

import android.app.Dialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.base.project.activity.BaseFragmentActivity;
import com.base.project.dialog.BaseFragmentDialog;
import com.example.projectlibs.R;

/**
 * @author CuongPG Oct 4, 2013
 */
public class DialogFragmentLoading extends BaseFragmentDialog {
    // public DialogLoading(Context context) {
    // super(context);
    //
    // requestWindowFeature(Window.FEATURE_NO_TITLE);
    // getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    //
    // setContentView(R.layout.loading_dialog_view);
    //
    // setCanceledOnTouchOutside(false);
    // }
    // @Override
    // public void onBackPressed() {
    // // super.onBackPressed();
    // }
    /**
     * counter object ref to dialog at the time
     */
    protected int countRef = 0;

    @Override
    protected int getViewContentID() {
        return R.layout.loading_dialog_view;
    }

    @Override
    protected void onCreateDialog(Dialog pDialog, View pViewDialog) {
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setCancelable(false);
    }
    @Deprecated
    public final void show(BaseFragmentActivity fragmentActivity) {
        super.show(fragmentActivity);
    }

    @Override
    @Deprecated
    public  void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    @Deprecated
    public  int show(FragmentTransaction transaction, String tag) {
        return super.show(transaction, tag);
    }

    @Override
    @Deprecated
    public  void dismiss() {
        super.dismiss();
    }
    
    @Override
    @Deprecated
    public  void dismissAllowingStateLoss() {
        super.dismissAllowingStateLoss();
    }
    
    public synchronized void controlShowDialog(BaseFragmentActivity fragmentActivity){
        if(fragmentActivity != null){
            if(countRef == 0){
                this.show(fragmentActivity);
            }
            countRef = countRef + 1;
        }
    }
    
    public synchronized void dismissDialog(){
        countRef = countRef -1 ;
        if(countRef <= 0){
            countRef = 0;
            this.dismissAllowingStateLoss();
        }
    }
}

/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.base.project.dialog;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.projectlibs.R;

/**
 * @author CuongPG
 * Jan 11, 2014
 */
public class DialogNetworkUnavaiable extends BaseFragmentDialog {

    @Override
    protected int getViewContentID() {
        return R.layout.layout_dialog_notification_network_unavaiable;
    }

    @Override
    protected void onCreateDialog(Dialog pDialog, View pViewDialog) {
        pViewDialog.findViewById(R.id.btnOk).setOnClickListener(new  OnClickListener() {
            
            @Override
            public void onClick(View v) {
                DialogNetworkUnavaiable.this.dismissAllowingStateLoss();
            }
        });
    }

}

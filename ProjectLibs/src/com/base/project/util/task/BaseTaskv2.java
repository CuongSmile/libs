/*
 * s * + * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.util.task;

import java.io.IOException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.base.project.activity.BaseFragmentActivity;
import com.base.project.util.connect.server.IURLDefine.STATUS_NETWORK_CONNECT;
import com.base.project.widget.DialogFragmentLoading;


/**
 * @author CuongPG Sep 27, 2013
 */
public abstract class BaseTaskv2<Params, Result> extends AsyncTask<Params, Void, Result> {
    private static String TAG = "BaseTask";
    private DialogFragmentLoading loadingDialog;
    private boolean isShowing = true;
    protected Context mContext;
    protected STATUS_NETWORK_CONNECT status = STATUS_NETWORK_CONNECT.SUCCESS;

    @Override
    protected final void onPostExecute(Result result) {
        this.closeLoadingDialog();
        switch (status) {
            case SUCCESS:
                this.onTaskPostExecute(result);
                break;
            case SOCKET_TIMEOUT:
                this.onSocketTimeoutException();
                break;
            case TIMEOUT:
                this.onTimeoutException();
                break;
            case OTHER:
                this.onIOException();
                break;
            default:
                this.onIOException();
                break;
        }

        this.onDestroy();
        super.onPostExecute(result);
    }

    @Override
    protected final Result doInBackground(Params... params) {
        try {
            return this.onTaskRunning(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected final void onPreExecute() {
        super.onPreExecute();
        this.showLoadingDialog();
        this.onTaskPreRunning();
    }

    public abstract void onTaskPostExecute(Result result);

    public abstract Result onTaskRunning(Params... params) throws IOException;

    protected void onTaskPreRunning() {}

    protected void onDestroy() {
        this.loadingDialog = null;
    }

    private void init() {
            if (this.mContext != null && !(this.mContext instanceof BaseFragmentActivity) &&
                    this.loadingDialog == null && isShowing) {
                this.loadingDialog = this.onCreateDialog();
            }
    }

    protected void showLoadingDialog() {
        if (this.mContext != null && this.mContext instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) mContext).showDialog();
        } else {
        if (loadingDialog != null && this.isShowing) {
            this.loadingDialog.show((BaseFragmentActivity) mContext);
        }
        }
    }

    protected void closeLoadingDialog() {
        if (this.mContext != null && this.mContext instanceof BaseFragmentActivity) {
            ((BaseFragmentActivity) mContext).dismissDialog();
        } else {
        if (loadingDialog != null && loadingDialog.getDialog().isShowing()) {
            this.loadingDialog.dismiss();
        }
        }
    }

    public void run(Context pContext, Params... params) {
        this.mContext = pContext;
        this.init();
        this.execute(params);
    }

    public void run(Context pContext, boolean pShowing, Params... params) {
        this.mContext = pContext;
        this.isShowing = pShowing;
        this.init();
        this.execute(params);
    }

    public Context getContext() {
        return this.mContext;
    }

    protected DialogFragmentLoading onCreateDialog() {
        return new DialogFragmentLoading();
    }

    protected void onTimeoutException() {
        Log.d(getClass().getName(), "Timeout Exception");
    }

    protected void onSocketTimeoutException() {
        Log.d(getClass().getName(), "Socket Timeout Exception");
    }

    protected void onIOException() {
        Log.d(getClass().getName(), "IO Exception");
    }
}

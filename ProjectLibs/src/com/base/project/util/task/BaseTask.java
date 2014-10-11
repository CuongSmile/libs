/*
s * + * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.util.task;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.base.project.activity.BaseFragmentActivity;
import com.base.project.util.DialogUtility;
import com.base.project.util.connect.server.HttpUtils;
import com.base.project.util.connect.server.IURLDefine.STATUS_NETWORK_CONNECT;
import com.base.project.widget.DialogFragmentLoading;
import com.example.projectlibs.R;

/**
 * @author CuongPG Sep 27, 2013
 */
public abstract class BaseTask<Params, Result> extends AsyncTask<Params, Void, Result> {
	private static String TAG = "BaseTask";
//	private DialogFragmentLoading loadingDialog;
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
            } 
			 catch(SocketException e){
	                this.status =  STATUS_NETWORK_CONNECT.SOCKET_TIMEOUT;
	            }
	            catch(TimeoutException e){
	                this.status = STATUS_NETWORK_CONNECT.TIMEOUT;
	            }
	            catch (IOException e) {
	                this.status = STATUS_NETWORK_CONNECT.OTHER;
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

	public abstract Result onTaskRunning(Params... params) throws TimeoutException,SocketException,IOException;

	protected void onTaskPreRunning() {
	}

	protected void onDestroy() {
//		this.loadingDialog = null;
	}

//	private void init() {
//		if (this.loadingDialog == null && isShowing) {
//			this.loadingDialog = this.onCreateDialog();
//		}
//	}

	protected void showLoadingDialog() {
		if (this.isShowing && mContext instanceof BaseFragmentActivity) {
//			this.loadingDialog.controlShowDialog((BaseFragmentActivity) mContext);
		    ((BaseFragmentActivity)mContext).showDialog();
		}
	}

	protected void closeLoadingDialog() {
		if (mContext instanceof BaseFragmentActivity) {
		    ((BaseFragmentActivity)mContext).dismissDialog();
		}

	}

	public void run(Context pContext, Params... params) {
		this.mContext = pContext;
		if (!HttpUtils.checkStatusNetwork(pContext)) {
			this.onNoNetwork(pContext);
		}
//		this.init();
		this.execute(params);
	}

	public void run(Context pContext, boolean pShowing, Params... params) {
		this.mContext = pContext;
		if (!HttpUtils.checkStatusNetwork(pContext)) {
			this.onNoNetwork(pContext);
		}
		this.isShowing = pShowing;
//		this.init();
		this.execute(params);
	}

	public Context getContext() {
		return this.mContext;
	}

	protected DialogFragmentLoading onCreateDialog() {
		return new DialogFragmentLoading();
	}

	protected void onIOException() {
	}

	protected void onTimeoutException() {
	}

	protected void onSocketTimeoutException() {
	}

	protected void onNoNetwork(Context pContext) {
		Log.d(getClass().getName(), "No Network");
	}

	protected void showErrorDialog(int messageID) {
		if (mContext != null) {
			DialogUtility.alert(mContext, mContext.getResources().getString(messageID));
		}
	}
}

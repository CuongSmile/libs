/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.base.project.util.task;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.util.Log;

import com.base.project.util.JsonUtil;
import com.base.project.util.LogTracker;
import com.base.project.util.connect.server.HttpUtils;
import com.base.project.util.connect.server.IURLDefine.STATUS_NETWORK_CONNECT;


/**
 * @author CuongPG
 * Jan 11, 2014
 */
public abstract class BaseNetworkPostTask<Params, Result> extends BaseNetworkTask<Params, Result> {
    protected List<? extends NameValuePair> lstValuePair;

    @Override
    public void run(Context pContext, boolean pShowing, Params... params) {
        super.run(pContext, pShowing, params);
    }

    @Override
    public void run(Context pContext, Params... params) {
        super.run(pContext, params);
    }

    public void run(Context pContext, List<? extends NameValuePair> plstValuePair, Params... params) {
        this.lstValuePair = plstValuePair;
        super.run(pContext, params);
        
    }

    public void run(Context pContext, boolean pShowing, List<? extends NameValuePair> plstValuePair,
            Params... params) {
        this.lstValuePair = plstValuePair;
        super.run(pContext, pShowing, params);
       
    }

    @Override
    public Result onTaskRunning(Params... params) throws IOException {
        Result result = null;
        // result = JsonUtil.fromString(this.getRespone((String) params[0]), this.getType());
        if (this.getTypeReference() != null) {
            result =
                    JsonUtil.<Result>fromString(this.getRespone((String) params[0]), this.getTypeReference());
        }
        this.onAfterTaskRunning(result);
        return result;
    }

    @Override
    protected String getRespone(String pUrl) {
        String result = null;
        Log.e("GreyHoundPostTask", "start getdata from sURL : " + pUrl);
        Log.e("GreyHoundPostTask", "start getdata with data: " + lstValuePair);
        if (pUrl != null && this.lstValuePair != null) {
            try {

                // HttpPost post = new HttpPost(pUrl);
                // post.setEntity(new UrlEncodedFormEntity(this.lstValuePair));
                // result = NetworkUtil.executeHttpRequest(post);
                // result = HttpUtils.postDataWithArgs(pUrl, lstValuePair);
                // result = HttpUtils.postDataWithArgs(pUrl, lstValuePair);
                Log.e("GreyHoundPostTask", "post data  : " + pUrl);
                result = HttpUtils.postDataWithArgs(pUrl, lstValuePair);
            } catch (SocketException e) {
                LogTracker.writeExceptionLogToFile(e);
                status = STATUS_NETWORK_CONNECT.SOCKET_TIMEOUT;
            } catch (TimeoutException e) {
                LogTracker.writeExceptionLogToFile(e);
                status = STATUS_NETWORK_CONNECT.TIMEOUT;
            } catch (IOException e) {
                LogTracker.writeExceptionLogToFile(e);
                status = STATUS_NETWORK_CONNECT.OTHER;
            } catch (Exception e) {
                LogTracker.writeExceptionLogToFile(e);
                status = STATUS_NETWORK_CONNECT.OTHER;
            }
        }
        Log.e("GreyHoundPostTask", "get final result : " + pUrl);
        return result;
    }


}

/*
 * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.util.task;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.type.TypeReference;

import android.content.Context;
import android.util.Log;

import com.base.project.util.JsonUtil;
import com.base.project.util.connect.server.HttpUtils;
import com.base.project.util.connect.server.IURLDefine.STATUS_NETWORK_CONNECT;

/**
 * @author CuongPG Oct 19, 2013
 */
@SuppressWarnings("hiding")
public abstract class BaseNetworkTask<Params, Result> extends BaseTask<Params, Result> {

    @Override
    public void run(Context pContext, boolean pShowing, Params... params) {
        if (HttpUtils.checkStatusNetwork(pContext)) {
            super.run(pContext, pShowing, params);
        } else {
            this.onNoNetwork(pContext);
        }
    }
//    @SuppressWarnings("unchecked")
//    protected  Class<Result> getType(){
//
//        Class<Result>  mType = null;
//        ParameterizedType parameterizedType = ((ParameterizedType) getClass().getGenericSuperclass());
//        Type[] arrType = parameterizedType.getActualTypeArguments();
//        
//        if(arrType[1] instanceof ParameterizedType){
//            mType = (Class<Result>)((ParameterizedType)arrType[1]).getActualTypeArguments()[0];
//        } else {
//            mType = (Class<Result>)arrType[1];
//        }
//            return mType;
//    
//    }
    @Override
    public void run(Context pContext, Params... params) {
        if (HttpUtils.checkStatusNetwork(pContext)) {
            super.run(pContext, params);
        } else {
            this.onNoNetwork(pContext);
        }
    }

    protected void onNoNetwork(Context pContext) {
        Log.d(getClass().getName(), "No Network");
    }
    
    protected String getRespone(String pUrl)  {
        String result  = null;
        if(pUrl != null){
            try {
//                result =  NetworkUtil.getData(pUrl);
               result= HttpUtils.getData(pUrl);
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
        }
        return result;
    }
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
    @SuppressWarnings("rawtypes")
    protected abstract TypeReference getTypeReference();
    protected void onAfterTaskRunning(Result result){
    }
}

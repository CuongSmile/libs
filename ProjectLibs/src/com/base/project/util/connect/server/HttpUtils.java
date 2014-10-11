package com.base.project.util.connect.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * @author CuongPG Nov 14, 2013
 */
public class HttpUtils {
    private static final int TIME_OUT_CONNECTION = 10000;
    private static final int TIME_OUT_SOCKET = 10000;
    public static final int BUFFER_SIZE = 8 * 1024;

    private static void disableConnectionReuseIfNecessary() {
        // HTTP connection reuse which was buggy pre-froyo
        // this is 
//        if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
//        }
    }

    public static boolean checkStatusNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo wifiNetwork = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {
                return true;
            }
    
            NetworkInfo mobileNetwork = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }
    
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected()) {
                return true;
            }
        }
        return false;
    }
    public static HttpURLConnection createDefaultURLConnect(String http_link) throws MalformedURLException,
            IOException {
        URL url = new URL(http_link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(TIME_OUT_CONNECTION);
        connection.setReadTimeout(TIME_OUT_SOCKET);
        connection.setRequestProperty(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
        disableConnectionReuseIfNecessary();
        return connection;
    }

    public static String postDataString(HttpURLConnection urlConnection, String data) throws IOException{
        String ret = null;
        if (data != null) {
            Log.d("HttpUtils", "start method postDataString");
//            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Accept-Charset",HTTP.UTF_8);
            byte[] data_bytes = data.getBytes(HTTP.UTF_8);
            urlConnection.setRequestProperty(HTTP.CONTENT_LEN, String.valueOf(data_bytes.length));
            urlConnection.setFixedLengthStreamingMode(data_bytes.length);
           
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(data_bytes);
            out.flush();
            out.close();
            ret = readStream(urlConnection.getInputStream());
        }
        return ret;
    }
    
    public static String readStream(InputStream in) throws IOException {
        if (in == null) {
            return null;
        }
        String ret = null;
        StringBuilder bld = new StringBuilder();
        InputStream buffer_is = new BufferedInputStream(in);
        InputStreamReader reader = new InputStreamReader(buffer_is);
        BufferedReader buffer = new BufferedReader(reader, BUFFER_SIZE);
        char[] bytes_buffer = new char[BUFFER_SIZE];
        int len = 0;
        while ((len = buffer.read(bytes_buffer)) != -1) {
            bld.append(bytes_buffer, 0, len);
        }
        buffer.close();
        ret = bld.toString();
        return ret;
    }

    public static String postDataString(String http_link, String data) throws SocketException,TimeoutException,IOException {
        String ret = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = createDefaultURLConnect(http_link);
            ret = postDataString(urlConnection, data);
        }
        catch(Exception ex){
            if(ex instanceof SocketException){
                throw new SocketException();
            } else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_CLIENT_TIMEOUT
                    || urlConnection.getResponseCode() == HttpURLConnection.HTTP_GATEWAY_TIMEOUT 
                    || ex instanceof TimeoutException 
                    || ex instanceof ConnectTimeoutException
                    || ex instanceof SocketTimeoutException ){
                throw new TimeoutException();
            } else throw new IOException();
         }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                urlConnection = null;
            }
        }
        Log.d("HttpUtils", "have result");
        return ret;
    }
    
  
    
    public static String buildPostContent(List<? extends NameValuePair> args) {
        StringBuilder bld = new StringBuilder();
        for (NameValuePair pair : args) {
            if (!"".equals(pair.getName()) && pair.getName() != null) {
                bld.append(pair.getName()).append("=");
                if (pair.getValue() != null) {
                    bld.append(pair.getValue());
                }
                bld.append("&");
            }
        }
        if (bld.length() > 0) {
            bld.deleteCharAt(bld.length() - 1);
        }
        return bld.toString();
    }

    // post method with param is list name value pair;
    public static String postDataWithArgs(String http_link, List<? extends NameValuePair> args) throws SocketException,TimeoutException,IOException{
        Log.d("HttpUtils", "start method postDataWithArgs");
        return postDataString(http_link, buildPostContent(args));
    }
    
    
    // get method
    public static String getData(String http_link) throws SocketException,TimeoutException,IOException{
        String ret = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = createDefaultURLConnect(http_link);
            ret = readStream(urlConnection.getInputStream());
        }  
        catch(Exception ex){
           if(ex instanceof SocketException){
               throw new SocketException();
           } else if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_CLIENT_TIMEOUT
                   || urlConnection.getResponseCode() == HttpURLConnection.HTTP_GATEWAY_TIMEOUT 
                   || ex instanceof TimeoutException 
                   || ex instanceof ConnectTimeoutException
                   || ex instanceof SocketTimeoutException ){
               throw new TimeoutException();
           } else throw new IOException();
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                urlConnection = null;
            }
        }
        return ret;
    }

    
    
    public static HttpClient getDefaultHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, TIME_OUT_CONNECTION);
        HttpConnectionParams.setSoTimeout(params, TIME_OUT_SOCKET);
        HttpClient client = new DefaultHttpClient(params);
        return client;
    }
    
    /**
     * execute a get request by http client
     * 
     * @author MinhTDH
     * @param request
     * @return String
     ********************************************************* 
     */
    public static String executeHttpRequest(HttpRequestBase request)throws SocketException,TimeoutException,IOException {
        String ret = null;
        HttpClient client = getDefaultHttpClient();
        ResponseHandler<String> handler = new BasicResponseHandler();
        try {
            ret = client.execute(request, handler);
        }
        catch(Exception ex){
            ex.printStackTrace();
               if(ex instanceof SocketException){
                   throw new SocketException();
               } else if(ex instanceof TimeoutException || ex instanceof ConnectTimeoutException || ex instanceof SocketTimeoutException){
                   throw new TimeoutException();
               } else throw new IOException();
            }
        return ret;
    }
}
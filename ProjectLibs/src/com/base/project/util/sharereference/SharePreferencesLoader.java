/*
 * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.util.sharereference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author TND
 * Oct 24, 2013
 */
public  class SharePreferencesLoader {

    private static final String LOG_TAG = SharePreferencesLoader.class.getSimpleName();
    private static SharedPreferences mPreferences;
    private  int PREFERENCES_MODE = 0;
    private  String PREFERENCES_NAME = "PREFERENCES_NAME";
    
    private Context mContext;
    
    private static SharePreferencesLoader sharePreferencesLoader;
    
    private  SharePreferencesLoader() {
    }    
    
    public static SharePreferencesLoader getInstance(){
        if(sharePreferencesLoader == null){
           sharePreferencesLoader = new SharePreferencesLoader();
        }
        return sharePreferencesLoader;
    }
    
    public SharePreferencesLoader setupContect(Context pContext){
        this.mContext = pContext;
        return this;
    }
    
    public SharePreferencesLoader setupPreferncesName( final String name){
        PREFERENCES_NAME = name;
        return this;
    }
    public SharePreferencesLoader setupPreferncesMode( final int mode){
        PREFERENCES_MODE = mode;
        return this;
    }
    public SharedPreferences build(){
        this.mPreferences = mContext.getSharedPreferences(PREFERENCES_NAME, PREFERENCES_MODE);
        return this.mPreferences;
    }

    public  Editor saveValueToSharedPreferences() {
        return this.mPreferences.edit();
    }

    public  void saveValueToSharedPreferences(final String pKeyName, final String pValue) {
        saveValueToSharedPreferences().putString(pKeyName, pValue).commit();
    }

    public  void saveValueToSharedPreferences(final String pKeyName, final int pValue) {
        saveValueToSharedPreferences().putInt(pKeyName, pValue).commit();
    }

    public void saveLongValueToSharedPreferences(final String pKeyName, final long pValue) {
        saveValueToSharedPreferences().putLong(pKeyName, pValue).commit();
    }

    public  void saveValueToSharedPreferences(final String pKeyName, final boolean pValue) {
        saveValueToSharedPreferences().putBoolean(pKeyName, pValue).commit();
    }

    public  String getValueFromPreferences(final String pKeyName, final String pDefaultValue) {
        return this.mPreferences.getString(pKeyName, pDefaultValue);
    }

    public  int getValueFromPreferences(final String pKeyName, final int pDefaultValue) {
        return this.mPreferences.getInt(pKeyName, pDefaultValue);
    }

    public  long getValueLongFromPreferences(final String pKeyName, final long pDefaultValue) {
        return this.mPreferences.getLong(pKeyName, pDefaultValue);
    }
    
    public void onDestroy(){
        mContext = null;
        mPreferences = null;
        sharePreferencesLoader = null;
    }

    public  boolean getValueFromPreferences(final String pKeyName, final boolean pDefaultValue) {
        return this.mPreferences.getBoolean(pKeyName, pDefaultValue);
    }
}

/*
 * Copyright (C) 2013 TinhVan Outsourcing.
 */
package com.base.project.util.sharereference;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author TND
 * Oct 24, 2013
 */
public abstract class ISharePreferences {

    private static final String LOG_TAG = ISharePreferences.class.getSimpleName();
    private static SharedPreferences mPreferences;
    private static int PREFERENCES_MODE = 0;
    private static String PREFERENCES_NAME = "PREFERENCES_NAME";
    
    // create instance by application
    public static SharedPreferences getPreferences() {
//        if (mPreferences == null)
//            mPreferences =
//                    ("get via application").getAppContext().getSharedPreferences(PREFERENCES_NAME,
//                            PREFERENCES_MODE);
        return mPreferences;
    }

    public ISharePreferences(final String pPreferencesName, final int pPreferencesMode) {
        PREFERENCES_NAME = pPreferencesName;
        PREFERENCES_MODE = pPreferencesMode;
    }

    public static Editor saveValueToSharedPreferences() {
        return getPreferences().edit();
    }

    public static void saveValueToSharedPreferences(final String pKeyName, final String pValue) {
        saveValueToSharedPreferences().putString(pKeyName, pValue).commit();
    }

    public static void saveValueToSharedPreferences(final String pKeyName, final int pValue) {
        saveValueToSharedPreferences().putInt(pKeyName, pValue).commit();
    }

    public void saveLongValueToSharedPreferences(final String pKeyName, final long pValue) {
        saveValueToSharedPreferences().putLong(pKeyName, pValue).commit();
    }

    public static void saveValueToSharedPreferences(final String pKeyName, final boolean pValue) {
        saveValueToSharedPreferences().putBoolean(pKeyName, pValue).commit();
    }

    public static String getValueFromPreferences(final String pKeyName, final String pDefaultValue) {
        return getPreferences().getString(pKeyName, pDefaultValue);
    }

    public static int getValueFromPreferences(final String pKeyName, final int pDefaultValue) {
        return getPreferences().getInt(pKeyName, pDefaultValue);
    }

    public static long getValueLongFromPreferences(final String pKeyName, final long pDefaultValue) {
        return getPreferences().getLong(pKeyName, pDefaultValue);
    }

    public static boolean getValueFromPreferences(final String pKeyName, final boolean pDefaultValue) {
        return getPreferences().getBoolean(pKeyName, pDefaultValue);
    }
}

package com.base.project.util.gcm;


public class GCMConfig {
    //  save permission using in a app
    //
//    <!-- GCM requires Android SDK version 2.2 (API level 8) or above. -->
//    <!--
//         The targetSdkVersion is optional, but it's always a good practice
//         to target higher versions.
//    -->
//    <uses-sdk
//        android:minSdkVersion="10"
//        android:targetSdkVersion="16" />
//
//    <!-- GCM connects to Google Services. -->
//    <uses-permission android:name="android.permission.INTERNET" />
//
//    <!-- GCM requires a Google account. -->
//    <uses-permission android:name="android.permission.GET_ACCOUNTS" />
//
//    <!-- Keeps the processor from sleeping when a message is received. -->
//    <uses-permission android:name="android.permission.WAKE_LOCK" />
//
//    <!--
//     Creates a custom permission so only this app can receive its messages.
//
//     NOTE: the permission *must* be called PACKAGE.permission.C2D_MESSAGE,
//           where PACKAGE is the application's package name.
//    -->
//    <permission
//        android:name="com.google.android.gcm.demo.app.permission.C2D_MESSAGE"
//        android:protectionLevel="signature" />
//
//    <uses-permission android:name="com.google.android.gcm.demo.app.permission.C2D_MESSAGE" />
//
//    <!-- This app has permission to register and receive data message. -->
//    <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
    //
    
//    <!--
//    WakefulBroadcastReceiver that will receive intents from GCM
//    services and hand them to the custom IntentService.
//
//    The com.google.android.c2dm.permission.SEND permission is necessary
//    so only GCM services can send data messages for the app.
//  -->
//  <receiver
//      android:name=".GCMBroadcastReceiver"
//      android:permission="com.google.android.c2dm.permission.SEND" >
//      <intent-filter>
//
//          <!-- Receives the actual messages. -->
//          <action android:name="com.google.android.c2dm.intent.RECEIVE" />
//
//          <category android:name="com.google.android.gcm.demo.app" />
//      </intent-filter>
//  </receiver>
//
//  <service android:name=".GCMIntentService" />
}

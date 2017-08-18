//package com.mcruncher.firebase.quickstart.fcm;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//
///**
// * Author : Madasamy
// * Version : x.x.x
// */
//
//public class GcmIntentService extends GCMBaseIntentService {
//    private static final String TAG = "GGM <-----> FRAB";
//    private Bundle extras;
//
//    public GCMIntentService() {
//        super(Globals.SENDER_ID);
//    }
//
//    @Override
//    public void onDestroy() {
//        Log.d(TAG, "terminando servicio");
//    }
//
//    @Override
//    protected void onRegistered(Context context, String registrationId) {
//        Log.i(TAG, "onRegistered: registrationId=" + registrationId);
//    }
//
//    @Override
//    protected void onUnregistered(Context context, String registrationId) {
//        Log.i(TAG, "onUnregistered: registrationId = " + registrationId);
//    }
//    @Override
//    protected void onMessage(Context context, Intent data) {
//        extras = data.getExtras();
//        String message = extras.getString("msg");
//        Log.d("******", message);
//        sendNotification(message);
//    }
//
//    @Override
//    protected void onError(Context arg0, String errorId) {
//        Log.e(TAG, "onError: errorId = " + errorId);
//    }
//}

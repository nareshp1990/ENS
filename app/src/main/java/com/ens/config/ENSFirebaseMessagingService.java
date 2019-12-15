package com.ens.config;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;

public class ENSFirebaseMessagingService extends FirebaseMessagingService {


    public static final String TAG = ENSFirebaseMessagingService.class.getCanonicalName();

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "### Firebase Token: " + token );
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "### Firebase Message Received: " + remoteMessage );
    }
}

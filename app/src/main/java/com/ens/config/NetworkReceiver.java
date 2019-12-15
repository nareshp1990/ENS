package com.ens.config;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ens.utils.NetworkState;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.greenrobot.event.EventBus;

public class NetworkReceiver extends BroadcastReceiver {

    public static final String TAG = NetworkReceiver.class.getCanonicalName();

    private final EventBus eventBus = EventBus.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {

        // Check if activity is visible  or not
        boolean activityVisible = ENSApplication.isActivityVisible();

        if (activityVisible) {

            String status = NetworkState.getConnectivityStatusString(context);

            // Get current time
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());

            String eventData = "@" + formattedDate + ": device network state: " + status;

            Log.i(TAG,eventData);

            // Post the event with this line
            eventBus.post(NetworkState.isInternetAvailable(context));
        }
    }
}
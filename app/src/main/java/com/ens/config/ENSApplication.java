package com.ens.config;

import android.app.Application;

public class ENSApplication extends Application {

    // Gloabl declaration of variable to use in whole app
    // Variable that will check the current activity state
    public static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused
    }
}

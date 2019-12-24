package com.ens.config;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ens.api.AuthApi;
import com.ens.api.NewsApi;
import com.ens.api.PollApi;
import com.ens.api.UserApi;
import com.ens.exception.ApiErrorEvent;
import com.ens.model.user.User;
import com.ens.utils.SharedPrefsUtils;
import com.facebook.FacebookSdk;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.greenrobot.event.EventBus;

public class ENSApplication extends Application {

    public static final String TAG = ENSApplication.class.getCanonicalName();

    private static Context context;

    // Gloabl declaration of variable to use in whole app
    // Variable that will check the current activity state
    public static boolean activityVisible;

    private EventBus eventBus = EventBus.getDefault();

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static AuthApi authApi;

    private static UserApi userApi;

    private static NewsApi newsApi;

    private static PollApi pollApi;

    @Override
    public void onCreate() {
        super.onCreate();
        ENSApplication.context = getApplicationContext();
        eventBus.register(this);
        FacebookSdk.setApplicationId("2633898890029437");
        FacebookSdk.sdkInitialize(ENSApplication.context);
    }

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }

    public static void activityResumed() {
        activityVisible = true;// this will set true when activity resumed

    }

    public static void activityPaused() {
        activityVisible = false;// this will set false when activity paused
    }

    public void onEvent(ApiErrorEvent event) {
        Log.e(TAG, event.toString());
        Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static AuthApi getAuthApi() {
        if (authApi == null) {
            authApi = APIClient.getClient().create(AuthApi.class);
        }
        return authApi;
    }

    public static NewsApi getNewsApi() {
        if (newsApi == null) {
            newsApi = APIClient.getClient().create(NewsApi.class);
        }
        return newsApi;
    }

    public static PollApi getPollApi() {
        if (pollApi == null) {
            pollApi = APIClient.getClient().create(PollApi.class);
        }
        return pollApi;
    }

    public static UserApi getUserApi() {
        if (userApi == null) {
            userApi = APIClient.getClient().create(UserApi.class);
        }
        return userApi;
    }

    public static Context getAppContext(){
        return ENSApplication.context;
    }

    public static void saveLoggedInUserId(long userId){
        SharedPrefsUtils.setLongPreference(getAppContext(),"userId",userId);
    }

    public static Long getLoggedInUserId(){
        return SharedPrefsUtils.getLongPreference(getAppContext(),"userId",0);
    }

    public static void saveLoggedInUser(String userString){
        SharedPrefsUtils.setStringPreference(getAppContext(),"loggedInUser",userString);
    }

    public static User getLoggedInUser(){

        String loggedInUser = SharedPrefsUtils.getStringPreference(getAppContext(), "loggedInUser");

        try {
            return getObjectMapper().readValue(loggedInUser, User.class);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Log.e(TAG, "getLoggedInUser: " + e );
        }

        return null;
    }

    public static boolean isUserLoggedIn(){
        return SharedPrefsUtils.getBooleanPreference(getAppContext(),"skipLogin",false);
    }
}

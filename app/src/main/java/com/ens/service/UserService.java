package com.ens.service;

import android.content.Context;
import android.util.Log;

import com.ens.bus.FCMKeyUpdateEvent;
import com.ens.bus.UserCreatedEvent;
import com.ens.config.ENSApplication;
import com.ens.exception.ApiErrorEvent;
import com.ens.model.api.ApiResponse;
import com.ens.model.user.User;
import com.ens.model.user.UserRequest;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    public static final String TAG = UserService.class.getCanonicalName();

    private final EventBus eventBus = EventBus.getDefault();

    private Context context;

    public UserService(Context context) {
        this.context = context;
    }

    public void createUser(UserRequest userRequest){

        Call<ApiResponse> apiResponseCall = ENSApplication.getUserApi().createUser(userRequest);

        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### User Create Response : " + response.body().toString());
                        eventBus.post(new UserCreatedEvent(response.body()));
                    }
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void getUser(Long userId){

        Call<User> apiUser = ENSApplication.getUserApi().getUser(userId);

        apiUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### User Get Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void updateUserFCMKey(Long userId, String fcmKey){

        Call<User> userCall = ENSApplication.getUserApi().updateUserFCMKey(userId, fcmKey);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### User FCM Key Update Response : " + response.body().toString());
                        eventBus.post(new FCMKeyUpdateEvent(response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

}

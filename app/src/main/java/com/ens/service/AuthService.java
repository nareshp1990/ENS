package com.ens.service;

import android.content.Context;
import android.util.Log;

import com.ens.bus.UserLoggedInEvent;
import com.ens.config.ENSApplication;
import com.ens.exception.ApiErrorEvent;
import com.ens.model.user.User;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthService {

    public static final String TAG = AuthService.class.getCanonicalName();

    private final EventBus eventBus = EventBus.getDefault();

    private Context context;

    public AuthService(Context context) {
        this.context = context;
    }

    public void login(String mobileNumber, String password){

        Call<User> userResponseCall = ENSApplication.getAuthApi().login(mobileNumber, password);

        userResponseCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### User Login Response : " + response.body().toString());
                        eventBus.post(new UserLoggedInEvent(response.body()));
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

package com.ens.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ens.api.AuthApi;
import com.ens.model.user.UserResponse;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthService {

    public static final String TAG = AuthService.class.getCanonicalName();

    private Context context;
    private AuthApi authApi;
    private EventBus eventBus;

    public AuthService(Context context, AuthApi authApi, EventBus eventBus) {
        this.context = context;
        this.authApi = authApi;
        this.eventBus = eventBus;
    }

    public void login(String mobileNumber, String password){

        Call<UserResponse> userResponseCall = authApi.login(mobileNumber, password);

        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### User Login Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(context,"Error while login",Toast.LENGTH_LONG).show();
            }
        });

    }

}

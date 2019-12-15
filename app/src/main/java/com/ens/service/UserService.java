package com.ens.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ens.api.UserApi;
import com.ens.model.api.ApiResponse;
import com.ens.model.user.User;
import com.ens.model.user.UserRequest;

import java.util.UUID;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    public static final String TAG = UserService.class.getCanonicalName();

    private Context context;
    private EventBus eventBus;
    private UserApi userApi;

    public UserService(Context context, EventBus eventBus, UserApi userApi) {
        this.context = context;
        this.eventBus = eventBus;
        this.userApi = userApi;
    }

    public void createUser(UserRequest userRequest){

        Call<ApiResponse> apiResponseCall = userApi.createUser(userRequest);

        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### User Create Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context,"Error while creating user",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getUser(UUID userId){

        Call<User> apiUser = userApi.getUser(userId);

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
                Toast.makeText(context,"Error while fetching user",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void updateUserFCMKey(UUID userId, String fcmKey){

        Call<User> userCall = userApi.updateUserFCMKey(userId, fcmKey);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### User FCM Key Update Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context,"Error while updating user fcm key",Toast.LENGTH_LONG).show();
            }
        });

    }

}

package com.ens.api;

import com.ens.model.user.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {

    @FormUrlEncoded
    @POST("/v1/api/auth/login")
    Call<UserResponse> login(@Field("mobileNumber") String mobileNumber, @Field("password") String password);

}

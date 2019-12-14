package com.ens.api;

import com.ens.model.api.ApiResponse;
import com.ens.model.user.User;
import com.ens.model.user.UserRequest;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/v1/api/users")
    Call<ApiResponse> createUser(@Body UserRequest userRequest);

    @PUT("/v1/api/users/{userId}")
    Call<ApiResponse> updateUser(@Path ("userId") UUID userId, @Body UserRequest userRequest);

    @GET("/v1/api/users/{userId}")
    Call<User> getUser(@Path ("userId") UUID userId);

    @DELETE("/v1/api/users/{userId}")
    Call<ApiResponse> deleteUser(@Path ("userId") UUID userId);

    @FormUrlEncoded
    @PATCH("/v1/api/users/{userId}")
    Call<User> updateUserFCMKey(@Path ("userId") UUID userId, @Field("fcmKey") String fcmKey);

}

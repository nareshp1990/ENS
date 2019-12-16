package com.ens.api;

import com.ens.model.api.ApiResponse;
import com.ens.model.api.PagedResponse;
import com.ens.model.news.ActionType;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItem;
import com.ens.model.news.NewsItemAction;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @FormUrlEncoded
    @POST("/v1/api/news/{userId}/{newsItemId}/comment")
    Call<ApiResponse> postComment(@Path("userId") UUID userId, @Path("newsItemId") UUID newsItemId, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("/v1/api/news/{userId}/{newsItemId}/action")
    Call<NewsItemAction> postNewsItemAction(@Path("userId") UUID userId, @Path("newsItemId") UUID newsItemId, @Field("actionType")ActionType actionType);

    @GET("/v1/api/news/{userId}")
    Call<PagedResponse<NewsItem>> getAllNewsItems(@Path("userId") UUID userId, @Query("contentType")ContentType contentType, @Query("page") int page, @Query("size") int size);

    @GET("/v1/api/news/{userId}/scroll")
    Call<String> getNewsScrollText(@Path("userId") UUID userId, @Query("page") int page, @Query("size") int size);
}

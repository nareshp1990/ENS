package com.ens.api;

import com.ens.model.api.ApiResponse;
import com.ens.model.api.PagedResponse;
import com.ens.model.news.ActionType;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItem;
import com.ens.model.news.NewsItemAction;
import com.ens.model.news.NewsItemRequest;
import com.ens.model.news.ScrollResponse;
import com.ens.model.news.VideoRequest;

import java.util.Set;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {

    @FormUrlEncoded
    @POST("/v1/api/news/{userId}/{newsItemId}/comment")
    Call<ApiResponse> postComment(@Path("userId") Long userId, @Path("newsItemId") Long newsItemId, @Field("comment") String comment);

    @FormUrlEncoded
    @POST("/v1/api/news/{userId}/{newsItemId}/action")
    Call<NewsItemAction> postNewsItemAction(@Path("userId") Long userId, @Path("newsItemId") Long newsItemId, @Field("actionType")ActionType actionType);

    @GET("/v1/api/news/{userId}")
    Call<PagedResponse<NewsItem>> getAllNewsItems(@Path("userId") Long userId, @Query("contentTypes") Set<ContentType> contentTypes, @Query("newsItemId") Long newsItemId, @Query("page") int page, @Query("size") int size);

    @GET("/v1/api/news/{userId}/scroll")
    Call<ScrollResponse> getNewsScrollText(@Path("userId") Long userId, @Query("page") int page, @Query("size") int size);

    @GET("/v1/api/news/{userId}/{newsItemId}/action")
    Call<NewsItemAction> getNewsItemUserActions(@Path("userId") Long userId, @Path("newsItemId") Long newsItemId);

    @GET("/v1/api/news/{userId}/{newsItemId}")
    Call<NewsItem> getNewsItemById(@Path("userId") Long userId, @Path("newsItemId") Long newsItemId);

    @POST("/v1/api/news/{userId}")
    Call<ApiResponse> createNews(@Path("userId") Long userId, @Body NewsItemRequest newsItemRequest);

    @POST("/v1/api/news/{userId}/video")
    Call<ApiResponse> createVideoNews(@Path("userId") Long userId, @Body VideoRequest videoRequest);

}

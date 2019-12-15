package com.ens.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ens.api.NewsApi;
import com.ens.model.api.ApiResponse;
import com.ens.model.api.PagedResponse;
import com.ens.model.news.ActionType;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItemActionResponse;
import com.ens.model.news.NewsItemResponse;

import java.util.UUID;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsService {

    public static final String TAG = NewsService.class.getCanonicalName();

    private Context context;
    private EventBus eventBus;
    private NewsApi newsApi;

    public NewsService(Context context, EventBus eventBus, NewsApi newsApi) {
        this.context = context;
        this.eventBus = eventBus;
        this.newsApi = newsApi;
    }

    public void postComment(UUID userId, UUID newsItemId, String comment){

        Call<ApiResponse> apiResponseCall = newsApi.postComment(userId, newsItemId, comment);

        apiResponseCall.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Post Comment Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context,"Error while posting comment",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void postNewsItemAction(UUID userId, UUID newsItemId, ActionType actionType){

        Call<NewsItemActionResponse> newsItemActionResponseCall = newsApi.postNewsItemAction(userId, newsItemId, actionType);

        newsItemActionResponseCall.enqueue(new Callback<NewsItemActionResponse>() {
            @Override
            public void onResponse(Call<NewsItemActionResponse> call, Response<NewsItemActionResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Post News Item Action Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<NewsItemActionResponse> call, Throwable t) {
                Toast.makeText(context,"Error while posting news item action",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getAllNewsItems(UUID userId, ContentType contentType, int page, int size){

        Call<PagedResponse<NewsItemResponse>> pagedResponseCall = newsApi.getAllNewsItems(userId, contentType, page, size);

        pagedResponseCall.enqueue(new Callback<PagedResponse<NewsItemResponse>>() {
            @Override
            public void onResponse(Call<PagedResponse<NewsItemResponse>> call, Response<PagedResponse<NewsItemResponse>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Get News Items Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }

            }

            @Override
            public void onFailure(Call<PagedResponse<NewsItemResponse>> call, Throwable t) {
                Toast.makeText(context,"Error while fetching news items",Toast.LENGTH_LONG).show();
            }
        });

    }

}

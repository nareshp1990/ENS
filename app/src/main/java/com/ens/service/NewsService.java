package com.ens.service;

import android.content.Context;
import android.util.Log;

import com.ens.adapters.NewsCardViewAdapter;
import com.ens.adapters.VideoViewAdapter;
import com.ens.bus.NewsActionEvent;
import com.ens.bus.NewsLoadedEvent;
import com.ens.config.ENSApplication;
import com.ens.exception.ApiErrorEvent;
import com.ens.model.api.ApiResponse;
import com.ens.model.api.PagedResponse;
import com.ens.model.news.ActionType;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItem;
import com.ens.model.news.NewsItemAction;
import com.ens.model.news.ScrollResponse;

import java.util.HashSet;
import java.util.Set;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsService {

    public static final String TAG = NewsService.class.getCanonicalName();

    private final EventBus eventBus = EventBus.getDefault();

    private Context context;

    public NewsService(Context context) {
        this.context = context;
    }

    public void postComment(Long userId, Long newsItemId, String comment){

        Call<ApiResponse> apiResponseCall = ENSApplication.getNewsApi().postComment(userId, newsItemId, comment);

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
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void postNewsItemAction(Long userId, Long newsItemId, ActionType actionType, NewsCardViewAdapter.NewsCardViewHolder holder){

        Call<NewsItemAction> newsItemActionResponseCall = ENSApplication.getNewsApi().postNewsItemAction(userId, newsItemId, actionType);

        newsItemActionResponseCall.enqueue(new Callback<NewsItemAction>() {
            @Override
            public void onResponse(Call<NewsItemAction> call, Response<NewsItemAction> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Post News Item Action Response : " + response.body().toString());
                        eventBus.post(new NewsActionEvent(response.body(),holder));
                    }
                }

            }

            @Override
            public void onFailure(Call<NewsItemAction> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void postNewsItemAction(Long userId, Long newsItemId, ActionType actionType, VideoViewAdapter.VideoViewHolder holder){

        Call<NewsItemAction> newsItemActionResponseCall = ENSApplication.getNewsApi().postNewsItemAction(userId, newsItemId, actionType);

        newsItemActionResponseCall.enqueue(new Callback<NewsItemAction>() {
            @Override
            public void onResponse(Call<NewsItemAction> call, Response<NewsItemAction> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Post News Item Action Response : " + response.body().toString());
                        eventBus.post(new NewsActionEvent(response.body(),holder));
                    }
                }

            }

            @Override
            public void onFailure(Call<NewsItemAction> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void getAllNewsItems(Long userId, ContentType contentType, long newsItemId, int page, int size){

        Set<ContentType> contentTypes = new HashSet<>();
        contentTypes.add(contentType);

        Call<PagedResponse<NewsItem>> pagedResponseCall = ENSApplication.getNewsApi().getAllNewsItems(userId, contentTypes, newsItemId, page, size);

        pagedResponseCall.enqueue(new Callback<PagedResponse<NewsItem>>() {
            @Override
            public void onResponse(Call<PagedResponse<NewsItem>> call, Response<PagedResponse<NewsItem>> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Get News Items Response : " + response.body().toString());
                        eventBus.post(new NewsLoadedEvent(contentType,response.body()));
                    }
                }

            }

            @Override
            public void onFailure(Call<PagedResponse<NewsItem>> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void getNewsScrollText(Long userId, int page, int size){

        Call<ScrollResponse> scrollTextResponseCall = ENSApplication.getNewsApi().getNewsScrollText(userId, page, size);

        scrollTextResponseCall.enqueue(new Callback<ScrollResponse>() {
            @Override
            public void onResponse(Call<ScrollResponse> call, Response<ScrollResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Get News Scroll Text Response : " + response.body());
                        eventBus.post(new NewsLoadedEvent(ContentType.SCROLL,response.body().getScrollText()));
                    }
                }

            }

            @Override
            public void onFailure(Call<ScrollResponse> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void getNewsItemUserAction(Long userId, Long newsItemId){

        Call<NewsItemAction> newsItemActionResponseCall = ENSApplication.getNewsApi().getNewsItemUserActions(userId, newsItemId);

        newsItemActionResponseCall.enqueue(new Callback<NewsItemAction>() {
            @Override
            public void onResponse(Call<NewsItemAction> call, Response<NewsItemAction> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Post News Item Action Response : " + response.body().toString());
                        eventBus.post(new NewsActionEvent(response.body()));
                    }
                }

            }

            @Override
            public void onFailure(Call<NewsItemAction> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void getNewsItemById(Long userId, Long newsItemId){

        Call<NewsItem> responseCall = ENSApplication.getNewsApi().getNewsItemById(userId, newsItemId);

        responseCall.enqueue(new Callback<NewsItem>() {
            @Override
            public void onResponse(Call<NewsItem> call, Response<NewsItem> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Get News Items Response : " + response.body().toString());
                        eventBus.post(new NewsLoadedEvent(response.body()));
                    }
                }

            }

            @Override
            public void onFailure(Call<NewsItem> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void postNewsItemAction(Long userId, Long newsItemId, ActionType actionType){

        Call<NewsItemAction> newsItemActionResponseCall = ENSApplication.getNewsApi().postNewsItemAction(userId, newsItemId, actionType);

        newsItemActionResponseCall.enqueue(new Callback<NewsItemAction>() {
            @Override
            public void onResponse(Call<NewsItemAction> call, Response<NewsItemAction> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Post News Item Action Response : " + response.body().toString());
                        eventBus.post(new NewsActionEvent(response.body()));
                    }
                }

            }

            @Override
            public void onFailure(Call<NewsItemAction> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

}

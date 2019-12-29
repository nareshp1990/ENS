package com.ens.service;

import android.content.Context;
import android.util.Log;

import com.ens.bus.FileUploadedEvent;
import com.ens.config.ENSApplication;
import com.ens.exception.ApiErrorEvent;
import com.ens.model.content.ContentResponse;

import java.io.File;

import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContentService {

    public static final String TAG = ContentService.class.getCanonicalName();

    private final EventBus eventBus = EventBus.getDefault();

    private Context context;

    public ContentService(Context context) {
        this.context = context;
    }

    public void uploadFile(File file){

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<ContentResponse> contentResponseCall = ENSApplication.getContentApi().uploadFile("ENS", "ENS", "ENS", body);

        contentResponseCall.enqueue(new Callback<ContentResponse>() {
            @Override
            public void onResponse(Call<ContentResponse> call, Response<ContentResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### File Upload Response : " + response.body().toString());
                        eventBus.post(new FileUploadedEvent(response.body()));
                    }
                }

            }

            @Override
            public void onFailure(Call<ContentResponse> call, Throwable t) {

                eventBus.post(new ApiErrorEvent(t));

            }
        });

    }

}

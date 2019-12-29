package com.ens.api;

import com.ens.model.content.ContentResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ContentApi {

    @Multipart
    @POST("/v1/api/content/upload/{serviceId}/{tenantId}/{userId}")
    Call<ContentResponse> uploadFile(@Path("serviceId") String serviceId,
                                        @Path("tenantId") String tenantId,
                                        @Path("userId") String userId,
                                        @Part MultipartBody.Part file);

}

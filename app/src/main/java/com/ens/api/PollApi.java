package com.ens.api;

import com.ens.model.api.PagedResponse;
import com.ens.model.poll.PollResponse;
import com.ens.model.poll.VoteRequest;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PollApi {

    @GET("/v1/api/polls/{userId}")
    Call<PagedResponse<PollResponse>> getPolls(@Path("userId") UUID userId, @Query("page") int page, @Query("size") int size);

    @GET("/v1/api/polls/{userId}/{pollId}")
    Call<PollResponse> getPollById(@Path("userId") UUID userId, @Path("pollId") UUID pollId);

    @POST("/v1/api/polls/{userId}/{pollId}/votes")
    Call<PollResponse> castVote(@Path("userId") UUID userId, @Path("pollId") UUID pollId, @Body VoteRequest voteRequest);



}

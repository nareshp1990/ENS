package com.ens.api;

import com.ens.model.api.PagedResponse;
import com.ens.model.poll.Poll;
import com.ens.model.poll.VoteRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PollApi {

    @GET("/v1/api/polls/{userId}")
    Call<PagedResponse<Poll>> getPolls(@Path("userId") Long userId, @Query("page") int page, @Query("size") int size);

    @GET("/v1/api/polls/{userId}/{pollId}")
    Call<Poll> getPollById(@Path("userId") Long userId, @Path("pollId") Long pollId);

    @POST("/v1/api/polls/{userId}/{pollId}/votes")
    Call<Poll> castVote(@Path("userId") Long userId, @Path("pollId") Long pollId, @Body VoteRequest voteRequest);



}

package com.ens.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ens.api.PollApi;
import com.ens.model.api.PagedResponse;
import com.ens.model.poll.PollResponse;
import com.ens.model.poll.VoteRequest;

import java.util.UUID;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollService {

    public static final String TAG = PollService.class.getCanonicalName();

    private Context context;
    private EventBus eventBus;
    private PollApi pollApi;

    public PollService(Context context, EventBus eventBus, PollApi pollApi) {
        this.context = context;
        this.eventBus = eventBus;
        this.pollApi = pollApi;
    }

    public void getPolls(UUID userId, int page, int size){

        Call<PagedResponse<PollResponse>> responseCall = pollApi.getPolls(userId, page, size);

        responseCall.enqueue(new Callback<PagedResponse<PollResponse>>() {
            @Override
            public void onResponse(Call<PagedResponse<PollResponse>> call, Response<PagedResponse<PollResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Get Polls Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<PollResponse>> call, Throwable t) {
                Toast.makeText(context,"Error while fetching polls data",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void castVote(UUID userId, UUID pollId, VoteRequest voteRequest){

        Call<PollResponse> pollResponseCall = pollApi.castVote(userId, pollId, voteRequest);

        pollResponseCall.enqueue(new Callback<PollResponse>() {
            @Override
            public void onResponse(Call<PollResponse> call, Response<PollResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Cat Vote Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<PollResponse> call, Throwable t) {
                Toast.makeText(context,"Error while casting vote",Toast.LENGTH_LONG).show();
            }
        });

    }

}

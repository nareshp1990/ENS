package com.ens.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ens.api.PollApi;
import com.ens.model.api.PagedResponse;
import com.ens.model.poll.Poll;
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

        Call<PagedResponse<Poll>> responseCall = pollApi.getPolls(userId, page, size);

        responseCall.enqueue(new Callback<PagedResponse<Poll>>() {
            @Override
            public void onResponse(Call<PagedResponse<Poll>> call, Response<PagedResponse<Poll>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Get Polls Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<Poll>> call, Throwable t) {
                Toast.makeText(context,"Error while fetching polls data",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void castVote(UUID userId, UUID pollId, VoteRequest voteRequest){

        Call<Poll> pollResponseCall = pollApi.castVote(userId, pollId, voteRequest);

        pollResponseCall.enqueue(new Callback<Poll>() {
            @Override
            public void onResponse(Call<Poll> call, Response<Poll> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Cat Vote Response : " + response.body().toString());
                        eventBus.post(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Poll> call, Throwable t) {
                Toast.makeText(context,"Error while casting vote",Toast.LENGTH_LONG).show();
            }
        });

    }

}

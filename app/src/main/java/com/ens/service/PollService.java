package com.ens.service;

import android.content.Context;
import android.util.Log;

import com.ens.adapters.PollCardViewAdapter;
import com.ens.bus.PollsLoadedEvent;
import com.ens.bus.VoteCastedEvent;
import com.ens.config.ENSApplication;
import com.ens.exception.ApiErrorEvent;
import com.ens.model.api.PagedResponse;
import com.ens.model.poll.Poll;
import com.ens.model.poll.VoteRequest;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollService {

    public static final String TAG = PollService.class.getCanonicalName();

    private final EventBus eventBus = EventBus.getDefault();

    private Context context;

    public PollService(Context context) {
        this.context = context;
    }

    public void getPolls(Long userId, int page, int size){

        Call<PagedResponse<Poll>> responseCall = ENSApplication.getPollApi().getPolls(userId, page, size);

        responseCall.enqueue(new Callback<PagedResponse<Poll>>() {
            @Override
            public void onResponse(Call<PagedResponse<Poll>> call, Response<PagedResponse<Poll>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Get Polls Response : " + response.body().toString());
                        eventBus.post(new PollsLoadedEvent(response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<Poll>> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

    public void castVote(Long userId, Long pollId, VoteRequest voteRequest, PollCardViewAdapter.PollCardViewHolder pollCardViewHolder){

        Call<Poll> pollResponseCall = ENSApplication.getPollApi().castVote(userId, pollId, voteRequest);

        pollResponseCall.enqueue(new Callback<Poll>() {
            @Override
            public void onResponse(Call<Poll> call, Response<Poll> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i(TAG, "### Cat Vote Response : " + response.body().toString());
                        eventBus.post(new VoteCastedEvent(response.body(),pollCardViewHolder));
                    }
                }
            }

            @Override
            public void onFailure(Call<Poll> call, Throwable t) {
                eventBus.post(new ApiErrorEvent(t));
            }
        });

    }

}

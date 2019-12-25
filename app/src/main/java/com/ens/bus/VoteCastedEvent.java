package com.ens.bus;

import com.ens.adapters.PollCardViewAdapter;
import com.ens.model.poll.Poll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteCastedEvent {

    private Poll poll;
    private PollCardViewAdapter.PollCardViewHolder pollCardViewHolder;

}

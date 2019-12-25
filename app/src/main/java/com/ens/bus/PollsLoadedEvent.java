package com.ens.bus;

import com.ens.model.api.PagedResponse;
import com.ens.model.poll.Poll;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollsLoadedEvent {

    private PagedResponse<Poll> pollPagedResponse;

}

package com.ens.model.poll;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class VoteRequest implements Serializable {

    private UUID choiceId;

}

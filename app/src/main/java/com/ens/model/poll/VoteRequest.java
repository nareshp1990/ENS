package com.ens.model.poll;

import java.io.Serializable;

import lombok.Data;

@Data
public class VoteRequest implements Serializable {

    private Long choiceId;

}

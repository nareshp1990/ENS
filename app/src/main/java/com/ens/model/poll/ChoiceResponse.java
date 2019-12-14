package com.ens.model.poll;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class ChoiceResponse implements Serializable {

    private UUID id;
    private String text;
    private long voteCount;

}

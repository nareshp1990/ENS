package com.ens.model.poll;

import java.io.Serializable;

import lombok.Data;

@Data
public class Choice implements Serializable {

    private Long id;
    private String text;
    private long voteCount;
    private double pollPercentage;

}

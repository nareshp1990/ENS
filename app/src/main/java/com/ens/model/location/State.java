package com.ens.model.location;

import java.io.Serializable;

import lombok.Data;

@Data
public class State implements Serializable {

    private Long id;
    private String stateName;
    private String capitalName;
    private String type;

}

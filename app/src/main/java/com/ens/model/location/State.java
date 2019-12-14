package com.ens.model.location;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class State implements Serializable {

    private UUID id;
    private String stateName;
    private String capitalName;
    private String type;

}

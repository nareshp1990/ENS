package com.ens.model.location;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class Area implements Serializable {

    private UUID id;
    private String areaName;

}

package com.ens.model.api;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class ApiResponse implements Serializable {

    private UUID id;
    private Boolean success;
    private String message;

}

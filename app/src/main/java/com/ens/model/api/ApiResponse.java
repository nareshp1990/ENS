package com.ens.model.api;

import java.io.Serializable;

import lombok.Data;

@Data
public class ApiResponse implements Serializable {

    private Long id;
    private Boolean success;
    private String message;

}

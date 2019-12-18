package com.ens.model.location;

import java.io.Serializable;

import lombok.Data;

@Data
public class District implements Serializable {

    private Long id;
    private String districtName;

}

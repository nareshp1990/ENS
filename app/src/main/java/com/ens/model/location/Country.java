package com.ens.model.location;

import java.io.Serializable;

import lombok.Data;

@Data
public class Country implements Serializable {

    private Long id;
    private String countryName;

}

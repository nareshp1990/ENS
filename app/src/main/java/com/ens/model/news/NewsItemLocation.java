package com.ens.model.news;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsItemLocation implements Serializable {

    protected boolean isInternational;
    protected Long countryId;
    protected Long stateId;
    protected Long districtId;
    protected Long areaId;
}


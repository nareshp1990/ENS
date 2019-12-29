package com.ens.model.news;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsItemCommon extends NewsItemLocation implements Serializable {

    protected String headLine;
    protected String description;
    protected ContentType contentType;
    protected NewsType newsType;
}
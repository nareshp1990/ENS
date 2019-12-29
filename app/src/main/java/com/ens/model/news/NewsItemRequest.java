package com.ens.model.news;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsItemRequest extends NewsItemCommon implements Serializable {

    private String imageUrl;

}
package com.ens.model.news;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class NewsItemActionResponse extends NewsItemAction implements Serializable {

    private UUID newsItemId;

}

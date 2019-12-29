package com.ens.model.content;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContentResponse {


    @JsonProperty("url")
    private String url;
    @JsonProperty("status")
    private String status;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("id")
    private int id;
    @JsonProperty("downloadUrl")
    private String downloadUrl;

}

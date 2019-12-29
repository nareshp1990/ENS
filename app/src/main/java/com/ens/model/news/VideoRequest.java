package com.ens.model.news;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoRequest extends NewsItemCommon implements Serializable {

    private String thumbnailImageUrl;
    private String videoUrl;
    private String youtubeVideoId;
    private String duration;
    private String size;
    private VideoType videoType;

}
package com.ens.model.news;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NewsItem implements Serializable {

    private Long newsItemId;
    private String headLine;
    private String description;
    private String imageUrl;
    private ContentType contentType;
    private NewsType newsType;

    private String thumbnailImageUrl;
    private String videoUrl;
    private String youtubeVideoId;
    private String duration;
    private String size;
    private VideoType videoType;

    private long views;
    private long likes;
    private long unLikes;
    private long comments;
    private long whatsAppShares;
    private long facebookShares;
    private long instagramShares;
    private long helloAppShares;
    private long twitterShares;
    private long telegramShares;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdOn;
    private String createdBy;
    private String createdByProfileImageUrl;

}

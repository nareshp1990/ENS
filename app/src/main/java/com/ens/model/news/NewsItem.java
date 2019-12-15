package com.ens.model.news;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class NewsItem implements Serializable {

    private UUID newsItemId;
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

    private Long views;
    private Long likes;
    private Long unLikes;
    private Long comments;
    private Long whatsAppShares;
    private Long facebookShares;
    private Long instagramShares;
    private Long helloAppShares;
    private Long twitterShares;
    private Long telegramShares;

    private LocalDateTime createdOn;
    private String createdBy;
    private String createdByProfileImageUrl;

}

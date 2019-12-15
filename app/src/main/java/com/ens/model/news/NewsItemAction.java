package com.ens.model.news;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class NewsItemAction implements Serializable {

    private UUID newsItemId;
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
}

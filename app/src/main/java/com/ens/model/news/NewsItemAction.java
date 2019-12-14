package com.ens.model.news;

import java.io.Serializable;

import lombok.Data;

@Data
public class NewsItemAction implements Serializable {

    protected long views;
    protected long likes;
    protected long unLikes;
    protected long comments;
    protected long whatsAppShares;
    protected long facebookShares;
    protected long instagramShares;
    protected long helloAppShares;
    protected long twitterShares;
    protected long telegramShares;

}

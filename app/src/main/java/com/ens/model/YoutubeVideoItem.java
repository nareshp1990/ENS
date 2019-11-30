package com.ens.model;

public class YoutubeVideoItem {

    private String postId;
    private String videoId;
    private String title;

    public YoutubeVideoItem(String videoId, String title) {
        this.videoId = videoId;
        this.title = title;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.ens.model;

public class CarouselViewItem {

    private String postId;
    private String imageUrl;
    private String imageCaption;

    public CarouselViewItem(String imageUrl, String imageCaption) {
        this.imageUrl = imageUrl;
        this.imageCaption = imageCaption;
    }

    public CarouselViewItem() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}

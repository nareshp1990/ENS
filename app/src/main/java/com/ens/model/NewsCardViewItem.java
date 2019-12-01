package com.ens.model;

import java.io.Serializable;
import java.util.List;

public class NewsCardViewItem implements Serializable {

    private String postId;
    private String imageUrl;
    private String headLine;
    private String description;
    private int views;
    private int likes;
    private int unLikes;
    private int comments;
    private int whatsAppShares;
    private int facebookShares;
    private int helloAppShares;
    private int instagramShares;
    private List<Comment> commentList;
    private String createdOn;
    private String strCreatedOn;
    private String videoThumbnailUrl;
    private String videoUrl;
    private String videoDuration;
    private String videoSize;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getUnLikes() {
        return unLikes;
    }

    public void setUnLikes(int unLikes) {
        this.unLikes = unLikes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getWhatsAppShares() {
        return whatsAppShares;
    }

    public void setWhatsAppShares(int whatsAppShares) {
        this.whatsAppShares = whatsAppShares;
    }

    public int getFacebookShares() {
        return facebookShares;
    }

    public void setFacebookShares(int facebookShares) {
        this.facebookShares = facebookShares;
    }

    public int getHelloAppShares() {
        return helloAppShares;
    }

    public void setHelloAppShares(int helloAppShares) {
        this.helloAppShares = helloAppShares;
    }

    public int getInstagramShares() {
        return instagramShares;
    }

    public void setInstagramShares(int instagramShares) {
        this.instagramShares = instagramShares;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getStrCreatedOn() {
        return strCreatedOn;
    }

    public void setStrCreatedOn(String strCreatedOn) {
        this.strCreatedOn = strCreatedOn;
    }

    public String getVideoThumbnailUrl() {
        return videoThumbnailUrl;
    }

    public void setVideoThumbnailUrl(String videoThumbnailUrl) {
        this.videoThumbnailUrl = videoThumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoSize() {
        return videoSize;
    }

    public void setVideoSize(String videoSize) {
        this.videoSize = videoSize;
    }

    public class Comment {

        private String postId;
        private String commentId;
        private String comment;
        private String commentedOn;
        private String commentedById;
        private String commentedByName;
        private String commentedByProfileImageUrl;

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommentedOn() {
            return commentedOn;
        }

        public void setCommentedOn(String commentedOn) {
            this.commentedOn = commentedOn;
        }

        public String getCommentedById() {
            return commentedById;
        }

        public void setCommentedById(String commentedById) {
            this.commentedById = commentedById;
        }

        public String getCommentedByName() {
            return commentedByName;
        }

        public void setCommentedByName(String commentedByName) {
            this.commentedByName = commentedByName;
        }

        public String getCommentedByProfileImageUrl() {
            return commentedByProfileImageUrl;
        }

        public void setCommentedByProfileImageUrl(String commentedByProfileImageUrl) {
            this.commentedByProfileImageUrl = commentedByProfileImageUrl;
        }
    }
}

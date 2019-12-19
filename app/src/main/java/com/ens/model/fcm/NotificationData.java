package com.ens.model.fcm;

import com.ens.model.news.ContentType;
import com.ens.model.news.VideoType;
import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationData implements Serializable {

    private String title;
    private String content;
    private Long newsItemId;
    private ContentType contentType;
    private String imageUrl;
    private String thumbnailImageUrl;
    private String videoUrl;
    private VideoType videoType;
    private Long userId;
    private String smallIcon;
    private String appName;
    private LocalDateTime timeStamp;
    private String largeIcon;

    public NotificationData(RemoteMessage remoteMessage){

        Map<String, String> data = remoteMessage.getData();

        this.title = data.get("title");
        this.content = data.get("content");
//        this.newsItemId = Long.parseLong(data.get("newsItemId"));
//        this.contentType = ContentType.valueOf(data.get("contentType"));
        this.imageUrl = data.get("imageUrl");
        if(data.get("thumbnailImageUrl")!=null && !data.get("thumbnailImageUrl").isEmpty()){
            this.imageUrl = data.get("thumbnailImageUrl");
        }
//        this.videoUrl = data.get("videoUrl");
//        this.videoType = VideoType.valueOf(data.get("videoType"));
//        this.userId = Long.parseLong(data.get("userId"));
//        this.smallIcon = data.get("smallIcon");
//        this.appName = data.get("appName");
//        this.timeStamp = LocalDateTime.parse(data.get("timeStamp"));
//        this.largeIcon = data.get("largeIcon");

    }

}

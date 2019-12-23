package com.ens.model.fcm;

import com.ens.model.news.ContentType;
import com.google.firebase.messaging.RemoteMessage;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationData implements Serializable {

    private String title;
    private String content;
    private Long newsItemId;
    private String imageUrl;
    private Long userId;
    private ContentType contentType;

    public NotificationData(RemoteMessage remoteMessage) {

        RemoteMessage.Notification notification = remoteMessage.getNotification();

        this.title = notification.getTitle();
        this.content = notification.getBody();

        if (notification.getImageUrl() != null) {
            this.imageUrl = notification.getImageUrl().toString();
        }

        if (remoteMessage.getData() != null) {

            Map<String, String> data = remoteMessage.getData();

            this.newsItemId = Long.parseLong(data.get("newsItemId") == null ? "0" : data.get("newsItemId"));
            this.userId = Long.parseLong(data.get("userId") == null ? "0" : data.get("userId"));
            this.contentType = ContentType.valueOf(data.get("contentType"));

        }

    }

}

package com.ens.bus;

import com.ens.adapters.NewsCardViewAdapter;
import com.ens.adapters.VideoViewAdapter;
import com.ens.model.news.NewsItemAction;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsActionEvent {

    private NewsItemAction newsItemAction;
    private NewsCardViewAdapter.NewsCardViewHolder newsCardViewHolder;
    private VideoViewAdapter.VideoViewHolder videoViewHolder;


    public NewsActionEvent(NewsItemAction newsItemAction) {
        this.newsItemAction = newsItemAction;
    }

    public NewsActionEvent(NewsItemAction newsItemAction, NewsCardViewAdapter.NewsCardViewHolder newsCardViewHolder) {
        this.newsItemAction = newsItemAction;
        this.newsCardViewHolder = newsCardViewHolder;
    }

    public NewsActionEvent(NewsItemAction newsItemAction, VideoViewAdapter.VideoViewHolder videoViewHolder) {
        this.newsItemAction = newsItemAction;
        this.videoViewHolder = videoViewHolder;
    }
}

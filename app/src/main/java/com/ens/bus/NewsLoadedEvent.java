package com.ens.bus;

import com.ens.model.api.PagedResponse;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsLoadedEvent {

    private ContentType contentType;
    private PagedResponse<NewsItem> newsItemPagedResponse;
    private String scrollText;
    private NewsItem newsItem;

    public NewsLoadedEvent(ContentType contentType, String scrollText) {
        this.contentType = contentType;
        this.scrollText = scrollText;
    }

    public NewsLoadedEvent(ContentType contentType, PagedResponse<NewsItem> newsItemPagedResponse) {
        this.contentType = contentType;
        this.newsItemPagedResponse = newsItemPagedResponse;
    }

    public NewsLoadedEvent(NewsItem newsItem) {
        this.newsItem = newsItem;
    }
}

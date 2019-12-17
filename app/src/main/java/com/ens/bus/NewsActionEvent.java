package com.ens.bus;

import com.ens.adapters.NewsCardViewAdapter;
import com.ens.model.news.NewsItemAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsActionEvent {

    private NewsItemAction newsItemAction;
    private NewsCardViewAdapter.NewsCardViewHolder newsCardViewHolder;

}

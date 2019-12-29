package com.ens.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.model.news.NewsItem;
import com.zhpan.bannerview.holder.ViewHolder;

public class BannerViewHolder implements ViewHolder<NewsItem> {

    @Override
    public int getLayoutId() {
        return R.layout.main_page_banner_item;
    }

    @Override
    public void onBind(View itemView, NewsItem data, int position, int size) {

        ImageView imageView = itemView.findViewById(R.id.banner_image);
        TextView textView = itemView.findViewById(R.id.tv_describe);
        textView.setText(data.getHeadLine());
        textView.setMaxLines(1);
        Glide.with(imageView).load(data.getImageUrl()).into(imageView);

    }
}

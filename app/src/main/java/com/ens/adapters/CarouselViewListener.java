package com.ens.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.activities.NewsCardDetailedActivity;
import com.ens.config.ENSApplication;
import com.ens.model.news.ActionType;
import com.ens.model.news.NewsItem;
import com.ens.service.NewsService;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import java.util.List;

public class CarouselViewListener implements ViewListener, ImageClickListener {

    private List<NewsItem> carouselViewItems;
    private Context context;
    private NewsService newsService;

    public CarouselViewListener(List<NewsItem> carouselViewItems, Context context, NewsService newsService) {
        this.carouselViewItems = carouselViewItems;
        this.context = context;
        this.newsService = newsService;
    }

    @Override
    public View setViewForPosition(int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.main_page_carousel_img_card, null);

        ImageView imgCarousel = view.findViewById(R.id.imgCarousel);
        TextView txtImageCaption = view.findViewById(R.id.txtImageCaption);

        Glide.with(context).load(carouselViewItems.get(position).getImageUrl()).into(imgCarousel);
        txtImageCaption.setText(carouselViewItems.get(position).getHeadLine());

        return view;
    }


    @Override
    public void onClick(int position) {

        Intent intent = new Intent(context, NewsCardDetailedActivity.class);
        intent.putExtra("newsItemId", carouselViewItems.get(position).getNewsItemId());
        context.startActivity(intent);

        newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), carouselViewItems.get(position).getNewsItemId(), ActionType.VIEW);
    }
}

package com.ens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.model.news.NewsItem;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import java.util.List;

public class CarouselViewListener implements ViewListener, ImageClickListener {

    private List<NewsItem> carouselViewItems;
    private Context context;

    public CarouselViewListener(List<NewsItem> carouselViewItems, Context context) {
        this.carouselViewItems = carouselViewItems;
        this.context = context;
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

        Toast.makeText(context, "Clicked item: " + carouselViewItems.get(position).getHeadLine(), Toast.LENGTH_SHORT).show();

    }
}

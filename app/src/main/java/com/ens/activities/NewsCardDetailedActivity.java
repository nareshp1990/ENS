package com.ens.activities;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.model.news.NewsItem;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NewsCardDetailedActivity extends AppCompatActivity {

    @BindView(R.id.imgDetailedView)
    private ImageView imgDetailedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_card_detailed);
        ButterKnifeLite.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        NewsItem news_card_item = (NewsItem) getIntent().getSerializableExtra("news_card_item");

        Glide.with(this).load(news_card_item.getImageUrl()).into(imgDetailedView);
    }
}

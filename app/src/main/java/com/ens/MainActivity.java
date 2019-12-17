package com.ens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ens.adapters.CarouselViewListener;
import com.ens.adapters.NewsCardViewAdapter;
import com.ens.adapters.VideoViewAdapter;
import com.ens.adapters.YoutubeVideoAdapter;
import com.ens.bus.NewsLoadedEvent;
import com.ens.config.ENSApplication;
import com.ens.exception.ApiErrorEvent;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItem;
import com.ens.nav.drawer.DrawerHeader;
import com.ens.nav.drawer.DrawerMenuItem;
import com.ens.service.NewsService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.synnapps.carouselview.CarouselView;

import java.util.List;
import java.util.UUID;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getCanonicalName();

    @BindView(R.id.drawerView)
    private PlaceHolderView mDrawerView;

    @BindView(R.id.drawerLayout)
    private DrawerLayout mDrawer;

    @BindView(R.id.toolbar)
    private Toolbar mToolbar;

    @BindView(R.id.scrollTextView)
    private TextView scrollTextView;

    @BindView(R.id.mainPageCarouselView)
    private CarouselView mainPageCarouselView;

    @BindView(R.id.youtubeThumbnailRecyclerView)
    private RecyclerView youtubeThumbnailRecyclerView;

    @BindView(R.id.newsCardRecyclerView)
    private RecyclerView newsCardRecyclerView;

    @BindView(R.id.newsPollRecyclerView)
    private RecyclerView newsPollRecyclerView;

    @BindView(R.id.ensVideoRecyclerView)
    private RecyclerView ensVideoRecyclerView;

    @BindView(R.id.fabPostNews)
    private FloatingActionButton fabPostNews;

    private NewsService newsService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnifeLite.bind(this);
        hideToolbarTitle();
        setupDrawer();

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.w(TAG, "getInstanceId failed", task.getException());
                return;
            }

            // Get new Instance ID token
            String token = task.getResult().getToken();

            Log.d(TAG, "### Firebase Token: " + token);

        });

        newsService = new NewsService(this);

        initializePage();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ENSApplication.activityPaused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ENSApplication.activityResumed();
    }

    public void onEvent(Boolean isInternetAvailable) {
        Toast.makeText(this, isInternetAvailable ? "Internet Available" : "Internet Not Available", Toast.LENGTH_SHORT).show();
    }

    public void hideToolbarTitle() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(null);
        mToolbar.setTitle("");
        mToolbar.setSubtitle("");
        mToolbar.setLogo(null);
    }

    private void setupDrawer() {

        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TERMS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        //remove drawer icon
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setHomeAsUpIndicator(null);
    }

    private void initializePage() {

        scrollTextView.setSelected(true);

        newsService.getNewsScrollText(UUID.fromString("244d5bdf-904c-43f1-9781-c5d8b19821ed"), 0, 5);
        newsService.getAllNewsItems(UUID.fromString("244d5bdf-904c-43f1-9781-c5d8b19821ed"), ContentType.IMAGE_SLIDER, 0, 8);
        newsService.getAllNewsItems(UUID.fromString("244d5bdf-904c-43f1-9781-c5d8b19821ed"), ContentType.YOUTUBE, 0, 15);
        newsService.getAllNewsItems(UUID.fromString("244d5bdf-904c-43f1-9781-c5d8b19821ed"), ContentType.IMAGE, 0, 5);
        newsService.getAllNewsItems(UUID.fromString("244d5bdf-904c-43f1-9781-c5d8b19821ed"), ContentType.VIDEO, 0, 5);

        /*
        newsPollRecyclerView.setAdapter(new PollCardViewAdapter(this, preparePollCardItems()));
        newsPollRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));*/

        fabPostNews.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Create News", Toast.LENGTH_SHORT).show());

    }

    public void onEvent(NewsLoadedEvent newsLoadedEvent) {

        switch (newsLoadedEvent.getContentType()) {

            case SCROLL: {
                scrollTextView.setText(newsLoadedEvent.getScrollText());
            }
            break;

            case IMAGE_SLIDER: {
                List<NewsItem> newsItems = newsLoadedEvent.getNewsItemPagedResponse().getContent();
                CarouselViewListener carouselViewListener = new CarouselViewListener(newsItems, this);
                mainPageCarouselView.setViewListener(carouselViewListener);
                mainPageCarouselView.setPageCount(newsItems.size());
                mainPageCarouselView.setImageClickListener(carouselViewListener);
            }
            break;

            case YOUTUBE: {
                youtubeThumbnailRecyclerView.setAdapter(new YoutubeVideoAdapter(newsLoadedEvent.getNewsItemPagedResponse().getContent(), this));
                youtubeThumbnailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            }
            break;

            case IMAGE: {
                newsCardRecyclerView.setAdapter(new NewsCardViewAdapter(this, newsLoadedEvent.getNewsItemPagedResponse().getContent(),newsService));
                newsCardRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            }
            break;

            case VIDEO: {
                ensVideoRecyclerView.setAdapter(new VideoViewAdapter(this, newsLoadedEvent.getNewsItemPagedResponse().getContent()));
                ensVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            }
            break;

        }

    }

    public void onApiError(ApiErrorEvent event) {
        Log.e(TAG, event.toString());
        Toast.makeText(this, "Something went wrong, please try again.", Toast.LENGTH_SHORT).show();
    }

}

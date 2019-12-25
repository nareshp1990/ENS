package com.ens;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ens.adapters.CarouselViewListener;
import com.ens.adapters.NewsCardViewAdapter;
import com.ens.adapters.PollCardViewAdapter;
import com.ens.adapters.VideoViewAdapter;
import com.ens.adapters.YoutubeVideoAdapter;
import com.ens.bus.FCMKeyUpdateEvent;
import com.ens.bus.NewsLoadedEvent;
import com.ens.bus.PollsLoadedEvent;
import com.ens.config.ENSApplication;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItem;
import com.ens.nav.drawer.DrawerHeader;
import com.ens.nav.drawer.DrawerMenuItem;
import com.ens.service.NewsService;
import com.ens.service.PollService;
import com.ens.service.UserService;
import com.ens.utils.NetworkState;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.placeholderview.PlaceHolderView;
import com.synnapps.carouselview.CarouselView;

import java.util.List;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

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

    @BindView(R.id.mainPageSwipeToRefresh)
    private SwipeRefreshLayout mainPageSwipeToRefresh;

    private NewsService newsService;

    private UserService userService;

    private PollService pollService;

    private EventBus eventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnifeLite.bind(this);
        hideToolbarTitle();
        setupDrawer();
        scrollTextView.setSelected(true);
        mainPageSwipeToRefresh.setOnRefreshListener(this);

        newsService = new NewsService(this);
        userService = new UserService(this);
        pollService = new PollService(this);

        if (NetworkState.isInternetAvailable(getApplicationContext())) {
            updateUserFCMKey();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
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

        if (NetworkState.isInternetAvailable(getApplicationContext())) {
            initializePage();
        }

    }

    private void updateUserFCMKey() {

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.w(TAG, "getInstanceId failed", task.getException());
                return;
            }

            // Get new Instance ID token
            String token = task.getResult().getToken();

            Log.d(TAG, "### Firebase Token: " + token);

            userService.updateUserFCMKey(ENSApplication.getLoggedInUserId(), token);

        });

    }

    public void onEvent(Boolean isInternetAvailable) {
        Toast.makeText(this, isInternetAvailable ? "Internet Available" : "Internet Not Available", Toast.LENGTH_SHORT).show();
        if (isInternetAvailable) {
            initializePage();
        }
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
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE, this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS, this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE, this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS, this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS, this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TERMS, this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS, this))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT, this));

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

        newsService.getNewsScrollText(ENSApplication.getLoggedInUserId(), 0, 5);
        newsService.getAllNewsItems(ENSApplication.getLoggedInUserId(), ContentType.IMAGE_SLIDER, 0, 0, 8);
        newsService.getAllNewsItems(ENSApplication.getLoggedInUserId(), ContentType.YOUTUBE, 0, 0, 15);
        newsService.getAllNewsItems(ENSApplication.getLoggedInUserId(), ContentType.IMAGE, 0, 0, 5);
        newsService.getAllNewsItems(ENSApplication.getLoggedInUserId(), ContentType.VIDEO, 0, 0, 5);
        pollService.getPolls(ENSApplication.getLoggedInUserId(), 0, 5);

        fabPostNews.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Create News", Toast.LENGTH_SHORT).show());

    }

    public void onEvent(NewsLoadedEvent newsLoadedEvent) {

        if (newsLoadedEvent == null || newsLoadedEvent.getContentType() == null) {
            return;
        }

        switch (newsLoadedEvent.getContentType()) {

            case SCROLL: {

                if (newsLoadedEvent.getScrollText() != null && !newsLoadedEvent.getScrollText().isEmpty()) {

                    scrollTextView.setText(newsLoadedEvent.getScrollText());

                }

            }
            break;

            case IMAGE_SLIDER: {

                if (newsLoadedEvent.getNewsItemPagedResponse() != null && newsLoadedEvent.getNewsItemPagedResponse().getContent() != null) {

                    List<NewsItem> newsItems = newsLoadedEvent.getNewsItemPagedResponse().getContent();
                    CarouselViewListener carouselViewListener = new CarouselViewListener(newsItems, this, newsService);
                    mainPageCarouselView.setViewListener(carouselViewListener);
                    mainPageCarouselView.setPageCount(newsItems.size());
                    mainPageCarouselView.setImageClickListener(carouselViewListener);

                }

            }
            break;

            case YOUTUBE: {

                if (newsLoadedEvent.getNewsItemPagedResponse() != null && newsLoadedEvent.getNewsItemPagedResponse().getContent() != null) {

                    youtubeThumbnailRecyclerView.setAdapter(new YoutubeVideoAdapter(newsLoadedEvent.getNewsItemPagedResponse().getContent(), this));
                    youtubeThumbnailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

                }

            }
            break;

            case IMAGE: {

                if (newsLoadedEvent.getNewsItemPagedResponse() != null && newsLoadedEvent.getNewsItemPagedResponse().getContent() != null) {

                    newsCardRecyclerView.setAdapter(new NewsCardViewAdapter(this, newsLoadedEvent.getNewsItemPagedResponse().getContent(), newsService));
                    newsCardRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

                }

            }
            break;

            case VIDEO: {

                if (newsLoadedEvent.getNewsItemPagedResponse() != null && newsLoadedEvent.getNewsItemPagedResponse().getContent() != null) {

                    ensVideoRecyclerView.setAdapter(new VideoViewAdapter(this, newsLoadedEvent.getNewsItemPagedResponse().getContent(), newsService));
                    ensVideoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

                }

            }
            break;

        }

    }

    public void onEvent(FCMKeyUpdateEvent event) {
        Log.d(TAG, "### Firebase Token Updated : " + event);
    }

    public void onEvent(PollsLoadedEvent pollsLoadedEvent) {

        if (pollsLoadedEvent != null && pollsLoadedEvent.getPollPagedResponse() != null && pollsLoadedEvent.getPollPagedResponse().getContent() != null) {

            newsPollRecyclerView.setAdapter(new PollCardViewAdapter(this, pollsLoadedEvent.getPollPagedResponse().getContent(), pollService));
            newsPollRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        }

    }

    @Override
    public void onRefresh() {
        //Do your work here
        initializePage();
        mainPageSwipeToRefresh.setRefreshing(false);
    }
}

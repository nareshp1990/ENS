package com.ens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ens.R;
import com.ens.adapters.NewsDetailedViewAdapter;
import com.ens.adapters.listeners.PaginationScrollListener;
import com.ens.bus.NewsActionEvent;
import com.ens.bus.NewsLoadedEvent;
import com.ens.config.ENSApplication;
import com.ens.model.api.PagedResponse;
import com.ens.model.news.ActionType;
import com.ens.model.news.ContentType;
import com.ens.model.news.NewsItem;
import com.ens.service.NewsService;
import com.ens.utils.AppUtils;
import com.ens.utils.DateUtils;
import com.github.abdularis.civ.CircleImageView;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import java.util.HashSet;
import java.util.Set;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsCardDetailedActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = NewsCardDetailedActivity.class.getCanonicalName();

    private EventBus eventBus = EventBus.getDefault();

    @BindView(R.id.coordinatorLayout)
    private CoordinatorLayout coordinatorLayout;

    @BindView(R.id.imgDetailedView)
    private ImageView imgDetailedView;

    @BindView(R.id.txtNewsCardHeadline)
    private TextView txtNewsCardHeadline;

    @BindView(R.id.createdByCircleImageView)
    private CircleImageView createdByCircleImageView;

    @BindView(R.id.txtNewsCardPostedBy)
    private TextView txtNewsCardPostedBy;

    @BindView(R.id.txtNewsCardCreatedOn)
    private TextView txtNewsCardCreatedOn;

    @BindView(R.id.txtNewsCardDetailedViewDescription)
    private TextView txtNewsCardDetailedViewDescription;


    @BindView(R.id.txtNewsCardViewsCount)
    private TextView txtNewsCardViewsCount;
    @BindView(R.id.txtNewsCardLikeCount)
    private TextView txtNewsCardLikeCount;
    @BindView(R.id.txtNewsCardUnLikeCount)
    private TextView txtNewsCardUnLikeCount;
    @BindView(R.id.txtNewsCardCommentsCount)
    private TextView txtNewsCardCommentsCount;
    @BindView(R.id.txtNewsCardWhatsAppShareCount)
    private TextView txtNewsCardWhatsAppShareCount;
    @BindView(R.id.txtNewsCardFacebookShareCount)
    private TextView txtNewsCardFacebookShareCount;
    @BindView(R.id.txtNewsCardHelloAppShareCount)
    private TextView txtNewsCardHelloAppShareCount;
    @BindView(R.id.txtNewsCardInstagramShareCount)
    private TextView txtNewsCardInstagramShareCount;

    @BindView(R.id.layoutLike)
    private LinearLayout layoutLike;
    @BindView(R.id.layoutUnLike)
    private LinearLayout layoutUnLike;
    @BindView(R.id.layoutComments)
    private LinearLayout layoutComments;
    @BindView(R.id.layoutWhatsappShare)
    private LinearLayout layoutWhatsappShare;
    @BindView(R.id.layoutFacebookShare)
    private LinearLayout layoutFacebookShare;
    @BindView(R.id.layoutInstagramShare)
    private LinearLayout layoutInstagramShare;
    @BindView(R.id.layoutHelloAppShare)
    private LinearLayout layoutHelloAppShare;

    /*@BindView(R.id.swipeToRefresh)
    private SwipeRefreshLayout swipeToRefresh;*/

    @BindView(R.id.newsCardRecyclerView)
    private RecyclerView newsCardRecyclerView;

    @BindView(R.id.progress_bar)
    private ProgressBar progressBar;

    private NewsService newsService;

    private long newsItemId;
    private NewsItem newsItem;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 0;
    private NewsDetailedViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_card_detailed);
        ButterKnifeLite.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(false);
        ab.setTitle("");

        setListeners();

        newsService = new NewsService(this);

        setRecyclerView();

    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsCardRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new NewsDetailedViewAdapter(this);
        newsCardRecyclerView.setAdapter(adapter);

        newsCardRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                if (!isLastPage) {
                    new Handler().postDelayed(() -> loadData(page), 200);
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

        });

        adapter.setOnItemClickListener((view, newsItemId) -> {

            load(newsItemId);

            newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, ActionType.VIEW);

        });

    }

    private void setListeners() {

        layoutLike.setOnClickListener(this);
        layoutComments.setOnClickListener(this);
        layoutFacebookShare.setOnClickListener(this);
        layoutHelloAppShare.setOnClickListener(this);
        layoutInstagramShare.setOnClickListener(this);
        layoutWhatsappShare.setOnClickListener(this);
        layoutUnLike.setOnClickListener(this);
        imgDetailedView.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        newsItemId = intent.getLongExtra("newsItemId", 0);
        Log.d(TAG, "### NewsItemId :  " + newsItemId);

        load(newsItemId);

        loadData(page);

    }

    private void load(long newsItemId) {
        newsService.getNewsItemById(ENSApplication.getLoggedInUserId(), newsItemId);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        newsItemId = intent.getLongExtra("newsItemId", 0);
        Log.d(TAG, "### NewsItemId :  " + newsItemId);

        load(newsItemId);

        loadData(page);
    }

    public void onEvent(NewsLoadedEvent newsLoadedEvent) {

        if (newsLoadedEvent != null && newsLoadedEvent.getNewsItem() != null) {

            NewsItem newsItem = newsLoadedEvent.getNewsItem();
            this.newsItem = newsItem;

            Glide.with(this).load(newsItem.getImageUrl()).into(imgDetailedView);

            txtNewsCardHeadline.setText(newsItem.getHeadLine());
            txtNewsCardDetailedViewDescription.setText(newsItem.getDescription());
            txtNewsCardCreatedOn.setText(DateUtils.asPrettyDateTime(newsItem.getCreatedOn()));
            txtNewsCardPostedBy.setText(newsItem.getCreatedBy());

            Glide.with(this).load(newsItem.getCreatedByProfileImageUrl()).into(createdByCircleImageView);

            txtNewsCardViewsCount.setText(String.valueOf(newsItem.getViews()));
            txtNewsCardLikeCount.setText(String.valueOf(newsItem.getLikes()));
            txtNewsCardUnLikeCount.setText(String.valueOf(newsItem.getUnLikes()));
            txtNewsCardCommentsCount.setText(String.valueOf(newsItem.getComments()));
            txtNewsCardWhatsAppShareCount.setText(String.valueOf(newsItem.getWhatsAppShares()));
            txtNewsCardFacebookShareCount.setText(String.valueOf(newsItem.getFacebookShares()));
            txtNewsCardInstagramShareCount.setText(String.valueOf(newsItem.getInstagramShares()));
            txtNewsCardHelloAppShareCount.setText(String.valueOf(newsItem.getHelloAppShares()));

        }

    }

    public void onEvent(NewsActionEvent newsActionEvent) {

        txtNewsCardViewsCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getViews()));
        txtNewsCardLikeCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getLikes()));
        txtNewsCardUnLikeCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getUnLikes()));
        txtNewsCardCommentsCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getComments()));
        txtNewsCardWhatsAppShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getWhatsAppShares()));
        txtNewsCardFacebookShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getFacebookShares()));
        txtNewsCardInstagramShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getInstagramShares()));
        txtNewsCardHelloAppShareCount.setText(String.valueOf(newsActionEvent.getNewsItemAction().getHelloAppShares()));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layoutLike: {
                newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, ActionType.LIKE);
            }
            break;
            case R.id.layoutUnLike: {
                newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, ActionType.UNLIKE);

            }
            break;
            case R.id.layoutComments: {

                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("newsItemId",newsItemId);
                startActivity(intent);

            }
            break;
            case R.id.layoutWhatsappShare: {
                AppUtils.launchShareIntent(this, coordinatorLayout, newsItem);
                newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, ActionType.WHATSAPP);

            }
            break;
            case R.id.layoutFacebookShare: {
                AppUtils.launchShareIntent(this, coordinatorLayout, newsItem);
                newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, ActionType.FACEBOOK);

            }
            break;
            case R.id.layoutInstagramShare: {
                AppUtils.launchShareIntent(this, coordinatorLayout, newsItem);
                newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, ActionType.INSTAGRAM);

            }
            break;
            case R.id.layoutHelloAppShare: {
                AppUtils.launchShareIntent(this, coordinatorLayout, newsItem);
                newsService.postNewsItemAction(ENSApplication.getLoggedInUserId(), newsItemId, ActionType.HELLO_APP);
            }
            break;
            case R.id.imgDetailedView: {
                Intent fullScreenImageViewIntent = new Intent(this, FullScreenImageActivity.class);
                fullScreenImageViewIntent.putExtra("image_url",newsItem.getImageUrl());
                startActivity(fullScreenImageViewIntent);
            }
            break;

        }

    }

    private void loadData(int page) {

        progressBar.setVisibility(View.VISIBLE);

        Set<ContentType> contentTypes = new HashSet<>();
        contentTypes.add(ContentType.IMAGE);
        contentTypes.add(ContentType.IMAGE_SLIDER);

        Call<PagedResponse<NewsItem>> allNewsItems = ENSApplication.getNewsApi().getAllNewsItems(ENSApplication.getLoggedInUserId(), contentTypes, newsItemId, page, 30);

        allNewsItems.enqueue(new Callback<PagedResponse<NewsItem>>() {
            @Override
            public void onResponse(Call<PagedResponse<NewsItem>> call, Response<PagedResponse<NewsItem>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        PagedResponse<NewsItem> newsItemPagedResponse = response.body();
                        resultAction(newsItemPagedResponse);

                    }
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<NewsItem>> call, Throwable t) {

            }
        });

    }

    private void resultAction(PagedResponse<NewsItem> newsItemPagedResponse) {
        progressBar.setVisibility(View.INVISIBLE);
        isLoading = false;
        if (newsItemPagedResponse != null) {
            adapter.addItems(newsItemPagedResponse.getContent());
            if (newsItemPagedResponse.getPage() + 1 == newsItemPagedResponse.getTotalPages()) {
                isLastPage = true;
            } else {
                page = newsItemPagedResponse.getPage() + 1;
            }
        }
    }

}

package com.ens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ens.R;
import com.ens.adapters.CommentViewAdapter;
import com.ens.adapters.listeners.PaginationScrollListener;
import com.ens.bus.CommentPostedEvent;
import com.ens.config.ENSApplication;
import com.ens.model.api.PagedResponse;
import com.ens.model.news.comment.Comment;
import com.ens.service.NewsService;
import com.google.android.material.textfield.TextInputEditText;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    public static final String TAG = CommentActivity.class.getCanonicalName();

    @BindView(R.id.toolbar)
    private Toolbar toolbar;

    @BindView(R.id.swipeToRefreshLayout)
    private SwipeRefreshLayout swipeToRefreshLayout;

    @BindView(R.id.commentsRecyclerView)
    private RecyclerView commentsRecyclerView;

    @BindView(R.id.progress_bar)
    private ProgressBar progressBar;

    @BindView(R.id.txtComment)
    private TextInputEditText txtComment;

    @BindView(R.id.btnPostComment)
    private Button btnPostComment;

    private EventBus eventBus = EventBus.getDefault();

    private NewsService newsService;

    private long newsItemId;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int page = 0;

    private CommentViewAdapter adapter;

    private boolean onRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnifeLite.bind(this);
        newsService = new NewsService(this);
        swipeToRefreshLayout.setOnRefreshListener(this);
        btnPostComment.setOnClickListener(this);

        setRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        newsItemId = intent.getLongExtra("newsItemId", 0);
        Log.d(TAG, "### NewsItemId :  " + newsItemId);

        page = 0;
        onRefresh = true;
        loadData(page);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        newsItemId = intent.getLongExtra("newsItemId", 0);
        Log.d(TAG, "### NewsItemId :  " + newsItemId);

        page = 0;
        onRefresh = true;
        loadData(page);

    }

    @Override
    public void onRefresh() {
        page = 0;
        onRefresh = true;
        loadData(page);
    }

    private void setRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        commentsRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new CommentViewAdapter(this);
        commentsRecyclerView.setAdapter(adapter);

        commentsRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

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

    }


    private void loadData(int page) {

        progressBar.setVisibility(View.VISIBLE);

        Call<PagedResponse<Comment>> allComments = ENSApplication.getNewsApi().getAllComments(newsItemId, page, 50);

        allComments.enqueue(new Callback<PagedResponse<Comment>>() {
            @Override
            public void onResponse(Call<PagedResponse<Comment>> call, Response<PagedResponse<Comment>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        PagedResponse<Comment> commentPagedResponse = response.body();

                        resultAction(commentPagedResponse);

                    }
                }
            }

            @Override
            public void onFailure(Call<PagedResponse<Comment>> call, Throwable t) {

            }
        });

    }

    private void resultAction(PagedResponse<Comment> commentPagedResponse) {
        progressBar.setVisibility(View.INVISIBLE);
        isLoading = false;
        if (commentPagedResponse != null) {
            if (onRefresh) {
                adapter.clearItems();
                onRefresh = false;
                swipeToRefreshLayout.setRefreshing(false);
            }
            adapter.addItems(commentPagedResponse.getContent());
            if (commentPagedResponse.getPage() + 1 == commentPagedResponse.getTotalPages()) {
                isLastPage = true;
            } else {
                page = commentPagedResponse.getPage() + 1;
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnPostComment: {

                String userComment = txtComment.getText().toString();

                newsService.postComment(ENSApplication.getLoggedInUserId(), newsItemId, userComment);

                txtComment.setText("");

            }
            break;
        }
    }

    public void onEvent(CommentPostedEvent commentPostedEvent) {

        if (commentPostedEvent != null && commentPostedEvent.getApiResponse() != null) {

            Toast.makeText(this, "Comment Posted Successfully", Toast.LENGTH_LONG).show();
            page = 0;
            onRefresh = true;
            loadData(page);

        }

    }
}

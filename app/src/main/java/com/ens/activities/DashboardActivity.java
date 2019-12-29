package com.ens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.ens.R;
import com.ens.adapters.DashboardAdapter;
import com.ens.model.dashboard.DashboardItem;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DashboardActivity extends AppCompatActivity implements DashboardAdapter.OnItemClickListener {

    public static final String TAG = DashboardActivity.class.getCanonicalName();

    @BindView(R.id.dashboardRecyclerView)
    private RecyclerView dashboardRecyclerView;

    private DashboardAdapter dashboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnifeLite.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        dashboardAdapter = new DashboardAdapter(getPrepareDashboardItems(), this, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false);

        dashboardRecyclerView.setLayoutManager(gridLayoutManager);
        dashboardRecyclerView.setAdapter(dashboardAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    public List<DashboardItem> getPrepareDashboardItems() {

        List<DashboardItem> dashboardItems = new ArrayList<>();

        DashboardItem news = new DashboardItem();
        news.setDescription("Create News");
        dashboardItems.add(news);

        DashboardItem job = new DashboardItem();
        job.setDescription("Job Notification");
        dashboardItems.add(job);

        DashboardItem greetings = new DashboardItem();
        greetings.setDescription("Greetings");
        dashboardItems.add(greetings);

        DashboardItem ads = new DashboardItem();
        ads.setDescription("Ads");
        dashboardItems.add(ads);

        DashboardItem realEstate = new DashboardItem();
        realEstate.setDescription("Real Estate");
        dashboardItems.add(realEstate);

        DashboardItem events = new DashboardItem();
        events.setDescription("Events");
        dashboardItems.add(events);

        DashboardItem others = new DashboardItem();
        others.setDescription("Others");
        dashboardItems.add(others);

        DashboardItem feedback = new DashboardItem();
        feedback.setDescription("Feedback");
        dashboardItems.add(feedback);

        return dashboardItems;
    }

    @Override
    public void onItemClick(DashboardItem item) {

        startActivity(new Intent(this, CreateNewsActivity.class));

    }
}


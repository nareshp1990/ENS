package com.ens.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ens.R;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenImageActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = FullScreenImageActivity.class.getCanonicalName();

    private ImageView imageView;
    private boolean toggle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(this);

        PackageManager packageManager = getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(sendIntent, 0);

        resolveInfos.stream().forEach(resolveInfo -> {

            String packageName = resolveInfo.activityInfo.packageName;

            Log.d(TAG, "### package name : " + packageName );
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        showImage(intent.getStringExtra("image_url"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImage(getIntent().getStringExtra("image_url"));
    }

    private void showImage(String imageUrl) {

        Glide.with(this).load(imageUrl).into(imageView);

    }

    @Override
    public void onClick(View v) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (toggle) {

            toggle = false;

            imageView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        } else {

            toggle = true;

            imageView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

}

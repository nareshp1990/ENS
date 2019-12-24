package com.ens.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import com.ens.BuildConfig;
import com.ens.model.news.NewsItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidx.core.content.FileProvider;

public class AppUtils {

    /**
     * Gets the version name of the application. For e.g. 1.9.3
     **/
    public static String getApplicationVersionNumber(Context context) {

        String versionName = null;

        if (context == null) {
            return versionName;
        }

        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * Gets the version code of the application. For e.g. Maverick Meerkat or 2013050301
     **/
    public static int getApplicationVersionCode(Context ctx) {

        int versionCode = 0;

        try {
            versionCode = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    /**
     * Gets the version number of the Android OS For e.g. 2.3.4 or 4.1.2
     **/
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }


    public static int getRandomIntegerBetweenRange(double min, double max) {
        double x = (int) (Math.random() * ((max - min) + 1)) + min;
        return (int) x;
    }

    public static Uri getLocalBitmapUri(Context context, Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                bmpUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            } else {
                bmpUri = Uri.fromFile(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public static Uri screenShot(Context context, View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        return AppUtils.getLocalBitmapUri(context, bitmap);
    }

    public static void launchShareIntent(Context context, View view, NewsItem newsItem) {

        Uri uri = AppUtils.screenShot(context, view);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/*");
        sendIntent.putExtra(Intent.EXTRA_TEXT, newsItem.getHeadLine());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, newsItem.getHeadLine());
        // (Optional) Here we're setting the title of the content
        sendIntent.putExtra(Intent.EXTRA_TITLE, newsItem.getHeadLine());

        // (Optional) Here we're passing a content URI to an image to be displayed
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Show the Sharesheet
        context.startActivity(Intent.createChooser(sendIntent, "Share News Link"));

    }
}

package com.ens.config;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ens.MainActivity;
import com.ens.R;
import com.ens.activities.NewsCardDetailedActivity;
import com.ens.bus.FCMKeyUpdateEvent;
import com.ens.model.fcm.NotificationData;
import com.ens.service.UserService;
import com.ens.utils.AppUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import de.greenrobot.event.EventBus;


public class ENSFirebaseMessagingService extends FirebaseMessagingService {


    public static final String TAG = ENSFirebaseMessagingService.class.getCanonicalName();

    private UserService userService;

    private EventBus eventBus = EventBus.getDefault();

    public ENSFirebaseMessagingService() {

        eventBus.register(this);
        this.userService = new UserService(ENSApplication.getAppContext());

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "### Firebase Token: " + token);
        userService.updateUserFCMKey(ENSApplication.getLoggedInUserId(), token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "### Firebase Message Received: " + remoteMessage);

        final NotificationData notificationData = new NotificationData(remoteMessage);

        Handler uiHandler = new Handler(Looper.getMainLooper());

        uiHandler.post(() -> {

            Picasso.get()
                    .load(notificationData.getImageUrl())
                    .into(new Target() {

                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            sendNotification(bitmap, notificationData);
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        });

    }


    public void onEvent(FCMKeyUpdateEvent event) {
        Log.d(TAG, "### Firebase Token Updated : " + event);
    }

    private void sendNotification(Bitmap bitmap, final NotificationData notificationData) {

        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(bitmap);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent();

        if (notificationData.getContentType() != null) {

            switch (notificationData.getContentType()){
                case IMAGE: {
                    intent.setClass(getApplicationContext(), NewsCardDetailedActivity.class);
                }
                break;
                default: {
                    intent.setClass(getApplicationContext(), MainActivity.class);
                }
                break;
            }

        }else {
            intent.setClass(getApplicationContext(),MainActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("newsItemId",notificationData.getNewsItemId());
        intent.putExtra("userId",notificationData.getUserId());
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "102";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "eeroju news", NotificationManager.IMPORTANCE_DEFAULT);

            //Configure Notification Channel
            notificationChannel.setDescription(notificationData.getContent());
            notificationChannel.enableLights(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(notificationData.getTitle())
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(notificationData.getContent())
                .setContentIntent(pendingIntent)
                .setStyle(style)
                .setLargeIcon(bitmap)
                .setWhen(System.currentTimeMillis());

        notificationManager.notify(AppUtils.getRandomIntegerBetweenRange(10,100), notificationBuilder.build());

    }

}

package com.ens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ens.MainActivity;
import com.ens.R;
import com.ens.config.ENSApplication;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_DELAY_DURATION = 1000;
    private Handler waitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById(R.id.splash_tag_line_text).setSelected(true);

        delaySplashScreen();
    }

    private void delaySplashScreen() {

        waitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //The following code will execute after the 5 seconds.

                try {

                    if (ENSApplication.isUserLoggedIn()) {

                        // After delay start main activity if user logged in
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    } else {

                        // After delay start login activity if user not logged in
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e("SplashScreen", "delaySplashScreen() -> run(): ", ex);
                }

            }
        }, SPLASH_DISPLAY_DELAY_DURATION); // 5 seconds delay.

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        waitHandler.removeCallbacksAndMessages(null);
    }
}

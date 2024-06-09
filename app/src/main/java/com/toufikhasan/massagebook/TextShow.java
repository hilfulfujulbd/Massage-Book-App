package com.toufikhasan.massagebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.massagebook.R;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class TextShow extends AppCompatActivity {
    public static final String FILE_NAME = "FILE_NAME";
    public static final String TITLE_NAME = "TITLE_NAME";
    InternetAccess internetAccess;
    LinearLayout linearLayoutAds;
    AdView mAdView;
    AdsController adsController;
    private String filename;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);

        internetAccess = new InternetAccess(this);
        adsController = new AdsController(this);

        MobileAds.initialize(this, initializationStatus -> {
        });


        mAdView = findViewById(R.id.adView);
        linearLayoutAds = findViewById(R.id.layoutAdsLayout);

        if (internetAccess.isConnected()) {
            showAdsSomeTime();
        }

        Intent intent = getIntent();
        filename = intent.getStringExtra(FILE_NAME);
        String titleName = intent.getStringExtra(TITLE_NAME);

        // Title Change
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleName);

        fileRead();


    }

    @Override
    public void onBackPressed() {
        adsController.showInterstitialAds();
        super.onBackPressed();
    }

    public void fileRead() {
        TextView showText = findViewById(R.id.showText);
        // Font Change code start
        showText.setTypeface(Typeface.createFromAsset(getAssets(), "font/Bangla_Font.ttf"));
        // Font Change code start

        String text = "";
        try {
            InputStream x = getAssets().open(filename);
            int size = x.available();
            byte[] buffer = new byte[size];
            x.read(buffer);
            x.close();
            text = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        showText.setText(text);
    }

    private void showAdsSomeTime() {

        final int ADS_SHOW_AFTER = 45000;
        if (isAdsShow()) {
            saveAdsData();
            adsController.loadInterstitialAds();

            if (mAdView != null) {
                adsController.loadBannerAds(mAdView);
                linearLayoutAds.setVisibility(View.VISIBLE);
            } else {
                linearLayoutAds.setVisibility(View.GONE);
            }

        }
        bannerAdsLoadAfterSomeTime(ADS_SHOW_AFTER);
        countDownTimer.start();
    }

    public void bannerAdsLoadAfterSomeTime(final long milliseconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                updateAdsData();
            }
        };
    }

    private boolean isAdsShow() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("INTERSTITIAL", MODE_PRIVATE);
        return sharedPreferences.getBoolean("ADS_SHOW", true);
    }

    @SuppressLint("ApplySharedPref")
    private void saveAdsData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("INTERSTITIAL", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ADS_SHOW", false);
        editor.commit();
    }

    @SuppressLint("ApplySharedPref")
    private void updateAdsData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("INTERSTITIAL", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("ADS_SHOW", true);
        editor.commit();

        if (linearLayoutAds.getVisibility() == View.GONE) {
            showAdsSomeTime();
        }
    }

    /**
     * Called when leaving the activity
     */
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

}
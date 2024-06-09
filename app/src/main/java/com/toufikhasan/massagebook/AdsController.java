package com.toufikhasan.massagebook;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.massagebook.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class AdsController {


    Context context;
    AdRequest adRequest;
    private InterstitialAd mInterstitialAd;

    public AdsController(Context context) {
        this.context = context;
    }

    public void loadBannerAds(AdView adView) {
        adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void loadInterstitialAds() {
        if (mInterstitialAd == null) {

            adRequest = new AdRequest.Builder().build();

            InterstitialAd.load(context, context.getString(R.string.ads_image_textShow_page), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
                    mInterstitialAd = interstitialAd;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    mInterstitialAd = null;
                }
            });
        }
    }

    public void showInterstitialAds() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show((Activity) context);
        }
    }


}

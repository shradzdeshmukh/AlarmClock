package com.cyno.alarm.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by hp on 19-03-2016.
 */
public class AddsImplementer implements IAdsListner {

    private final AdListener listner;

    public AddsImplementer(AdListener listener){
        this.listner = listener;
    }

    @Override
    public void setUpInterstitialAds(Context context, InterstitialAd mInterstitialAd) {
        mInterstitialAd.setAdUnitId(INTERTITIAL_UNIT_ID);
        mInterstitialAd.setAdListener(listner);
    }

    @Override
    public void buildNewInterstitialAd(InterstitialAd mInterstitialAd) {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEV_ID)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void buildNewBannerAd(final AdView adView) {
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(AdView.VISIBLE);
            }
        });
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(BANNAR_UNIT_ID);
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addTestDevice(TEST_DEV_ID);
        adView.loadAd(builder.build());
    }

    @Override
    public void showIntertialAd(InterstitialAd mInterstitialAd, Activity activity ,Intent intent ) {
        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();
        else
            activity.startActivity(intent);
    }

    @Override
    public void showBannerAd() {

    }


    @Override
    public void doNextTask(Activity activity , int task , Intent intent) {
        if(task == END_ACTIVITY)
            activity.finish();
        else
            activity.startActivity(intent);
    }
}

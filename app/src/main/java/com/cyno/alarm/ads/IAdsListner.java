package com.cyno.alarm.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by hp on 19-03-2016.
 */
public interface IAdsListner {

    public static final String TEST_DEV_ID = "6D4426E11ABF9F449070736467E43A18";
    public static final String BANNAR_UNIT_ID = "ca-app-pub-1382822742393934/2136199602";
    public static final String INTERTITIAL_UNIT_ID = "ca-app-pub-1382822742393934/6096577604";

    public static final int START_ACTIVITY = 1;
    public static final int END_ACTIVITY = 2;

    public void setUpInterstitialAds(Context context, InterstitialAd mInterstitialAd);
    public void buildNewInterstitialAd(InterstitialAd mInterstitialAd);
    public void buildNewBannerAd(AdView adView);
    public void showIntertialAd(InterstitialAd mInterstitialAd , Activity activity , Intent intent);
    public void showBannerAd();
    public void doNextTask(Activity activity , int task ,  Intent intent);
}

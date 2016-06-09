package com.cyno.alarm.in_app_utils;

import android.app.Activity;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 15-03-2016.
 */
public class InAppListnerImpl implements InAppListner, IabHelper.OnIabPurchaseFinishedListener {

    private static final String IS_PREMIUM = "IS_PREMIUM";
    private static final String SKU = "test_premium_subscription";
    private static final int REQUEST_CODE = 100;

    private final Activity activity;
    private IabHelper mHelper;
    private String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnJNlKYPAA9QeFGTfk/vpwL+bgstcQB/x1Q4IorSADk4Y0fSyXteE/3+H/5IcEoxSYH/eOnN2eFxXNpdlYAk96wN+gvePjPT/uJY16iehr9ehsVOMsktrfsZvDW9so6KuVPTI2T6GTyp99uC+0qf04QSGAg/qnOuz9aduSa/Nf6qKs40iFNeAjLnShblqkCW4FTTHzU+6YgvzetVbFbWsMvZVd3slqrzZxRNaIPup0oenFKy0Kxlos+8HeolwQU1wKkl0TBZyeB6rRTLOpe7ZWz0aJitZhLbqXFpqyBKS6xQ201HlOJmaek3wM6eIfQOtkDBvOGjRyl5zU0GH/ErokwIDAQAB";
    private ArrayList<String> additionalSkuList;

    public InAppListnerImpl(Activity activity){
        this.activity = activity;
        mHelper = new IabHelper(activity, base64EncodedPublicKey);
        setUpInApp();
    }

    @Override
    public boolean checkIfPremium() {
        return PreferenceManager.getDefaultSharedPreferences(activity).getBoolean(IS_PREMIUM , false);
    }

    @Override
    public void requestInventory() {
        mHelper.queryInventoryAsync(true, additionalSkuList,
                new IabHelper.QueryInventoryFinishedListener() {
                    @Override
                    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                        if (result.isFailure()) {
                            Log.d("in app", "querry failed " + result.getMessage());
                            return;
                        } else {

                            if (inventory.getSkuDetails(SKU) != null) {
                                String price = inventory.getSkuDetails("test_premium_subscription").getPrice();
                                Log.d("in app", "querry success " + price);

                            } else {
                                Log.d("in app", "no inventry for sku");
                            }
                        }
                        // update the UI
                    }
                });
    }

    @Override
    public void initiatePurchase() {
        mHelper.launchPurchaseFlow(activity , SKU, REQUEST_CODE,
                this, "");
    }

    @Override
    public void destroy() {
        if (mHelper != null)
            mHelper.dispose();
        mHelper = null;
    }


    private void setUpInApp() {
        try {
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        Log.d("in app", "Problem setting up In-app Billing: " + result);
                    } else {
                        Log.d("in app", "success " + result);
                        additionalSkuList = new ArrayList<String>();
                        additionalSkuList.add("test_premium_subscription");

                    }
                }
            });
        }catch (Exception ex){

        }
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {

    }
}

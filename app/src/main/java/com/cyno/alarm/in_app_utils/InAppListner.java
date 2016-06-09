package com.cyno.alarm.in_app_utils;

/**
 * Created by hp on 15-03-2016.
 */
public interface InAppListner {


    public boolean checkIfPremium();
    public void requestInventory();
    public void initiatePurchase();
    public void destroy();

}

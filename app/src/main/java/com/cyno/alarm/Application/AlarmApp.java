package com.cyno.alarm.Application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by hp on 11-07-2016.
 */
public class AlarmApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

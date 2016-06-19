package com.cyno.alarm.UtilsAndConstants;

import android.content.Context;
import android.preference.PreferenceManager;

import com.cyno.alarm.models.Weather;

/**
 * Created by hp on 19-06-2016.
 */
public class Utils {

    private static final String KEY_WEATHER_CODE = "weather_code";
    private static final String KEY_IS_DAY = "is_day";
    private static final String KEY_TEMP_C = "temp_c";
    private static final String KEY_TEMP_F = "temp_f";
    private static final String KEY_TEMP_NOW_F = "temp_now_f";
    private static final String KEY_TEMP_NOW_C = "temp_now_c";

    public static String getLatLong(Context context){
        return "18.5204300,73.8567440";
    }

    public static void setWeather(Weather weather, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(KEY_WEATHER_CODE , weather.getCurrent().getCondition().getCode())
                .putInt(KEY_IS_DAY , weather.getCurrent().getIs_day())
                .putFloat(KEY_TEMP_C ,(float) weather.getCurrent().getTemp_c())
                .putFloat(KEY_TEMP_F ,(float)  weather.getCurrent().getTemp_f())
                .putFloat(KEY_TEMP_NOW_F ,(float)  weather.getCurrent().getFeelslike_f())
                .putFloat(KEY_TEMP_NOW_C ,(float)  weather.getCurrent().getFeelslike_c()).commit();
    }

    public static boolean isDay(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_IS_DAY ,1) == 1 ;
    }

    public static int getWeatherCode(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(KEY_WEATHER_CODE,-1) ;
    }

    public static float getTemperatureF(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(KEY_TEMP_F,-1) ;
    }

    public static float getTemperatureC(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(KEY_TEMP_C,-1) ;
    }

    public static float getCurrentTemperatureC(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(KEY_TEMP_NOW_C,-1) ;
    }
    public static float getCurrentTemperatureF(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(KEY_TEMP_NOW_F,-1) ;
    }

}

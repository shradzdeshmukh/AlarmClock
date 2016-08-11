package com.cyno.alarm.UtilsAndConstants;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.cyno.alarm.AlarmApplication;
import com.cyno.alarm.database.PicCodesTable;
import com.cyno.alarm.database.SummaryCodesTable;
import com.cyno.alarm.models.Weather;
import com.cyno.alarm.ui.MainActivity;
import com.cyno.alarm.ui.SettingsActivity;

import java.util.Locale;

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
    private static final String KEY_LOCATION = "location";
    private static final String KEY_INITIAL_LOCATION = "initial_location";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_WEATHER_UPDATES = "weather_updates";
    private static final String KEY_IS_FIRST_TIME_LAUNCH = "FirstLaunch";

    public static String getLatLong(Context context) {
        Location loc = getLocation(getLocationManager(context));
        if(loc != null)
            return  loc.getLatitude()+","+loc.getLongitude();
        else if(hasInitialLocation(context))
            return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LATITUDE , "")
                    + " , "+PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LONGITUDE, "");
        return null;
    }

    public static LocationManager getLocationManager(Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static boolean hasInitialLocation(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_INITIAL_LOCATION, false);
    }

    public static void storeLatLon(String latitude, String longitude, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_LATITUDE
                , latitude.trim()).putString(KEY_LONGITUDE, longitude.trim()).putBoolean(KEY_INITIAL_LOCATION , true).commit();

    }

    @SuppressWarnings("MissingPermission")
    public static Location getLocation(LocationManager locationManager) {
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public static void setWeather(Weather weather, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(KEY_WEATHER_CODE , weather.getCurrent().getCondition().getCode())
                .putString(KEY_LOCATION, weather.getLocation().getName())
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

    public static String getCurrentLocation(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_LOCATION,"") ;
    }

    public static String getDayPicCode(int code) {

        switch (code){
            case 1000:
                return "B";
            case 1003:
                return "H";
            case 1006:
                return "N";
            case 1009:
                return "Y";
            case 1030:
                return "S";
            case 1063:
                return "Q";
            case 1066:
                return "V";
            case 1069:
                return "W";
            case 1072:
                return "T";
            case 1087:
                return "O";
            case 1114:
                return "X";
            case 1117:
                return "Z";
            case 1135:
                return "L";
            case 1147:
                return "L";
            case 1150:
                return "Q";
            case 1153:
                return "Q";
            case 1168:
                return "Q";
            case 1171:
                return "R";
            case 1180:
                return "Q";
            case 1183:
                return "Q";
            case 1186:
                return "R";
            case 1189:
                return "R";
            case 1192:
                return "R";
            case 1195:
                return "R";
            case 1198:
                return "R";
            case 1201:
                return "Q";
            case 1204:
                return "R";
            case 1207:
                return "T";
            case 1210:
                return "U";
            case 1213:
                return "U";
            case 1216:
                return "W";
            case 1219:
                return "W";
            case 1222:
                return "X";
            case 1225:
                return "X";
            case 1237:
                return "X";
            case 1240:
                return "Q";
            case 1243:
                return "R";
            case 1246:
                return "R";
            case 1249:
                return "Q";
            case 1252:
                return "R";
            case 1255:
                return "V";
            case 1258:
                return "W";
            case 1261:
                return "X";
            case 1264:
                return "X";
            case 1273:
                return "P";
            case 1276:
                return "P";
            case 1279:
                return "P";
            case 1282:
                return "P";
        }
        return "A";
    }

    public static String getNightPicCode(int code) {
        switch (code){
            case 1000:
                return "2";
            case 1003:
                return "I";
            case 1006:
                return "N";
            case 1009:
                return "Y";
            case 1030:
                return "9";
            case 1063:
                return "7";
            case 1066:
                return "\"";
            case 1069:
                return "#";
            case 1072:
                return "!";
            case 1087:
                return "O";
            case 1114:
                return "$";
            case 1117:
                return "Z";
            case 1135:
                return "L";
            case 1147:
                return "L";
            case 1150:
                return "7";
            case 1153:
                return "7";
            case 1168:
                return "7";
            case 1171:
                return "8";
            case 1180:
                return "7";
            case 1183:
                return "7";
            case 1186:
                return "8";
            case 1189:
                return "8";
            case 1192:
                return "8";
            case 1195:
                return "8";
            case 1198:
                return "8";
            case 1201:
                return "7";
            case 1204:
                return "8";
            case 1207:
                return "!";
            case 1210:
                return "\"";
            case 1213:
                return "\"";
            case 1216:
                return "#";
            case 1219:
                return "#";
            case 1222:
                return "$";
            case 1225:
                return "$";
            case 1237:
                return "$";
            case 1240:
                return "7";
            case 1243:
                return "8";
            case 1246:
                return "8";
            case 1249:
                return "7";
            case 1252:
                return "8";
            case 1255:
                return "\"";
            case 1258:
                return "#";
            case 1261:
                return "$";
            case 1264:
                return "$";
            case 1273:
                return "6";
            case 1276:
                return "6";
            case 1279:
                return "6";
            case 1282:
                return "6";
        }
        return "2";
    }

    public static String getOverView(Context context,
                                     int weatherCode, String locale) {
        Cursor cursor = context.getContentResolver().query(SummaryCodesTable.CONTENT_URI ,
                null , SummaryCodesTable.COL_UNIQUE_CODE+" = ? AND "+
                        SummaryCodesTable.COL_LANGUAGE_CODE + " = ? " ,
                new String[]{String.valueOf(weatherCode),locale},null);
        String summary = null;
        if(cursor != null){
            if(cursor.moveToNext()){
                summary = cursor.getString(cursor.getColumnIndex(SummaryCodesTable.COL_DAY_SUMMARY));
            }
            cursor.close();
        }
        return summary;
    }

    public static String getPic(Context context,int weatherCode , boolean isDay) {
        String col = null;
        if(isDay)
            col = PicCodesTable.COL_DAY_PIC;
        else
            col = PicCodesTable.COL_NIGHT_PIC;

        Cursor cursor = context.getContentResolver().query(PicCodesTable.CONTENT_URI ,
                null , PicCodesTable.COL_UNIQUE_CODE+" = ? ",
                new String[]{String.valueOf(weatherCode)},null);
        String pic = null;
        if(cursor != null){
            if(cursor.moveToNext()){
                pic = cursor.getString(cursor.getColumnIndex(col));
            }
            cursor.close();
        }
        return pic;
    }

    public static boolean isWeatherPermited(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_WEATHER_UPDATES , false);
    }

    public static void setWeatherPermission(Context context , boolean permission){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(KEY_WEATHER_UPDATES , permission).commit();
    }

    public static boolean isFirstTime(Context context){
        boolean isFirstTime = PreferenceManager.getDefaultSharedPreferences(context).
                getBoolean(KEY_IS_FIRST_TIME_LAUNCH, true);
        if(isFirstTime)
            setFirstTime(context);
        return isFirstTime;
    }

    public static void setFirstTime(Context context){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(KEY_IS_FIRST_TIME_LAUNCH , false).commit();
    }

    public static boolean isGpsOn(Context context) {
        Log.d("flow1" , "turning on gps");
        return getLocationManager(context).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void askToTurnOnGps(Context context) {
        Intent callGPSSettingIntent = new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(callGPSSettingIntent);

    }

    public static String getBestProvider(Context context) {
        LocationManager manager = getLocationManager(context);
        String provider = null;
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            provider = LocationManager.GPS_PROVIDER;
        else if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            provider = LocationManager.NETWORK_PROVIDER;
        else
            provider = LocationManager.PASSIVE_PROVIDER;

        Log.d("provider","provider = "+provider);
        return provider;
    }

    public static void trackScreen(Activity context, String s){
        if(SettingsActivity.isAnalyticsEnabled(context))
        ((AlarmApplication)context.getApplication()).
                trackScreenView(s);

    }

    public static void trackEvent(Context context, String category, String action, String label ){
        if(SettingsActivity.isAnalyticsEnabled(context)) {

            if (!(context instanceof Activity))
                ((AlarmApplication) context.getApplicationContext()).trackEvent(category, action, label);
            else
                ((AlarmApplication) ((Activity) context).getApplication()).
                        trackEvent(category, action, label);
        }
    }


}

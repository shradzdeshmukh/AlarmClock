/*
package com.cyno.alarm.networking;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.VolleyError;
import com.cyno.alarm.UtilsAndConstants.AppUtils;
import com.cyno.alarm.models.Weather;
import com.cyno.alarm.ui.MainActivity;

*/
/**
 * Created by hp on 07-05-2016.
 *//*

public class GetWeatherNetworking extends AbstractNetworking {


    */
/*
    login with facebook
    Welcome fb-dineshjohncena@yahoo.co.in

     *//*


//    public static final String mBaseUrl = "http://api.apixu.com/v1/current.json?key=4df6d3f159ba404cac1144529161906&q=18.5204300,73.8567440";
    public static final String mBaseUrl = "http://api.apixu.com/v1/current.json?";
    private static final String KEY = "4df6d3f159ba404cac1144529161906";
    private final Handler handler;

    public GetWeatherNetworking(Context context, boolean isForeground , Handler handler) {
        super(context, isForeground);
        url = mBaseUrl + "key=" + KEY + "&q=" + AppUtils.getLatLong(context);
        url = url.replace(" " , "");
        this.handler = handler;
    }

    @Override
    protected void setParams() {

    }

    @Override
    public void makeRequest(Class mClass) {
        Log.d("location" , "calling api");
        super.makeRequest(mClass);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
        Log.d("error" ,"error = "+ error.getLocalizedMessage());
    }

    @Override
    public void onResponse(Object response) {
        Log.d("location" , "response " + response);
        super.onResponse(response);
        Weather weather = (Weather) response;
        AppUtils.setWeather(weather,context);

        if(isForeground)
            handler.sendEmptyMessage(MainActivity.WEATHER_UPDATED);
    }
}
*/

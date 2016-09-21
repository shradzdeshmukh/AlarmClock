/*
package com.cyno.alarm.networking;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.VolleyError;
import com.cyno.alarm.models.Weather;

*/
/**
 * Created by hp on 07-05-2016.
 *//*

public class GetCodesNetworking extends AbstractNetworking {


//    public static final String mBaseUrl = "http://api.apixu.com/v1/current.json?key=4df6d3f159ba404cac1144529161906&q=18.5204300,73.8567440";
    public static final String mBaseUrl = "http://www.apixu.com/doc/conditions.json";
    private final Handler handler;

    public GetCodesNetworking(Context context, boolean isForeground , Handler handler) {
        super(context, isForeground);
        url = mBaseUrl ;
        this.handler = handler;
    }

    @Override
    protected void setParams() {

    }

    @Override
    public void makeRequest(Class mClass) {
        super.makeRequest(mClass);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        super.onErrorResponse(error);
        Log.d("error" ,"error = "+ error.getLocalizedMessage());
    }

    @Override
    public void onResponse(Object response) {
        super.onResponse(response);
        Weather weather = (Weather) response;


    }
}
*/

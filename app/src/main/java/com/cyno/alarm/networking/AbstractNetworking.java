package com.cyno.alarm.networking;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.logging.Handler;

/**
 * Created by hp on 07-05-2016.
 */
public abstract class AbstractNetworking implements Response.ErrorListener, Response.Listener {

    private static final int TIMEOUT = 30000;
    private static final int RETRIES = 2;
    private static final float BACKOFF = 0;
    protected final Context context;
    protected final boolean isForeground;
    protected String url;

    public AbstractNetworking(Context context , boolean isForeground){
        this.context = context;
        this.isForeground = isForeground;
    }

    protected abstract void setParams();

    protected void makeRequest(Class mClass){
        final GsonRequest gsonRequest =
                new GsonRequest(url, mClass, null,this, this);
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT , RETRIES , BACKOFF));
        VolleySingleton.getInstance(context.getApplicationContext())
                .addToRequestQueue(gsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("networking" ,"error "+ error.getLocalizedMessage());
    }

    @Override
    public void onResponse(Object response) {
    }
}

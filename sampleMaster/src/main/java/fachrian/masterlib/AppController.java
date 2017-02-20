package fachrian.masterlib;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import fachrian.fachrian_library.lib.LocationMaster;
import fachrian.fachrian_library.lib.LogMaster;
import fachrian.masterlib.m.SharedPreferences;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class AppController extends Application {
    private static AppController mInstance;
    private static final String TAG = AppController.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        final Context context = this;

        LocationMaster locationMaster = new LocationMaster(this);
        locationMaster.taskGetLocation(context, 30000);

        System.out.println("=======================");
        LogMaster.display(context, SharedPreferences.getPUSH_NOTIFICATION_KEY(context));


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public void abordRequestQueue(String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TextUtils.isEmpty(tag) ? TAG : tag);
        }
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
}

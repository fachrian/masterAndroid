package fachrian.fachrian_library.lib;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class SwipeRefreshMaster {

    public static void show(SwipeRefreshLayout swipeRefreshLayout, Context context) {
        Activity activity = (Activity) context;
        if (!activity.isDestroyed()) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    public static void dismiss(SwipeRefreshLayout swipeRefreshLayout, Context context) {
        Activity activity = (Activity) context;
        if (!activity.isDestroyed()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}

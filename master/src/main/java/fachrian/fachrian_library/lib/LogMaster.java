package fachrian.fachrian_library.lib;

import android.content.Context;
import android.util.Log;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class LogMaster {
    public static void display(Context context, String stat) {
        Log.i(context.getClass().getName(), stat);

    }
}

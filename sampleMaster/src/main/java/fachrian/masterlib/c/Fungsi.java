package fachrian.masterlib.c;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import fachrian.masterlib.m.SharedPreferences;
import fachrian.masterlib.v.Login;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class Fungsi {
    public static void logout(Context context) {
        SharedPreferences.flush(context);
        Activity activity = (Activity) context;
        activity.startActivity(new Intent(context, Login.class));
        activity.finish();
    }


}

package fachrian.masterlib.c;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import fachrian.fachrian_library.lib.AlertDialogMaster;
import fachrian.masterlib.m.SharedPreferences;
import fachrian.masterlib.v.Login;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class VolleyController {
    public static boolean VOLLEY_DEBBUG = false;
    public static int VOLLEY_TIMEOUT = 10000;

    public static void volleyErrorHandler(Context context, VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(context, "Timeout", Toast.LENGTH_LONG);
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(context, "No Connection", Toast.LENGTH_LONG);
        } else if (error instanceof AuthFailureError) {
            SharedPreferences.flush(context);
            new AlertDialogMaster(context).unauthorized(Login.class);
        } else if (error instanceof ServerError) {

        } else if (error instanceof NetworkError) {

        } else if (error instanceof ParseError) {

        }

    }
}

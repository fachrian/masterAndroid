package fachrian.masterlib.m;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class SharedPreferences {

    public static final String NAME = "NAME";
    private static final String TOKEN = "TOKEN";
    private static final String ID_USER = "ID_USER";
    private static final String PUSH_NOTIFICATION_KEY = "PUSH_NOTIFICATION_KEY";
    private static final String ACCESS = "ACCESS";
    private static final String EMAIL = "EMAIL";
    private static final String GOOGLE_ID = "GOOGLE_ID";
    private static final String LONGI = "LONGI";
    private static final String LATI = "LATI";
    private static final String FOTO = "FOTO";
    private static final String TLP = "TLP";

    public static String getTLP(Context context) {
        return getSharedPreferences(context).getString(TLP, null);
    }

    public static String getFOTO(Context context) {
        return getSharedPreferences(context).getString(FOTO, null);
    }

    public static void setTLP(Context context, String langi) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TLP, langi);
        editor.apply();
    }

    public static void setFOTO(Context context, String lati) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(FOTO, lati);
        editor.apply();
    }

    public static String getLONGI(Context context) {
        return getSharedPreferences(context).getString(LONGI, null);
    }

    public static String getLATI(Context context) {
        return getSharedPreferences(context).getString(LATI, null);
    }

    public static void setLATI(Context context, String langi) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LONGI, langi);
        editor.apply();
    }

    public static void setLONGI(Context context, String lati) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(LATI, lati);
        editor.apply();
    }


    public static String getGOOGLE_ID(Context context) {
        return getSharedPreferences(context).getString(GOOGLE_ID, null);
    }

    public static void setGOOGLE_ID(Context context, String google_id) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(GOOGLE_ID, google_id);
        editor.apply();
    }

    public static String getEMAIL(Context context) {
        return getSharedPreferences(context).getString(EMAIL, null);
    }

    public static void setEMAIL(Context context, String email) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public static String getNAME(Context context) {
        return getSharedPreferences(context).getString(NAME, null);
    }

    public static void setNAME(Context context, String name) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(NAME, name);
        editor.apply();
    }

    public static String getTOKEN(Context context) {
        return getSharedPreferences(context).getString(TOKEN, null);
    }

    public static void setTOKEN(Context context, String token) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public static String getID_USER(Context context) {
        return getSharedPreferences(context).getString(ID_USER, null);
    }

    public static void setID_USER(Context context, String id_user) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ID_USER, id_user);
        editor.apply();
    }

    public static String getPUSH_NOTIFICATION_KEY(Context context) {
        System.out.println("get GCM = "+getSharedPreferences(context).getString(PUSH_NOTIFICATION_KEY, ""));
        return getSharedPreferences(context).getString(PUSH_NOTIFICATION_KEY, "");
    }

    public static void setPUSH_NOTIFICATION_KEY(Context context, String push_notification_key) {
        System.out.println("set GCM = "+ push_notification_key);
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PUSH_NOTIFICATION_KEY, push_notification_key);
        editor.apply();
    }

    public static String getACCESS(Context context) {
        return getSharedPreferences(context).getString(ACCESS, null);
    }

    public static void setACCESS(Context context, String access) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(ACCESS, access);
        editor.apply();
    }


    private static android.content.SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void flush(Context ctx) {
        android.content.SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
}

package fachrian.fachrian_library.lib;

import android.content.Context;
import android.os.Build;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class ProgressDialogMaster {

    public static android.app.ProgressDialog show(android.app.ProgressDialog progressDialog, final Context context) {
        try {


            if (progressDialog == null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    progressDialog = new android.app.ProgressDialog(context, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    progressDialog = new android.app.ProgressDialog(context);
                }
                progressDialog = new android.app.ProgressDialog(context);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading ...");

            }

            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        } catch (Exception ignored) {
            System.out.println(ignored);
        }
        return progressDialog;
    }

    public static android.app.ProgressDialog dismiss(final android.app.ProgressDialog progressDialog, Context context) {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

}

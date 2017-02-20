package fachrian.fachrian_library.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;


/**
 * Created by Fachrian on 24/06/2016.
 */

public class AlertDialogMaster {
    AlertDialog.Builder alert;
    Context context;

    public AlertDialogMaster(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alert = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            alert = new AlertDialog.Builder(context);
        }
    }

    public AlertDialog.Builder AlertDialogMaster() {
        return alert;
    }

    public void showDialog(final String TITLE, final String MESSAGE) {
        alert.setTitle(TITLE)
                .setCancelable(false)
                .setMessage(MESSAGE)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        alert.create();
        alert.show();
    }

    public void showDialogFinish(final Context context, final String TITLE, final String MESSAGE) {
        alert.setTitle(TITLE)
                .setCancelable(false)
                .setMessage(MESSAGE)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        ((Activity) context).finish();
                    }
                });
        alert.create();
        alert.show();
    }

    public void unauthorized(final Class aClass) {//401

        alert.setTitle("Warning")
                .setCancelable(false)
                .setMessage("Authentication failed. Please login again")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(((Activity) context).getApplicationContext(), aClass);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        ((Activity) context).startActivity(intent);
                        ((Activity) context).finish();
                    }
                });
        alert.create();
        alert.show();
    }

}

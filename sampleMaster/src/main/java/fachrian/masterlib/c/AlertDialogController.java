package fachrian.masterlib.c;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import fachrian.fachrian_library.lib.AlertDialogMaster;
import fachrian.masterlib.R;

import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class AlertDialogController {
    public static void showDialogGPSOn(final Context context) {
        AlertDialog.Builder alert = new AlertDialogMaster(context).AlertDialogMaster();
        alert.setTitle("Info")
                .setCancelable(false)
                .setMessage("GPS harus dalam keadaan aktif untuk menjalankan aplikasi")
                .setPositiveButton("Aktifkan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ((Activity) context).finish();
                    }
                });

        alert.create();
        alert.show();
    }

    public static void showDialogTokenSalah(final Context context) {

        AlertDialog.Builder alert = new AlertDialogMaster(context).AlertDialogMaster();
        alert.setTitle("Info")
                .setCancelable(false)
                .setMessage("Maaf authentifikasi gagal. Silahkan login kembali.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        Fungsi.logout(context);
                        ((Activity) context).finish();
                    }
                });
        alert.create();
        alert.show();
    }

    public static void showDialog(final Context context, final String TITLE, final String MESSAGE) {

        AlertDialog.Builder alert = new AlertDialogMaster(context).AlertDialogMaster();
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

    public static void showDialogFinish(final Context context, final String TITLE, final String MESSAGE) {

        AlertDialog.Builder alert = new AlertDialogMaster(context).AlertDialogMaster();
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



    public void showDialogBatalTugas(Context context) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view2 = inflater.inflate(R.layout.dialog_batal, null);

        final EditText komentar = (EditText) view2.findViewById(R.id.komentar);
        final Button batalkan = (Button) view2.findViewById(R.id.batalkan);
        final Button kembali = (Button) view2.findViewById(R.id.kembali);


        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view2);
        dialog.setCancelable(false);
        dialog.show();

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        layoutParams.copyFrom(window.getAttributes());

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);


        batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (komentar.getText().length() == 0) {
                    komentar.setError("Tidak boleh kosong");
                } else {

                    dialog.dismiss();
                }

            }
        });
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

}

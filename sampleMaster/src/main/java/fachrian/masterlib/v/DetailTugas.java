package fachrian.masterlib.v;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import fachrian.fachrian_library.lib.MapsMaster;
import fachrian.fachrian_library.lib.ProgressDialogMaster;
import fachrian.masterlib.R;
import fachrian.masterlib.c.AlertDialogController;
import fachrian.masterlib.c.contract.ApplicationContract;
import fachrian.masterlib.c.presenter.DetailTugasPresenter;

public class DetailTugas extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, ApplicationContract.DetailTugasView {

    String ID_TUGAS, ALAMAT, DATE, DERAJAT_X, DERAJAT_Y, JUDUL, KETERANGAN_KEADAAN, NAMA_USER, NO_HP;
    boolean IS_ACCEPT = true;
    Button report, done;
    ImageView call;
    static android.app.ProgressDialog progressDialog;
    Context context;
    DetailTugasPresenter detailTugasPresenter;
    MapsMaster mapsMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Tugas");

        if (!getIntent().getExtras().isEmpty()) {
            Bundle bundle = getIntent().getExtras();
            ID_TUGAS = bundle.getString("ID_TUGAS");
            JUDUL = bundle.getString("JUDUL");
            ALAMAT = bundle.getString("ALAMAT");
            DATE = bundle.getString("DATE");
            DERAJAT_Y = bundle.getString("DERAJAT_Y");
            DERAJAT_X = bundle.getString("DERAJAT_X");
            NAMA_USER = bundle.getString("NAMA");
            NO_HP = bundle.getString("TLP");
            KETERANGAN_KEADAAN = bundle.getString("KETERANGAN");
        }

        detailTugasPresenter = new DetailTugasPresenter(this, this);
        TextView nama = (TextView) findViewById(R.id.nama);
        TextView judul = (TextView) findViewById(R.id.judul);
        TextView lokasi = (TextView) findViewById(R.id.alamat);
        TextView detail = (TextView) findViewById(R.id.detail);


        report = (Button) findViewById(R.id.report);
        report.setOnClickListener(this);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(this);
        call = (ImageView) findViewById(R.id.call);
        call.setOnClickListener(this);

        nama.setText(NAMA_USER);
        judul.setText(JUDUL);
        lokasi.setText(ALAMAT);
        detail.setText(KETERANGAN_KEADAAN);


        mapsMaster = new MapsMaster(this, this, R.id.map);


    }


    @Override
    protected void onResume() {
        super.onResume();
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialogController.showDialogGPSOn(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + NO_HP));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
                break;



        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng latLng = new LatLng(-7.108940, 112.404257);
        mapsMaster.showLocation(map, latLng, ALAMAT);


    }

    @Override
    public void onBackPressed() {

        if (IS_ACCEPT) {
            AlertDialogController.showDialog(context, "Info", "Tugas harus diselesaikan");
        } else {
            super.onBackPressed();
            finish();
        }
    }




    @Override
    public void showProgress(boolean stat) {
        if (stat) {
            progressDialog = ProgressDialogMaster.show(progressDialog, this);
        } else {
            progressDialog = ProgressDialogMaster.dismiss(progressDialog, this);
        }
    }

    @Override
    public void onResponDoneTugas(int stat) {
        if (stat == 1) {
            this.finish();
        } else if (stat == 2) {
            AlertDialogController.showDialogTokenSalah(context);
        }
    }

    @Override
    public void onResponBatalTugas(int stat) {
        if (stat == 1) {
            AlertDialogController.showDialogFinish(context, "Info", "Tugas " + JUDUL + " berhasil dibatalkan");
        } else if (stat == 2) {
            AlertDialogController.showDialogTokenSalah(context);
        }
    }
}

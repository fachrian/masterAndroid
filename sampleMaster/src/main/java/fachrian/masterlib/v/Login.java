package fachrian.masterlib.v;


import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import fachrian.fachrian_library.lib.GoogleSignInMaster;
import fachrian.fachrian_library.lib.ProgressDialogMaster;
import fachrian.fachrian_library.lib.firebase.FirebaseController;
import fachrian.masterlib.R;
import fachrian.masterlib.c.AlertDialogController;
import fachrian.masterlib.c.contract.ApplicationContract;
import fachrian.masterlib.c.presenter.LoginPresenter;
import fachrian.masterlib.m.SharedPreferences;

public class Login extends AppCompatActivity implements View.OnClickListener, ApplicationContract.LoginView {
    Context context = this;
    android.app.ProgressDialog progressDialog = null;

    LoginPresenter loginPresenter;
    GoogleSignInMaster googleSignInMaster;
    EditText username, password;

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialogController.showDialogGPSOn(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this, this);
        if (FirebaseController.getToken() != null) {
            SharedPreferences.setPUSH_NOTIFICATION_KEY(context, FirebaseController.getToken());
            FirebaseController.setTopic("DRIVER");
        }
        if (SharedPreferences.getACCESS(context) != null
                && SharedPreferences.getACCESS(context).equals("TRUE")) {
            startActivity(new Intent(context, Home.class));
            this.finish();
        } else {
            setContentView(R.layout.activity_login);
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);

            googleSignInMaster = new GoogleSignInMaster(this, this);

            findViewById(R.id.login_button2).setOnClickListener(this);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button2:
                System.out.println("klik");
                if (username.length() == 0) {
                    username.setError("Tidak boleh kosong");
                } else if (password.length() == 0) {
                    password.setError("Tidak boleh kosong");
                } else {
                    loginPresenter.login(username.getText().toString(), password.getText().toString());
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        googleSignInMaster.requestSignIn(requestCode, data);
    }


    @Override
    public void showProgress(boolean stat) {
        if (stat) {
            progressDialog = ProgressDialogMaster.show(progressDialog, context);
        } else {
            progressDialog = ProgressDialogMaster.dismiss(progressDialog, context);
        }
    }


    @Override
    public void onResponSignIn(boolean stat) {
//        loginPresenter.login();
    }

    @Override
    public void onResponLoginProcess(int stat) {
        if (stat == 1) {
            startActivity(new Intent(this, Home.class));
            finish();
        } else if (stat == 2) {
            AlertDialogController.showDialog(this, "Info", "Akun tidak terdaftar sebagai driver");
        } else if (stat == 3) {
            AlertDialogController.showDialog(this, "Info", "Email tidak falid");
        } else if (stat == 4) {
            AlertDialogController.showDialog(this, "Info", "Password salah");
        } else if (stat == 4) {
            AlertDialogController.showDialog(this, "Info", "Email tidak terdaftar");
        }
    }
}

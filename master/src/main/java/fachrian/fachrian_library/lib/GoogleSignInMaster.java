package fachrian.fachrian_library.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;



/**
 * Created by Fachrian on 24/06/2016.
 */

public class GoogleSignInMaster implements GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    Context context;
    private static final int RC_SIGN_IN = 9001;

    public GoogleSignInMaster(Context context, FragmentActivity fragmentActivity) {
        this.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(fragmentActivity /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LogMaster.display(context, "koneksi gagal");
    }


    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((Activity) context).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void requestSignIn(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("LOG", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            LogMaster.display(context, acct.getDisplayName());
            LogMaster.display(context, acct.getEmail());
            LogMaster.display(context, acct.getId());

//            SharedPreferences.setEMAIL(context, acct.getEmail());
//            SharedPreferences.setGOOGLE_ID(context, acct.getId());
//            SharedPreferences.setNAME(context, acct.getDisplayName());

            signOut();
        } else {
            LogMaster.display(context, "login gagal");
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        LogMaster.display(context, status.toString());

                    }
                });
    }
}

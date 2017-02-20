package fachrian.masterlib.c.presenter;


import android.content.Context;

import fachrian.masterlib.c.ResController;
import fachrian.masterlib.c.contract.ApplicationContract;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class LoginPresenter {
    ApplicationContract.LoginView loginView;
    ResController resController;

    public LoginPresenter(ApplicationContract.LoginView loginView, Context context) {
        this.loginView = loginView;
        resController = new ResController(context);
    }

    public void login(String user, String pass) {
        resController.processLogin(loginView, user, pass);
    }
}

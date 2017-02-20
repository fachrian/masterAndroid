package fachrian.masterlib.c.presenter;


import android.content.Context;

import fachrian.masterlib.c.ResController;
import fachrian.masterlib.c.contract.ApplicationContract;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class DetailTugasPresenter {
    ApplicationContract.DetailTugasView detailTugasView;
    ResController resController;

    public DetailTugasPresenter(ApplicationContract.DetailTugasView detailTugasView, Context context) {
        this.detailTugasView = detailTugasView;
        resController = new ResController(context);
    }

    public void getDoneTugas(String ID_TUGAS) {

    }

    public void getsBatalTugas(String ID_TUGAS, String KET) {

    }
}

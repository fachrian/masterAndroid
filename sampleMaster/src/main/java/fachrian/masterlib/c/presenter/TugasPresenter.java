package fachrian.masterlib.c.presenter;


import android.content.Context;

import fachrian.masterlib.c.ResController;
import fachrian.masterlib.c.contract.ApplicationContract;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class TugasPresenter {
    ApplicationContract.TugasView tugasView;
    ResController resController;

    public TugasPresenter (ApplicationContract.TugasView tugasView, Context context){
        this.tugasView = tugasView;
        resController = new ResController(context);
    }
    public void getAllTugas() {
        resController.processGetAllTugas(tugasView);
    }
}

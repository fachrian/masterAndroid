package fachrian.masterlib.c.contract;


import java.util.List;

import fachrian.masterlib.v.profider.TugasProvider;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class ApplicationContract {

    public interface LoginView {
        void onResponLoginProcess(int stat);

        void onResponSignIn(boolean stat);

        void showProgress(boolean stat);
    }



    public interface TugasView {
        void showProgress(boolean stat);

        void onResponGetTugasProcess(List<TugasProvider> list);
    }



    public interface DetailTugasView {
        void showProgress(boolean stat);

        void onResponDoneTugas(int stat);

        void onResponBatalTugas(int stat);


    }

}

package hambaallah.ululazmi.v.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hambaallah.ululazmi.R;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class HistoriEventFragment extends Fragment   {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        final View view = inflater.inflate(R.layout.fragment_histori_event, container, false);

        return view;
    }




}

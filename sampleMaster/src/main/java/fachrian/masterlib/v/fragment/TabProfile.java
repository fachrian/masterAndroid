package fachrian.masterlib.v.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fachrian.fachrian_library.lib.GlideMaster;
import fachrian.masterlib.R;
import fachrian.masterlib.c.Fungsi;
import fachrian.masterlib.Setting;
import fachrian.masterlib.m.SharedPreferences;


/**
 * Created by Fachrian on 24/06/2016.
 */

public class TabProfile extends Fragment implements View.OnClickListener {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_profile, container, false);
        context = getContext();
        ImageView foto = (ImageView) view.findViewById(R.id.foto);
        TextView nama = (TextView) view.findViewById(R.id.nama);
        TextView tlp = (TextView) view.findViewById(R.id.tlp);

        GlideMaster.setImage(
                context,
                foto,
                Setting.URL_IMAGE_USER + "" + SharedPreferences.getFOTO(context),
                GlideMaster.transformation(context, GlideMaster.CROP_CIRCLE));
        nama.setText(SharedPreferences.getNAME(context));
        tlp.setText(SharedPreferences.getTLP(context));

        view.findViewById(R.id.logout).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.logout:
                Fungsi.logout(context);
                break;
        }
    }
}

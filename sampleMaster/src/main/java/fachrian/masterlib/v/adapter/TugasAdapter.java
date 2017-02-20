package fachrian.masterlib.v.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fachrian.fachrian_library.lib.DateFormatMaster;
import fachrian.fachrian_library.lib.LocationMaster;
import fachrian.masterlib.R;
import fachrian.masterlib.v.DetailTugas;
import fachrian.masterlib.v.profider.TugasProvider;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.MyViewHolder>
        implements RecyclerView.OnItemTouchListener {
    private Context context;
    private List<TugasProvider> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView judul;
        final TextView alamat;
        final TextView tanggal;
        final ImageView foto;
        final CardView card;

        public MyViewHolder(View view) {
            super(view);
            judul = (TextView) view.findViewById(R.id.judul);
            alamat = (TextView) view.findViewById(R.id.alamat);
            tanggal = (TextView) view.findViewById(R.id.tanggal);
            foto = (ImageView) view.findViewById(R.id.foto);
            card = (CardView) view.findViewById(R.id.card);

        }
    }


    public TugasAdapter(List<TugasProvider> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    private GestureDetector gestureDetector;
    private TugasAdapter.ClickListener clickListener;

    public TugasAdapter(Context context, final RecyclerView recyclerView, final TugasAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tugas, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TugasProvider provider = list.get(position);

        LocationMaster locationMaster = new LocationMaster(context);
        double distance = 0;
        if (provider.getDERAJAT_X() != null && provider.getDERAJAT_Y() != null && !provider.getDERAJAT_X().equals("null") && !provider.getDERAJAT_Y().equals("null")) {
            distance = locationMaster.distance(
                    locationMaster.getLatitude(),
                    locationMaster.getLongitude(),
                    Double.parseDouble(provider.getDERAJAT_X()),
                    Double.parseDouble(provider.getDERAJAT_Y()));
            if (distance >= 0) {
                holder.alamat.setText(distance + " km");
            } else {
                holder.alamat.setText(" -");
            }
        } else {
            holder.alamat.setText(" -");
        }


        holder.judul.setText(provider.getJUDUL());
        holder.tanggal.setText(DateFormatMaster.convertFormat(provider.getDATE(), DateFormatMaster.DMY_format));

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (provider.getSTATUS().equals("2")) {
                    toDetail(provider);
                } else {
                    toDetail(provider);
                }

            }

        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    android.app.ProgressDialog progressDialog;


    public void toDetail(TugasProvider provider) {
        Bundle bundle = new Bundle();
        bundle.putString("ID_TUGAS", provider.getID_TUGAS());
        bundle.putString("ALAMAT", provider.getALAMAT());
        bundle.putString("DATE", provider.getDATE());
        bundle.putString("DETAJAT_X", provider.getDERAJAT_X());
        bundle.putString("DERAJAT_Y", provider.getDERAJAT_Y());
        bundle.putString("JUDUL", provider.getJUDUL());
        bundle.putString("NAMA", provider.getNAMA());
        bundle.putString("TLP", provider.getTLP());
        bundle.putString("KETERANGAN", provider.getKETERANGAN());


        Intent intent = new Intent(context, DetailTugas.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

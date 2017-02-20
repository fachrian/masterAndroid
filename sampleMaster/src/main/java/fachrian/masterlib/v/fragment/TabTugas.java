package fachrian.masterlib.v.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


import fachrian.fachrian_library.lib.SwipeRefreshMaster;
import fachrian.masterlib.R;
import fachrian.masterlib.c.contract.ApplicationContract;
import fachrian.masterlib.c.presenter.TugasPresenter;
import fachrian.masterlib.v.adapter.TugasAdapter;
import fachrian.masterlib.v.profider.TugasProvider;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class TabTugas extends Fragment implements ApplicationContract.TugasView {
    Context context;
    boolean processGetAllTugas;
    TugasAdapter adapter;
    private final TabTugas.DataHandler handler = new TabTugas.DataHandler();
    private final List<TugasProvider> list = new ArrayList<>();
    BroadcastReceiver broadcastReceiver;
    TugasPresenter tugasPresenter;

    @Override
    public void showProgress(boolean stat) {
        if (stat) {
            SwipeRefreshMaster.show(handler.swipeRefreshLayout, context);
        } else {
            SwipeRefreshMaster.dismiss(handler.swipeRefreshLayout, context);
        }
    }

    @Override
    public void onResponGetTugasProcess(List<TugasProvider> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        adapter.notifyDataSetChanged();
    }

    static class DataHandler {
        RecyclerView recyclerView;
        SwipeRefreshLayout swipeRefreshLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        final View view = inflater.inflate(R.layout.tab_tugas, container, false);
        adapter = new TugasAdapter(list, context);
        tugasPresenter = new TugasPresenter(this, this.getContext());

        final LinearLayoutManager linearLayoutManager;
        linearLayoutManager = new LinearLayoutManager(context);

        handler.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        handler.recyclerView.setLayoutManager(linearLayoutManager);
        handler.recyclerView.setItemAnimator(new DefaultItemAnimator());
        handler.recyclerView.setAdapter(adapter);

        handler.swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        showProgress(false);
        handler.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tugasPresenter.getAllTugas();
            }
        });
        tugasPresenter.getAllTugas();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String msg_for_me = intent.getStringExtra("some_msg");
                //log our message value
                tugasPresenter.getAllTugas();

            }
        };
        context.registerReceiver(broadcastReceiver, new IntentFilter("new_tugas"));
    }


}

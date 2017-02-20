package fachrian.masterlib.v;

import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import fachrian.masterlib.R;
import fachrian.masterlib.c.AlertDialogController;
import fachrian.masterlib.v.adapter.TabAdapter;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class Home extends AppCompatActivity {
     Toolbar toolbar;
    private int[] tabIcons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };
    private String[] tabTitles = {
            "Kejadian",
            "Tugas",
            "History",
            "Setting"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        for (int i = 0; i < tabIcons.length; i++) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TabAdapter adapter = new TabAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                toolbar.setTitle(tabTitles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialogController.showDialogGPSOn(this);
        }
    }
}

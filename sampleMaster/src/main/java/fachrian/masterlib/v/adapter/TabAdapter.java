package fachrian.masterlib.v.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fachrian.masterlib.v.fragment.TabProfile;
import fachrian.masterlib.v.fragment.TabTugas;


/**
 * Created by Fachrian on 24/06/2016.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TabTugas();
            case 1:
                return new TabTugas();
            case 2:
                return new TabTugas();
            case 3:
                return new TabProfile();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

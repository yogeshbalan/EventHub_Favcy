package com.yogesh.eventhub.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yogesh.eventhub.ui.fragments.TabAllEventsFragment;
import com.yogesh.eventhub.ui.fragments.TabFavoritesFragment;


/**
 * Created by Aron on 7/9/2015.
 */
public class MainFragmentsAdapter extends FragmentPagerAdapter {
    public MainFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new TabAllEventsFragment();
        }else if (position == 1) {
            return new TabFavoritesFragment();
        }/*else if (position == 2) {
            return new TabAllEventsFragment();
        }else if (position == 3) {
            return new TabFavoritesFragment();
        }*/else{
            return new TabAllEventsFragment();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "ALL EVENTS" ;
        } else if (position == 1) {
            return "FAVORITES";
        } /*else if (position == 2) {
            return "ALL EVENTS";
        }else if (position == 3) {
            return "FAVORITES";
        }*/ else {
            return null;
        }
    }
}

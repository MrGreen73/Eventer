package com.ivan.eventer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.view.Event.ChatFragment;
import com.ivan.eventer.view.Event.PreviewFragment;
import com.ivan.eventer.view.Event.SettingsEventFragment;
import com.ivan.eventer.view.Event.ThingsFragment;

public class EventMonitorAdapter extends FragmentPagerAdapter {

    private int mNumOfTabs;
    private boolean mIsAuthor;

    public Fragment[] mFragments;

    public EventMonitorAdapter(EventActivity sourceActivity, FragmentManager fm, int numOfTabs, boolean isAuthor) {
        super(fm);
        mNumOfTabs = numOfTabs;
        mIsAuthor = isAuthor;
        mFragments = new Fragment[]{new SettingsEventFragment(), new PreviewFragment(), new ChatFragment(), new ThingsFragment()};

        for (Fragment fragment : mFragments){
            if (fragment instanceof EventActivity.ConnectionListener){
                sourceActivity.addConnectionListener((EventActivity.ConnectionListener)fragment);
            }
        }
    }

    @Override
    public Fragment getItem(int position) {

        // Если гость, то мы "урезаем" первую страницу с настройками
        if (!mIsAuthor) {
            position += 1;
        }
        switch (position) {
            case 0:
                return (mFragments[0]);
            case 1:
                return (mFragments[1]);
            case 2:
                return (mFragments[2]);
            case 3:
                return (mFragments[3]);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        if (mIsAuthor) {

            return mNumOfTabs;

        }

        // Если гость, то страниц на одну меньше
        return mNumOfTabs - 1;

    }

}
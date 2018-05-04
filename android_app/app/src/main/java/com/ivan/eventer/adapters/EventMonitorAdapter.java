package com.ivan.eventer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ivan.eventer.view.ChatFragment;

public class EventMonitorAdapter extends FragmentPagerAdapter {

    private int mNumOfTabs;
    private boolean mIsAuthor;

    public EventMonitorAdapter(FragmentManager fm, int numOfTabs, boolean isAuthor) {
        super(fm);
        mNumOfTabs = numOfTabs;
        mIsAuthor = isAuthor;
    }

    @Override
    public Fragment getItem(int position) {

        // Если гость, то мы "урезаем" первую страницу с настройками
        if (!mIsAuthor) {
            position += 1;
        }
        switch (position) {
            case 0:
                return (new ChatFragment());
            case 1:
                return (new ChatFragment());
            case 2:
                return (new ChatFragment());
            case 3:
                return (new ChatFragment());
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
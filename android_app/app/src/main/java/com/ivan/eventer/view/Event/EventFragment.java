package com.ivan.eventer.view.Event;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.EventMonitorAdapter;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.controller.MainActivity;

public class EventFragment extends Fragment {

    // Для отображения табов
    private ViewPager mViewPager;

    // Область, в которой расположены табы
    private TabLayout mTabLayout;

    //
    private int mPageNumber;

    EventMonitorAdapter mEventMonitorAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;

        if (isAuthor()) {

            view = inflater.inflate(R.layout.fragment_event_author, container, false);

        } else {

            view = inflater.inflate(R.layout.fragment_event_guest, container, false);

        }

        mViewPager = view.findViewById(R.id.viewPager);
        mTabLayout = view.findViewById(R.id.tabLayout);

        mEventMonitorAdapter = new EventMonitorAdapter((EventActivity) getActivity(), getFragmentManager(), 4, isAuthor());
        mViewPager.setAdapter(mEventMonitorAdapter);

        // Добавляем стандартного слушателя
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                mPageNumber = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mViewPager.setCurrentItem(mPageNumber);

        return view;

    }

    private boolean isAuthor() {
        return EventActivity.sEventPreview.getAuthor().equals(MainActivity.sPersonDate.getEmail());

    }

}

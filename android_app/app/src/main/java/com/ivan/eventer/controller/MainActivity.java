package com.ivan.eventer.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.ivan.eventer.R;
import com.ivan.eventer.view.CreateFragment;
import com.ivan.eventer.view.HomeFragment;
import com.ivan.eventer.view.LikeFragment;
import com.ivan.eventer.view.ProfileFragment;
import com.ivan.eventer.view.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private Fragment container;
    private Fragment fragment;

    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();

                    if (container == null) {

                        fragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

                    }
                    break;
                case R.id.navigation_search:
                    fragment = new SearchFragment();

                    if (container == null) {

                        fragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

                    }
                    break;
                case R.id.navigation_create:
                    fragment = new CreateFragment();

                    if (container == null) {

                        fragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

                    }
                    break;
                case R.id.navigation_like:
                    fragment = new LikeFragment();

                    if (container == null) {

                        fragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

                    }
                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();

                    if (container == null) {

                        fragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

                    }
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentManager = getSupportFragmentManager();
        container = fragmentManager.findFragmentById(R.id.mainContainer);
        fragment = new HomeFragment();

        if (container == null) {

            fragmentManager.beginTransaction().add(R.id.mainContainer, fragment).addToBackStack(null).commit();

        }

        bnve.enableAnimation(true);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        int widthDp = 36;
        int heightDp = 36;
        int sp = 0;

        bnve.setIconSize(widthDp, heightDp);
        bnve.setTextSize(sp);

    }

}

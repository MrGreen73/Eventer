package com.ivan.eventer.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ivan.eventer.R;
import com.ivan.eventer.view.EventFragment;
import com.ivan.eventer.view.SearchFragment;
import com.ivan.eventer.view.SettingsFragment;
import com.ivan.eventer.view.StartFragment;

public class EventActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment mContainer;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.eventFragmentContainer);

        if (fragment == null) {

            fragment = new EventFragment();
            fm.beginTransaction()
                    .add(R.id.eventFragmentContainer, fragment)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }
}

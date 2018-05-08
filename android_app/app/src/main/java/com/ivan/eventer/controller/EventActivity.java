package com.ivan.eventer.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.model.Event;
import com.ivan.eventer.model.EventPreview;
import com.ivan.eventer.view.EventFragment;

public class EventActivity extends AppCompatActivity {

    public static EventPreview sEventPreview;

    private FragmentManager mFragmentManager;
    private Fragment mContainer;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        makeEvent(getID());

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.eventFragmentContainer);

        if (fragment == null) {

            fragment = new EventFragment();
            fm.beginTransaction()
                    .add(R.id.eventFragmentContainer, fragment)
                    .commit();
        }

    }

    private void makeEvent(String id) {
//TODO: Исправить
        Event event = Commands.findEventById("145");
        EventActivity.sEventPreview = new EventPreview(event.getID(), event.getTitle(), "10", event.getDescribe(), "NaN", event.getAuthor());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    private String getID(){

        return getIntent().getStringExtra("ID");

    }
}

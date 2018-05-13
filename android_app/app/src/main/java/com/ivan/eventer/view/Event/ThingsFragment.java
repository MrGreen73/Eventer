package com.ivan.eventer.view.Event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.ThingAdapter;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.model.Thing;

import java.util.List;


public class ThingsFragment extends Fragment implements EventActivity.ConnectionListener {

    private EventActivity mEventActivity;

    //Лист
    private List<Thing> mThingsList;

    // Адаптер
    private ThingAdapter mThingAdapter;

    // Вещь
    private EditText mThing;

    // Кнопка для добавления
    private ImageButton mSendBtn;

    // Список
    private RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEventActivity = (EventActivity) getActivity();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_things, container, false);
        mSendBtn = v.findViewById(R.id.thing_send_btn);
        mThing = v.findViewById(R.id.thing_edit_text);

        mSendBtn.setOnClickListener(v1 -> {

            String thing = mThing.getText().toString();
            addThing(thing);

        });

        mRecyclerView = v.findViewById(R.id.recyclerThings);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        showThings();

        return v;

    }

    private void showThings() {

        initializeData();

    }

    private void addThing(String thing) {

        if (!TextUtils.isEmpty(thing)) {

//            String data = EventActivity.sEventPreview.getID() + " " + thing;
            Commands.addThings(EventActivity.sEventPreview.getID(), thing);
            mThing.setText("");
            //            mEventActivity.sendData(data);

        }

    }

    private void initializeData() {

        Thread thread = new Thread() {
            @Override
            public void run() {

                mThingsList= Commands.thingsOfEvent(EventActivity.sEventPreview.getID()).getArr();

                getActivity().runOnUiThread(() -> {

                    mThingAdapter = new ThingAdapter(mThingsList);
                    mRecyclerView.setAdapter(mThingAdapter);


                    if (mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {

                        mRecyclerView.scrollToPosition(mThingsList.size() - 1);

                    }
                });

            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public char getCommandType() {
        return '2';
    }

    @Override
    public void getData(String data) {
        Log.d("DEBUG", "things get data");

        mThingsList= Commands.thingsOfEvent(EventActivity.sEventPreview.getID()).getArr();

        getActivity().runOnUiThread(() -> {

            mThingAdapter = new ThingAdapter(mThingsList);
            mRecyclerView.setAdapter(mThingAdapter);

            if (mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {

                mRecyclerView.scrollToPosition(mThingsList.size() - 1);

            }
        });

    }
}

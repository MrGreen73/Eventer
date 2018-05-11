package com.ivan.eventer.view.Main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.R;
import com.ivan.eventer.adapters.EventsListAdapter;
import com.ivan.eventer.model.Event;

import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    // Список события
    private List<Event> mEventList;
    // Адаптер для вывода списка событий
    private EventsListAdapter mEventsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = v.findViewById(R.id.recyclerHome);

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        // Загрузка событий из базы данных
        initializeData();

        Collections.reverse(mEventList);
        mEventsListAdapter = new EventsListAdapter(mEventList);
        rv.setAdapter(mEventsListAdapter);

        return v;

    }

    // Загрузка событий из баззы данных
    private void initializeData(){

        Thread thread = new Thread(){

            @Override
            public void run() {

            mEventList = Commands.getEvents();

            }

        };

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

}

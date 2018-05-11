package com.ivan.eventer.view.Main.Search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.EventsListAdapter;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultFragment extends Fragment {

    // Список события
    private List<Event> mEventList;
    // Список нужных события
    private List<Event> mSearchList;
    // Адаптер для вывода списка событий
    private EventsListAdapter mEventsListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        RecyclerView rv = view.findViewById(R.id.recyclerHome);

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        // Загрузка событий из базы данных
        initializeData();

        switch (MainActivity.TYPE_SEARCH_ACTIVE){

            case 0:
                String title = MainActivity.FILTER_OR_TITLE;
                mSearchList = makeSelectionByTitle(title);
                break;

            case 1:
                String filter = MainActivity.FILTER_OR_TITLE;
                mSearchList = makeSelectionByFilter(filter);
                break;

            default:
                mSearchList = new ArrayList<>();
                break;


        }

        Collections.reverse(mSearchList);
        mEventsListAdapter = new EventsListAdapter(mSearchList);
        rv.setAdapter(mEventsListAdapter);

        return view;

    }

    private List<Event> makeSelectionByFilter(String filter) {

        List<Event> result = new ArrayList<>();

        if (filter.length() > 1) {

            if (filter.charAt(0) == ';') {

                filter = filter.substring(1);

                String[] time = filter.split(" ");

                for (String aTime : time){

                    for (Event event : mEventList) {

                        if (event.getTime().equals(aTime)) {

                            result.add(event);

                        }

                    }

                }


            } else if (filter.charAt(filter.length() - 1) == ';'){

                filter = filter.substring(0, filter.length() - 2);

                String[] kind = filter.split(" ");

                for (String aKind : kind){

                    for (Event event : mEventList) {

                        if (event.getKind().equals(aKind)) {

                            result.add(event);

                        }

                    }

                }

            } else {

                String[] typeFilter = filter.split(";");

                String[] kind = typeFilter[0].split(" ");
                String[] time = typeFilter[1].split(" ");

                for (String aKind : kind) {

                    for (String aTime : time) {

                        for (Event event : mEventList) {

                            if (event.getKind().equals(aKind) && event.getTime().equals(aTime)) {

                                result.add(event);

                            }

                        }

                    }

                }

            }


        }

        return result;

    }

    private List<Event> makeSelectionByTitle(String title) {

        List<Event> result = new ArrayList<>();

        for (Event event : mEventList){

            if (event.getTitle().equals(title)){

                result.add(event);

            }

        }

        return result;

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

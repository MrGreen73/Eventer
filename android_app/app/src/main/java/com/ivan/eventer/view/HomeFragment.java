package com.ivan.eventer.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.adapterEvents;
import com.ivan.eventer.adapters.adapterPerson;

import java.util.ArrayList;
import java.util.List;

import com.ivan.eventer.model.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private List<Event> mEventList;
    private adapterEvents mAdapterEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = v.findViewById(R.id.recyclerHome);

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        initializeData();

        mAdapterEvents = new adapterEvents(mEventList);
        rv.setAdapter(mAdapterEvents);

        return v;

    }


    private void initializeData(){

        //TODO: Сделать выгрузку событий из базы данных

        mEventList = new ArrayList<>();

        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mEventList.add(new Event("Пикник", "Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));


    }



}

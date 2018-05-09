package com.ivan.eventer.view.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.ProfileAdapter;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.Event;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private List<Event> mEventList;
    private ProfileAdapter mProfileAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView rv = v.findViewById(R.id.recyclerProfile);

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        initializeData();

        mProfileAdapter = new ProfileAdapter(mEventList);
        rv.setAdapter(mProfileAdapter);

        return v;

    }

    private void initializeData(){

        mEventList = Commands.allEventsOfUser(MainActivity.sPersonDate.getEmail());

        if (mEventList == null) {

            mEventList = new ArrayList<>();

        }

    }

}

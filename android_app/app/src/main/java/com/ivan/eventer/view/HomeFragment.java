package com.ivan.eventer.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.adapterPerson;

import java.util.ArrayList;
import java.util.List;

import com.ivan.eventer.model.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rv = v.findViewById(R.id.recyclerHome);

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        initializeData();

        adapterPerson adapter = new adapterPerson(persons);
        rv.setAdapter(adapter);

        return v;

    }

    private List<Person> persons;

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.ic_home_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.ic_home_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_dashboard_black_24dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_notifications_black_24dp));


    }



}

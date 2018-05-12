package com.ivan.eventer.view.Main.Search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterFragment extends Fragment {

    private List<String> mListKind;
    private List<String> mListTime;

    //Checkbox Kind
    private CheckBox mFilterKindWalk;
    private CheckBox mFilterKindSport;
    private CheckBox mFilterKindCinema;
    private CheckBox mFilterKindActive;
    private CheckBox mFilterKindParty;
    private CheckBox mFilterKindArt;

    //Checkbox Time
    private CheckBox mFilterTimeMorning;
    private CheckBox mFilterTimeDay;
    private CheckBox mFilterTimeEvening;

    //Button
    private Button mSearchBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_filter, container, false);

        mFilterKindWalk = view.findViewById(R.id.filterKindWalk);
        mFilterKindSport = view.findViewById(R.id.filterKindSport);
        mFilterKindCinema= view.findViewById(R.id.filterKindCinema);
        mFilterKindActive = view.findViewById(R.id.filterKindActive);
        mFilterKindParty = view.findViewById(R.id.filterKindParty);
        mFilterKindArt = view.findViewById(R.id.filterKindArt);

        mFilterTimeMorning = view.findViewById(R.id.filterTimeMorning);
        mFilterTimeDay = view.findViewById(R.id.filterTimeDay);
        mFilterTimeEvening = view.findViewById(R.id.filterTimeEvening);

        mSearchBtn = view.findViewById(R.id.filterBtnSearch);

        mSearchBtn.setOnClickListener(v -> {

            mListKind = makeKind();
            mListTime = makeTime();

            searchByFilter();

        });

        return view;

    }

    private void searchByFilter() {

        String filter = "";

        for (String i : mListKind){

            filter += (i + " ");

        }

        if (filter.length() > 1){

            filter = filter.substring(0, filter.length() - 1);

        }

        filter += ";";

        for (String i : mListTime){

            filter += (i + " ");

        }

        if (filter.charAt(filter.length() - 1) != ';'){

            filter = filter.substring(0, filter.length() - 1);

        }

        MainActivity.FILTER_OR_TITLE = filter;
        MainActivity.TYPE_SEARCH_ACTIVE = MainActivity.TYPE_SEARCH_FILTER;
        MainActivity.changeFragment(new SearchResultFragment());

    }

    private List<String> makeTime() {

        List<String> listTime = new ArrayList<>();

        if (mFilterTimeMorning.isChecked()) {

            listTime.add("Утро");

        }

        if (mFilterTimeDay.isChecked()) {

            listTime.add("День");

        }

        if (mFilterTimeEvening.isChecked()) {

            listTime.add("Вечер");

        }

        return listTime;

    }

    private List<String> makeKind() {

        List<String> listKind = new ArrayList<>();

        if (mFilterKindWalk.isChecked()) {

            listKind.add("Прогулка");

        }

        if (mFilterKindSport.isChecked()) {

            listKind.add("Спорт");

        }

        if (mFilterKindCinema.isChecked()) {

            listKind.add("Кино");

        }

        if (mFilterKindActive.isChecked()) {

            listKind.add("Активный");

        }

        if (mFilterKindParty.isChecked()) {

            listKind.add("Вечеринка");

        }

        if (mFilterKindArt.isChecked()) {

            listKind.add("Искусство");

        }

        return listKind;

    }

}

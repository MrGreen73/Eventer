package com.ivan.eventer.view.Main.Search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.ivan.eventer.R;

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

        mSearchBtn = view.findViewById(R.id.filterBtnSearch);

        mSearchBtn.setOnClickListener(v -> {

            mListKind = makeKind();
            mListTime = makeTime();

            searchByFilter();

        });

        return view;

    }

    private void searchByFilter() {

        //TODO: Выполнить поиск

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

package com.ivan.eventer.view.Main.Search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;

public class SearchFragment extends Fragment {

    private Button mSearchByTitle;
    private Button mSearchByFilter;
    private Button mSearchByQR;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_search, container, false);

        mSearchByTitle = v.findViewById(R.id.searchByTitle);
        mSearchByFilter = v.findViewById(R.id.searchByFilter);
        mSearchByQR = v.findViewById(R.id.searchByQR);

        mSearchByTitle.setOnClickListener(v1 -> {

            MainActivity.changeFragment(new SearchTitleFragment());

        });

        mSearchByFilter.setOnClickListener(v1 -> {

            MainActivity.changeFragment(new SearchFilterFragment());

        });

        mSearchByQR.setOnClickListener(v1 -> {

            MainActivity.changeFragment(new SearchQRFragment());

        });

        return v;

    }

}

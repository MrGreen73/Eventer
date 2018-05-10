package com.ivan.eventer.view.Main.Search;

import android.content.Intent;
import android.net.Uri;
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
    private Button mSearchByAfisha;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_search, container, false);

        mSearchByTitle = v.findViewById(R.id.searchByTitle);
        mSearchByFilter = v.findViewById(R.id.searchByFilter);
        mSearchByQR = v.findViewById(R.id.searchByQR);
        mSearchByAfisha = v.findViewById(R.id.searchByAfisha);

        mSearchByTitle.setOnClickListener(v1 -> {

            MainActivity.changeFragment(new SearchTitleFragment());

        });

        mSearchByFilter.setOnClickListener(v1 -> {

            MainActivity.changeFragment(new SearchFilterFragment());

        });

        mSearchByQR.setOnClickListener(v1 -> {

            MainActivity.changeFragment(new SearchQRFragment());

        });

        mSearchByAfisha.setOnClickListener(v1 -> {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.afisha.ru"));
            startActivity(browserIntent);

        });

        return v;

    }

}

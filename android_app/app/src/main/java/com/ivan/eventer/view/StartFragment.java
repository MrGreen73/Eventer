package com.ivan.eventer.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ivan.eventer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_start, container, false);

        Button regBtn = v.findViewById(R.id.startRegBtn);
        Button logBtn = v.findViewById(R.id.startLogBtn);

        regBtn.setOnClickListener(view -> {

            Fragment newFragment = new RegisterFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.startContainer, newFragment)
                    .addToBackStack(null)
                    .commit();

        });

        logBtn.setOnClickListener(view -> {

            Fragment newFragment = new LoginFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();

            transaction.replace(R.id.startContainer, newFragment)
                    .addToBackStack(null)
                    .commit();

        });

        return v;
    }

}

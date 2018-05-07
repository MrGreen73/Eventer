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

public class StartFragment extends Fragment {

    //Кнопки
    private Button mButtonRegister; //Для регистрации
    private Button mButtonLogin; //Для авторизации

    private FragmentTransaction mFragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_start, container, false);

        mButtonRegister = v.findViewById(R.id.startRegBtn);
        mButtonLogin = v.findViewById(R.id.startLogBtn);
        mFragmentTransaction = getFragmentManager().beginTransaction();

        mButtonRegister.setOnClickListener(view -> {

            //Отправление на фрагмент для регистрации
            sendToFragment(new RegisterFragment());

        });

        mButtonLogin.setOnClickListener(view -> {

            //Отправление на фрагмент для авторизации
            sendToFragment(new LoginFragment());

        });

        return v;
    }

    private void sendToFragment(Fragment fragment) {

        mFragmentTransaction
                .replace(R.id.startContainer, fragment)
                .addToBackStack(null)
                .commit();

    }

}

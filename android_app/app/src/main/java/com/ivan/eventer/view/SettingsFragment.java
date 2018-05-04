package com.ivan.eventer.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ivan.eventer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    public static final String USER_SETTINGS = "user_settings";
    public static final String USER_NAME = "name";
    public static final String USER_AGE = "age";
    public static final String USER_CITY = "city";



    private EditText mName;
    private EditText mAge;
    private EditText mCity;
    private Button mSaveBtn;
    private Button mOutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mName = view.findViewById(R.id.settingsName);
        mAge = view.findViewById(R.id.settingsAge);
        mCity = view.findViewById(R.id.settinsgCity);
        mSaveBtn = view.findViewById(R.id.saveBtn);
        mOutBtn = view.findViewById(R.id.outBtn);

        loadOldDate();

        mSaveBtn.setOnClickListener(v -> {

            String name = mName.getText().toString();
            String age = mAge.getText().toString();
            String city = mCity.getText().toString();
            View focusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(city)){

                saveNewDate(name, age, city);

            } else {

                if (TextUtils.isEmpty(city)) {

                    mCity.setError("Введите город");
                    focusView = mCity;

                }

                if (TextUtils.isEmpty(age)) {

                    mAge.setError("Введите возраст");
                    focusView = mAge;

                }

                if (TextUtils.isEmpty(name)) {

                    mName.setError("Введите имя");
                    focusView = mName;

                }

                focusView.requestFocus();

            }

        });

        mOutBtn.setOnClickListener(v -> {

            logOut();

        });

        return  view;

    }

    private void logOut() {

        //TODO: Добавить выход из приложения

    }

    private void saveNewDate(String name, String age, String city) {

        //TODO: Добавить сохранение новых данных

    }

    private void loadOldDate() {

        //TODO: Добавить выгрузку в поля старые данные

    }

}

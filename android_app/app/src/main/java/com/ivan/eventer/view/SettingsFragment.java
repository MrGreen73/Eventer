package com.ivan.eventer.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.controller.StartActivity;

public class SettingsFragment extends Fragment {

    // Поля ввода
    private EditText mName; // Имя
    private EditText mAge; // Возраст
    private EditText mCity; // Город

    // Кнопки
    private Button mSaveBtn; // Для сохранения данных
    private Button mOutBtn; // Для выхода из аккаунта

    // Для загрузки данных о пользователе
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mName = view.findViewById(R.id.settingsName);
        mAge = view.findViewById(R.id.settingsAge);
        mCity = view.findViewById(R.id.settinsgCity);
        mSaveBtn = view.findViewById(R.id.saveBtn);
        mOutBtn = view.findViewById(R.id.outBtn);
        mSharedPreferences = getActivity().getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);

        // Загрузка данных о пользователе
        loadOldDate();

        // Сохранение новых данных о пользователе
        mSaveBtn.setOnClickListener(v -> {

            String name = mName.getText().toString();
            String age = mAge.getText().toString();
            String city = mCity.getText().toString();
            View focusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(city)){

                // Сохранение новых данных о пользователе
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

    // Выход из аккаунта
    private void logOut() {

        MainActivity.sPersonDate.deletePerson();

        Intent startIntent = new Intent(getActivity(), StartActivity.class);
        startActivity(startIntent);
        getActivity().finish();

    }

    // Сохранение новых данных о пользователе
    private void saveNewDate(String name, String age, String city) {

        MainActivity.sPersonDate.updatePerson(name, age, city);
        updateSharedPreferences(name, age, city);
//        Commands.updatePerson(name, age, city);

    }

    // Сохранение новых данных о пользователе локально
    private void updateSharedPreferences(String name, String age, String city) {

        mSharedPreferences = getActivity().getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(StartActivity.USER_NAME, name);
        editor.putString(StartActivity.USER_AGE, age);
        editor.putString(StartActivity.USER_CITY, city);
        editor.apply();

    }

    // Загрузка данных о пользователе
    private void loadOldDate() {

        mName.setText(MainActivity.sPersonDate.getName());
        mAge.setText(MainActivity.sPersonDate.getAge());
        mCity.setText(MainActivity.sPersonDate.getCity());

    }

}

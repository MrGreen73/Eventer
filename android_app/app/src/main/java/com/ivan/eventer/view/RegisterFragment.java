package com.ivan.eventer.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.controller.StartActivity;

public class RegisterFragment extends Fragment {

    private EditText mName;
    private EditText mEmail;
    private EditText mAge;
    private EditText mCity;
    private EditText mPassword;
    private Button mButton;
    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mName = v.findViewById(R.id.registerName);
        mEmail= v.findViewById(R.id.registerEmail);
        mAge= v.findViewById(R.id.registerAge);
        mCity= v.findViewById(R.id.registerCity);
        mPassword= v.findViewById(R.id.registerPassword);
        mButton = v.findViewById(R.id.regBtn);
        mProgressDialog = new ProgressDialog(getActivity());

        mButton.setOnClickListener(v1 -> {

            String name = mName.getText().toString();
            String email= mEmail.getText().toString();
            String age = mAge.getText().toString();
            String city= mCity.getText().toString();
            String password = mPassword.getText().toString();

            View focusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(password)) {

                mProgressDialog.setTitle(getString(R.string.progressDialogRegister));
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                registerUser(name, email, age, city, password);

            } else {

                if (TextUtils.isEmpty(password)){

                    mPassword.setError("Введите пароль");
                    focusView = mPassword;

                }

                if (TextUtils.isEmpty(city)) {

                    mCity.setError("Введите город");
                    focusView = mCity;

                }

                if (TextUtils.isEmpty(age)) {

                    mAge.setError("Введите возраст");
                    focusView = mAge;

                }

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Введите почту");
                    focusView = mEmail;

                }

                if (TextUtils.isEmpty(name)) {

                    mName.setError("Введите имя");
                    focusView = mName;

                }

                focusView.requestFocus();

            }

        });

        return v;

    }

    private void registerUser(String name, String email, String age, String city, String password) {

        //Запустить новый тред
        Thread thread = new Thread() {

            @Override
            public void run() {

                Commands.createUser(name, email, age, city, password);

            }

        };

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        saveDate(name, email, age, city);
        mProgressDialog.dismiss();
        sentToMain();

    }

    private void saveDate(String name, String email, String age, String city) {

        mSharedPreferences = getActivity().getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(StartActivity.USER_NAME, name);
        editor.putString(StartActivity.USER_EMAIL, email);
        editor.putString(StartActivity.USER_AGE, age);
        editor.putString(StartActivity.USER_CITY, city);
        editor.apply();

    }

    //Посылает пользователя на главную страницу
    private void sentToMain() {

        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();

    }

}

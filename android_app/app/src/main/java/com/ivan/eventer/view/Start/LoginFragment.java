package com.ivan.eventer.view.Start;


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
import android.widget.Toast;

import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.controller.StartActivity;
import com.ivan.eventer.model.User;

public class LoginFragment extends Fragment {

    // Поля ввода
    private EditText mEmail; // Почта
    private EditText mPassword; // Пароль

    // Кнопка для авторизации
    private Button mButtonLogin;

    // Диалог во время выполнения авторизации
    private ProgressDialog mProgressDialog;

    // Для сохранения данных о пользоавтеле
    private SharedPreferences mSharedPreferences;

    // Для подсветки ошибок
    private View mFocusView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = v.findViewById(R.id.loginEmail);
        mPassword= v.findViewById(R.id.loginPassword);
        mButtonLogin = v.findViewById(R.id.logBtn);
        mProgressDialog = new ProgressDialog(getActivity());

        mButtonLogin.setOnClickListener(v1 -> {

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            mFocusView = null;

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                mProgressDialog.setTitle(getString(R.string.progressDialogLogin));
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                loginUser(email, password);

            } else {

                if (TextUtils.isEmpty(password)) {

                    mPassword.setError("Введите пароль");
                    mFocusView = mPassword;

                }

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Введите почту");
                    mFocusView = mEmail;

                }

                    mFocusView.requestFocus();

            }

        });

        return v;

    }

    private void loginUser(String email, String password) {

        final User[] user = new User[1];
        Thread thread = new Thread(){

            @Override
            public void run() {

                mProgressDialog.show();
                user[0] = Commands.loginUser(email, password);

            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (user[0] != null){

            // В случае успешной авторизации

            // Сохраняем данные о пользователе
            saveDate(user[0].getName(), user[0].getEmail(), user[0].getAge(), user[0].getCity());
            // Останавливаем диалог
            mProgressDialog.dismiss();

            // Оповещаем пользователе об успешной авторизации
            Toast.makeText(getActivity(), "Вы успешно авторизованы", Toast.LENGTH_SHORT).show();

            // Отправление на главную активность
            sentToMain();

        } else {

            // В случае проблемы с авторизацией

            // Останавливаем диалог
            mProgressDialog.dismiss();
            //Оповещаем пользователя о некорректности введенных данных
            Toast.makeText(getActivity(), "Почта или пароль введены неверно", Toast.LENGTH_SHORT).show();
            // Устанавливаем совет пользователю
            mEmail.setError("Проверьте почту");
            mPassword.setError("Проверьте пароль");
            // Ставим курсор на ввод почты
            mFocusView = mEmail;
            mFocusView.requestFocus();

        }

    }

    // Сохраняет данные о пользователе локально
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

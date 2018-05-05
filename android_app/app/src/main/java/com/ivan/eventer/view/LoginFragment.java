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
import android.widget.Toast;

import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.controller.StartActivity;
import com.ivan.eventer.model.User;

//import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText mEmail;
    private EditText mPassword;
    private Button mButton;
    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;

//    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = v.findViewById(R.id.loginEmail);
        mPassword= v.findViewById(R.id.loginPassword);
        mButton= v.findViewById(R.id.logBtn);
        mProgressDialog = new ProgressDialog(getActivity());
//        mAuth = FirebaseAuth.getInstance();

        mButton.setOnClickListener(v1 -> {

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            View focusView = null;

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                mProgressDialog.setTitle(getString(R.string.progressDialogLogin));
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                loginUser(email, password);


            } else {

                if (TextUtils.isEmpty(password)) {

                    mPassword.setError("Введите пароль");
                    focusView = mPassword;

                }

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Введите почту");
                    focusView = mEmail;

                }

                    focusView.requestFocus();

            }

        });

        return v;

    }

    private void loginUser(String email, String password) {

        final User[] user = new User[1];
        Thread thread = new Thread(){

            @Override
            public void run() {

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

            saveDate(user[0].getName(), user[0].getEmail(), user[0].getAge(), user[0].getCity());
            mProgressDialog.dismiss();
            sentToMain();

        } else {

            Toast.makeText(getActivity(), "NOPE", Toast.LENGTH_SHORT).show();

        }


/*

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()){

                //TODO: Добавить локальную загрузку данных о пользователе
                mProgressDialog.dismiss();
                sentToMain();

            } else {

                mProgressDialog.hide();
                Snackbar snackbar = Snackbar.make(getView(), "Ошибка", Snackbar.LENGTH_LONG);
                snackbar.setAction("Повторить", v12 -> loginUser(email, password));
                snackbar.show();

            }

        });


*/

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

package com.ivan.eventer.view;


import android.app.ProgressDialog;
import android.content.Intent;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private EditText mName;
    private EditText mEmail;
    private EditText mAge;
    private EditText mCity;
    private EditText mPassword;
    private Button mButton;
    private ProgressDialog mProgressDialog;

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

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(password)) {

                mProgressDialog.setTitle(getString(R.string.progressDialogRegister));
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                registerUser(name, email, age, city, password);


            } else {

                //TODO: добавить shackbar здесь

            }

        });

        return v;

    }

    private void registerUser(String name, String email, String age, String city, String password) {

        //TODO: добавить регистрацию пользователя

        mProgressDialog.dismiss();
        sentToMain();

    }


    //Посылает пользователя на главную страницу
    private void sentToMain() {

        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();

    }

}

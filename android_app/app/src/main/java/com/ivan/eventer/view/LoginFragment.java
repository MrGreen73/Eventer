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
import com.ivan.eventer.controller.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText mEmail;
    private EditText mPassword;
    private Button mButton;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = v.findViewById(R.id.loginEmail);
        mPassword= v.findViewById(R.id.loginPassword);
        mButton= v.findViewById(R.id.logBtn);
        mProgressDialog = new ProgressDialog(getActivity());

        mButton.setOnClickListener(v1 -> {

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                mProgressDialog.setTitle(getString(R.string.progressDialogLogin));
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);

                loginUser(email, password);


            } else {

                //TODO: добавить shackbar здесь

            }

        });

        return v;

    }

    private void loginUser(String email, String password) {

        //TODO: выполнить проверку наличия пользователя в базе данных

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

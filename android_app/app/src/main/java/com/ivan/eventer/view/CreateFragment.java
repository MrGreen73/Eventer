package com.ivan.eventer.view;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.eventer.R;

import java.io.*;
import java.net.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {

    private EditText mName;
    private EditText mCount;
    private EditText mDescribe;
    private Button mButton;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create, container, false);

        mName = v.findViewById(R.id.createName);
        mCount= v.findViewById(R.id.createCount);
        mDescribe= v.findViewById(R.id.createDescribe);
        mButton = v.findViewById(R.id.createBtn);
        mProgressDialog = new ProgressDialog(getActivity());

        mButton.setOnClickListener(v1 -> {

            String name = mName.getText().toString();
            String count= mCount.getText().toString();
            String describe = mDescribe.getText().toString();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(count) && !TextUtils.isEmpty(describe)) {

                mProgressDialog.setTitle("Создание события");
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                makeEvent(name, count, describe);

            } else {

                //TODO: сделать snackbar здесь

            }

        });



        return v;

    }

    private void makeEvent(String name, String count, String describe) {

        //TODO: сделать создание события

        mProgressDialog.dismiss();


    }

}

package com.ivan.eventer.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivan.eventer.R;

import java.io.*;
import java.net.*;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateFragment extends Fragment {

    private EditText mName;
    private EditText mCount;
    private EditText mDescribe;
    private Button mButton;
    private ProgressDialog mProgressDialog;

    //Firebase
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

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

            View focusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(count) && !TextUtils.isEmpty(describe)) {

                mProgressDialog.setTitle("Создание события");
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                makeEvent(name, count, describe);

            } else {


                if (TextUtils.isEmpty(describe)) {

                    mDescribe.setError("Введите описание");
                    focusView = mDescribe;

                }

                if (TextUtils.isEmpty(count)) {

                    mCount.setError("Введите количество человек");
                    focusView = mCount;

                }

                if (TextUtils.isEmpty(name)) {

                    mName.setError("Введите название");
                    focusView = mName;

                }

                focusView.requestFocus();

            }

        });

        return v;

    }

    private void makeEvent(String name, String count, String describe) {

        mProgressDialog.dismiss();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").push();

        HashMap<String, String> event = new HashMap<>();
        event.put("name", name);
        event.put("count", count);
        event.put("describe", describe);
        event.put("author", mUser.getEmail());
        event.put("place", "0 0");
        event.put("address", "NaN");

        mDatabase.setValue(event).addOnCompleteListener(task -> {

            if (task.isSuccessful()){

                Snackbar.make(getView(), "Событие создано", Snackbar.LENGTH_LONG).show();

            } else {

                Snackbar snackbar = Snackbar.make(getView(), "Ошибка", Snackbar.LENGTH_LONG);
                snackbar.setAction("Повторить", v12 -> makeEvent(name, count, describe));
                snackbar.show();

            }

        });

    }

}

package com.ivan.eventer.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateFragment extends Fragment {

    private EditText mName;
    private EditText mCount;
    private EditText mDescribe;
    private Button mButton;
    private ImageButton mChangeImageBtn;
    private ImageView mImageEvent;
    private ProgressDialog mProgressDialog;
    private List<Integer> mListImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create, container, false);

        mName = v.findViewById(R.id.createName);
        mCount= v.findViewById(R.id.createCount);
        mDescribe= v.findViewById(R.id.createDescribe);
        mButton = v.findViewById(R.id.createBtn);
        mChangeImageBtn = v.findViewById(R.id.changeImageBtn);
        mImageEvent = v.findViewById(R.id.createImageEvent);
        mListImage = initializeData();


        mProgressDialog = new ProgressDialog(getActivity());

        mChangeImageBtn.setOnClickListener(v1 -> {

            Random random = new Random();
            Integer position = random.nextInt(mListImage.size());
            mImageEvent.setImageResource(mListImage.get(position));

        });

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

    private List<Integer> initializeData() {

        List<Integer> date = new ArrayList<>();

        date.add(R.drawable.item1);
        date.add(R.drawable.item2);
        date.add(R.drawable.item3);
        date.add(R.drawable.item4);
        date.add(R.drawable.item5);
        date.add(R.drawable.item6);
        date.add(R.drawable.item7);

        return date;

    }

    private void makeEvent(String name, String count, String describe) {

        Thread thread = new Thread() {

            @Override
            public void run() {

                Commands.createEvent(count, describe, name, "NaN");

            }

        };

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        mProgressDialog.dismiss();

    }

}

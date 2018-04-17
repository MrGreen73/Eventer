package com.ivan.eventer.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

    private Button btn;
    private EditText name;
    private EditText age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create, container, false);




        return v;

    }

}

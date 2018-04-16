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


/*
        System.out.println("Welcome to Client side");



        System.out.println("Connecting to... " + "localhost");

        try {

            final Socket[] fromserver = {null};
            final BufferedReader[] in = new BufferedReader[1];
            final PrintWriter[] out = new PrintWriter[1];
            final BufferedReader[] inu = new BufferedReader[1];
            Thread thread = new Thread(){


                public void run(){


                    try {
                        fromserver[0] = new Socket("206.189.25.215", 8080);

//                        Toast.makeText(getActivity(), "SERVER", Toast.LENGTH_SHORT).show();
                        in[0] = new
                                BufferedReader(new
                                InputStreamReader(fromserver[0].getInputStream()));
                        out[0] = new
                                PrintWriter(fromserver[0].getOutputStream(), true);
                        inu[0] = new
                                BufferedReader(new InputStreamReader(System.in));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            };

            thread.start();
            Toast.makeText(getActivity(), "до", Toast.LENGTH_SHORT).show();

            String fuser, fserver;
//            new TestS(fromserver);

            btn = v.findViewById(R.id.btn);
            name = v.findViewById(R.id.name);
            age = v.findViewById(R.id.age);

            String myName = name.getEditableText().toString();
//            int myAge= Integer.parseInt(age.getEditableText().toString());

            Toast.makeText(getActivity(), "сюда", Toast.LENGTH_SHORT).show();

            btn.setOnClickListener(v1 -> {

                out[0].println("create");

            });


//            out[0].close();
//            in[0].close();
//            inu[0].close();
//            fromserver[0].close();

        } catch (Exception ex){
            Toast.makeText(getActivity(), "Дошел до кэтча", Toast.LENGTH_SHORT).show();

        }
*/






        return v;

    }

}

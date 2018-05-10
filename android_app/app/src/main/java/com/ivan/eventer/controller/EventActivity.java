package com.ivan.eventer.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.model.Event;
import com.ivan.eventer.model.EventPreview;
import com.ivan.eventer.view.Event.EventFragment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class EventActivity extends AppCompatActivity {

    public static EventPreview sEventPreview;

    private FragmentManager mFragmentManager;
    private Fragment mContainer;
    private Fragment mFragment;

    static int serverPort = 6667; // здесь обязательно нужно указать порт к которому привязывается сервер.
    static String address = "192.168.180.58"; // это IP-адрес компьютера, где исполняется наша серверная программа.

    public static Thread mThreadFrom;

    public static DataOutputStream out;
    public static DataInputStream in;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        makeEvent(getID());

        Thread thread = new Thread() {

            @Override
            public void run() {

                makeConnection();

            }

        };

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.eventFragmentContainer);

        if (fragment == null) {

            fragment = new EventFragment();
            fm.beginTransaction()
                    .add(R.id.eventFragmentContainer, fragment)
                    .commit();
        }

    }

    private void makeEvent(String id) {

        Event event = Commands.findEventById(id);
        EventActivity.sEventPreview = new EventPreview(
                event.getID(),
                event.getTitle(),
                event.getDescribe(),
                event.getAuthor(),
                event.getImage(),
                event.getKind(),
                event.getTime(),
                event.getPlace(),
                event.getDate()
        );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        try{

            mThreadFrom.stop();

        } catch (Exception ex) {

            finish();

        }

        finish();

    }

    private String getID(){

        return getIntent().getStringExtra("ID");

    }
    public void makeConnection() {

        try {

            InetAddress ipAddress = InetAddress.getByName(address); // создаем объект который отображает вышеописанный IP-адрес.
            System.out.println("Any of you heard of a socket with IP address " + address + " and port " + serverPort + "?");
            Socket socket = new Socket(ipAddress, serverPort); // создаем сокет используя IP-адрес и порт сервера.
            System.out.println("Yes! I just got hold of the program.");

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиентом.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            in = new DataInputStream(sin);
            out = new DataOutputStream(sout);

            out.writeUTF(getID());

            System.err.println(getID());

            out.flush();

            System.out.println("Type in something and press enter. Will send it to the server and tell ya what it thinks.");
            System.out.println();


        } catch (Exception x) {

            x.printStackTrace();

        }

    }

}

package com.ivan.eventer.view.Event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.MessageAdapter;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    //Лист сообщений
    private List<Message> mMessagesList;

    // Адаптер сообщений
    private MessageAdapter mMessageAdapter;

    // Сообщение
    private EditText mMessage;

    // Кнопка для отправки сообщения
    private ImageButton mSendBtn;

    // Список сообщений
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        mSendBtn = v.findViewById(R.id.chat_send_btn);
        mMessage = v.findViewById(R.id.chat_edit_text_message);

        mSendBtn.setOnClickListener(v1 -> {

            String message = mMessage.getText().toString();
            addMessage(message);

        });


        mRecyclerView = v.findViewById(R.id.recyclerChat);

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        showMessages();

        return v;

    }

    private void showMessages() {

        initializeData();

        mMessageAdapter = new MessageAdapter(mMessagesList);
        mRecyclerView.setAdapter(mMessageAdapter);

    }

    private void addMessage(String message) {

        try {
            EventActivity.out.writeUTF("email" + " " + message); // отсылаем введенную строку текста серверу.
            EventActivity.out.flush(); // заставляем поток закончить передачу данных.
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   out.wr

    }

    private void initializeData(){

        mMessagesList = new ArrayList<>();

        //Получение листа сообщений у данного события
//        mMessagesList = Commands.getMessages(EventActivity.sEventPreview.getID());

    }


}

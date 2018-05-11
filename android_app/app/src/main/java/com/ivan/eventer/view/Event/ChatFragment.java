package com.ivan.eventer.view.Event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.MessageAdapter;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.Message;

import java.io.IOException;
import java.util.Date;
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



    }

    private void addMessage(String message) {

        try {//TODO:Сделать проверку на пустые сообщения
//email time text
            if (!TextUtils.isEmpty(message)){

                EventActivity.out.writeUTF(MainActivity.sPersonDate.getEmail() + " " + new Date().getTime() + " " + message); // отсылаем введенную строку текста серверу.
                EventActivity.out.flush(); // заставляем поток закончить передачу данных.

            }
        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private void initializeData(){

        Thread thread = new Thread(){
            @Override
            public void run() {

                mMessagesList = (Commands.getMessages(EventActivity.sEventPreview.getID())).getMessages();

                getActivity().runOnUiThread(() -> {

                    mMessageAdapter = new MessageAdapter(mMessagesList);
                    mRecyclerView.setAdapter(mMessageAdapter);

                    if (mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {

                        mRecyclerView.scrollToPosition(mMessagesList.size() - 1);

                    }


                });
            }
        };

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        EventActivity.mThreadFrom = new Thread(() -> {

            while (true) {

                String messageString = null; // ждем пока сервер отошлет строку текста.

                try {

                    messageString = EventActivity.in.readUTF();


                    String finalMessageString = messageString;
                    getActivity().runOnUiThread(()->{
                        Toast.makeText(getActivity(), finalMessageString, Toast.LENGTH_LONG).show();

                    });
                } catch (IOException e) {

//                        EventActivity.mThreadFrom.stop();
                        break;

                }

                if (messageString != null && !messageString.equals("success")){

                    if (messageString.charAt(0) == '1'){

                        String email = messageString.split(" ")[1];
                        String timeString = messageString.split(" ")[2];
                        Long time = Long.parseLong(timeString);
                        String mes = messageString.substring(email.length() + timeString.length() + 4);

                        if (!mes.isEmpty()){

                            Message message = new Message();
                            message.setFrom(email);
                            message.setMessage(mes);
                            message.setDate(time);

                            getActivity().runOnUiThread(() -> {

                                mMessagesList.add(message);
                                mMessageAdapter = new MessageAdapter(mMessagesList);
                                mRecyclerView.setAdapter(mMessageAdapter);
                                mMessage.setText("");

                                if (mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {

                                    mRecyclerView.scrollToPosition(mMessagesList.size() - 1);

                                }

                            });

                        }

                    }

                }

            }

        });

        EventActivity.mThreadFrom.start();

    }

}

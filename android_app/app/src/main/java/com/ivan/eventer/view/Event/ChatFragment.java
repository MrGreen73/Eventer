package com.ivan.eventer.view.Event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.MessageAdapter;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.Message;

import java.util.Date;
import java.util.List;

public class ChatFragment extends Fragment implements EventActivity.ConnectionListener {

    private EventActivity mEventActivity;
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
//        mMessageAdapter = new MessageAdapter(mMessagesList = new ArrayList<>());
//        mRecyclerView.setAdapter(mMessageAdapter);

        showMessages();

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventActivity = (EventActivity) getActivity();
    }

    private void showMessages() {

        initializeData();

    }

    private void addMessage(String message) {

            if (!TextUtils.isEmpty(message)) {
                String data = MainActivity.sPersonDate.getEmail() + " " + new Date().getTime() + " " + message;
                mEventActivity.sendData(data);
            }
    }

    private void initializeData() {

        Thread thread = new Thread() {
            @Override
            public void run() {

//                mMessagesList.clear();
//                mMessagesList.addAll((Commands.getMessages(EventActivity.sEventPreview.getID())).getMessages());
                mMessagesList = Commands.getMessages(EventActivity.sEventPreview.getID()).getMessages();
                getActivity().runOnUiThread(() -> {

                    mMessageAdapter = new MessageAdapter(mMessagesList);
                    mRecyclerView.setAdapter(mMessageAdapter);

//                    mMessageAdapter.notifyDataSetChanged();

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
    }

    @Override
    public char getCommandType() {
        return '1';
    }

    @Override
    public void getData(String data) {
        Log.d("DEBUG", "chat get data");

        String email = data.split(" ")[0];
        String timeString = data.split(" ")[1];
        Long time = Long.parseLong(timeString);
        String mes = data.substring(email.length() + timeString.length() + 2);
        Log.d("DEBUG", "Get " + data);
        if (!mes.isEmpty()) {
            Message message = new Message();
            message.setFrom(email);
            message.setMessage(mes);
            message.setDate(time);

            getActivity().runOnUiThread(() -> {

                mMessagesList.add(message);
//                                mMessageAdapter = new MessageAdapter(mMessagesList);
//                mMessageAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mMessageAdapter);
                mMessage.setText("");

                if (mRecyclerView.getScrollState() != RecyclerView.SCROLL_STATE_DRAGGING) {
                    mRecyclerView.scrollToPosition(mMessagesList.size() - 1);
                }

            });
        }
    }
}

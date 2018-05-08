package com.ivan.eventer.view.Event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.MessageAdapter;
import com.ivan.eventer.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private List<Message> mMessagesList;
    private MessageAdapter mMessageAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView rv = v.findViewById(R.id.recyclerChat);

        rv.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        initializeData();

        mMessageAdapter = new MessageAdapter(mMessagesList);
        rv.setAdapter(mMessageAdapter);

        return v;

    }

    private void initializeData(){

        //TODO: Сделать выгрузку cообщений из базы данных

        mMessagesList = new ArrayList<>();
        //Получение листа сообщений у данного события
//        mMessagesList = Commands.getMessages(EventActivity.sEventPreview.getID());

        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));
        mMessagesList.add(new Message("Прекрасно проведем время на природе, возможно стоит взять мяч", "ivan666@gmail.com"));

    }


}

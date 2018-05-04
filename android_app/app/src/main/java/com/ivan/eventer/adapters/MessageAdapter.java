package com.ivan.eventer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.model.Event;
import com.ivan.eventer.model.Message;

import java.util.List;

/**
 * Created by ivan on 28.04.18.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private static final int TYPE_AUTHOR = 0;
    private static final int TYPE_GUEST = 1;
    List<Message> mMessagesList;

    public MessageAdapter(List<Message> messagesList) {

        mMessagesList = messagesList;
    }

    @Override
    public int getItemViewType(int position) {

        return isAuthor(position) ? TYPE_AUTHOR : TYPE_GUEST;

    }

    private boolean isAuthor(int position) {

        //TODO: Добавить проверку на авторство
        return (position % 3 == 0);

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {

            case TYPE_AUTHOR: {

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_author, parent, false);
                break;

            }

            default: {

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_guest, parent, false);
                break;

            }

        }

        return new MessageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {


        holder.messageText.setText(mMessagesList.get(position).getTextMessage());
        holder.messageAuthor.setText(mMessagesList.get(position).getAuthorMessage());
        holder.messageDate.setText(DateFormat.format("dd-MM-yyyy (hh:mm:ss)", mMessagesList.get(position).getTimeMessage()));



    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageText;
        TextView messageAuthor;
        TextView messageDate;

        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.chat_message);
            messageAuthor = itemView.findViewById(R.id.chat_author);
            messageDate = itemView.findViewById(R.id.chat_date);

        }

    }

    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}

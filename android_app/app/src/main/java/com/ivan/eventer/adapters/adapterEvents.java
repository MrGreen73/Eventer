package com.ivan.eventer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.eventer.R;
import com.ivan.eventer.model.Event;

import java.util.List;

/**
 * Created by ivan on 28.04.18.
 */

public class adapterEvents extends RecyclerView.Adapter<adapterEvents.EventViewHolder> {


    List<Event> mEventList;

    public adapterEvents(List<Event> eventList) {
        mEventList = eventList;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView eventTitle;
        TextView eventDescribe;
        TextView eventAuthor;

        EventViewHolder(View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.itemEventTitle);
            eventDescribe = itemView.findViewById(R.id.itemEventDescribe);
            eventAuthor = itemView.findViewById(R.id.itemEventAuthor);

        }

    }

    @NonNull
    @Override
    public adapterEvents.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

        return new EventViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull adapterEvents.EventViewHolder holder, int position) {

        holder.eventTitle.setText(mEventList.get(position).getTitle());
        holder.eventDescribe.setText(mEventList.get(position).getDescribe());
        holder.eventAuthor.setText(mEventList.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}

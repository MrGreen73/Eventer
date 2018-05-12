package com.ivan.eventer.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.model.Event;

import java.util.List;


public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.EventViewHolder> {


    private List<Event> mEventList;

    public EventsListAdapter(List<Event> eventList) {
        mEventList = eventList;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        MyEventListener mEventListener;
        TextView eventTitle;
        TextView eventDescribe;
        TextView eventAuthor;
        ImageView eventImage;

        EventViewHolder(View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.itemEventTitle);
            eventDescribe = itemView.findViewById(R.id.itemEventDescribe);
            eventAuthor = itemView.findViewById(R.id.itemEventAuthor);
            mEventListener = new MyEventListener();
            eventImage = itemView.findViewById(R.id.itemEventImage);
            itemView.setOnClickListener(mEventListener);

        }

        class MyEventListener implements View.OnClickListener {

            String id;

            @Override
            public void onClick(View v) {

                // Cделать задачю для загрузки ферст


                Intent eventIntent = new Intent(v.getContext(), EventActivity.class);
                eventIntent.putExtra("ID", id);

                Context context = v.getContext();
                context.startActivity(eventIntent);
            }

        }

    }

    @NonNull
    @Override
    public EventsListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

        return new EventViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EventsListAdapter.EventViewHolder holder, int position) {

        holder.eventTitle.setText(mEventList.get(position).getTitle());
        holder.eventDescribe.setText(mEventList.get(position).getDescribe());
        holder.eventAuthor.setText(mEventList.get(position).getAuthor());
        holder.mEventListener.id = mEventList.get(position).getID();
        holder.eventImage.setImageBitmap(getBitmap(mEventList.get(position).getImage()));

    }

    private Bitmap getBitmap(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);

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
package com.ivan.eventer.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.model.Thing;

import java.util.List;

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ThingViewHolder> {

    List<Thing> mThingsList;

    public ThingAdapter(List<Thing> thingsList) {

        mThingsList = thingsList;

    }

    @NonNull
    @Override
    public ThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ThingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ThingViewHolder holder, int position) {

        holder.thingName.setText(mThingsList.get(position).getTitle());
        holder.thingCheckBox.setChecked(mThingsList.get(position).getValue());
        holder.mThingListener.number = position;

    }

    public class ThingViewHolder extends RecyclerView.ViewHolder {

        TextView thingName;
        CheckBox thingCheckBox;
        MyThingListener mThingListener;

        public ThingViewHolder(View itemView) {
            super(itemView);

            thingName = itemView.findViewById(R.id.itemText);
            thingCheckBox= itemView.findViewById(R.id.itemCheck);
            mThingListener = new MyThingListener();
            thingCheckBox.setOnClickListener(mThingListener);

        }


        class MyThingListener implements View.OnClickListener {

            Integer number;

            @Override
            public void onClick(View v) {

                thingCheckBox.setChecked(!thingCheckBox.isChecked());

                Commands.changeThingById(EventActivity.sEventPreview.getID(), number);

            }

        }

    }


    @Override
    public int getItemCount() {
        return mThingsList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}

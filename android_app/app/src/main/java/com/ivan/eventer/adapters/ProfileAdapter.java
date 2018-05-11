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
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.Event;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    List<Event> mEventList;

    public ProfileAdapter(List<Event> eventList) {

        mEventList = eventList;
    }

    @Override
    public int getItemViewType(int position) {

        return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;

    }

    private boolean isPositionHeader(int position) {

        return position == 0;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {

            case TYPE_HEADER: {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_header, parent, false);
                return new HeaderViewHolder(view);

            }

            default: {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
                return new ItemViewHolder(view);

            }

        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderViewHolder) {

            ((HeaderViewHolder) holder).profileName.setText(MainActivity.sPersonDate.getName());
            ((HeaderViewHolder) holder).profileAge.setText(MainActivity.sPersonDate.getAge());
            ((HeaderViewHolder) holder).profileCity.setText(MainActivity.sPersonDate.getCity());
            ((HeaderViewHolder) holder).profileCount.setText((mEventList==null?"0":Integer.toString(mEventList.size())));
            ((HeaderViewHolder) holder).profileImage.setImageBitmap(getBitmap(MainActivity.sPersonDate.getImage()));

        } else {

            ((ItemViewHolder) holder).eventTitle.setText(mEventList.get(position - 1).getTitle());
            ((ItemViewHolder) holder).eventDescribe.setText(mEventList.get(position - 1).getDescribe());
            ((ItemViewHolder) holder).eventAuthor.setText(mEventList.get(position - 1).getAuthor());
            ((ItemViewHolder) holder).eventImage.setImageBitmap(getBitmap(mEventList.get(position - 1).getImage()));
            ((ItemViewHolder) holder).mEventListener.id = mEventList.get(position - 1).getID();

        }

    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView profileName;
        TextView profileAge;
        TextView profileCity;
        TextView profileCount;
        ImageView profileImage;


        public HeaderViewHolder(View itemView) {
            super(itemView);

            profileName = itemView.findViewById(R.id.profileName);
            profileAge = itemView.findViewById(R.id.profileAge);
            profileCity = itemView.findViewById(R.id.profileCity);
            profileCount= itemView.findViewById(R.id.profileCount);
            profileImage = itemView.findViewById(R.id.profileImage);

        }

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemViewHolder.MyEventListener mEventListener;
        TextView eventTitle;
        TextView eventDescribe;
        TextView eventAuthor;
        ImageView eventImage;


        public ItemViewHolder(View itemView) {
            super(itemView);

            eventTitle = itemView.findViewById(R.id.itemEventTitle);
            eventDescribe = itemView.findViewById(R.id.itemEventDescribe);
            eventAuthor = itemView.findViewById(R.id.itemEventAuthor);
            mEventListener = new ItemViewHolder.MyEventListener();
            eventImage = itemView.findViewById(R.id.itemEventImage);
            itemView.setOnClickListener(mEventListener);

        }

        class MyEventListener implements View.OnClickListener {

            String id;

            @Override
            public void onClick(View v) {

                Intent eventIntent = new Intent(v.getContext(), EventActivity.class);
                eventIntent.putExtra("ID", id);

                Context context = v.getContext();
                context.startActivity(eventIntent);

            }

        }

    }

    @Override
    public int getItemCount() {
        return mEventList.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    private Bitmap getBitmap(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);

    }

}

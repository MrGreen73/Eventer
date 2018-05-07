package com.ivan.eventer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ivan.eventer.R;
import com.ivan.eventer.model.Thing;

import java.util.ArrayList;

public class EventThingAdapter extends BaseAdapter {

    private Context activity;
    private ArrayList<Thing> data;
    private static LayoutInflater inflater = null;
    private View vi;
    private ViewHolder viewHolder;

    public EventThingAdapter(Context context, ArrayList<Thing> items) {
        this.activity = context;
        this.data = items;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
public boolean isChecked(int i){return data.get(i).isCheckbox();}
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        vi = view;
        //Populate the Listview
        final int pos = position;
        Thing items = data.get(pos);
        if (view == null) {
            vi = inflater.inflate(R.layout.listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) vi.findViewById(R.id.checkbox);
            viewHolder.name = (TextView) vi.findViewById(R.id.name);
            vi.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) view.getTag();
        viewHolder.name.setText(items.getName());
        if (items.isCheckbox()) {
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }
        return vi;
    }

    public ArrayList<Thing> getAllData() {
        return data;
    }

    public void setCheckBox(int position) {
        //Update status of checkbox
        Thing items = data.get(position);
        items.setCheckbox(!items.isCheckbox());
        notifyDataSetChanged();
    }

    public void add(Thing item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public int size() {
        return data.size() - 1;
    }

    public void clear() {
        data.clear();
    }

    public class ViewHolder {
        TextView name;
        CheckBox checkBox;
    }
}

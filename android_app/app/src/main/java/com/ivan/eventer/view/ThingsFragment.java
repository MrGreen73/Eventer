package com.ivan.eventer.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.EventThingAdapter;
import com.ivan.eventer.model.Thing;

import java.util.ArrayList;


public class ThingsFragment extends Fragment {
    public static final String EVENT_PREVIEW = "thingsfragment.event_preview";

    private ArrayList<Thing> names;
    private ImageButton mCreateThing;
    private EditText mTextThing;

//    private EventPreview mPreview;
    private String mEvenId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPreview = (EventPreview) getArguments().getSerializable(EVENT_PREVIEW);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_things, container, false);
        mCreateThing = v.findViewById(R.id.buttonAdd);
        // super.onCreate(savedInstanceState);
        //  setContentView(R.layout.main);
//        mPreview = (EventPreview) getArguments().getSerializable(EVENT_PREVIEW);
//        mEvenId = mPreview.getEventId();
        mTextThing = v.findViewById(R.id.editText);
        names = new ArrayList<Thing>();

        // находим список
        ListView lvMain = (ListView) v.findViewById(R.id.lvMain);

        // создаем адаптер
        EventThingAdapter adapter = new EventThingAdapter(getActivity(),
                names);

        // присваиваем адаптер списку
        lvMain.setAdapter(adapter);
/*
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                for (DataSnapshot elem : dataSnapshot.getChildren()) {

                    Thing item = new Thing(elem.child("Name").getValue().toString(),
                            Boolean.parseBoolean(elem.child("CheckBox").getValue().toString()),
                            elem.getKey().toString());
                    adapter.add(item);
                }
                //TODO Убрать слишком частые обнволения

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

*/
/*

        });
*/
/*
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                //Item Selected from list
                //adapter.setCheckBox(position);
                HashMap<String, String> userMap = new HashMap<>();
                Thing thing = (Thing)adapter.getItem(position);
                userMap.put("Name", thing.getName());
                userMap.put("CheckBox", String.valueOf(!thing.isCheckbox()));
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(mEvenId).child("Things").child(thing.getId());
                mDatabase.setValue(userMap);

                //  mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(mEvenId).push();
            }
        });
        mCreateThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // создаем адаптер
                String s = mTextThing.getText().toString();
                //проверка на пустую строку
                if (s.equals("")) return;

                mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(mEvenId).child("Things").push();
                Thing item = new Thing(s, false, mDatabase.getKey());
                //  adapter.add(item);
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("Name", item.getName());
                userMap.put("CheckBox", "false");
                mDatabase.setValue(userMap);
                // mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(mEvenId).push();
                lvMain.smoothScrollToPosition(adapter.size());

                mTextThing.setText("");
                // присваиваем адаптер списку
            }
        });
*/
        return v;

    }

}

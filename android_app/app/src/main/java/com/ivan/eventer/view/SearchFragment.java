package com.ivan.eventer.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ivan.eventer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    Button btnKind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_search, container, false);

        btnKind = v.findViewById(R.id.btnSearchKind);

        btnKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.inflate(R.menu.popupmenu); // Для Android 4.0
                // для версии Android 3.0 нужно использовать длинный вариант
                // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
                // popupMenu.getMenu());

                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                // Toast.makeText(PopupMenuDemoActivity.this,
                                // item.toString(), Toast.LENGTH_LONG).show();
                                // return true;
                                switch (item.getItemId()) {

                                    case R.id.menu1:
                                        Toast.makeText(getContext(),
                                                "Вы выбрали PopupMenu 1",
                                                Toast.LENGTH_SHORT).show();
                                        return true;
                                    case R.id.menu2:
                                        Toast.makeText(getContext(),
                                                "Вы выбрали PopupMenu 2",
                                                Toast.LENGTH_SHORT).show();
                                        return true;
                                    case R.id.btnSearchCity:
                                        Toast.makeText(getContext(),
                                                "Вы выбрали PopupMenu 3",
                                                Toast.LENGTH_SHORT).show();
                                        return true;
                                    default:
                                        return true;
                                }
                            }
                        });

                popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {

                    @Override
                    public void onDismiss(PopupMenu menu) {
                        Toast.makeText(getContext(), "onDismiss",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                popupMenu.show();

            }
        });


        return v;

    }

}

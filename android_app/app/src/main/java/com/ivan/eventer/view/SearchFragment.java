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

        btnKind.setOnClickListener(view -> {

            PopupMenu popupMenu = new PopupMenu(getActivity(), view);
            popupMenu.inflate(R.menu.popupmenu);
            // Для Android 4.0
            // для версии Android 3.0 нужно использовать длинный вариант
            // popupMenu.getMenuInflater().inflate(R.menu.popupmenu,
            // popupMenu.getMenu());



            popupMenu
                    .setOnMenuItemClickListener(item -> {
                        // Toast.makeText(PopupMenuDemoActivity.this,
                        // item.toString(), Toast.LENGTH_LONG).show();
                        // return true;
                        switch (item.getItemId()) {

                            case R.id.menu1:

                                if (item.isChecked()){

                                    item.setChecked(false);

                                } else {

                                    item.setChecked(true);

                                }
break;

                            case R.id.menu2:

                                if (item.isChecked()){

                                    item.setChecked(false);

                                } else {

                                    item.setChecked(true);

                                }
break;

                            /*case R.id.menu3:

                                if (item.isChecked()){

                                    item.setChecked(false);

                                } else {

                                    item.setChecked(true);

                                }*/

                            case R.id.menu4:

                                if (item.isChecked()){

                                    item.setChecked(false);

                                } else {

                                    item.setChecked(true);

                                }
break;

                            case R.id.menu5:

                                if (item.isChecked()){

                                    item.setChecked(false);

                                } else {

                                    item.setChecked(true);

                                }
break;

                            case R.id.menu6:

                                if (item.isChecked()){

                                    item.setChecked(false);

                                } else {

                                    item.setChecked(true);

                                }
break;

                            default:
                                break;

                        }

                        return true;
                        //TODO: Добавить обновление списка

                    });

            popupMenu.setOnDismissListener(menu -> {

                //TODO: Добавить обновление списка
                Toast.makeText(getContext(), "Обновление", Toast.LENGTH_SHORT).show();


            });
            popupMenu.show();

        });


        return v;

    }

}

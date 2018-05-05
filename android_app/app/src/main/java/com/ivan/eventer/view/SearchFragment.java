package com.ivan.eventer.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.eventer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    Button btnKind;

    // это будет именем файла настроек
    private static final String APP_PREFERENCES = "mysettings";
    private static final String APP_PREFERENCES_NAME = "NAME";
    private SharedPreferences mSettings;

    private Button mSaveBtn;
    private Button mLoadBtn;
    private EditText mName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        mSaveBtn = v.findViewById(R.id.search_save_btn);
        mLoadBtn= v.findViewById(R.id.search_load_btn);
        mName = v.findViewById(R.id.search_name);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        mSaveBtn.setOnClickListener(v1 -> {

            String name = mName.getText().toString();
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_NAME, name);
            editor.apply();
            Toast.makeText(getActivity(), "SAVED", Toast.LENGTH_SHORT).show();


        });

        mLoadBtn.setOnClickListener(v1 -> {

            String name = mSettings.getString("EE", "DD");
            mName.setText(name);
            Toast.makeText(getActivity(), "LOADED", Toast.LENGTH_SHORT).show();

        });


        return v;

    }

}

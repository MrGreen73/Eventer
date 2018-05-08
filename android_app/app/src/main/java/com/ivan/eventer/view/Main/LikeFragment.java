package com.ivan.eventer.view.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikeFragment extends Fragment {


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_like, container, false);

        //Связываемся с нашим ExpandableListView:
        expListView = v.findViewById(R.id.lvExp);
        //Подготавливаем список данных:
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
        //Настраиваем listAdapter:
        expListView.setAdapter(listAdapter);

        return v;

    }

    /*
    * Подготавливаем данные для списка:
    */
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        //Добавляем данные о пунктах списка:
        listDataHeader.add("Вид");
        listDataHeader.add("Город");
        listDataHeader.add("Время");
        listDataHeader.add("Мелочи");

        //Добавляем данные о подпунктах:
        List<String> kind = new ArrayList<String>();
        kind.add("Спорт");
        kind.add("Тусовки");
        kind.add("Кино");
        kind.add("Прогулка");
        kind.add("Пикник");
        kind.add("Мероприятие");

        List<String> city = new ArrayList<String>();
        city.add("Москва");
        city.add("Мурманск");
        city.add("Гусь-Хрустальный");
        city.add("Одинцово");

        List<String> time = new ArrayList<String>();
        time.add("Утро");
        time.add("День");
        time.add("Вечер");

        List<String> things = new ArrayList<String>();
        things.add("Деньги");
        things.add("Специальная одежда");
        things.add("Еда");

        listDataChild.put(listDataHeader.get(0), kind);
        listDataChild.put(listDataHeader.get(1), city);
        listDataChild.put(listDataHeader.get(2), time);
        listDataChild.put(listDataHeader.get(3), things);

    }

}

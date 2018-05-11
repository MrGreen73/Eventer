package com.ivan.eventer.view.Main.Search;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.Bitap;

import java.util.List;

public class SearchTitleFragment extends Fragment {

    private EditText mTitle;
    private Button mSearchBtn;
    private View mFocusView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_search_title, container, false);

        mTitle = view.findViewById(R.id.searchTitle);
        mSearchBtn = view.findViewById(R.id.titleBtnSearch);
        mFocusView = null;

        mSearchBtn.setOnClickListener(v -> {

            String title = mTitle.getText().toString();

            if (!TextUtils.isEmpty(title)){

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                searchByTitle(title);

            } else {

                mTitle.setError("Введите название");
                mFocusView = mTitle;
                mFocusView.requestFocus();

            }

        });


        return view;
    }

    private void searchByTitle(String title) {

        MainActivity.FILTER_OR_TITLE = title;
        MainActivity.TYPE_SEARCH_ACTIVE = MainActivity.TYPE_SEARCH_TITLE;
        MainActivity.changeFragment(new SearchResultFragment());

        List<Integer> integerList = Bitap.find("bbaaavv", "aaa", 2);
        String string = new String();

        for (Integer i : integerList){
            string += (Integer.toString(i) + " ");
        }

        Toast.makeText(getActivity(), string, Toast.LENGTH_LONG).show();

        //TODO: Выполнить поиск
//        https://github.com/simon-watiau/Bitap-in-Java/blob/master/src/com/sw/bitap/Bitap.java

    }

}

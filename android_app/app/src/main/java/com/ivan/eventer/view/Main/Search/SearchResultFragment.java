package com.ivan.eventer.view.Main.Search;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ivan.eventer.R;
import com.ivan.eventer.adapters.EventsListAdapter;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.Bitap;
import com.ivan.eventer.model.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultFragment extends Fragment {

    // Список события
    private List<Event> mEventList;
    // Список нужных события
    private List<Event> mSearchList;
    // Адаптер для вывода списка событий
    private EventsListAdapter mEventsListAdapter;
    private RecyclerView mRecyclerView;

    // Диалог во время выполнения авторизации
    private ProgressDialog mProgressDialog;
    private AsyncTask mMyTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_result, container, false);


        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setTitle("Поиск событий");
        mProgressDialog.setMessage(getString(R.string.progressDialogWait));
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mRecyclerView = view.findViewById(R.id.recyclerResultSearch);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(llm);

        // Загрузка событий из базы данных
        initializeData();

        return view;

    }

    private List<Event> makeSelectionByFilter(String filter) {

        Toast.makeText(getActivity(), filter, Toast.LENGTH_LONG).show();

        List<Event> result = new ArrayList<>();

        if (filter.length() > 1) {

            if (filter.charAt(0) == ';') {

                filter = filter.substring(1);

                String[] time = filter.split(" ");

                for (String aTime : time){

                    for (Event event : mEventList) {

                        if (event.getTime().equals(aTime)) {

                            result.add(event);

                        }

                    }

                }


            } else if (filter.charAt(filter.length() - 1) == ';'){

                filter = filter.substring(0, filter.length() - 1);

                String[] kind = filter.split(" ");

                for (String aKind : kind){

                    for (Event event : mEventList) {

                        if (event.getKind().equals(aKind)) {

                            result.add(event);

                        }

                    }

                }

            } else {

                String[] typeFilter = filter.split(";");

                String[] kind = typeFilter[0].split(" ");
                String[] time = typeFilter[1].split(" ");

                for (String aKind : kind) {

                    for (String aTime : time) {

                        for (Event event : mEventList) {

                            if (event.getKind().equals(aKind) && event.getTime().equals(aTime)) {

                                result.add(event);

                            }

                        }

                    }

                }

            }

        }

        return result;

    }

    private List<Event> makeSelectionByTitle(String title) {

        List<Event> result = new ArrayList<>();
        boolean check;
        for (Event event : mEventList){

            check = false;

            String eventTitle = event.getTitle();

            for (int k = 0; k < 4; k ++) {

                List<Integer> list = Bitap.find(eventTitle, title, k);
                if (list != null && list.size() > 0){
                    check = true;
                }

            }

            if (check) {

                result.add(event);

            }

        }

        return result;

    }

    // Загрузка событий из баззы данных
    private void initializeData(){

        mMyTask = new DownloadTask()
                .execute();

    }

    private class DownloadTask extends AsyncTask<Void,Integer,Void> {

        // Before the tasks execution
        protected void onPreExecute(){

            // Display the progress dialog on async task start
            mProgressDialog.show();

        }

        // Do the task in background/non UI thread
        protected Void doInBackground(Void...tasks){

            mEventList = Commands.getEvents();

            switch (MainActivity.TYPE_SEARCH_ACTIVE){

                case 0:
                    String title = MainActivity.FILTER_OR_TITLE;
                    mSearchList = makeSelectionByTitle(title);
                    break;

                case 1:
                    String filter = MainActivity.FILTER_OR_TITLE;
                    mSearchList = makeSelectionByFilter(filter);
                    break;

                default:
                    mSearchList = new ArrayList<>();
                    break;

            }

            return null;

        }

        // After each task done
        protected void onProgressUpdate(Integer... progress){

            // Update the progress bar on dialog
            mProgressDialog.setProgress(progress[0]);

        }

        // When all async task done
        protected void onPostExecute(Void result){

            Collections.reverse(mSearchList);

            // Hide the progress dialog
            mProgressDialog.dismiss();

            mEventsListAdapter = new EventsListAdapter(mSearchList);
            mRecyclerView.setAdapter(mEventsListAdapter);


        }

    }


}

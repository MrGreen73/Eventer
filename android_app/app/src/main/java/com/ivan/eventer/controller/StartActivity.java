package com.ivan.eventer.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ivan.eventer.R;
import com.ivan.eventer.view.StartFragment;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // Необходима проверка: авторизован ли пользователь
        if (true) {

            sentToStart();//Если не авторизован

        } else {

            sentToMain();//Если авторизован

        }
    }


    //Посылает пользователя на главную страницу
    private void sentToMain() {

        Intent mainIntent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }

    //Посылает пользователя на страницу регистрации или авторизации
    private void sentToStart() {

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.startContainer);

        if (fragment == null) {

            fragment = new StartFragment();
            fm.beginTransaction()
                    .add(R.id.startContainer, fragment)
                    .commit();
        }

    }
}

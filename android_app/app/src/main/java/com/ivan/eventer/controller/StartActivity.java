package com.ivan.eventer.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.ivan.eventer.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        // Необходима проверка: авторизован ли пользователь
        if (false) {

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



    }
}

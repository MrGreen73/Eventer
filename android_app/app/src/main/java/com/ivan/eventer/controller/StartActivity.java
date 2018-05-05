package com.ivan.eventer.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
import com.ivan.eventer.R;
import com.ivan.eventer.view.StartFragment;

public class StartActivity extends AppCompatActivity {

    public static final String PATH_TO_DATA_ABOUT_USER = "dataAboutUser";
    public static final String USER_NAME = "NAME";
    public static final String USER_EMAIL= "EMAIL";
    public static final String USER_AGE = "AGE";
    public static final String USER_CITY = "CITY";
    private SharedPreferences mSharedPreferences;

    // Firebase
//    private FirebaseAuth mFirebaseAuth; // Для проверки аутентификации

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mSharedPreferences = this.getSharedPreferences(PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);

//        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();

//        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();

        // Необходима проверка: авторизован ли пользователь
        if (/*currentUser == null*//*mSharedPreferences.getString("NAME", "-1").equals("-1")*/true) {

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

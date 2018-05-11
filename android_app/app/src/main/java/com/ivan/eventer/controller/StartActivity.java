package com.ivan.eventer.controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.ivan.eventer.R;
import com.ivan.eventer.view.Start.StartFragment;

import org.apache.log4j.Logger;

public class StartActivity extends AppCompatActivity {

    Logger mLogger = Logger.getLogger(StartActivity.class);

    //Ключи для сохранения информации о пользователе
    public static final String PATH_TO_DATA_ABOUT_USER = "dataAboutUser";
    public static final String USER_NAME = "NAME";
    public static final String USER_EMAIL= "EMAIL";
    public static final String USER_AGE = "AGE";
    public static final String USER_CITY = "CITY";
    public static final String USER_IMAGE = "IMAGE";
    public static final String USER_IMAGE_PATH = "IMAGE_PATH";

    // Для разрешения
    private static final int PERMISSION_REQUEST_CODE = 1;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mSharedPreferences = this.getSharedPreferences(PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{

                            Manifest.permission.WRITE_EXTERNAL_STORAGE

                    },
                    PERMISSION_REQUEST_CODE);

        }

        // Необходима проверка: авторизован ли пользователь
        if (mSharedPreferences.getString("NAME", "").equals("")) {

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode != PERMISSION_REQUEST_CODE || grantResults.length != 1) {

            finish();

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

}

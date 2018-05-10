package com.ivan.eventer.controller;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.ivan.eventer.R;
import com.ivan.eventer.model.PersonDate;
import com.ivan.eventer.view.Main.CreateFragment;
import com.ivan.eventer.view.Main.HomeFragment;
import com.ivan.eventer.view.Main.ProfileFragment;
import com.ivan.eventer.view.Main.Search.SearchFragment;
import com.ivan.eventer.view.Main.SettingsFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    // Для перехода по фрагментам
    private static FragmentManager mFragmentManager;
    private static Fragment mContainer;
    private Fragment mFragment;

    // Название тулбара
    private TextView mToolbarTitle;

    // Для просмотра данных пользователя
    private SharedPreferences mSharedPreferences;

    // Класс для хранения настроек
    public static PersonDate sPersonDate;

    // Кнопка для перехода к настройкам
    private ImageButton mSettingsBtn;

    // Настройки нижнего меню
    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        switch (item.getItemId()) {

            case R.id.navigation_home:
                changeFragment(new HomeFragment());
                changeTitle("Главная");
                break;

            case R.id.navigation_search:

                changeFragment(new SearchFragment());
                changeTitle("Поиск");
                break;

            case R.id.navigation_create:

                changeFragment(new CreateFragment());
                changeTitle("Создание");
                break;
/*
                    case R.id.navigation_like:

                        changeFragment(new LikeFragment());
                        changeTitle("Оповещения");
                        break;*/

            case R.id.navigation_profile:

                changeFragment(new ProfileFragment());
                changeTitle("Профиль");
                break;
        }

        return true;

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Загружаем данные о пользователе
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                PERMISSION_REQUEST_CODE);

        Toolbar mToolbar = findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
        mToolbarTitle = findViewById(R.id.toolbarTitle);

        BottomNavigationViewEx bnve = findViewById(R.id.bnve);
        bnve.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mFragmentManager = getSupportFragmentManager();
        mContainer = mFragmentManager.findFragmentById(R.id.mainContainer);
        mFragment = new HomeFragment();
        mSettingsBtn = findViewById(R.id.logOut);

        // Переход к настройкам
        mSettingsBtn.setOnClickListener(v -> {

            changeFragment(new SettingsFragment());
            changeTitle("Настройки");

        });


        if (mContainer == null) {

            mFragmentManager.beginTransaction().add(R.id.mainContainer, mFragment).addToBackStack(null).commit();
            changeTitle("Главная");

        }

        bnve.enableAnimation(true);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        int widthDp = 33;
        int heightDp = 33;
        int sp = 0;

        bnve.setIconSize(widthDp, heightDp);
        bnve.setTextSize(sp);

    }

    // Загрузка данных о пользователе
    private void loadDate() {

        mSharedPreferences = this.getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
        File file = new File(folder, mSharedPreferences.getString(StartActivity.USER_IMAGE_PATH, "sdcard/")); // создать уникальное имя для файла основываясь на дате сохранения

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        sPersonDate = new PersonDate(
                mSharedPreferences.getString(StartActivity.USER_NAME, "Пользователь"),
                mSharedPreferences.getString(StartActivity.USER_EMAIL, "user@email.ru"),
                mSharedPreferences.getString(StartActivity.USER_AGE, "18"),
                mSharedPreferences.getString(StartActivity.USER_CITY, "Москва"),
                mSharedPreferences.getString(StartActivity.USER_IMAGE_PATH, "sdcard/"),
                baos.toByteArray()
        );
//        } else {
//            finishAffinity();
//        }
    }

    // Установка названия тулбара
    private void changeTitle(String title) {

        mToolbarTitle.setText(title);

    }

    //Смена фрагмента
    public static void changeFragment(Fragment fragment) {

        if (mContainer == null) {

            mFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

        }

    }

    public static void addFragment(Fragment fragment) {

        if (mContainer == null) {

            mFragmentManager.beginTransaction().add(R.id.mainContainer, fragment).addToBackStack(null).commit();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadDate();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

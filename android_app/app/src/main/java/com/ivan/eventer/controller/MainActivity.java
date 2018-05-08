package com.ivan.eventer.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.ivan.eventer.R;
import com.ivan.eventer.model.PersonDate;
import com.ivan.eventer.view.Main.CreateFragment;
import com.ivan.eventer.view.Main.HomeFragment;
import com.ivan.eventer.view.Main.LikeFragment;
import com.ivan.eventer.view.Main.ProfileFragment;
import com.ivan.eventer.view.Main.Search.SearchFragment;
import com.ivan.eventer.view.Main.SettingsFragment;

public class MainActivity extends AppCompatActivity {

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

                    case R.id.navigation_like:

                        changeFragment(new LikeFragment());
                        changeTitle("Оповещения");
                        break;

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
        loadDate();

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

        int widthDp = 36;
        int heightDp = 36;
        int sp = 0;

        bnve.setIconSize(widthDp, heightDp);
        bnve.setTextSize(sp);

    }

    // Загрузка данных о пользователе
    private void loadDate() {

        mSharedPreferences = this.getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);
        sPersonDate = new PersonDate(
                mSharedPreferences.getString(StartActivity.USER_NAME, "Пользователь"),
                mSharedPreferences.getString(StartActivity.USER_EMAIL, "user@email.ru"),
                mSharedPreferences.getString(StartActivity.USER_AGE, "18"),
                mSharedPreferences.getString(StartActivity.USER_CITY, "Москва")
        );

    }

    // Установка названия тулбара
    private void changeTitle(String title){

        mToolbarTitle.setText(title);

    }

    //Смена фрагмента
    public static void changeFragment(Fragment fragment){

        if (mContainer == null) {

            mFragmentManager.beginTransaction().replace(R.id.mainContainer, fragment).commit();

        }

    }

    public static void addFragment(Fragment fragment){

        if (mContainer == null) {

            mFragmentManager.beginTransaction().add(R.id.mainContainer, fragment).addToBackStack(null).commit();

        }

    }

}

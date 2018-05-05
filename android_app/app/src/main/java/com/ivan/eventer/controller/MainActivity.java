package com.ivan.eventer.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.ivan.eventer.R;
import com.ivan.eventer.model.PersonDate;
import com.ivan.eventer.view.CreateFragment;
import com.ivan.eventer.view.HomeFragment;
import com.ivan.eventer.view.LikeFragment;
import com.ivan.eventer.view.ProfileFragment;
import com.ivan.eventer.view.SearchFragment;
import com.ivan.eventer.view.SettingsFragment;

//import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private Fragment mContainer;
    private Fragment mFragment;
    private TextView mToolbarTitle;
    private SharedPreferences mSharedPreferences;
    public static PersonDate sPersonDate;

    private ImageButton mSettingsBtn;

    private BottomNavigationViewEx.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragment = new HomeFragment();

                    if (mContainer == null) {

                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, mFragment).commit();
                        changeTitle("Главная");

                    }
                    break;
                case R.id.navigation_search:
                    mFragment = new SearchFragment();

                    if (mContainer == null) {

                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, mFragment).commit();
                        changeTitle("Поиск");

                    }
                    break;
                case R.id.navigation_create:
                    mFragment = new CreateFragment();

                    if (mContainer == null) {

                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, mFragment).commit();
                        changeTitle("Создание");

                    }
                    break;
                case R.id.navigation_like:
                    mFragment = new LikeFragment();

                    if (mContainer == null) {

                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, mFragment).commit();
                        changeTitle("Оповещения");

                    }
                    break;
                case R.id.navigation_profile:
                    mFragment = new ProfileFragment();

                    if (mContainer == null) {

                        mFragmentManager.beginTransaction().replace(R.id.mainContainer, mFragment).commit();
                        changeTitle("Профиль");

                    }
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        mSettingsBtn.setOnClickListener(v -> {

            mFragment = new SettingsFragment();

            if (mContainer == null) {

                mFragmentManager.beginTransaction().replace(R.id.mainContainer, mFragment).commit();
                changeTitle("Настройки");

            }

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

    private void loadDate() {

        mSharedPreferences = this.getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);
        sPersonDate = new PersonDate(
                mSharedPreferences.getString(StartActivity.USER_NAME, "User"),
                mSharedPreferences.getString(StartActivity.USER_EMAIL, "user@email.ru"),
                mSharedPreferences.getString(StartActivity.USER_AGE, "18"),
                mSharedPreferences.getString(StartActivity.USER_CITY, "Moscow")
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.logOut) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeTitle(String title){

        mToolbarTitle.setText(title);

    }

}

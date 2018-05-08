package com.ivan.eventer.view.Main;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ivan.eventer.R;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.controller.StartActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {

    //TODO: Добавить кнопки для инструкции

    // Поля ввода
    private EditText mName; // Имя
    private EditText mAge; // Возраст
    private EditText mCity; // Город

    // Кнопки
    private Button mSaveBtn; // Для сохранения данных
    private Button mOutBtn; // Для выхода из аккаунта
    private ImageButton mInfBtn; // Для получения информации
    private ImageButton mChangeImageBtn; // Для смены картинки
    private ImageButton mLoadImageBtn; // Для загрузки картинки

    // Картинка
    private ImageView mImageUser;

    // Для загрузки данных о пользователе
    private SharedPreferences mSharedPreferences;

    // Список стандартных картинок
    private List<Integer> mListImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mName = view.findViewById(R.id.settingsName);
        mAge = view.findViewById(R.id.settingsAge);
        mCity = view.findViewById(R.id.settinsgCity);
        mSaveBtn = view.findViewById(R.id.saveBtn);
        mOutBtn = view.findViewById(R.id.outBtn);
        mInfBtn = view.findViewById(R.id.settingsInformation);
        mLoadImageBtn = view.findViewById(R.id.settingsLoadImageBtn);
        mChangeImageBtn = view.findViewById(R.id.settingsChangeImageBtn);
        mImageUser= view.findViewById(R.id.settingsImageUser);
        mSharedPreferences = getActivity().getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);

        // Загрузка данных о пользователе
        loadOldDate();

        //Инициализация картинок
        mListImage = initializeData();

        // Сохранение новых данных о пользователе
        mSaveBtn.setOnClickListener(v -> {

            String name = mName.getText().toString();
            String age = mAge.getText().toString();
            String city = mCity.getText().toString();
            View focusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(city)){

                // Сохранение новых данных о пользователе
                saveNewDate(name, age, city);

            } else {

                if (TextUtils.isEmpty(city)) {

                    mCity.setError("Введите город");
                    focusView = mCity;

                }

                if (TextUtils.isEmpty(age)) {

                    mAge.setError("Введите возраст");
                    focusView = mAge;

                }

                if (TextUtils.isEmpty(name)) {

                    mName.setError("Введите имя");
                    focusView = mName;

                }

                focusView.requestFocus();

            }

        });

        mOutBtn.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Выход")
                    .setMessage("Вы уверены, что хотите выйти?")
                    .setIcon(R.drawable.ic_information)
                    .setCancelable(false)
                    .setPositiveButton("Да",
                            ((dialog, id) -> {dialog.cancel();logOut();}))
                    .setNegativeButton("Нет",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();

        });

        mInfBtn.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Информация!")
                    .setMessage("Используй это приложение и будешь счастлив!")
                    .setIcon(R.drawable.ic_information)
                    .setCancelable(false)
                    .setNegativeButton("Хорошо",
                            (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();

        });

        // Смена картинки
        mChangeImageBtn.setOnClickListener(v1 -> {

            Random random = new Random();
            Integer position = random.nextInt(mListImage.size());
            mImageUser.setImageResource(mListImage.get(position));

        });

        // Загрузка картинки
        mLoadImageBtn.setOnClickListener(v1 -> {

            loadImage();

        });

        return  view;

    }

    // Выход из аккаунта
    private void logOut() {

        MainActivity.sPersonDate.deletePerson();
        updateSharedPreferences("","","");

        Intent startIntent = new Intent(getActivity(), StartActivity.class);
        startActivity(startIntent);
        getActivity().finish();

    }

    // Сохранение новых данных о пользователе
    private void saveNewDate(String name, String age, String city) {

        MainActivity.sPersonDate.updatePerson(name, age, city);
        updateSharedPreferences(name, age, city);
//        Commands.updatePerson(name, age, city);

    }

    // Сохранение новых данных о пользователе локально
    private void updateSharedPreferences(String name, String age, String city) {

        mSharedPreferences = getActivity().getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(StartActivity.USER_NAME, name);
        editor.putString(StartActivity.USER_AGE, age);
        editor.putString(StartActivity.USER_CITY, city);
        editor.apply();

    }

    // Загрузка данных о пользователе
    private void loadOldDate() {

        mName.setText(MainActivity.sPersonDate.getName());
        mAge.setText(MainActivity.sPersonDate.getAge());
        mCity.setText(MainActivity.sPersonDate.getCity());

    }

    static final int GALLERY_REQUEST = 1;
    private void loadImage() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;

        switch(requestCode) {

            case GALLERY_REQUEST:

                if(resultCode == RESULT_OK){

                    Uri selectedImage = imageReturnedIntent.getData();

                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);

                    } catch (IOException e) {

                        e.printStackTrace();

                    }

                    mImageUser.setImageBitmap(bitmap);

                }

                break;

            default:

                break;

        }

    }

    private List<Integer> initializeData() {

        List<Integer> date = new ArrayList<>();

        date.add(R.drawable.ic_profile);
        date.add(R.drawable.ic_for_profile1);
        date.add(R.drawable.ic_for_profile2);
        date.add(R.drawable.ic_for_profile3);
        date.add(R.drawable.ic_for_profile4);
        date.add(R.drawable.ic_for_profile5);
        date.add(R.drawable.ic_for_profile6);
        date.add(R.drawable.ic_for_profile7);
        date.add(R.drawable.ic_for_profile8);
        date.add(R.drawable.ic_for_profile9);
        date.add(R.drawable.ic_for_profile10);
        date.add(R.drawable.ic_for_profile11);
        date.add(R.drawable.ic_for_profile12);
        date.add(R.drawable.ic_for_profile13);
        date.add(R.drawable.ic_for_profile14);
        date.add(R.drawable.ic_for_profile15);
        date.add(R.drawable.ic_for_profile16);

        return date;

    }


}
package com.ivan.eventer.view.Start;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.controller.StartActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    // Поля ввода
    private EditText mName; // Имя
    private EditText mEmail; // Почта
    private EditText mAge; // Возраст
    private EditText mCity; // Город
    private EditText mPassword; // Пароль

    // Кнопка для регистрации
    private Button mButtonRegister;

    // Диалог во время выполнения регистрации
    private ProgressDialog mProgressDialog;

    // Для сохранения данных о пользователе
    private SharedPreferences mSharedPreferences;

    // Путь папки
    String mFolderToSave;
    String mPath;

    // Для подсветки ошибки
    private View mFocusView;

    // Для проверки валидности почты
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mName = v.findViewById(R.id.registerName);
        mEmail= v.findViewById(R.id.registerEmail);
        mAge= v.findViewById(R.id.registerAge);
        mCity= v.findViewById(R.id.registerCity);
        mPassword= v.findViewById(R.id.registerPassword);
        mButtonRegister = v.findViewById(R.id.regBtn);
        mProgressDialog = new ProgressDialog(getActivity());


        Time time = new Time();
        time.setToNow();
        mFolderToSave = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        mPath = Integer.toString(time.year) + Integer.toString(time.month) + Integer.toString(time.monthDay) + Integer.toString(time.hour) + Integer.toString(time.minute) + Integer.toString(time.second) + ".png";


        mButtonRegister.setOnClickListener(v1 -> {

            String name = mName.getText().toString();
            String email= mEmail.getText().toString();
            String age = mAge.getText().toString();
            String city= mCity.getText().toString();
            String password = mPassword.getText().toString();

            mFocusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(city) && !TextUtils.isEmpty(password) && password.length() > 5 && validate(email)) {

                mProgressDialog.setTitle(getString(R.string.progressDialogRegister));
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                registerUser(name, email, age, city, password);

            } else {

                if (password.length() < 6) {

                    mPassword.setError("Пароль должен состоять минимум из 6 символов");
                    mFocusView = mPassword;

                }

                if (TextUtils.isEmpty(password)){

                    mPassword.setError("Введите пароль");
                    mFocusView = mPassword;

                }

                if (TextUtils.isEmpty(city)) {

                    mCity.setError("Введите город");
                    mFocusView = mCity;

                }

                if (TextUtils.isEmpty(age)) {

                    mAge.setError("Введите возраст");
                    mFocusView = mAge;

                }

                if (!validate(email)){

                    mEmail.setError("Введите почту корректно");
                    mFocusView = mEmail;

                }

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Введите почту");
                    mFocusView = mEmail;

                }

                if (TextUtils.isEmpty(name)) {

                    mName.setError("Введите имя");
                    mFocusView = mName;

                }

                mFocusView.requestFocus();

            }

        });

        return v;

    }

    private void registerUser(String name, String email, String age, String city, String password) {


        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(R.drawable.ic_profile);
        Bitmap bitmap = ((BitmapDrawable)(imageView).getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        byte[] image = baos.toByteArray();

        //Запустить новый тред
        Thread thread = new Thread() {

            @Override
            public void run() {

                Commands.createUser(name, email, age, city, password, image);

/*                getActivity().runOnUiThread(() -> {

                    mProgressDialog.show();

                });*/
            }

        };

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        // Сохраняем данные о пользователе
        saveDate(name, email, age, city, image);
        // Закрываем диалог
        mProgressDialog.dismiss();
        // Переходим на главную активность
        sentToMain();

    }

    // Сохранение данных о пользователе локально
    private void saveDate(String name, String email, String age, String city, byte[] image) {

        mSharedPreferences = getActivity().getSharedPreferences(StartActivity.PATH_TO_DATA_ABOUT_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(StartActivity.USER_NAME, name);
        editor.putString(StartActivity.USER_EMAIL, email);
        editor.putString(StartActivity.USER_AGE, age);
        editor.putString(StartActivity.USER_CITY, city);
        editor.putString(StartActivity.USER_IMAGE_PATH, mPath);
        saveImage(image);
        editor.apply();

    }

    //Посылает пользователя на главную страницу
    private void sentToMain() {

        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();

    }

    // Проверяет валидность почты
    private boolean validate(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();

    }


    private Bitmap getBitmap(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);

    }

    //Сохранение картинки на SD
    private String saveImage(byte[] image) {


        OutputStream fOut;

        try {

            File file = new File(mFolderToSave, mPath); // создать уникальное имя для файла основываясь на дате сохранения

            fOut = new FileOutputStream(file);

            Bitmap bitmap = getBitmap(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

            fOut.flush();
            fOut.close();

            // регистрация в фотоальбоме
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());

        } catch (Exception e) { // здесь необходим блок отслеживания реальных ошибок и исключений, общий Exception приведен в качестве примера

            return e.getMessage();

        }

        return "";

    }

}
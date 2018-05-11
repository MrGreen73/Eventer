package com.ivan.eventer.view.Start;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.controller.StartActivity;
import com.ivan.eventer.model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class LoginFragment extends Fragment {

    // Поля ввода
    private EditText mEmail; // Почта
    private EditText mPassword; // Пароль

    // Кнопка для авторизации
    private Button mButtonLogin;

    // Диалог во время выполнения авторизации
    private ProgressDialog mProgressDialog;
    private AsyncTask mMyTask;

    // Для сохранения данных о пользоавтеле
    private SharedPreferences mSharedPreferences;

    // Для подсветки ошибок
    private View mFocusView;

    // Путь папки
    String mFolderToSave;
    String mPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = v.findViewById(R.id.loginEmail);
        mPassword= v.findViewById(R.id.loginPassword);
        mButtonLogin = v.findViewById(R.id.logBtn);
        mProgressDialog = new ProgressDialog(getActivity());

        Time time = new Time();
        time.setToNow();
        mFolderToSave = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        mPath = Integer.toString(time.year) + Integer.toString(time.month) + Integer.toString(time.monthDay) + Integer.toString(time.hour) + Integer.toString(time.minute) + Integer.toString(time.second) + ".png";

        mButtonLogin.setOnClickListener(v1 -> {

            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            mFocusView = null;

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                mProgressDialog.setTitle(getString(R.string.progressDialogLogin));
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                loginUser(email, password);

            } else {

                if (TextUtils.isEmpty(password)) {

                    mPassword.setError("Введите пароль");
                    mFocusView = mPassword;

                }

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("Введите почту");
                    mFocusView = mEmail;

                }

                    mFocusView.requestFocus();

            }

        });

        return v;

    }

    private void loginUser(String email, String password) {

        mMyTask = new DownloadTask()
                .execute(
                        email,
                        password
                );

        // В случае проблемы с авторизацией
        // Останавливаем диалог
        mProgressDialog.dismiss();
        //Оповещаем пользователя о некорректности введенных данных
        Toast.makeText(getActivity(), "Почта или пароль введены неверно", Toast.LENGTH_SHORT).show();
        // Устанавливаем совет пользователю
        mEmail.setError("Проверьте почту");
        mPassword.setError("Проверьте пароль");
        // Ставим курсор на ввод почты
        mFocusView = mEmail;
        mFocusView.requestFocus();

    }

    private class DownloadTask extends AsyncTask<String,Integer,Void> {

        // Before the tasks execution
        protected void onPreExecute(){

            // Display the progress dialog on async task start
            mProgressDialog.show();

        }

        // Do the task in background/non UI thread
        protected Void doInBackground(String...tasks){

            User user;

            user = Commands.loginUser(tasks[0], tasks[1]);

            if (user != null) {

                // Сохраняем данные о пользователе
                saveDate(user.getName(), user.getEmail(), user.getAge(), user.getCity(), user.getImage());

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

            // Hide the progress dialog
            mProgressDialog.dismiss();

            // Оповещаем пользователе об успешной авторизации
            Toast.makeText(getActivity(), "Вы успешно авторизованы", Toast.LENGTH_SHORT).show();

            // Отправление на главную активность
            sentToMain();

        }

    }


    // Сохраняет данные о пользователе локально
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

    private Bitmap getBitmap(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);

    }

    //Посылает пользователя на главную страницу
    private void sentToMain() {

        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainIntent);
        getActivity().finish();

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

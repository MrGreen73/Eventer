package com.ivan.eventer.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.controller.MainActivity;
import com.ivan.eventer.model.EventPreview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class CreateFragment extends Fragment {

    //Поля ввода
    private EditText mName; // Название
    private EditText mCount; // Количество человек
    private EditText mDescribe; // Описание

    // Кнопки
    private Button mButtonCreate; // Для создания события
    private ImageButton mChangeImageBtn; // Для смены картинки
    private ImageButton mLoadImageBtn; // Для загрузки картинки

    // Папка куда сохранять, в данном случае - корень SD-карты
    String folderToSave = Environment.getExternalStorageDirectory().toString();

    // Картинка
    private ImageView mImageEvent;

    // Диалог для ожидания создания события
    private ProgressDialog mProgressDialog;

    // Список стандартных картинок
    private List<Integer> mListImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create, container, false);

        mName = v.findViewById(R.id.createName);
        mCount= v.findViewById(R.id.createCount);
        mDescribe= v.findViewById(R.id.createDescribe);
        mButtonCreate = v.findViewById(R.id.createBtn);
        mLoadImageBtn = v.findViewById(R.id.createLoadImageBtn);
        mChangeImageBtn = v.findViewById(R.id.createChangeImageBtn);
        mImageEvent = v.findViewById(R.id.createImageEvent);
        mListImage = initializeData();


        mProgressDialog = new ProgressDialog(getActivity());

        // Смена картинки
        mChangeImageBtn.setOnClickListener(v1 -> {

            Random random = new Random();
            Integer position = random.nextInt(mListImage.size());
            mImageEvent.setImageResource(mListImage.get(position));

        });

        // Загрузка картинки
        mLoadImageBtn.setOnClickListener(v1 -> {

            //TODO: Добавить загрузку картинки из галереи
            loadImage();

        });

        // Создание события
        mButtonCreate.setOnClickListener(v1 -> {

            String name = mName.getText().toString();
            String count= mCount.getText().toString();
            String describe = mDescribe.getText().toString();

            View focusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(count) && !TextUtils.isEmpty(describe)) {

                mProgressDialog.setTitle("Создание события");
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                makeEvent(name, count, describe);

            } else {


                if (TextUtils.isEmpty(describe)) {

                    mDescribe.setError("Введите описание");
                    focusView = mDescribe;

                }

                if (TextUtils.isEmpty(count)) {

                    mCount.setError("Введите количество человек");
                    focusView = mCount;

                }

                if (TextUtils.isEmpty(name)) {

                    mName.setError("Введите название");
                    focusView = mName;

                }

                focusView.requestFocus();

            }

        });

        return v;

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

                    mImageEvent.setImageBitmap(bitmap);

                }

                break;

            default:

                break;

        }

    }

    private List<Integer> initializeData() {

        List<Integer> date = new ArrayList<>();

        date.add(R.drawable.item1);
        date.add(R.drawable.item2);
        date.add(R.drawable.item3);
        date.add(R.drawable.item4);
        date.add(R.drawable.item5);
        date.add(R.drawable.item6);
        date.add(R.drawable.item7);

        return date;

    }

    private void makeEvent(String name, String count, String describe) {

        final String[] id = new String[1];
        Thread thread = new Thread() {

            @Override
            public void run() {

                id[0] = Commands.createEvent(Integer.valueOf(count), describe, name, "NaN");
                //TODO: Добавить сохранение картинки в базе данных

            }

        };

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

        //TODO: Сделать проверку на создание события
        //TODO: Сделать переход на EventActivity
        EventActivity.sEventPreview = new EventPreview(id[0], name, count, describe, "NaN", MainActivity.sPersonDate.getEmail());
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), "Событие создано", Toast.LENGTH_SHORT).show();

        Intent eventIntent = new Intent(getActivity(), EventActivity.class);
        eventIntent.putExtra("ID", id);
        startActivity(eventIntent);
        getActivity().finish();

    }

    //Сохранение картинки на SD
    private String saveImage() {

        //TODO: Выполинть проверку работы метода
        //TODO: Добавить сохранение картинки в базе по ID(Integer.toString(time.year) + Integer.toString(time.month) + Integer.toString(time.monthDay) + Integer.toString(time.hour) + Integer.toString(time.minute) + Integer.toString(time.second) + ".jpg")

        OutputStream fOut = null;
        Time time = new Time();
        time.setToNow();

        try {

            File file = new File(folderToSave, Integer.toString(time.year) + Integer.toString(time.month) + Integer.toString(time.monthDay) + Integer.toString(time.hour) + Integer.toString(time.minute) + Integer.toString(time.second) + ".jpg"); // создать уникальное имя для файла основываясь на дате сохранения
            fOut = new FileOutputStream(file);

            Bitmap bitmap = ((BitmapDrawable)mImageEvent.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // сохранять картинку в jpeg-формате с 85% сжатия.

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

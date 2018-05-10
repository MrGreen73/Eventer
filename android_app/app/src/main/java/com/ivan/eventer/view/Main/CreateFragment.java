package com.ivan.eventer.view.Main;

import android.app.DatePickerDialog;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.EventActivity;
import com.ivan.eventer.controller.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

    // Для даты
    private Button mDateBtn;
    private EditText mDateText;
    private int mYear;
    private int mMonth;
    private int mDay;

    //Для выбора времени дня
    private RadioGroup mRadioGroupTime;
    private String mChoiceTime;

    //Для выбора вида
    private RadioGroup mRadioGroupKind;
    private String mChoiceKind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_create, container, false);

        mName = v.findViewById(R.id.createName);
        mDescribe= v.findViewById(R.id.createDescribe);
        mButtonCreate = v.findViewById(R.id.createBtn);
        mLoadImageBtn = v.findViewById(R.id.createLoadImageBtn);
        mChangeImageBtn = v.findViewById(R.id.createChangeImageBtn);
        mImageEvent = v.findViewById(R.id.createImageEvent);
        mRadioGroupKind= v.findViewById(R.id.radioGroupKind);
        mRadioGroupTime = v.findViewById(R.id.radioGroupTime);
        mDateText = v.findViewById(R.id.picked_date);
        mDateBtn = v.findViewById(R.id.btn_date);
        mChoiceTime = "Утро";
        mChoiceKind= "Прогулка";
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

            loadImage();

        });

        // Создание события
        mButtonCreate.setOnClickListener(v1 -> {

            String name = mName.getText().toString();
            String time= mDateText.getText().toString();
            String describe = mDescribe.getText().toString();

            View focusView = null;

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(describe)) {

                mProgressDialog.setTitle("Создание события");
                mProgressDialog.setMessage(getString(R.string.progressDialogWait));
                mProgressDialog.setCanceledOnTouchOutside(false);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                makeEvent(name, time, describe);

            } else {


                if (TextUtils.isEmpty(describe)) {

                    mDescribe.setError("Введите описание");
                    focusView = mDescribe;

                }

                if (TextUtils.isEmpty(time)) {

                    mDateText.setError("Выберите время");
                    focusView = mDateText;

                }

                if (TextUtils.isEmpty(name)) {

                    mName.setError("Введите название");
                    focusView = mName;

                }

                focusView.requestFocus();

            }

        });


        mRadioGroupTime.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButtonMorning:
                    mChoiceTime = "Утро";
                    break;
                case R.id.radioButtonDay:
                    mChoiceTime = "День";
                    break;
                case R.id.radioButtonEvening:
                    mChoiceTime = "Вечер";
                    break;
                default:
                    mChoiceTime = "Утро";
                    break;
            }
        });

        mRadioGroupKind.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radioButtonWalk:
                    mChoiceTime = "Прогулка";
                    break;
                case R.id.radioButtonSport:
                    mChoiceTime = "Спорт";
                    break;
                case R.id.radioButtonCinema:
                    mChoiceTime = "Кино";
                    break;
                case R.id.radioButtonActive:
                    mChoiceTime = "Активный отдых";
                    break;
                case R.id.radioButtonParty:
                    mChoiceTime = "Вечеринка";
                    break;
                case R.id.radioButtonArt:
                    mChoiceTime = "Искусство";
                    break;
                default:
                    mChoiceTime = "Прогулка";
                    break;
            }
        });

        mDateBtn.setOnClickListener(v1 -> {

            callDatePicker();

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

        date.add(R.drawable.natural_item1);
        date.add(R.drawable.image_1);
        date.add(R.drawable.image_2);
        date.add(R.drawable.image_3);
        date.add(R.drawable.image_4);
        date.add(R.drawable.image_5);
        date.add(R.drawable.image_6);
        date.add(R.drawable.event_1);
        date.add(R.drawable.event_2);
        date.add(R.drawable.event_3);
        date.add(R.drawable.event_4);
        date.add(R.drawable.event_5);
        date.add(R.drawable.event_6);
        date.add(R.drawable.event_7);
        date.add(R.drawable.event_8);
        date.add(R.drawable.event_9);
        date.add(R.drawable.event_10);
        date.add(R.drawable.event_11);
        date.add(R.drawable.event_12);

        return date;

    }

    private void callDatePicker() {
        // получаем текущую дату
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        // инициализируем диалог выбора даты текущими значениями
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    String editTextDateParam = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                    mDateText.setText(editTextDateParam);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void makeEvent(String name, String time, String describe) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Bitmap bitmap = ((BitmapDrawable)mImageEvent.getDrawable()).getBitmap();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // сохранять картинку в jpeg-формате с 85% сжатия.


        final String[] id = new String[1];
        Thread thread = new Thread() {

            @Override
            public void run() {

//                mProgressDialog.show();
                id[0] = Commands.createEvent(MainActivity.sPersonDate.getEmail(), name, describe, baos.toByteArray(), mChoiceKind, mChoiceTime, time);

            }

        };

        thread.start();

        try {

            thread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

//        EventActivity.sEventPreview = new EventPreview(id[0], name, time, describe, "NaN", MainActivity.sPersonDate.getEmail());
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(), "Событие создано", Toast.LENGTH_SHORT).show();

        Intent eventIntent = new Intent(getActivity(), EventActivity.class);
        eventIntent.putExtra("ID", id[0]);
        startActivity(eventIntent);


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

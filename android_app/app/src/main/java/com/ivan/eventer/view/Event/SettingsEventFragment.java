package com.ivan.eventer.view.Event;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ivan.eventer.R;
import com.ivan.eventer.backend.Commands;
import com.ivan.eventer.controller.EventActivity;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class SettingsEventFragment extends Fragment implements
        OnMapReadyCallback {

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final float DEFAULT_ZOOM = 18f;

    //Text
    private EditText mTitle;
    private EditText mDescribe;

    private TextView mAddressView;

    private Button mSaveButton;
    private Button mChooseButton;


    //Progress
    private ProgressDialog mProgressDialog;

    private Place mPlace;

//    private EventPreview mPreview;

    //Map
    private Marker mMarkerPosition;
    private LatLng mPosition;
    private String mAddress;
    private GoogleMap mMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_settings_event, container, false);

        mProgressDialog = new ProgressDialog(getActivity());

        mSaveButton = v.findViewById(R.id.saveEventSettings);
        mChooseButton = v.findViewById(R.id.chooseBtnSettingsEvent);

//        Устанавливаем название
        mTitle = v.findViewById(R.id.nameSettingsEvent);
        mTitle.setText(EventActivity.sEventPreview.getTitle());

        // Устанавливаем описание
        mDescribe = v.findViewById(R.id.describeSettingsEvent);
        mDescribe.setText(EventActivity.sEventPreview.getDescribe());

        // Устанавливаем адресс
        mAddress = EventActivity.sEventPreview.getAddress();
        mAddressView = v.findViewById(R.id.seAddress);
        mAddressView.setText(mAddress);

        //Устанавливаем метку на карте
        String[] pos = EventActivity.sEventPreview.getPosition().split(" ");
        mPosition = new LatLng(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPosition, DEFAULT_ZOOM));

        mChooseButton.setOnClickListener(view -> {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {

                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);

            } catch (GooglePlayServicesRepairableException e) {

                e.printStackTrace();
                Toast.makeText(getActivity(), "Гугл места недоступна!", Toast.LENGTH_LONG).show();

            } catch (GooglePlayServicesNotAvailableException e) {

                Toast.makeText(getActivity(), "Возможность выбора недоступна!", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }

        });

        mSaveButton.setOnClickListener(view -> {

            String title = mTitle.getText().toString();
            String describe = mDescribe.getText().toString();
            String id = EventActivity.sEventPreview.getID();
            String position = mPosition.latitude + " " + mPosition.longitude;

            saveData(id, title, describe, position);


        });



        SupportMapFragment mapFragment = new SupportMapFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.mapSettingsEvent, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

        return v;

    }

    private void saveData(String id, String title, String describe, String position) {

        Commands.updateEvent(id, title, describe, position, mAddress);
        EventActivity.sEventPreview.setDescribe(describe);
        EventActivity.sEventPreview.setPosition(position);
        EventActivity.sEventPreview.setTitle(title);
        EventActivity.sEventPreview.setAddress(mAddress);

        Toast.makeText(getActivity(), "Сохранено. Перезайдите в событие!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Запрет перемещения
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);

        mMarkerPosition = mMap.addMarker(new MarkerOptions()
                .position(mPosition)
                .draggable(false)
                .flat(true)
                .title("Место встречи"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPosition, DEFAULT_ZOOM));
    }

    // Получаем результаты выбора в position
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                mPlace = PlacePicker.getPlace(data, getActivity());
                mPosition = mPlace.getLatLng();
                mMarkerPosition.setPosition(mPosition);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPosition, DEFAULT_ZOOM));
                mAddress = mPlace.getAddress().toString();
                mAddressView.setText(mAddress);
            }
        }
    }

}

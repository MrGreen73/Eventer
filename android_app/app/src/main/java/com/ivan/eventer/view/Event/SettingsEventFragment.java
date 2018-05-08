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

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ivan.eventer.R;

import static android.app.Activity.RESULT_OK;


public class SettingsEventFragment extends Fragment implements
        OnMapReadyCallback {


    public static final String EVENT_PREVIEW = "gchatfragment.preview";
    public static final String PLACE = "gchatfragment.place";
    public static final String ADDRESS = "gchatfragment.address";

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final float DEFAULT_ZOOM = 18f;

    //Text
    private EditText mName;
    private EditText mCount;
    private EditText mDescribe;

    private TextView mAddressView;

    private Button mSaveButton;
    private Button mChooseButton;


    //Progress
    private ProgressDialog mProgress;

    private String mEvenId;
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

//        mPreview = (EventPreview) getArguments().getSerializable(EVENT_PREVIEW);
        // TODO передать позицию
//        mPosition = (LatLng) getArguments().getParcelable(PLACE);
//        mAddress = mPreview.getAddress();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_settings_event, container, false);
/*

        mProgress = new ProgressDialog(getActivity());

//        mEvenId = mPreview.getEventId();

        mSaveButton = v.findViewById(R.id.saveEventSettings);
        mName = v.findViewById(R.id.nameSettingsEvent);
        mCount = v.findViewById(R.id.countSettingsEvent);
        mDescribe = v.findViewById(R.id.describeSettingsEvent);
//        mDescribe.setText(mPreview.getDescribe());
        mAddressView = v.findViewById(R.id.seAddress);
        mAddressView.setText(mAddress);
        mChooseButton = (Button) v.findViewById(R.id.chooseBtnSettingsEvent);

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

//        mName.setText(mPreview.getTitle());
//        mCount.setText(String.valueOf(mPreview.getCount()));
        //mPlace.setText(mPreview.getPlace());

        mSaveButton.setOnClickListener(v1 -> {

            //Progress
            mProgress = new ProgressDialog(getActivity());
            mProgress.setTitle("Сохраняем данные");
            mProgress.setMessage("Пожалуйста подождите, пока мы сохраним изменения");
            mProgress.show();

            String name = mName.getText().toString();
            String count = mCount.getText().toString();
            String describe = mDescribe.getText().toString();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(count)) {

                // Мап для события
                HashMap<String, String> userMap = new HashMap<>();
                userMap.put("Name", name);
                userMap.put("Count", count);
                if (mPlace == null){
                    userMap.put("Place", "0 0");
                    userMap.put("Address", "Неизвестность");
                }
                else{
                    userMap.put("Place", mPlace.getLatLng().latitude + " " + mPlace.getLatLng().longitude);
                    userMap.put("Address", mPlace.getAddress().toString());

                }
            }
            else{
                mProgress.cancel();
                Toast.makeText(getActivity(), "Название и количество людей должны быть заполнены!", Toast.LENGTH_LONG);
            }

        });

        SupportMapFragment mapFragment = new SupportMapFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.mapSettingsEvent, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);
*/

        return v;

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

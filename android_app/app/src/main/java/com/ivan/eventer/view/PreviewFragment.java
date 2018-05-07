package com.ivan.eventer.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import static android.app.Activity.RESULT_OK;


public class PreviewFragment extends Fragment implements
        OnMapReadyCallback {


    public static final String EVENT_PREVIEW = "previewfragment.preview";
    public static final String PLACE = "previewfragment.place";
    public static final String ADDRESS = "previewfragment.address";

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final float DEFAULT_ZOOM = 18f;

    //Text
    private TextView mName;
    private TextView mCount;
    private TextView mDescribe;

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
        mPosition = (LatLng) getArguments().getParcelable(PLACE);
//        mAddress = mPreview.getAddress();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_preview, container, false);


        mProgress = new ProgressDialog(getActivity());

//        mEvenId = mPreview.getEventId();
        //mDatabase = FirebaseDatabase.getInstance().getReference().child("Events").child(mEvenId);

        mSaveButton = v.findViewById(R.id.saveEventSettings);
        mName = v.findViewById(R.id.nameSettingsEvent);
        mCount = v.findViewById(R.id.countSettingsEvent);
        mDescribe = v.findViewById(R.id.describeSettingsEvent);
//        mDescribe.setText(mPreview.getDescribe());
        mAddressView = v.findViewById(R.id.addressSettingsEvent);
        mAddressView.setText(mAddress);
        mChooseButton = (Button) v.findViewById(R.id.chooseBtnSettingsEvent);

//        mName.setText(mPreview.getTitle());
//        mCount.setText(String.valueOf(mPreview.getCount()));
        //mPlace.setText(mPreview.getPlace());

        SupportMapFragment mapFragment = new SupportMapFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.mapPreview, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

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

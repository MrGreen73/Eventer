package com.ivan.eventer.view.Event;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.ivan.eventer.controller.EventActivity;

import static android.app.Activity.RESULT_OK;


public class PreviewFragment extends Fragment implements
        OnMapReadyCallback {

    private static final int PLACE_PICKER_REQUEST = 1;
    private static final float DEFAULT_ZOOM = 18f;

    //Text
    private TextView mTitle;
    private TextView mDescribe;
    private TextView mKind;
    private TextView mTime;
    private TextView mDate;

    private ImageView mImage;

    private TextView mAddressView;

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

        View v = inflater.inflate(R.layout.fragment_preview, container, false);

        mTitle = v.findViewById(R.id.namePreviewEvent);
        mDescribe = v.findViewById(R.id.describePreviewEvent);
        mAddressView = v.findViewById(R.id.addressPreviewEvent);
        mKind = v.findViewById(R.id.previewKind);
        mTime = v.findViewById(R.id.previewTime);
        mDate = v.findViewById(R.id.previewDate);
        mImage = v.findViewById(R.id.previewImage);

        mKind.setText(EventActivity.sEventPreview.getKind());
        mTime.setText(EventActivity.sEventPreview.getTime());
        mDate.setText(EventActivity.sEventPreview.getDate());
        mImage.setImageBitmap(getBitmap(EventActivity.sEventPreview.getImage()));

        mTitle.setText(EventActivity.sEventPreview.getTitle());
        mDescribe.setText(EventActivity.sEventPreview.getDescribe());
        mAddress = EventActivity.sEventPreview.getAddress();
        mAddressView.setText(mAddress);

        //Устанавливаем метку на карте
        String[] pos = EventActivity.sEventPreview.getPosition().split(" ");
        mPosition = new LatLng(Double.parseDouble(pos[0]), Double.parseDouble(pos[1]));

        SupportMapFragment mapFragment = new SupportMapFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.mapPreview, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);

        return v;

    }


    private Bitmap getBitmap(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);

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

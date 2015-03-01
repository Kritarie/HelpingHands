package com.awsickapps.helpinghands;


import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kritarie on 2/28/15.
 *
 */
public class NeedHelpFragment extends Fragment implements OnMapReadyCallback {

    @InjectView(R.id.address) TextView address;
    @InjectView(R.id.citystate) TextView citystate;

    private MapFragment mapFragment;
    private GoogleMap map;
    private Geocoder geocoder;

    private Context context;

    public static Fragment newInstance() {
        NeedHelpFragment f = new NeedHelpFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.need_help_fragment, container, false);
        ButterKnife.inject(this, view);

        context = getActivity().getApplicationContext();
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        geocoder = new Geocoder(context, Locale.getDefault());

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                double lat = location.getLatitude();
                double lng = location.getLongitude();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lat, lng), 15);
                map.animateCamera(cameraUpdate);

                List<Address> addresses = new ArrayList<>();

                try {
                    addresses = geocoder.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!addresses.isEmpty()) {
                    String geoaddress = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();

                    address.setText(geoaddress);
                    citystate.setText(city + ", " + state);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}

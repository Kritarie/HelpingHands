package com.awsickapps.helpinghands;


import android.app.Fragment;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.awsickapps.helpinghands.busevents.GeocodedEvent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import utils.GeocoderTask;

/**
 * Created by kritarie on 2/28/15.
 *
 */
public class NeedHelpFragment extends Fragment implements OnMapReadyCallback {

    @InjectView(R.id.address) TextView address;
    @InjectView(R.id.citystate) TextView citystate;
    @InjectView(R.id.helpButton) Button helpButton;

    private MapFragment mapFragment;
    private GoogleMap map;
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
        mapFragment.getMapAsync(this);

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
                new GeocoderTask(context, lat, lng).execute();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        BaseApplication.getEventBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BaseApplication.getEventBus().unregister(this);
    }

    @Subscribe
    public void setAddress(GeocodedEvent event) {
        address.setText(event.getAddress());
        citystate.setText(event.getCity() + ", " + event.getState());
    }

    public void requestHelp(String ailment){
        //TODO this should be replaced by the buttons onclick function
        helpButton.setText(ailment);
        Log.d("AILMENT", "Requesting help");
    }
}

package com.awsickapps.helpinghands;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kritarie on 2/28/15.
 *
 */
public class RescueActivity extends FragmentActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;

    private LatLng distressLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rescue_activity_layout);
        ButterKnife.inject(this);

        Bundle b = getIntent().getExtras();
        distressLocation = new LatLng(b.getDouble("lat"), b.getDouble("lng"));

        FragmentManager fm = getSupportFragmentManager();

        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.helpButton)
    public void helpButtonClicked() {
        String navigate = "http://maps.google.com/maps?daddr=" +
                distressLocation.latitude + "," + distressLocation.longitude;
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(navigate));
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMyLocationEnabled(true);
        map.getUiSettings().setAllGesturesEnabled(false);
        map.addMarker(new MarkerOptions()
                .position(distressLocation)
                .title("Help Needed")
                .snippet("A person at this location needs medical assistance!")
                .draggable(false));

        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(location.getLatitude(), location.getLongitude()));
                builder.include(distressLocation);
                LatLngBounds bounds = builder.build();
                int padding = 20; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                map.animateCamera(cu);
            }
        });

    }
}

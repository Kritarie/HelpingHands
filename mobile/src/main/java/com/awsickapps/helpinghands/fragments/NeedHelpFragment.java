package com.awsickapps.helpinghands.fragments;


import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.awsickapps.helpinghands.BaseApplication;
import com.awsickapps.helpinghands.DisclaimerActivity;
import com.awsickapps.helpinghands.R;
import com.awsickapps.helpinghands.RescueActivity;
import com.awsickapps.helpinghands.busevents.GeocodedEvent;
import com.awsickapps.helpinghands.busevents.StartSplashEvent;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.ApplicationData;
import utils.GeocoderTask;
import utils.RestClient;

/**
 * Created by kritarie on 2/28/15.
 *
 */
public class NeedHelpFragment extends Fragment implements OnMapReadyCallback {

    @InjectView(R.id.address) TextView address;
    @InjectView(R.id.citystate) TextView citystate;
    @InjectView(R.id.helpButton) Button helpButton;

    public static double lat,lng;

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Context context;
    private Location location;

    public static Fragment newInstance() {
        NeedHelpFragment f = new NeedHelpFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.getEventBus().post(new StartSplashEvent());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.need_help_fragment, container, false);
        ButterKnife.inject(this, view);

        Bundle b = getArguments();

        context = getActivity().getApplicationContext();

        FragmentManager fm = null;

        if (Build.VERSION.SDK_INT < 17)
            fm = getFragmentManager();
        else
            fm = getChildFragmentManager();


        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.setMyLocationEnabled(true);

        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location loc) {
                location = loc;
                lat = loc.getLatitude();
                lng = loc.getLongitude();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lat, lng), 15);
                map.animateCamera(cameraUpdate);
                new GeocoderTask(context, lat, lng).execute();
            }
        });

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                getGps(), 15);
        try{
            map.moveCamera(cameraUpdate);
        }catch (Exception e){}

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
        if(helpButton != null) {

            Toast.makeText(context, "Help is on the way!", Toast.LENGTH_LONG).show();

            //send their needed hand request and gps coordinates to MATT JENKINS!!!
            RestClient.get().requestHelp(ApplicationData.getRegId(), ailment, lat, lng, new Callback<Integer>() {

                @Override
                public void success(Integer integer, Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
            sendTextMessage(ailment, null); //pass gps coordinates for text message building
            dial911();
        }
        Log.d("AILMENT", "Requesting help");
    }



    private void sendTextMessage(String handNeeded, String location){

        SmsManager sms = SmsManager.getDefault();
        //add correct location to this message.
        sms.sendTextMessage("5409076417", null, "Hello, I am located at: UVA Campus \n I am suffering from/in need of help with: " + handNeeded + "\n Please send help!", null, null);

    }

    private void dial911(){

        Uri number = Uri.parse("tel:8047319861");
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);

    }

    @OnClick(R.id.helpButton)
    public void startEmergencyDisclaimer() {

        Intent i = new Intent(getActivity(), DisclaimerActivity.class);
        if(location != null) {
            i.putExtra("lat", location.getLatitude());
            i.putExtra("lng", location.getLongitude());
        }else{
            i.putExtra("lat", lat);
            i.putExtra("lng", lng);
        }
        startActivity(i);
    }

    private LatLng getGps() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;

        for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }

        double[] gps = new double[2];
        LatLng loc;
        if (l != null) {
            return new LatLng(l.getLatitude(), l.getLongitude());
        }

        return null;
    }
}

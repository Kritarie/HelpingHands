package utils;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.widget.Toast;

import com.awsickapps.helpinghands.BaseApplication;
import com.awsickapps.helpinghands.busevents.GeocodedEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/***An AsyncTask class for accessing the GeoCoding Web Service****/

public class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

    protected LatLng enteredAddress;
    protected MarkerOptions markerOptions;

    private Context context;
    private double lat;
    private double lng;

    public GeocoderTask(Context context, double lat, double lng){
        this.context = context;
        this.lat = lat;
        this.lng = lng;
    }


    @Override
    protected List<Address> doInBackground(String... locationName) {
        // Creating an instance of Geocoder class
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(context);
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
        } catch (IOException e) {

        }

        return addresses;
    }

    // part of the GeocoderTask life cycle
    @Override
    protected void onPostExecute(List<Address> addresses) {

        if (addresses == null || addresses.isEmpty()) return;
        String geoaddress = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();

        BaseApplication.getEventBus().post(new GeocodedEvent(geoaddress, city, state));
    }
}
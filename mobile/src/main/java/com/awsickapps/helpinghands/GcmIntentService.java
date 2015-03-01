package com.awsickapps.helpinghands;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.util.List;

import utils.ApplicationData;

/**
 * Created by kritarie on 2/28/15.
 *
 */
public class GcmIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCM Demo";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
                try {
                    JSONObject response = new JSONObject(extras.getString("data"));
                    Log.d(TAG,"json: " + response);
                    double lat = response.getDouble("lat");
                    double lng = response.getDouble("lng");
                    Log.d(TAG,"Received " + extras.toString());
                    Log.d(TAG,"   LAT/LNG " + lat+"/"+lng);
                    Location userLocation = getGps();

                    Location locationA = new Location("Distress location");
                    Log.d(TAG,"MY LAT/LNG " + userLocation.getLatitude()+"/"+userLocation.getLongitude());

                    locationA.setLatitude(lat);
                    locationA.setLongitude(lng);

                    float distance = locationA.distanceTo(userLocation);
                    double minDist = ApplicationData.getInt("distance");
                    System.out.println("Distance between is " + distance + " / " + 1610*minDist);

                    if (distance < 1610*minDist) {
                        Intent i = new Intent (this, RescueActivity.class);
                        i.putExtra("lat", lat);
                        i.putExtra("lng", lng);
                        i.putExtra("ailment", response.getString("ailment"));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }catch (Exception e){
                    Log.d(TAG,"error", e);
                }
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private Location getGps() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;

        for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }


        return l;
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.common_signin_btn_icon_light)
                        .setContentTitle("GCM Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}

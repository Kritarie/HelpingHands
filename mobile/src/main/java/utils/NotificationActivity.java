package utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.awsickapps.helpinghands.R;
import com.awsickapps.helpinghands.busevents.HelpMeActivity;

/**
 * Created by Matthew on 2/28/2015.
 */
public class NotificationActivity extends Activity{
    public static final int NOTIFICATION_CODE = 0x42;
    public static final int REQUEST_CODE = 0x1337;
    public static final String AILMENT_MESSAGE = "com.frogtown.helpinghands.ailmentmessage";
    Notification notification;

    @Override
    protected void onStart() {
        super.onStart();

        notification = buildNotification();

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_CODE, notification);
    }

    @Override
    protected void onResume() {
        super.onResume();

        finish();
    }

    public Notification buildNotification(){

        Intent asthmaIntent = new Intent(this, HelpMeActivity.class);
        asthmaIntent.setAction("com.frogtown.asthma");
        asthmaIntent.putExtra(AILMENT_MESSAGE, "ASTHMA ATTACK AHHH!!!");

        Intent cprIntent = new Intent(this, HelpMeActivity.class);
        asthmaIntent.setAction("com.frogtown.cpr");
        cprIntent.putExtra(AILMENT_MESSAGE, "MY HEART STOPPED, PLEASE HELP");

        PendingIntent pendingAsthmaIntent = PendingIntent.getActivity(this
                , REQUEST_CODE
                , asthmaIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingCprIntent = PendingIntent.getActivity(this
                , REQUEST_CODE
                , cprIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        return new Notification.Builder(this)
                .setContentTitle("Request A Helping Hand")
                .setContentText("Swipe left to request a helping hand")
                .setSmallIcon(R.drawable.ic_drawer)
                .addAction(R.drawable.ic_drawer, "Asthma Attack", pendingAsthmaIntent)
                .addAction(R.drawable.ic_drawer, "Request CPR", pendingCprIntent)
                .build();
    }
}

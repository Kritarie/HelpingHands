package utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;

import com.awsickapps.helpinghands.MainActivity;
import com.awsickapps.helpinghands.R;

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

        Intent asthmaIntent = new Intent(this, MainActivity.class);
        asthmaIntent.setAction("com.frogtown.asthma");
        asthmaIntent.putExtra(AILMENT_MESSAGE, "ASTHMA ATTACK AHHH!!!");
        asthmaIntent.setData((Uri.parse("foobar://" + SystemClock.elapsedRealtime())));
        asthmaIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Intent strokeIntent = new Intent(this, MainActivity.class);
        strokeIntent.setAction("com.frogtown.stroke");
        strokeIntent.putExtra(AILMENT_MESSAGE, "GET #STROKED");
        strokeIntent.setData((Uri.parse("foobar://" + SystemClock.elapsedRealtime())));
        strokeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Intent heartAttackIntent = new Intent(this, MainActivity.class);
        heartAttackIntent.setAction("com.frogtown.heartattack");
        heartAttackIntent.putExtra(AILMENT_MESSAGE, "MY HEART JUST EXPLODED");
        heartAttackIntent.setData((Uri.parse("foobar://" + SystemClock.elapsedRealtime())));
        heartAttackIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        Intent diabeetusIntent = new Intent(this, MainActivity.class);
        diabeetusIntent.setAction("com.frogtown.diabeetus");
        diabeetusIntent.putExtra(AILMENT_MESSAGE, "I NEED A SANDWHICH");
        diabeetusIntent.setData((Uri.parse("foobar://" + SystemClock.elapsedRealtime())));
        diabeetusIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingAsthmaIntent = PendingIntent.getActivity(this
                , NOTIFICATION_CODE
                , asthmaIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingStrokeIntent = PendingIntent.getActivity(this
                , NOTIFICATION_CODE
                , strokeIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingHeartAttackIntent = PendingIntent.getActivity(this
                , NOTIFICATION_CODE
                , heartAttackIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingDiabeetusIntent = PendingIntent.getActivity(this
                , NOTIFICATION_CODE
                , diabeetusIntent
                , PendingIntent.FLAG_UPDATE_CURRENT);

        return new Notification.Builder(this)
                .setContentTitle("Request A Helping Hand")
                .setContentText("Swipe left to request a helping hand")
                .setSmallIcon(R.drawable.ic_drawer)
                .addAction(R.drawable.ic_drawer, "Asthma Attack", pendingAsthmaIntent)
                .addAction(R.drawable.ic_drawer, "Stroke", pendingStrokeIntent)
                .addAction(R.drawable.ic_drawer, "Heart Attack", pendingStrokeIntent)
                .addAction(R.drawable.ic_drawer, "Diabeetus", pendingStrokeIntent)
                .build();
    }
}

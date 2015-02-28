package com.awsickapps.helpinghands.busevents;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.awsickapps.helpinghands.R;

import utils.NotificationActivity;

/**
 * Created by Matthew on 2/28/2015.
 */
public class HelpMeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me);

        Intent intent = getIntent();

        String data = "" + intent.getStringExtra(NotificationActivity.AILMENT_MESSAGE);

        ((TextView)findViewById(R.id.message)).setText(data);

        try{
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage("8047319861", null, "Help me!", null, null);
            Log.d("TEXTING", "Sent SMS!");
        }catch (Exception e){
            Toast.makeText(this, "Unable to send text", Toast.LENGTH_LONG);
            Log.d("TEXTING", "Error sending SMS", e);
        }
    }
}

package com.awsickapps.helpinghands;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.ApplicationData;
import utils.RestClient;

/**
 * Created by allen on 2/28/15.
 */
public class DisclaimerActivity extends ListActivity implements AdapterView.OnItemClickListener{

    @InjectView(R.id.tvDisclaimer)
    TextView tvDisclaimer;

    HashMap<View, String> viewMap;
    Adapter adapter;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMap = new HashMap<>();
        setContentView(R.layout.emergency_dialog);
        ButterKnife.inject(this);

        Bundle b = getIntent().getExtras();
        lat = b.getDouble("lat");
        lng = b.getDouble("lng");

        tvDisclaimer.setText(Html.fromHtml(
                "<h1>" + getString(R.string.warning_text) + "</h1>\n" +
                getString(R.string.disclaimer_start) + "\n" +
                "<h4><i>" + getString(R.string.disclaimer) + "</i></h4>\n" +
                getString(R.string.disclaimer_end)));


        adapter = new Adapter(this);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(this);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String handNeeded = viewMap.get(view);

        Toast.makeText(this, "Help is on the way!", Toast.LENGTH_LONG).show();

        //send their needed hand request and gps coordinates to MATT JENKINS!!!
        RestClient.get().requestHelp(ApplicationData.getRegId(), handNeeded, lat, lng, new Callback<Integer>() {

            @Override
            public void success(Integer integer, Response response) {
                dial911();
            }

            @Override
            public void failure(RetrofitError error) {
                dial911();
            }
        });
        sendTextMessage(handNeeded, null); //pass gps coordinates for text message building
        //dial911();
        finish();
    }

    private void sendTextMessage(String handNeeded, String location){

        SmsManager sms = SmsManager.getDefault();
        //add correct location to this message.
        sms.sendTextMessage("5409076417", null, "Hello, I am located at: UVA Campus \n I am suffering from/in need of help with: " + handNeeded + "\n Please send help!", null, null);

    }

    private void dial911(){

        Uri number = Uri.parse("tel:5409076417");
        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        startActivity(callIntent);

    }

    private class Adapter extends BaseAdapter{

        LayoutInflater inflater;
        View view;
        ArrayList<String> missingHandsList;
        Context context;

        public Adapter(Context context){

            this.context = context;
            setUpEmergencies();

        }

        public void setUpEmergencies(){
            missingHandsList = new ArrayList<>();
            String[] missingHands = getResources().getStringArray(R.array.helping_hands);

            for(String hand : missingHands){
                if(ApplicationData.isActive(ApplicationData.GET_HELP_WITH + hand))
                    missingHandsList.add(hand);
            }

            if(missingHandsList.isEmpty()) {
                for (String hand : missingHands)
                    missingHandsList.add(hand);
            }
        }

        @Override
        public int getCount() {
            return missingHandsList.size();
        }

        @Override
        public Object getItem(int position) {
            return missingHandsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.disclaimer_list_element, parent, false);

            viewMap.put(view, missingHandsList.get(position));
            TextView tv = (TextView) view.findViewById(R.id.tv);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);

            tv.setText(missingHandsList.get(position));
            iv.setImageResource(ApplicationData.getImageAsset(missingHandsList.get(position)));
            return view;
        }
    }
}

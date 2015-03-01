package com.awsickapps.helpinghands;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.InjectView;
import utils.ApplicationData;

/**
 * Created by allen on 2/28/15.
 */
public class DisclaimerActivity extends ListActivity {

    @InjectView(R.id.tvDisclaimer)
    TextView tvDisclaimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private class Adapter extends BaseAdapter{

        LayoutInflater inflater;
        View view;
        ArrayList<String> missingHandsList;
        Context context;

        public Adapter(Context context){

            this.context = context;
            String[] missingHands = getResources().getStringArray(R.array.helping_hands);

            for(String hand : missingHands){

                if(ApplicationData.isActive(ApplicationData.GET_HELP_WITH + hand))
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

            TextView tv = (TextView) view.findViewById(R.id.tv);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);

            tv.setText(missingHandsList.get(position));


            return view;
        }
    }


}

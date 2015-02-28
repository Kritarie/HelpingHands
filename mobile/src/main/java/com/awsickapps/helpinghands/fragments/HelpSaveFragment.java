package com.awsickapps.helpinghands.fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.awsickapps.helpinghands.R;

import java.util.HashMap;
import java.util.HashSet;

import utils.ApplicationData;

/**
 * Created by allen on 2/28/15.
 */
public class HelpSaveFragment extends ListFragment{

    ApplicationData data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_save, container, false);
        Adapter adapter = new Adapter(getActivity());
        setListAdapter(adapter);
        return view;
    }

    private class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        Context context;
        String[] helpOptions;
        HashSet<String> selectedHands;
        HashMap<CheckBox.OnCheckedChangeListener, String> viewMap;

        public Adapter(Context context){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            helpOptions = getResources().getStringArray(R.array.helping_hands);
            viewMap = new HashMap<>();
            selectedHands = data.getHelpsWith();
        }

        @Override
        public int getCount() {
            return helpOptions.length;
        }

        @Override
        public Object getItem(int position) {
            return helpOptions[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = inflater.inflate(R.layout.checkbox_list_element, parent, false);

            TextView tv = (TextView) row.findViewById(R.id.tv);
            ImageView iv = (ImageView) row.findViewById(R.id.iv);
            CheckBox cb = (CheckBox) row.findViewById(R.id.cb);

            String handOption = helpOptions[position];

            tv.setText(handOption);
            if(selectedHands.contains(handOption))
                cb.setChecked(true);

            CheckBox.OnCheckedChangeListener occl = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        selectedHands.add(viewMap.get(this));
                    else
                        selectedHands.remove(viewMap.get(this));
                }
            };

            viewMap.put(occl, handOption);
            cb.setOnCheckedChangeListener(occl);


            return row;
        }
    }
}

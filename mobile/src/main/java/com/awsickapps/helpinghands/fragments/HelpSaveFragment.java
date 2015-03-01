package com.awsickapps.helpinghands.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.awsickapps.helpinghands.BaseApplication;
import com.awsickapps.helpinghands.R;
import com.awsickapps.helpinghands.User;
import com.awsickapps.helpinghands.busevents.ChangesSavedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import utils.ApplicationData;
import utils.RestClient;

/**
 * Created by allen on 2/28/15.
 */
public class HelpSaveFragment extends ListFragment {

    private static String prefix;

    @InjectView(R.id.bSaveChanges) Button bSaveChanges;


    public static HelpSaveFragment newInstance(int position){

        if(position == 1){
            prefix = ApplicationData.HELP_WITH;
        }else if(position == 2){
            prefix = ApplicationData.GET_HELP_WITH;
        }

        return new HelpSaveFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_save, container, false);
        ButterKnife.inject(this, view);


        TextView tv = (TextView) view.findViewById(R.id.tvHeader);
        if(prefix.equals(ApplicationData.HELP_WITH))
            tv.setText(getString(R.string.selected_hands));
        else
            tv.setText(getString(R.string.hands_needed));

        Adapter adapter = new Adapter(getActivity());
        setListAdapter(adapter);
        return view;
    }

    private class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        Context context;
        String[] helpOptions;
        SeekBar seekbar;
        TextView label;
        HashMap<CheckBox.OnCheckedChangeListener, String> viewMap;
        double[] values = {0.5, 1, 1.5, 2, 3, 4, 5, 8, 12, 15};
        String labelText = "Willing to travel ";
        String labelText2 = " miles";

        public Adapter(Context context){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.context = context;
            helpOptions = getResources().getStringArray(R.array.helping_hands);
            viewMap = new HashMap<>();

            seekbar = new SeekBar(context);
            seekbar.setMax(values.length - 1);
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    label.setText(labelText + getDistance() + labelText2);
                    ApplicationData.update("distance", seekBar.getProgress());
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });

            label = new TextView(context);
            label.setText(labelText + getDistance() + labelText2);

        }

        public double getDistance(){
            return values[seekbar.getProgress()];
        }

        public boolean isOfferHelp(){
            return !prefix.equals(ApplicationData.GET_HELP_WITH);
        }

        @Override
        public int getCount() {
            return helpOptions.length + (isOfferHelp()?2:0);
        }

        @Override
        public Object getItem(int position) {
            if(position == helpOptions.length){
                return seekbar;
            }
            return helpOptions[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(position == helpOptions.length){
                return label;
            }else if(position == helpOptions.length+1){
                seekbar.setProgress(ApplicationData.getInt("distance"));
                return seekbar;
            }
            View row = inflater.inflate(R.layout.checkbox_list_element, parent, false);

            TextView tv = (TextView) row.findViewById(R.id.tv);
            ImageView iv = (ImageView) row.findViewById(R.id.iv);
            CheckBox cb = (CheckBox) row.findViewById(R.id.cb);

            String handOption = helpOptions[position];

            tv.setText(handOption);
            cb.setChecked(ApplicationData.isActive(prefix + handOption));

            CheckBox.OnCheckedChangeListener occl = new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ApplicationData.update(prefix + viewMap.get(this), isChecked);
                    BaseApplication.getEventBus().post(new HelpEvent());
                }
            };

            viewMap.put(occl, handOption);
            cb.setOnCheckedChangeListener(occl);
            iv.setImageResource(ApplicationData.getImageAsset(helpOptions[position]));


            return row;
        }
    }


    @OnClick(R.id.bSaveChanges)
    public void sendUpdatedSettingsToServer(){

        String needHelp = ApplicationData.HELP_WITH;
        String helpWith = ApplicationData.GET_HELP_WITH;
        String[] keys = getResources().getStringArray(R.array.helping_hands);

        ArrayList<String> usersHelpsWith = new ArrayList<>();
        ArrayList<String> userNeedsHelpsWith = new ArrayList<>();

        for(String hand : keys){
            if (ApplicationData.isActive(needHelp + hand))
                userNeedsHelpsWith.add(hand);

            if(ApplicationData.isActive(helpWith + hand))
                usersHelpsWith.add(hand);
        }

        //first, set this fragment to tell the nav drawer to press the first button (map view fragment)

        //then, the the lists that are built and send them to MATT JENKINS SERVER!!!!


        Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
        RestClient.get().updateUser(new User(ApplicationData.getRegId(), usersHelpsWith, userNeedsHelpsWith),
                                    new Callback<Integer>() {

            @Override
            public void success(Integer integer, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }

        });
        BaseApplication.getEventBus().post(new ChangesSavedEvent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public static class HelpEvent{}
}

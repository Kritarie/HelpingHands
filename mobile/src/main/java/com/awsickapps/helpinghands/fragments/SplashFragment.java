package com.awsickapps.helpinghands.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.awsickapps.helpinghands.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kritarie on 3/1/15.
 *
 */
public class SplashFragment extends Fragment {

    @InjectView(R.id.logo) ImageView iv;

    public static Fragment newInstance() {
        NeedHelpFragment f = new NeedHelpFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.splash_layout, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}

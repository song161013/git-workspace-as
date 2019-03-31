package com.danikula.videocache.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.androidannotations.annotations.EActivity;

public class SingleVideoActivity_ extends FragmentActivity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_single_video);
        if (state == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.containerView, VideoFragment.build(Video.ORANGE_1.url))
                    .commit();
        }
    }
}
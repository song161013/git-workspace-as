package com.danikula.videocache.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.androidannotations.annotations.EActivity;

public class MultipleVideosActivity_ extends FragmentActivity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_multiple_videos);
        if (state == null) {
            /*addVideoFragment(Video.ORANGE_1, R.id.videoContainer0);
            addVideoFragment(Video.ORANGE_2, R.id.videoContainer1);
            addVideoFragment(Video.ORANGE_3, R.id.videoContainer2);
            addVideoFragment(Video.ORANGE_4, R.id.videoContainer3);*/

            addVideoFragment(MyTestVideo.ORANGE_1, R.id.videoContainer0);
            addVideoFragment(MyTestVideo.ORANGE_2, R.id.videoContainer1);
            addVideoFragment(MyTestVideo.ORANGE_3, R.id.videoContainer2);
            addVideoFragment(MyTestVideo.ORANGE_4, R.id.videoContainer3);
        }
    }

    private void addVideoFragment(/*Video*/ MyTestVideo video, int containerViewId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, VideoFragment.build(video.url))
                .commit();
    }
}

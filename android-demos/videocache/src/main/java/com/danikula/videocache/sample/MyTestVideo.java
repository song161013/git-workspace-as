package com.danikula.videocache.sample;

/**
 * Created by songfei on 2018/10/30
 * Description：
 */
public enum MyTestVideo {
    ORANGE_1(MyTestVideo.Config.ROOT + "18103013321273.mp4"),
    ORANGE_2(MyTestVideo.Config.ROOT + "18103013321273.mp4"),
    ORANGE_3(MyTestVideo.Config.ROOT + "18103013321273.mp4"),
    ORANGE_4(MyTestVideo.Config.ROOT + "18103013321273.mp4"),
    ORANGE_5(MyTestVideo.Config.ROOT + "18103013321273.mp4");

    public final String url;

    MyTestVideo(String url) {
        this.url = url;
    }

    private class Config {
        //http://192.168.0.106:9999/public/images/iec/carousel_mov/2018-10-30/18103013321273.mp4
        private static final String ROOT = "http://192.168.0.106:9999/public/images/iec/carousel_mov/2018-10-30/";
    }
}

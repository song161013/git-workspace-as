package com.main.app;

import android.app.Application;

/**
 * Created by songfei on 2018/6/6
 * Description：
 */

public class MainApplication extends Application {
    private static MainApplication mInstance;

    public static MainApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}

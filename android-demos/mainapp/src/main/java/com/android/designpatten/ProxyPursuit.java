package com.android.designpatten;

import android.util.Log;

/**
 * Created by songfei on 2018/9/29
 * Description：追求者
 */
public class ProxyPursuit implements ProxyGiveGift {
    private final String TAG = "ProxyPursuit";
    private ProxySchoolGirl girl;

    public ProxyPursuit(ProxySchoolGirl girl) {
        this.girl = girl;
    }

    @Override
    public String giveFlower() {
        return girl.getName() + "送你花";
    }

    @Override
    public String giveDolls() {
        return girl.getName() + "送你球";
    }

    @Override
    public String giveBird() {
        return girl.getName() + "送你鸟";
    }
}

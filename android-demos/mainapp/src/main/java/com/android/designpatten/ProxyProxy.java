package com.android.designpatten;

/**
 * Created by songfei on 2018/9/29
 * Description：
 */
public class ProxyProxy implements ProxyGiveGift {
    private ProxyPursuit pursuit;

    public ProxyProxy(ProxySchoolGirl girl) {
        pursuit = new ProxyPursuit(girl);
    }

    @Override
    public String giveFlower() {
       return pursuit.giveFlower();
    }

    @Override
    public String giveDolls() {
        return  pursuit.giveDolls();
    }

    @Override
    public String giveBird() {
        return  pursuit.giveBird();
    }
}

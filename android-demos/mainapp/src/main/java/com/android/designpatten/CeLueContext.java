package com.android.designpatten;

/**
 * Created by songfei on 2018/6/6
 * Description：
 */

class CeLueContext {
    private CeLuoStrategy strategy;

    CeLueContext(CeLuoStrategy strategy) {
        this.strategy = strategy;
    }

    void ContextInterface() {
        strategy.suanfaInteeface();
    }

}

package com.android.designpatten;

/**
 * Created by songfei on 2018/5/25
 * Description：
 */

public class OperateSub extends Operation {
    @Override
    public double getResutl() {
        double result = getNumberA() - getNumberB();
        return result;
    }
}

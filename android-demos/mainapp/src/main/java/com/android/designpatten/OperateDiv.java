package com.android.designpatten;

/**
 * Created by songfei on 2018/5/25
 * Description：
 */

public class OperateDiv extends Operation {
    @Override
    public double getResutl() {
        if (getNumberB() == 0) {
            throw new RuntimeException("除数不能为空！");
        }
        double result = getNumberA() / getNumberB();
        return result;
    }
}

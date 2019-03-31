package com.android.designpatten;


/**
 * Created by songfei on 2018/5/25
 * Description：
 */

public class OperationFactory {
    public static Operation createOption(String optioner) {
        Operation operation = null;
        switch (optioner) {
            case "-": {
                operation = new OperateSub();
                break;
            }
            case "+": {
                operation = new OperateAdd();
                break;
            }
            case "*": {
                operation = new OperateMul();
                break;
            }
            case "/": {
                operation = new OperateDiv();
                break;
            }
        }
        return operation;
    }
}

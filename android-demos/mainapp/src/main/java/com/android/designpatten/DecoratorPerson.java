package com.android.designpatten;

/**
 * Created by songfei on 2018/10/15
 * Description：
 */
public class DecoratorPerson {
    //===================================================第一版start=============================================
    private String name;

    public DecoratorPerson(String name) {
        this.name = name;
    }

    public String wearWaitao() {
        return "穿外套";
    }

    public String wearMaoyi() {
        return "穿毛衣";
    }

    public String wearqiuku() {
        return "穿秋裤";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //===================================================第一版end=============================================

    //===================================================第二版start=============================================
    public String show() {
        return "装扮的" + name;
    }
    //===================================================第二版end=============================================


}

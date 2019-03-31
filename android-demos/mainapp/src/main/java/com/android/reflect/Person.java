package com.android.reflect;

import android.util.Log;

/**
 * Created by songfei on 2018/5/11
 * Description：
 */

public class Person {
    private static final String TAG = "Person";
    private String name;
    private int age;
    private String sex;

    public Person() {
        Log.e(TAG, "person 空构造");
    }

    public Person(String name) {
        this.name = name;
        Log.e(TAG, "带有String 构造");
    }

    private Person(String name, int age) {
        this.name = name;
        this.age = age;
        Log.e(TAG, "私有构造，带有String ，int构造");
    }

    public Person(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        Log.e(TAG, "带有String,int ,String构造");
    }

    public void method1() {
        Log.e(TAG, "没返回值，没参数方法");
    }

    public String method2() {
        Log.e(TAG, "有返回值，没参数方法");
        return "哈哈哈";
    }

    public void method3(String name) {
        Log.e(TAG, "没返回值，有参数方法,name=" + name);
    }

    public int method4(int age) {
        Log.e(TAG, "有返回值，有参数方法,age=" + age);
        return age;
    }

    private void method5(String name, int age) {
        Log.e(TAG, "私有方法,name=" + name + ",age=" + age);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "name=" + this.name + ",age=" + this.age + "，sex=" + this.sex;
    }
}

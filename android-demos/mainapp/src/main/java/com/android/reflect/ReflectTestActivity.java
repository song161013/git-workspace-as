package com.android.reflect;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.main.app.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by songfei on 2018/5/11
 * Description：
 */

public class ReflectTestActivity extends Activity {
    private static final String TAG = "ReflectTestActivity";
    private TextView tvText;
    private StringBuilder sb = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        initView();
        findView();
        reflect();
        reflectConstruct();
        reflectConstructCreateObj();
        reflectPrivateConstruct();
    }

    private void initView() {
        sb = new StringBuilder();
    }

    /**
     * 获取私有构造方法
     */
    private void reflectPrivateConstruct() {
        try {
            Class c1 = Class.forName("com.android.reflect.Person");
            Constructor con = c1.getDeclaredConstructor(String.class, int.class);
            con.setAccessible(true);
            Object obj = con.newInstance("songfei", 28);
            sb.append("\n\n").append("obj=").append(obj);
            tvText.setText(sb.toString());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取构造方法，创建对象
     */
    private void reflectConstructCreateObj() {
        try {
            Class c = Class.forName("com.android.reflect.Person");
            Constructor<Person> con1 = c.getConstructor(String.class, int.class, String.class);
            Object obj = con1.newInstance("哈哈", 26, "男");
            sb.append("\nobj=").append(obj).append("\n");
            tvText.setText(sb.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射获取构造方法
     */
    private void reflectConstruct() {
        try {
            Class c1 = Class.forName("com.android.reflect.Person");
            Constructor cons[] = c1.getConstructors();
            for (Constructor con : cons) {
                sb.append("con=").append(con).append("\n");
            }

            Constructor con1 = c1.getConstructor(new Class[0]);
            sb.append("con1=").append(con1).append("\n");

            Constructor con2 = c1.getConstructor(String.class);
            sb.append("con2=").append(con2).append("\n");

            Constructor con3 = c1.getConstructor(String.class, int.class);
            sb.append("con3").append(con3).append("\n");

            Constructor con4 = c1.getConstructor(String.class, int.class, String.class);
            sb.append("con4").append(con4).append("\n\n");
            tvText.setText(sb.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void reflect() {
        try {
            //1.
            Person p1 = new Person();
            Class c1 = p1.getClass();
            Log.e(TAG, "cl=" + c1);
            sb.append("c1=").append(c1).append("\n");

            Class c2 = Person.class;
            Log.e(TAG, "c2=" + c1);
            sb.append("c2=").append(c2).append("\n");

            Class c3 = Class.forName("com.android.reflect.Person");
            Log.e(TAG, "c3=" + c3);
            sb.append("c3=").append(c3).append("\n\n");

            tvText.setText(sb.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void findView() {
        tvText = findViewById(R.id.tv_text);
    }
}

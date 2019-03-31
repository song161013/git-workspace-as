package com.android.list.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.main.app.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by songfei on 2018/5/22
 * Description：自定义实体类排序
 */

public class ListSortActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnListSort;
    private static final String TAG = "ListSortActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_sort);
        findView();
    }

    private void findView() {
        btnListSort = (Button) findViewById(R.id.btn_collecton_sort);
        btnListSort.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_collecton_sort:
                testAddGoods();
                releaseGoodsSelect();
                break;
        }
    }

    List<ChannelGoodIdCount> list = new ArrayList<>();

    private void testAddGoods() {
        for (int i = 0; i < 13; i++) {
            Random r = new Random();
            ChannelGoodIdCount c = new ChannelGoodIdCount();
            c.setAttr_bar("Attr_bar" + i);
            c.setAttr_id("Attr_id" + i);
            int a = r.nextInt(301) + 600;
            c.setChannel("" + a);
            c.setGood_count(i);
            c.setSuppliers_id("suppliers_id" + i);
            list.add(c);
        }
        Log.e(TAG, "排序前");
        String s1 = "";
        for (ChannelGoodIdCount c : list) {
            s1 += c.getChannel() + ",";
        }
        Log.e(TAG, s1);
    }

    private void releaseGoodsSelect() {
//        1.盒式商品出货：上层先掉，下层后掉。
//        2.盒式吊式同时出：盒式先掉，吊式后掉。
        if (list.size() <= 1) {
            return;
        }
        Collections.sort(list);
        Log.e(TAG, "sort排序后");
        String s = "";
        for (ChannelGoodIdCount c : list) {
            s += c.getChannel() + ",";
        }
        Log.e(TAG, s);
        List<ChannelGoodIdCount> tempList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ChannelGoodIdCount c1 = list.get(i);
            if (Integer.valueOf(c1.getChannel()) <= 600) {
                tempList.add(c1);
            }
        }
        list.removeAll(tempList);
        list.addAll(list.size(), tempList);
        Log.e(TAG, "for循环排序后");
        String s1 = "";
        for (ChannelGoodIdCount c2 : list) {
            s1 += c2.getChannel() + ",";
        }
        Log.e(TAG, s1);
    }
}

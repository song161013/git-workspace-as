package com.android.expandablelistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;


import com.android.expandablelistview.adapter.MyExpandableListViewAdapter_Mobile_goods;
import com.main.app.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewActivity_Mobile_Goods extends Activity {

    private ExpandableListView expandableListView;

    /**
     * 所有分组的所有子项的 GridView 数据集合
     */
    private List<Channel> channels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableList);

        channels = new ArrayList<>();
        //数据
        for (int i = 1; i <= 17; i++) {
            Channel channel = new Channel();
            List<String> numbers = new ArrayList<>();
            int count = 0;
            for (int j = 0; j < 100; j++) {
                count++;
                numbers.add("" + j);
            }
            channel.setGroup("AD-" + i);
            channel.setNumbers(numbers);

            channels.add(channel);
        }

        // 创建适配器
        MyExpandableListViewAdapter_Mobile_goods adapter = new MyExpandableListViewAdapter_Mobile_goods(ExpandableListViewActivity_Mobile_Goods.this,
                channels);
        expandableListView.setAdapter(adapter);
        // 隐藏分组指示器
        expandableListView.setGroupIndicator(null);
        // 默认展开第一组
        expandableListView.expandGroup(0);
    }
}

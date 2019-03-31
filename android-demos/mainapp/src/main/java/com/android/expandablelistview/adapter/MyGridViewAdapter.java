package com.android.expandablelistview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.main.app.R;

import java.util.List;

/**
 * GridView 适配器
 */
public class MyGridViewAdapter extends BaseAdapter {

    private Context mContext;

    /**
     * 每个分组下的每个子项的 GridView 数据集合
     */
    private List<String> itemGridList;

    public MyGridViewAdapter(Context mContext, List<String> itemGridList) {
        this.mContext = mContext;
        this.itemGridList = itemGridList;
    }

    @Override
    public int getCount() {
        return itemGridList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemGridList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_expandablelistview_gridview_item, null);
        }
        TextView tvGridView = (TextView) convertView.findViewById(R.id.tv_gridview);
        tvGridView.setText(itemGridList.get(position));
        return convertView;
    }
}

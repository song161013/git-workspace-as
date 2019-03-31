package com.android.expandablelistview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.expandablelistview.Channel;
import com.main.app.R;

import java.util.List;


/**
 * ExpandableListView 适配器
 */
public class MyExpandableListViewAdapter_Mobile_goods extends BaseExpandableListAdapter {

    private Context mContext;


    /**
     * 所有分组的所有子项的 GridView 数据集合
     */
    private List<Channel> itemChannel;

    private GridView gridView;

    public MyExpandableListViewAdapter_Mobile_goods(Context context, List<Channel> itemChannel/*, List<String> childItem*/) {
        mContext = context;
        this.itemChannel = itemChannel;
    }

    @Override
    public int getGroupCount() {
        return itemChannel.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return itemChannel.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemChannel.get(groupPosition).getNumbers();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
            parent) {
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_expandablelistview_group, null);
        }
        ImageView ivGroup = (ImageView) convertView.findViewById(R.id.iv_group);
        TextView tvGroup = (TextView) convertView.findViewById(R.id.tv_group);
        // 如果是展开状态，就显示展开的箭头，否则，显示折叠的箭头
        if (isExpanded) {
            ivGroup.setImageResource(R.mipmap.expandable_ic_open);
        } else {
            ivGroup.setImageResource(R.mipmap.expandable_ic_close);
        }
        // 设置分组组名
        tvGroup.setText(itemChannel.get(groupPosition).getGroup());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View
            convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = View.inflate(mContext, R.layout.item_expandablelistview_item, null);
        }
        // 因为 convertView 的布局就是一个 GridView，
        // 所以可以向下转型为 GridView
        gridView = (GridView) convertView;
        // 创建 GridView 适配器
        MyGridViewAdapter gridViewAdapter = new MyGridViewAdapter(mContext, itemChannel.get(groupPosition).getNumbers());
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "点击了第" + (groupPosition + 1) + "组，第" +
                        (position + 1) + "项", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

package com.android.socket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.main.app.R;

import java.util.List;

/**
 * Created by 别乱动 on 2017/11/24.
 */

public class ListAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ListAdapter(Context context, List<String> list) {
        this.mList = list;
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold;
        if (view == null) {
            viewHold = new ViewHold();
            view = mInflater.inflate(R.layout.item_socket_listview, null);
            viewHold.tvContent = view.findViewById(R.id.tv_content);

            view.setTag(viewHold);
        } else {
            viewHold = (ViewHold) view.getTag();
        }
        String content = mList.get(i);
        viewHold.tvContent.setText(content);
        return view;
    }

    private static class ViewHold {
        TextView tvContent;
    }
}

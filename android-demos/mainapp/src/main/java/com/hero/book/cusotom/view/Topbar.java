package com.hero.book.cusotom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.main.app.R;

/**
 * Created by songfei on 2018/7/16
 * Description：
 */
public class Topbar extends RelativeLayout {
    private Context mContext;

    public Topbar(Context context) {
        super(context);
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Topbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void getType(Context context, AttributeSet attrs) {
        TypedArray toolBar = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
        toolBar.getString(R.styleable.ToolBar_title);
        toolBar.getDimension(R.styleable.ToolBar_titleSize, 0);
        toolBar.getColor(R.styleable.ToolBar_titleTextColor, 0);
        toolBar.getDrawable(R.styleable.ToolBar_leftBackground);
        toolBar.recycle();
    }
}

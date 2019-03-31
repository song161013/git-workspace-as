package com.custom.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.main.app.R;

/**
 * Created by songfei on 2018/9/18
 * Description：货道背景
 */
public class HuodaoBGActivity extends Activity {
    BuhuoTextView tvBuhuo, tvBuhuo1, tvBuhuo2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huodao_bg);
        findView();
        initData();
    }

    private void findView() {
        tvBuhuo = findViewById(R.id.tv_huodao);
        tvBuhuo1 = findViewById(R.id.tv_huodao1);
        tvBuhuo2 = findViewById(R.id.tv_huodao2);
    }

    private void initData() {
        tvBuhuo.setNum(1);
        tvBuhuo.setBG(R.color.gray);

        tvBuhuo1.setNum(1);
        tvBuhuo1.setBG(R.color.colorRed);

        tvBuhuo2.setNum(1);
        tvBuhuo2.setDraw(true);
        tvBuhuo2.setBG(R.color.transpraent_green);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        tvBuhuo2.setDrawable(bitmap);
        if (bitmap.isRecycled()) {
            bitmap.recycle();
        }

    }
}

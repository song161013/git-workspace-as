package com.android.glide;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.main.app.R;

/**
 * Created by songfei on 2018/6/12
 * Description：Glide框架学习
 */

public class GlideActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "GlideActivity";
    private ImageView mIvShowImage;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        findView();
        initDate();
    }

    private void initDate() {
        mContext = this;
    }

    private void findView() {
        Button btnLoad = (Button) findViewById(R.id.btn_glide_loadinamge);
        btnLoad.setOnClickListener(this);
        mIvShowImage = (ImageView) findViewById(R.id.iv_show_imange);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_glide_loadinamge: {
                showImageOnClick();
                break;
            }
        }
    }

    private void showImageOnClick() {
        String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String giturl = "http://p1.pstatp.com/large/166200019850062839d3";
//      Glide.with(mContext).load(url).into(mIvShowImage);
        //占位图功能
        //Glide.with(mContext).load(url).placeholder(R.drawable.permission_ic_phone).into(mIvShowImage);
        //清除缓存，然后加载图片
        Glide.with(GlideActivity.this)
                .load(giturl)
                .asBitmap()    //只加载g静态图片，默认情况下，gif和静态图都会加载。
                /*.asGif()*/  //只加载gif格式的图片
                .placeholder(R.drawable.permission_ic_phone)  //图片占位
                .diskCacheStrategy(DiskCacheStrategy.NONE)   // //清除缓存，然后加载图片
                .error(R.drawable.bg_btn_normal_supplement_line_selector)//异常占位图，图片加载失败显示的图片
                .override(100, 100)//指定加载图片的大小
                .into(mIvShowImage);
    }
}

package com.android.designpatten;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.main.app.R;

/**
 * Created by songfei on 2018/6/8
 * Description：
 */

public class DesignPatternActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designpatten);
        findView();
    }

    private void findView() {
        Button btnCelue = (Button) findViewById(R.id.btn_design_pattern_celue);
        btnCelue.setOnClickListener(this);

        Button btnGongchang = (Button) findViewById(R.id.btn_pattern_jiandangongchang);
        btnGongchang.setOnClickListener(this);

        Button btnProxy = (Button) findViewById(R.id.btn_design_pattern_proxy);
        btnProxy.setOnClickListener(this);

        //装饰模式
        Button btnDecorator = (Button) findViewById(R.id.btn_design_pattern_decorator);
        btnDecorator.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //设计模式
            case R.id.btn_pattern_jiandangongchang: {
                Intent intent = new Intent(DesignPatternActivity.this, JiandanGongchangPattenActivity.class);
                startActivity(intent);
                break;
            }
            //策略模式
            case R.id.btn_design_pattern_celue: {
                Intent intent = new Intent(DesignPatternActivity.this, CeLuePattenActivity.class);
                startActivity(intent);
                break;
            }
            //代理模式
            case R.id.btn_design_pattern_proxy: {
                Intent intent = new Intent(DesignPatternActivity.this, ProxyActivity.class);
                startActivity(intent);
                break;
            }

            //装饰模式
            case R.id.btn_design_pattern_decorator: {
                Intent intent = new Intent(DesignPatternActivity.this, DecoratorActivity.class);
                startActivity(intent);
                break;
            }

        }
    }
}

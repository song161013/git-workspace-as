package com.android.designpatten;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.main.app.R;

/**
 * Created by songfei on 2018/9/29
 * Description：
 */
public class ProxyActivity extends AppCompatActivity {
    private TextView tvReuslt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designpatten_proxy);
        findView();
        initData();
    }

    private void initData() {
        ProxySchoolGirl girl = new ProxySchoolGirl();
        girl.setName("哈哈哈");
        ProxyProxy proxy = new ProxyProxy(girl);

        showResult(proxy.giveBird());
        showResult(proxy.giveDolls());
        showResult(proxy.giveFlower());

    }

    private void findView() {
        tvReuslt = findViewById(R.id.tv_result);
    }

    private void showResult(String content) {
        String txt = tvReuslt.getText().toString();

        StringBuilder sb = new StringBuilder();
        sb.append(txt);
        sb.append("\n");
        sb.append(content);
        tvReuslt.setText(sb.toString());
    }
}

package com.android.designpatten;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.main.app.R;

import org.w3c.dom.Text;

/**
 * Created by songfei on 2018/10/15
 * Description：装饰模式
 */
public class DecoratorActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvShow;
    private Button btnDecorator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designpattern_decorator);
        findView();
    }

    private void findView() {
        tvShow = findViewById(R.id.tv_show);
        btnDecorator = findViewById(R.id.btn_decorator);
        btnDecorator.setOnClickListener(this);
    }

    private void showText(String newText) {
        String old = tvShow.getText().toString();
        StringBuilder sb = new StringBuilder();
        sb.append(old);
        sb.append("\n");
        sb.append(newText);
        tvShow.setText(sb.toString());
    }

    @Override
    public void onClick(View v) {
        tvShow.setText("第一版");
        DecoratorPerson per = new DecoratorPerson("小明");
        showText(per.getName() + per.wearMaoyi());
        showText(per.getName() + per.wearqiuku());

        tvShow.setText("第二版======================");
        DecoratorFuzhuang waitao = new DecoratorWaitao();
        DecoratorPerson p2 = new DecoratorPerson("笑趴");
        tvShow.setText(waitao.show());
        tvShow.setText(p2.show());

    }
}

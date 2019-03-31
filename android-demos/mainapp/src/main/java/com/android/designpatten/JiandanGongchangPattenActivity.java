package com.android.designpatten;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.main.app.R;

/**
 * Created by songfei on 2018/5/25
 * Description：简单工厂模式
 * 1.界面逻辑与业务逻辑分开，降低耦合度
 */

public class JiandanGongchangPattenActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvResult;
    private EditText etOption, etNumberA, etNumberB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designpatten_gongchang);
        findView();
    }

    private void findView() {
        etOption = (EditText) findViewById(R.id.et_optioner);

        etNumberA = (EditText) findViewById(R.id.et_naumber_a);
        etNumberB = (EditText) findViewById(R.id.et_naumber_b);

        tvResult = (TextView) findViewById(R.id.tv_result);

        Button btnCalc = (Button) findViewById(R.id.btn_cal);
        btnCalc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cal: {
                gongChangPattern();
                break;
            }
        }
    }

    /**
     * 工厂模式
     */
    private void gongChangPattern() {
        Operation oper = OperationFactory.createOption(etOption.getText().toString());
        oper.setNumberA(Double.parseDouble(etNumberA.getText().toString()));
        oper.setNumberB(Double.parseDouble(etNumberB.getText().toString()));
        tvResult.setText(oper.getResutl() + "");
    }
}

package com.android.switchbtn;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.main.app.R;

/**
 * Created by songfei on 2018/10/9
 * Description：
 */
public class SwitchBtnActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private Switch swtichBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swtich_btn);
        findView();
    }

    @TargetApi(21)
    private void findView() {
        swtichBtn = findViewById(R.id.switch_btn);
        swtichBtn.setShowText(true);
        swtichBtn.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            swtichBtn.setSwitchTextAppearance(SwitchBtnActivity.this, R.style.s_true);
        } else {
            swtichBtn.setSwitchTextAppearance(SwitchBtnActivity.this, R.style.s_false);
        }
    }
}

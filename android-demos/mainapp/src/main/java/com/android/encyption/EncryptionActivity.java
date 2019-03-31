package com.android.encyption;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.main.app.R;


/**
 * Created by 别乱动 on 2017/12/5.
 */

public class EncryptionActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "EncryptionActivity";
    private Button btnEncrypt, btnDencrypt;
    private TextView tvEncrypt, tvDencrypt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);
        findView();
    }

    private void findView() {
        tvDencrypt = findViewById(R.id.tv_dncrypt);
        tvEncrypt = findViewById(R.id.tv_encrypt);

        btnEncrypt = findViewById(R.id.btn_encrypt);
        btnDencrypt = findViewById(R.id.btn_dencrypt);

        btnDencrypt.setOnClickListener(this);
        btnEncrypt.setOnClickListener(this);
    }

    String encryStr = null;
    String secretKey = null;

    @Override
    public void onClick(View view) {
        String originalText = "jsons丰拓数据";
        switch (view.getId()) {
            //生成一个动态key
            case R.id.btn_encrypt: //加密
                secretKey = AES.generateKey();
                // 调用AES加密方法
                encryStr = AES.encrypt(secretKey, originalText);
                tvDencrypt.setText("解密结果," + encryStr);
                Log.e(TAG, "加密结果：" + encryStr);
                break;

            case R.id.btn_dencrypt:
                //AES解密
                String decryStr = AES.decrypt(secretKey, encryStr);
                tvDencrypt.setText("解密结果," + decryStr);
                Log.e(TAG, "解密结果：" + decryStr);
                break;

        }
    }
}

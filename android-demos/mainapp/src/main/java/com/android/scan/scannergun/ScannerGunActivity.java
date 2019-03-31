package com.android.scan.scannergun;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.main.app.R;

/**
 * Created by songfei on 2018/5/12
 * Description：
 */

public class ScannerGunActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_gun);
        ScannerGunService service = new ScannerGunService();
    }
}

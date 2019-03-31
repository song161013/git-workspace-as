package com.android.scan.scannergun;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.chice.scangun.ScanGun;

/**
 * Created by songfei on 2018/5/12
 * Description：
 */

public class ScannerGunService extends AccessibilityService {
    final static String TAG = "ScannerGunService";
    private ScanGun mScannerGun = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub

    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            int keyCode = event.getKeyCode();
            if (keyCode <= 6) {
                return false;
            }
            if (mScannerGun.isMaybeScanning(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyEvent(event);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        mScannerGun = new ScanGun(new ScanGun.ScanGunCallBack() {

            @Override
            public void onScanFinish(String scanResult) {
                if (!TextUtils.isEmpty(scanResult)) {
                    Toast.makeText(ScannerGunService.this.getBaseContext(), "无界面监听扫描枪数据:" + scanResult, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        mScannerGun.setMaxKeysInterval(50);
        super.onCreate();
    }

}

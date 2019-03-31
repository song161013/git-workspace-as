package com.android.zxingsample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.google.zxing.client.result.TextParsedResult;
import com.main.app.R;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import com.mylhyl.zxing.scanner.common.Scanner;

/**
 * Created by songfei on 2018/5/22
 * Description：
 */

public class ZxingScanActivity extends AppCompatActivity implements OnScannerCompletionListener {
    private ScannerView mScannerView;
    private Result mLastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing_scan);
        findView();
        initScannerView();
    }

    private void initScannerView() {
        mScannerView.setMediaResId(R.raw.beep);//设置扫描成功的声音
        mScannerView.setDrawText("将二维码放入框内", true);
        mScannerView.setDrawTextColor(Color.RED);

//        if (scanMode == 1) {
//            //二维码
//            mScannerView.setScanMode(Scanner.ScanMode.QR_CODE_MODE);
//        } else if (scanMode == 2) {
//            //一维码
//            mScannerView.setScanMode(Scanner.ScanMode.PRODUCT_MODE);
//        }

        //显示扫描成功后的缩略图
//        mScannerView.isShowResThumbnail(showThumbnail);
//        //全屏识别
//        mScannerView.isScanFullScreen(extras.getBoolean(EXTRA_SCAN_FULL_SCREEN));
//        //隐藏扫描框
//        mScannerView.isHideLaserFrame(extras.getBoolean(EXTRA_HIDE_LASER_FRAME));
//        mScannerView.isScanInvert(true);//扫描反色二维码
//        mScannerView.setCameraFacing(CameraFacing.FRONT);
//        mScannerView.setLaserMoveSpeed(1);//速度

//        mScannerView.setLaserFrameTopMargin(100);//扫描框与屏幕上方距离
//        mScannerView.setLaserFrameSize(400, 400);//扫描框大小
//        mScannerView.setLaserFrameCornerLength(25);//设置4角长度
//        mScannerView.setLaserLineHeight(5);//设置扫描线高度
        mScannerView.setLaserLineResId(R.mipmap.wx_scan_line);//线图
        mScannerView.setLaserColor(Color.RED);
    }


    private void findView() {
        mScannerView = (ScannerView) findViewById(R.id.scanner_view);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        mScannerView.setOnScannerCompletionListener(this);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mScannerView.toggleLight(isChecked);
            }
        });
    }

    public static final String TAG = "ScanActivity";

    @Override
    public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
        if (rawResult == null) {
            Toast.makeText(this, "未发现二维码", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        final Bundle bundle = new Bundle();
        final ParsedResultType type = parsedResult.getType();
        Log.i(TAG, "ParsedResultType: " + type);
        switch (type) {
            case TEXT: {
                TextParsedResult textParsedResult = (TextParsedResult) parsedResult;
                bundle.putString(Scanner.Scan.RESULT, textParsedResult.getText());
                break;
            }
        }
//        showProgressDialog();
        onReturnScanResult(rawResult);
    }

    private void onReturnScanResult(Result rawResult) {
        Intent intent = getIntent();
        intent.putExtra(Scanner.Scan.RESULT, rawResult.getText());
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mLastResult != null) {
                    restartPreviewAfterDelay(0L);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void restartPreviewAfterDelay(long delayMS) {
        mScannerView.restartPreviewAfterDelay(delayMS);
        resetStatusView();
    }

    private void resetStatusView() {
        mLastResult = null;
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }
}

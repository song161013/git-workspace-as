package com.android.designpatten;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.main.app.MainApplication;
import com.main.app.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by songfei on 2018/5/28
 * Description：策略模式
 */

public class CeLuePattenActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CeLuePattenActivity";
    private EditText etPrice, etNumber;
    private TextView tvReslutl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designpatten_celue);
        findView();
    }

    private void findView() {
        etPrice = (EditText) findViewById(R.id.et_price);
        etNumber = (EditText) findViewById(R.id.et_number);
        tvReslutl = (TextView) findViewById(R.id.tv_result);

        Button btnCal = (Button) findViewById(R.id.btn_cal);
        Button btnReset = (Button) findViewById(R.id.btn_reset);
        Button btnCelue = (Button) findViewById(R.id.btn_celue);

        btnCelue.setOnClickListener(this);
        btnCal.setOnClickListener(this);
        btnReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cal: {
                String strNum = etNumber.getText().toString();
                String strPrice = etPrice.getText().toString();
                double resutl = Double.parseDouble(strNum) * Double.parseDouble(strPrice);
                tvReslutl.setText("结果=" + String.valueOf(resutl));
                break;
            }
            case R.id.btn_reset: {
                reset();
                break;
            }
            case R.id.btn_celue: {
                permission();

                CeLueContext context = new CeLueContext(new CeLuoSuanfaA());
                context.ContextInterface();

                CeLueContext contextB = new CeLueContext(new CeLuoSuanfaB());
                contextB.ContextInterface();

                String result = celuoPatten();
                if (result != null) {
                    tvReslutl.setText("\n结果=" + result);
                }
                break;
            }
        }
    }

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;

    private void permission() {
        try {
            mPermissionList.clear();
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(MainApplication.getInstance(), permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
                Log.e(TAG, "已经授权");
            } else {//请求权限方法
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "AA permission " + e);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("权限已申请");
            } else {
                showToast("权限已拒绝");
            }
        } else if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                    if (showRequestPermission) {
                        showToast("权限未申请");
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    private String celuoPatten() {
        String packname = MainApplication.getInstance().getPackageName();

        String sdPath = Environment.getExternalStorageDirectory().getPath();
        String sperator = File.separator;
        File dir = new File(sdPath, "appLog" + sperator + packname);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Date curDate = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月", new Locale("zh"));
        String time = format.format(curDate);

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", new Locale("zh"));
        String time1 = format1.format(curDate);

        File file = new File(dir, time + ".trace");
        try {
            FileReader read = new FileReader(file);
            char buff[] = new char[1024];
            int len = 0;
            StringBuilder result = new StringBuilder();
            while ((len = read.read(buff)) != -1) {
                result.append(buff, 0, len);
            }
            return result.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void reset() {
        etPrice.setText("");
        etNumber.setText("");
    }
}

package com.main.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.android.db.DBActivity;
import com.android.designpatten.DesignPatternActivity;
import com.android.encyption.EncryptionActivity;
import com.android.expandablelistview.ExpandableListviewActivity;
import com.android.glide.GlideActivity;
import com.android.hipermission.HipermissionActivity;
import com.android.list.sort.ListSortActivity;
import com.android.permission.PermissionActivity;
import com.android.reflect.ReflectTestActivity;
import com.android.regextest.RegexTestActivity;
import com.android.scan.scannergun.ScannerGunActivity;
import com.android.slide.drawablelayout.SlideDrawablelayoutActivity;
import com.android.switchbtn.SwitchBtnActivity;
import com.android.usb.UsbMainActivity;
import com.android.zxingsample.ZxingActivity;
import com.custom.view.HuodaoBGActivity;
import com.example.synodemo.FirstActivity;
import com.hero.book.cusotom.view.Custom_TVActivity;
import com.hoho.android.usbserial.examples.DeviceListActivity;

/**
 * Created by 别乱动 on 2018/1/26.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "Mainactivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    private void findView() {
        Button btnUsb = findViewById(R.id.btn_usb);
        btnUsb.setOnClickListener(this);

        Button btnRegex = findViewById(R.id.btn_regex);
        btnRegex.setOnClickListener(this);

        Button btnReflect = findViewById(R.id.btn_reflect);
        btnReflect.setOnClickListener(this);

        Button btnScannGun = findViewById(R.id.btn_scann_gun);
        btnScannGun.setOnClickListener(this);

        Button btnThreadPool = findViewById(R.id.btn_thread_pool);
        btnThreadPool.setOnClickListener(this);

        Button btnListSort = findViewById(R.id.btn_list_sort);
        btnListSort.setOnClickListener(this);

        Button btnEncrypt = findViewById(R.id.btn_encrypt);
        btnEncrypt.setOnClickListener(this);

        //条码扫描
        Button btnZxing = findViewById(R.id.btn_zxing_scann);
        btnZxing.setOnClickListener(this);

        Button btnExpandableListView = findViewById(R.id.btn_expandable_listview);
        btnExpandableListView.setOnClickListener(this);

        Button btnFingerprint = findViewById(R.id.btn_fingerprint);
        btnFingerprint.setOnClickListener(this);

        //设计模式
        Button btnDesign = findViewById(R.id.btn_design_pattern);
        btnDesign.setOnClickListener(this);

        Button btnPremissin = findViewById(R.id.btn_permission_test);
        btnPremissin.setOnClickListener(this);

        Button btnPremissin2 = findViewById(R.id.btn_permission_test_2);
        btnPremissin2.setOnClickListener(this);

        //Glide框架
        Button btnGlide = findViewById(R.id.btn_glide);
        btnGlide.setOnClickListener(this);

        //Glide框架
        Button btnDBLitePal = findViewById(R.id.btn_db_lite_pal);
        btnDBLitePal.setOnClickListener(this);

        //自定义view
        Button btnCustomView = findViewById(R.id.btn_cutsom_view);
        btnCustomView.setOnClickListener(this);

        //appshortcut快捷键
        Button btnShortcut = findViewById(R.id.btn_appshortcut);
        btnShortcut.setOnClickListener(this); //appshortcut快捷键
        Button btnJni = findViewById(R.id.btn_jni);
        btnJni.setOnClickListener(this);

        //货道号背景
        Button btnHuodaoBg = findViewById(R.id.btn_huodao_bg);
        btnHuodaoBg.setOnClickListener(this);  //货道号背景

        //switch按钮测试
        Button btnSwitch = findViewById(R.id.btn_switch_btn);
        btnSwitch.setOnClickListener(this);

        //switch按钮测试
        Button btnSlideMenu = findViewById(R.id.btn_slide_menu);
        btnSlideMenu.setOnClickListener(this);

        //otg usb通信
        Button btnOtgUsb = findViewById(R.id.btn_otg_usb);
        btnOtgUsb.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_usb: {
                Intent intent = new Intent(MainActivity.this, UsbMainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_regex: {
                Intent intent = new Intent(MainActivity.this, RegexTestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_reflect: {
                Intent intent = new Intent(MainActivity.this, ReflectTestActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_scann_gun: {
                Intent intent = new Intent(MainActivity.this, ScannerGunActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_thread_pool: {
//                Intent intent = new Intent(MainActivity.this, ThreadPoolActivity.class);
//                startActivity(intent);
                break;
            }
            case R.id.btn_list_sort: {
                Intent intent = new Intent(MainActivity.this, ListSortActivity.class);
                startActivity(intent);
                break;
            }
            //字符串加密、解密
            case R.id.btn_encrypt: {
                Intent intent = new Intent(MainActivity.this, EncryptionActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_zxing_scann: {
                Intent intent = new Intent(MainActivity.this, ZxingActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_expandable_listview: {
                Intent intent = new Intent(MainActivity.this, ExpandableListviewActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_fingerprint: {
                Intent intent = new Intent(MainActivity.this, FirstActivity.class);
                startActivity(intent);
                break;
            }
            //设计模式
            case R.id.btn_design_pattern: {
                Intent intent = new Intent(MainActivity.this, DesignPatternActivity.class);
                startActivity(intent);
                break;
            }

            //权限申请
            case R.id.btn_permission_test: {
                Intent intent = new Intent(MainActivity.this, PermissionActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.btn_permission_test_2: {
                Intent intent = new Intent(MainActivity.this, HipermissionActivity.class);
                startActivity(intent);
                break;
            }

            //Glide框架学习
            case R.id.btn_glide: {
                Intent intent = new Intent(MainActivity.this, GlideActivity.class);
                startActivity(intent);
                break;
            }

            //Litepal数据库框架学习
            case R.id.btn_db_lite_pal: {
                Intent intent = new Intent(MainActivity.this, DBActivity.class);
                startActivity(intent);
                break;
            }

            //自定义view
            case R.id.btn_cutsom_view: {
                Intent intent = new Intent(MainActivity.this, Custom_TVActivity.class);
                startActivity(intent);
                break;
            }

            //appShortcut
            case R.id.btn_appshortcut: {

                break;
            }
            /*共享柜货道背景*/
            case R.id.btn_huodao_bg: {
                Intent intent = new Intent(MainActivity.this, HuodaoBGActivity.class);
                startActivity(intent);
                break;
            }

            /*swtich Btn用法*/
            case R.id.btn_switch_btn: {
                Intent intent = new Intent(MainActivity.this, SwitchBtnActivity.class);
                startActivity(intent);
                break;
            }

            /*drawablelayout侧滑*/
            case R.id.btn_slide_menu: {
                Intent intent = new Intent(MainActivity.this, SlideDrawablelayoutActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_otg_usb: {
                Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}

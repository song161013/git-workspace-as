package com.android.usb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mjdev.libaums.UsbMassStorageDevice;
import com.github.mjdev.libaums.fs.FileSystem;
import com.github.mjdev.libaums.fs.UsbFile;
import com.github.mjdev.libaums.partition.Partition;
import com.main.app.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 别乱动 on 2018/1/26.
 */

public class UsbMainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "UsbMainActivity";
    private TextView tvInfo;
    private Button btnSend, btnRead;

    private UsbLocalManager mUsbLocalManager;
    private Context mContext;
    //需要的设备
    private UsbDevice mUsbDevice;

    private PendingIntent mPermissionIntent;
    private static final String ACTION_USB_PERMISSION = "com.androd.usb.USB_PERMISSION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb_main);
        findView();
        initUsbManager();
    }

    private void findView() {
        tvInfo = findViewById(R.id.tv_info);
        btnSend = findViewById(R.id.btn_send);
        btnRead = findViewById(R.id.btn_read);
        btnRead.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }

    /**
     * 启动usb后台服务
     */
    private void initUsbManager() {
        mUsbLocalManager = new UsbLocalManager(this);
    }

    public void unRegisterMessageReceiver() {
        unregisterReceiver(mUsbReceiver);
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                List<UsbDevice> devices = mUsbLocalManager.enumerateDevice();
                if (devices == null) {
                    toastShow("无法检测到设备，请重新插入");
                    return;
                }
                toastShow("插入设备！");
                for (int i = 0; i < devices.size(); i++) {
                    UsbDevice device = devices.get(i);
                    if (device.getVendorId() == 2385 && device.getProductId() == 5734) {
                        toastShow("找到设备！");
                        Log.i(TAG, "AA 找到设备了");
                        mUsbDevice = device;
                        UsbManager usbManager = mUsbLocalManager.getUsbManager();
                        if (usbManager != null) {
                            usbManager.requestPermission(device, mPermissionIntent);
                        }
                    }
                }
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                List<UsbDevice> devices = mUsbLocalManager.enumerateDevice();
                if (devices == null) {
                    toastShow("无法检测到设备，请重新插入");
                    //return;
                }
                toastShow("拔掉设备！");
            } else if (ACTION_USB_PERMISSION.equals(intent.getAction())) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    boolean isPermission = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false);
                    boolean isPed = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, true);
                    if (isPermission) {
                        if (device != null) {
                            Log.d(TAG, "AA permission gainted for device " + device.getProductId());
                        }
                    } else {
                        Log.d(TAG, "AA permission denied for device " + device.getProductId());
                    }
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        intentFilter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, intentFilter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterMessageReceiver();
    }

    private void toastShow(String msg) {
        Toast.makeText(UsbMainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void toastShow(int resId) {
        String msg = getResources().getString(resId);
        toastShow(msg);
    }

    private UsbDeviceConnection mDeviceConnection;

    // 打开设备
    public void openDevice(UsbInterface mInterface) {
        if (mInterface != null) {
            UsbDeviceConnection conn = null;
            // 在open前判断是否有连接权限；对于连接权限可以静态分配，也可以动态分配权限
            if (mUsbLocalManager.getUsbManager().hasPermission(mUsbDevice)) {
                conn = mUsbLocalManager.getUsbManager().openDevice(mUsbDevice);
            }
            if (conn == null) {
                Log.i(TAG, "AA conn is null,open设备失败！");
                return;
            }
            if (conn.claimInterface(mInterface, true)) {
                mDeviceConnection = conn;
                if (mDeviceConnection != null)// 到此你的android设备已经连上zigbee设备
                    Log.i(TAG, "AA open设备成功！");
                final String mySerial = mDeviceConnection.getSerial();
                Log.i(TAG, "设备serial number：" + mySerial);
            } else {
                Log.i(TAG, "无法打开连接通道。");
                conn.close();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send: {
                if (mUsbDevice != null) {
                    UsbInterface usbInterface = mUsbDevice.getInterface(0);
                    UsbManager usbManager = mUsbLocalManager.getUsbManager();
                    //USBEndpoint为读写数据所需的节点
                    UsbEndpoint inEndpoint = usbInterface.getEndpoint(0);  //读数据节点
                    UsbEndpoint outEndpoint = usbInterface.getEndpoint(1); //写数据节点
                    UsbDeviceConnection connection = usbManager.openDevice(mUsbDevice);
                    connection.claimInterface(usbInterface, true);

                    byte[] cmd = "123".getBytes();
                    //发送数据
                    byte[] byte2 = new byte[64];
                    int out = connection.bulkTransfer(outEndpoint, cmd, cmd.length, 3000);

                    //读取数据1   两种方法读取数据
                    int ret = connection.bulkTransfer(inEndpoint, byte2, byte2.length, 3000);
                    Log.e("ret", "ret:" + ret);
                    for (Byte byte1 : byte2) {
                        Log.e(TAG, "AA" + byte1.toString());
                        tvInfo.setText(" " + byte1.toString());
                    }
                }
                break;
            }
            case R.id.btn_read: {
                UsbMassStorageDevice massDevices[] = UsbMassStorageDevice.getMassStorageDevices(this);
                for (UsbMassStorageDevice massDevice : massDevices) {
                    if(massDevice.getUsbDevice().getProductId()==5734&&massDevice.getUsbDevice().getVendorId()==2385){
                        read(massDevice);
                    }
                }
                break;
            }
        }
    }

    private void read(UsbMassStorageDevice massDevice) {
        // before interacting with a device you need to call init()!
        try {
            massDevice.init();//初始化
            //Only uses the first partition on the device
            Partition partition = massDevice.getPartitions().get(0);
            FileSystem currentFs = partition.getFileSystem();
            //fileSystem.getVolumeLabel()可以获取到设备的标识
            //通过FileSystem可以获取当前U盘的一些存储信息，包括剩余空间大小，容量等等
            Log.d(TAG, "Capacity: " + currentFs.getCapacity());
            Log.d(TAG, "Occupied Space: " + currentFs.getOccupiedSpace());
            Log.d(TAG, "Free Space: " + currentFs.getFreeSpace());
            Log.d(TAG, "Chunk size: " + currentFs.getChunkSize());
            UsbFile root = currentFs.getRootDirectory();
        } catch (Exception e) {
            e.printStackTrace();
            toastShow("读取失败");
        }
    }
}

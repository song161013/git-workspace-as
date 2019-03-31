package com.android.usb;

import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by songfei on 2018/3/30
 * Description: USB管理类
 */

public class UsbLocalManager {
    public UsbLocalManager(Context context) {
        this.mContext = context;
        this.mUsbManager = (UsbManager) mContext.getSystemService(Context.USB_SERVICE);
    }

    private Context mContext;
    private UsbManager mUsbManager;
    private UsbDevice mUsbDevice;
    private final String TAG = "UsbServer";

    private int VendorID;
    private int ProductID;

    public UsbManager getUsbManager() {
        if (mUsbManager != null) {
            return mUsbManager;
        } else {
            return null;
        }

    }

    // 枚举设备函数
    public List<UsbDevice> enumerateDevice() {
        List<UsbDevice> devices = new ArrayList<>();
        Log.i(TAG, "开始进行枚举设备!");
        if (mUsbManager == null) {
            Log.e(TAG, "创建UsbManager失败，请重新启动应用！");
        } else {
            HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
            if (!(deviceList.isEmpty())) {
                Log.i(TAG, "deviceList is not null!");
                Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
                while (deviceIterator.hasNext()) {
                    UsbDevice device = deviceIterator.next();
                    devices.add(device);
                    //输出设备信息
                    Log.i(TAG, "AA DeviceInfo: VendorId=" + device.getVendorId() + " , ProductId=" + device.getProductId());
//                    // 保存设备VID和PID
//                    VendorID = device.getVendorId();
//                    ProductID = device.getProductId();
//                    mUsbDevice = device;
                }
            } else {
                Log.e(TAG, "请连接USB设备至PAD！");
                Toast.makeText(mContext, "请连接USB设备至PAD！", Toast.LENGTH_SHORT).show();
            }
        }
        return devices;
    }

    private UsbInterface Interface1;
    private UsbInterface Interface2;

    // 寻找设备接口
    public void getDeviceInterface(UsbDevice usbDevice) {
        if (usbDevice != null) {
            Log.d(TAG, "interfaceCounts : " + usbDevice.getInterfaceCount());
            for (int i = 0; i < usbDevice.getInterfaceCount(); i++) {
                UsbInterface intf = usbDevice.getInterface(i);
                if (i == 0) {
                    Interface1 = intf; // 保存设备接口
                    Log.i(TAG, "成功获得设备接口:" + Interface1.getId());
                }
                if (i == 1) {
                    Interface2 = intf;
                    Log.i(TAG, "成功获得设备接口:" + Interface2.getId());
                }
            }
        } else {
            Log.i(TAG, "设备为空！");
        }

    }

    private UsbEndpoint epBulkOut;
    private UsbEndpoint epBulkIn;
    private UsbEndpoint epControl;
    private UsbEndpoint epIntEndpointOut;
    private UsbEndpoint epIntEndpointIn;
    private UsbDeviceConnection myDeviceConnection;

    // 分配端点，IN | OUT，即输入输出；可以通过判断
    public UsbEndpoint assignEndpoint(UsbInterface mInterface) {
        for (int i = 0; i < mInterface.getEndpointCount(); i++) {
            UsbEndpoint ep = mInterface.getEndpoint(i);
            // look for bulk endpoint
            if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                    epBulkOut = ep;
                    Log.i(TAG, "Find the BulkEndpointOut," + "index:"
                            + i + "," + "使用端点号："
                            + epBulkOut.getEndpointNumber());
                } else {
                    epBulkIn = ep;
                    Log.i(TAG, "Find the BulkEndpointIn:" + "index:" + i
                            + "," + "使用端点号：" + epBulkIn.getEndpointNumber());
                }
            }
            // look for contorl endpoint
            if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_CONTROL) {
                epControl = ep;
                Log.i(TAG, "find the ControlEndPoint:" + "index:" + i + "," + epControl.getEndpointNumber());
            }
            // look for interrupte endpoint
            if (ep.getType() == UsbConstants.USB_ENDPOINT_XFER_INT) {
                if (ep.getDirection() == UsbConstants.USB_DIR_OUT) {
                    epIntEndpointOut = ep;
                    Log.i(TAG, "find the InterruptEndpointOut:" + "index:" + i + "," + epIntEndpointOut.getEndpointNumber());
                }
                if (ep.getDirection() == UsbConstants.USB_DIR_IN) {
                    epIntEndpointIn = ep;
                    Log.i(TAG, "find the InterruptEndpointIn:" + "index:" + i + "," + epIntEndpointIn.getEndpointNumber());
                }
            }
        }
        if (epBulkOut == null && epBulkIn == null && epControl == null
                && epIntEndpointOut == null && epIntEndpointIn == null) {
            throw new IllegalArgumentException("not endpoint is founded!");
        }
        return epIntEndpointIn;
    }

    // 打开设备
    public void openDevice(UsbInterface mInterface) {
        if (mInterface != null) {
            UsbDeviceConnection conn = null;
            // 在open前判断是否有连接权限；对于连接权限可以静态分配，也可以动态分配权限
            if (mUsbManager.hasPermission(mUsbDevice)) {
                conn = mUsbManager.openDevice(mUsbDevice);
            }
            if (conn == null) {
                return;
            }
            if (conn.claimInterface(mInterface, true)) {
                myDeviceConnection = conn;
                if (myDeviceConnection != null)// 到此你的android设备已经连上zigbee设备
                    Log.i(TAG, "open设备成功！");
                final String mySerial = myDeviceConnection.getSerial();
                Log.i(TAG, "设备serial number：" + mySerial);
            } else {
                Log.i(TAG, "无法打开连接通道。");
                conn.close();
            }
        }
    }
}

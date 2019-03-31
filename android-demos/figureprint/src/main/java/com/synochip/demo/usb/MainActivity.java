package com.synochip.demo.usb;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.synochip.util.Constants;
import com.synochip.util.ukey.OTG_KEY;
import com.synochip.util.ukey.Tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.synochip.util.Constants.INPUT_FINGER_SUCCESS;

//import com.synochip.OTG_Demo.R;
//import com.synochip.demo.OTG_KEY.R;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final String ACTION_USB_PERMISSION = "com.synochip.demo.OTG_DEMO";

    public int threadCnt;

    private static final int MAX_LINES = 12;
    private static final int CNT_LINES = 11;

    private static final int PS_NO_FINGER = 0x02;
    private static final int PS_OK = 0x00;
    private static int CHAR_BUFFER_A = 0x01;
    private static int CHAR_BUFFER_B = 0x02;
    private final static int DEV_ADDR = 0xffffffff;
    private static int fingerCnt = 1;
    private static int IMAGE_X = 256;
    private static int IMAGE_Y = 288;

    public int thread_i = 0;
    public int thread_sum = 0;
    private UsbManager mUsbManager;
    private UsbDevice mDevice;
    private PendingIntent mPermissionIntent;

    private OTG_KEY msyUsbKey;
    int mhKey = 0;
    int mhCon = 0;
    private TextView mResponseTextView;
    private Button mOpen;
    private Button mClose;
    private Button mLogIn;
    private Button mBtnSearch;
    private Button mLogOut;
    private Button mClear;
    private Button mDevMsg;
    private Button mUpImage;
    private String ss = null;
    String imagePath = "finger.bmp";
    byte[] fingerBuf = new byte[IMAGE_X * IMAGE_Y];
    byte[] fingerBuf2 = new byte[IMAGE_X * IMAGE_Y];
    byte[] fingerBufTest = new byte[IMAGE_X * IMAGE_Y];
    //private EditText mLongEdit;
    private Button mInit;
    ProgressBar bar = null;
    boolean ifChecked = false;

    // private EditText mEditText;
    // private EditText mOldPin;
    // private EditText mNewPin;
    ImageView fingerView = null;

    byte mbAppHand[] = new byte[1];
    byte mbConHand[] = new byte[1];
    boolean mIsOpen = true;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mIsOpen = true;
        mHandler = new Handler();
        findView();
        findView_my();
        initDate();

    }

    private void findView_my() {
        Button btnGetImage = findViewById(R.id.btn_get_image);
        btnGetImage.setOnClickListener(this);
        Button btnGetImage2 = findViewById(R.id.btn_get_image_2);
        btnGetImage2.setOnClickListener(this);
    }

    private void initDate() {
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);// 启动服务进程
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);

        openState(true);
        registerReceiver(mUsbReceiver, filter);
    }

    private void findView() {
        mOpen = (Button) findViewById(R.id.BOpen);
        mOpen.setOnClickListener(this);

        mClose = (Button) findViewById(R.id.BClose);
        mClose.setOnClickListener(this);

        mDevMsg = (Button) findViewById(R.id.devMsg);
        mDevMsg.setOnClickListener(this);

        mUpImage = (Button) findViewById(R.id.upImage);
        mUpImage.setOnClickListener(this);

        bar = (ProgressBar) findViewById(R.id.bar);
        fingerView = (ImageView) findViewById(R.id.fingerImage);

        mInit = (Button) findViewById(R.id.BTInit);
        mInit.setOnClickListener(this);

        // mLongEdit = (EditText) findViewById(R.id.ETOldPin);

        mLogIn = (Button) findViewById(R.id.BTLogIn);
        mLogIn.setOnClickListener(this);

        mBtnSearch = (Button) findViewById(R.id.BTLogInTest);
        mBtnSearch.setOnClickListener(this);

        mLogOut = (Button) findViewById(R.id.BTLogOut);
        mLogOut.setOnClickListener(this);

        mResponseTextView = (TextView) findViewById(R.id.TVLog);
        mResponseTextView.setMovementMethod(new ScrollingMovementMethod());
        // mResponseTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        // mResponseTextView.setLines(17);
        mResponseTextView.setMaxLines(MAX_LINES);
        mResponseTextView.setText("");

        // mEditText = (EditText)findViewById(R.id.ETSend);
        // mEditText.addTextChangedListener(mTextWatcher);
    }

    private void showToast(String content) {
        Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_image: {
                new TransferThread(1).start();
                break;
            }
            case R.id.btn_get_image_2: {
                if (msyUsbKey != null) {
                    if (PS_OK != msyUsbKey.PSdeleteGenChar(DEV_ADDR, 0, 1)) {
                        Log.i(TAG, "AA 删除指纹库失败");
                        showToast("删除指纹库失败");
                        return;
                    }
                    Log.i(TAG, "AA 删除指纹库成功");
                    showToast("删除指纹库成功");
                }
                break;
            }
        }
        if (v == mOpen) {
            boolean requested = false;
            for (UsbDevice device : mUsbManager.getDeviceList().values()) {
                if (0x2109 == device.getVendorId() && 0x7638 == device.getProductId()) {
                    mDevice = device;
                    mUsbManager.requestPermission(mDevice, mPermissionIntent);
                    requested = true;
                    logMsg("find this usb device vID:0x2109");
                    break;
                }
                if (0x0453 == device.getVendorId() && 0x9005 == device.getProductId()) {
                    mDevice = device;
                    mUsbManager.requestPermission(mDevice, mPermissionIntent);
                    requested = true;
                    logMsg("find this usb device vID:0x0453");
                    break;
                }
            }

            if (requested) {
                if (!mUsbManager.hasPermission(mDevice)) {
                    logMsg("no Permission!");
                    return;
                }

                try {
                    msyUsbKey = null;
                    msyUsbKey = new OTG_KEY(mUsbManager, mDevice);
                    int key[] = new int[1];
                    int ret = msyUsbKey.UsbOpen();
                    if (ret == OTG_KEY.DEVICE_SUCCESS) {
                        mhKey = key[0];
                        logMsg("open device success hkey :" + Tool.int2HexStr(mhKey));
                    } else {
                        logMsg("open device fail errocde :" + ret);// Tool.int2HexStr(ret));
                        return;
                    }

                } catch (Exception e) {
                    logMsg("Exception: => " + e.toString());
                    return;
                }
                int ret;
                if ((ret = msyUsbKey.PSword()) != 0) {
                    logMsg("密码验证错误" + ret);
                    //logMsg("my return:"+ msyUsbKey.synoprintf());
                    return;
                }
                // logMsg("验证成功");
            } else {
                logMsg("can't find this device!");
                return;
            }
            //获取设备信息
            int[] indexMax = new int[1];
            int[] len = new int[1];
            byte[] index = new byte[256];
            //每次打开是获取设备存储的指纹数量，就是这len
            if (0 != getUserContent(indexMax, index, len)) {
                logMsg("获取设备信息失败");
                return;
            }
            if (len[0] == 0) {
                logMsg("从第" + indexMax[0] + "个ID开始存储指纹");
                fingerCnt = 0;
            } else {
                indexMax[0]++;
                logMsg("从第" + indexMax[0] + "个ID开始存储指纹");
                fingerCnt = indexMax[0];
            }
            openState(false);
        } else if (v == mClose) {
            try {
                // uiState(true);
                logMsg("设备已关闭");
                openState(true);
                msyUsbKey.CloseCard(mhKey);
            } catch (Exception e) {
                logMsg("Exception: => " + e.toString());
                return;
            }

        } else if (v == mClear) {
            mResponseTextView.scrollTo(0, 1);
            mResponseTextView.setText("");
        } else if (v == mInit) { // finger//指纹录入---rgfy7uy
            // logMsg("请放上手指");
            // bar.setVisibility(View.VISIBLE);
            // bar.setProgress(0);
            // handler.post(mGetFingerThread);

            if (fingerCnt >= 256) {
                logMsg("指纹库已满，请删除");
                return;
            }

            bar.setVisibility(View.VISIBLE);
            bar.setProgress(0);

            ImputAsyncTask asyncTask = new ImputAsyncTask();
            asyncTask.execute(1);

        } else if (v == mLogIn) //搜索指纹
        {
            int ret;
            int[] fingerId = new int[1];
            while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
            if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                logMsg("上传图像失败:" + ret);
                return;
            }
            // logMsg("上传图像成功");
            if ((ret = WriteBmp(fingerBuf)) != 0) {
                logMsg("生成bmp文件失败:" + ret);
            }
            // logMsg("图像写入成功");
            String localName = "finger.bmp";
            FileInputStream localStream = null;
            try {
                localStream = openFileInput(localName);
            } catch (FileNotFoundException e) {
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(localStream);
            fingerView.setImageBitmap(bitmap);
            if (msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A) != PS_OK) {
                logMsg("生成特征失败");
                return;
            }
            if (PS_OK != msyUsbKey.PSSearch(DEV_ADDR, CHAR_BUFFER_A, 0, 10, fingerId)) {
                logMsg("没有找到此指纹");
                return;
            }
            logMsg("成功搜索到此指纹,ID===>" + fingerId[0]);
        } else if (v == mBtnSearch) {
            byte[] b = ss.getBytes();
//          byte b[] = Base64.decodeBase64(s);
            byte fingerBuf[] = /*fingerBufTest*/new byte[IMAGE_X * IMAGE_Y];
            System.arraycopy(fingerBuf, 0, fingerBuf, 0, b.length);
//          System.arraycopy(b, 0, fingerBuf, b.length, b.length);

            int ret;
            int[] fingerId = new int[1];
            if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                logMsg("上传图像失败:" + ret);
                return;
            }
            // logMsg("上传图像成功");
            if ((ret = WriteBmp(fingerBuf)) != 0) {
                logMsg("生成bmp文件失败:" + ret);
            }
            // logMsg("图像写入成功");
            String localName = "finger.bmp";
            FileInputStream localStream = null;
            try {
                localStream = openFileInput(localName);
            } catch (FileNotFoundException e) {
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(localStream);
            fingerView.setImageBitmap(bitmap);
            if (msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A) != PS_OK) {
                logMsg("生成特征失败");
                return;
            }
            if (PS_OK != msyUsbKey.PSSearch(DEV_ADDR, CHAR_BUFFER_A, 0, 10, fingerId)) {
                logMsg("没有找到此指纹");
                return;
            }
            logMsg("成功搜索到此指纹,ID===>" + fingerId[0]);
        } else if (v == mLogOut) //清空指纹库
        {
            if (PS_OK != msyUsbKey.PSEmpty(DEV_ADDR)) {
                logMsg("清空指纹库失败");
            }
            fingerCnt = 0;
            logMsg("清空指纹库成功");
        } else if (v == mDevMsg) {//查看设备信息
            int[] indexMax = new int[1];
            int[] len = new int[1];
            byte[] index = new byte[256];
            //每次打开是获取设备存储的指纹数量，就是这len
            if (0 != getUserContent(indexMax, index, len)) {
                logMsg("获取设备信息失败");
                return;
            }
            logMsg("目前设备存储有" + len[0] + "个指纹信息");
            // byte[] temp = new byte[len[0]];
            int i;
            logMsg("他们分别是:");
            for (i = 0; i < len[0]; i++) {
                logMsg("id:" + index[i]);
            }
            if (len[0] != 0) {
                logMsg("最大的ID为：" + indexMax[0]);
            }
            logMsg("获取设备信息成功");
        } else if (v == mUpImage) //上传图像
        {
            int ret;
            // BitmapFactory.Options options = new BitmapFactory.Options();
            // options.inSampleSize = 2;
            // Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
            while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    Log.e(TAG, "mUpImage-->" + e);
                }
            }
            try {
                Thread.sleep(30);
            } catch (Exception e) {
            }

            if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                logMsg("上传图像失败:" + ret);
                return;
            }
            // logMsg("上传图像成功");
            /*
             * byte[] temp = new byte[200]; int i; int j; for( i = 0; i<4 ; i++)
             * { if( i == 0 ) { for( j=0; j<49; j++ ) temp[j] = fingerBuf[j];
             * temp[49] = ','; } else if( i == 1) { for( j=50; j<99; j++ )
             * temp[j] = fingerBuf[8192+j-50]; temp[99] = ','; } else if( i ==
             * 2) { for( j=100; j<149; j++ ) temp[j] = fingerBuf[16384+j-100];
             * temp[149] = ','; } else if( i == 3) { for( j=150; j<199; j++ )
             * temp[j] = fingerBuf[24576+j-150]; temp[199] = ','; } }
             * logMsg("上传图像数据:"+Tool.byte2HexStr(temp, 200));
             */
            if ((ret = WriteBmp(fingerBuf)) != 0) {
                logMsg("生成bmp文件失败:" + ret);
            }
            //logMsg("图像写入成功");
            String localName = "finger.bmp";
            FileInputStream localStream = null;
            try {
                localStream = openFileInput(localName);
            } catch (FileNotFoundException e) {
                return;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(localStream);
            fingerView.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    // 捕获usb的插拔消息
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        // 收到消息
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (device != null) {
                    // call method to set up device communication
                }
                logMsg("Add:  DeviceName:  " + device.getDeviceName()
                        + "  DeviceProtocol: " + device.getDeviceProtocol()
                        + "\n");

            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                logMsg("Del: DeviceName:  " + device.getDeviceName()
                        + "  DeviceProtocol: " + device.getDeviceProtocol()
                        + "\n");
                if (null != msyUsbKey) {
                    msyUsbKey.CloseCard(mhKey);
                    msyUsbKey = null;
                }
            }
        }
    };

    public synchronized void logMsg(String msg) {
        String oldMsg = mResponseTextView.getText().toString();

        mResponseTextView.setText(oldMsg + "\n" + msg);
        new myAsyncTask().execute();

    }

    /*
     * private final TextWatcher mTextWatcher = new TextWatcher() { public void
     * beforeTextChanged(CharSequence s, int start, int count, int after) { }
     *
     * public void onTextChanged(CharSequence s, int start, int before, int
     * count) { }
     *
     * public void afterTextChanged(Editable s) { if (s.length() > 0) { int pos
     * = s.length() - 1; char c = s.charAt(pos); if ( !( c >= '0'&&c <='9'|| c
     * >= 'a'&&c <='f' || c >= 'A'&&c <='F')) { s.delete(pos,pos+1);
     * Toast.makeText(MainActivity.this,
     * "Error letter.",Toast.LENGTH_SHORT).show(); } } } };
     */
    @Override
    protected void onDestroy() {
        unregisterReceiver(mUsbReceiver);
        mIsOpen = false;
        super.onDestroy();
    }

    private class myAsyncTask extends AsyncTask<Void, Void, Void> {

        protected void onPostExecute(final Void result) {
            super.onPostExecute(result);
            if (mResponseTextView.getLineCount() > CNT_LINES) {
                // Toast.makeText(getApplicationContext(),
                // "LINE:"+mResponseTextView.getLineCount()+" CNT",
                // Toast.LENGTH_SHORT).show();
                mResponseTextView.scrollTo(0,
                        (mResponseTextView.getLineCount() - CNT_LINES)
                                * mResponseTextView.getLineHeight() + 5);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }

    // imput finger thread
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            bar.setProgress(msg.arg1);
            if (0 == msg.arg1) {
                handler.post(mGetFingerThread);
            } else if (50 == msg.arg1) {
                logMsg("第一枚手指获取成功");
                logMsg("请放入第二枚手指");

                int ret;
                if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                    logMsg("上传图像失败:" + ret);
                }
                // logMsg("上传图像成功");
                if ((ret = WriteBmp(fingerBuf)) != 0) {
                    logMsg("生成bmp文件失败:" + ret);
                }
                // logMsg("图像写入成功");
                String localName = "finger.bmp";
                FileInputStream localStream = null;
                try {
                    localStream = openFileInput(localName);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                fingerView.setImageBitmap(bitmap);

                handler.post(mGetFingerThread);
            } else if (100 == msg.arg1) {
                logMsg("第二枚手指获取成功");
                logMsg("指纹存储成功");
            }
        }
    };

    public class ImputAsyncTask extends AsyncTask<Integer, String, Integer> {
        byte[][] fingerFeature = new byte[6][512];
        int Progress = 0;

        @Override
        protected Integer doInBackground(Integer... params) {
            int cnt = 1;
            while (true) {
                int ret;
                while (msyUsbKey.PSGetImage(DEV_ADDR) != PS_NO_FINGER) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }

                if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                    //publishProgress("上传图像失败:"+ret);
                    continue;
                }
                //logMsg("上传图像成功");
                if ((ret = WriteBmp(fingerBuf)) != 0) {
                    //publishProgress("生成bmp文件失败:"+ret);
                    continue;
                }
                //logMsg("图像写入成功");
                publishProgress("OK");

                //生成模板
                if (cnt == 1) {
                    if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A)) != PS_OK) {
                        publishProgress("指纹1生成特征失败:" + ret);
                        continue;
                    } else {
                        publishProgress("请再次放上手指");
                        //publishProgress("生成模板失败");
                    }
                }

                if (cnt == 2) {
                    if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_B)) != PS_OK) {
                        publishProgress("指纹2生成特征失败:" + ret);
                        continue;
                    } else {
                        //publishProgress("生成模板失败");
                    }
                    if (msyUsbKey.PSRegModule(DEV_ADDR) != PS_OK) {
                        bar.setProgress(0);
                        thread_i = 0;
                        thread_sum = 0;
                        publishProgress("生成模板失败，请重新录入");
                        handler.removeCallbacks(mGetFingerThread);
                        continue;
                    }
                    //publishProgress("生成模板成功");
                    //need to fix the 1
                    if (fingerCnt >= 256) {
                        publishProgress("模板存满，请删除部分指纹信息");
                    }
                    if (msyUsbKey.PSStoreChar(DEV_ADDR, 1, fingerCnt) != PS_OK) {
                        bar.setProgress(0);
                        thread_i = 0;
                        thread_sum = 0;
                        publishProgress("存储特征失败,请重新录入");
                        handler.removeCallbacks(mGetFingerThread);
                        continue;
                    }
                    publishProgress("录入指纹成功,=====>ID:" + fingerCnt);
                    return 0;
                }
                cnt++;
            }
            //publishProgress("指纹录入成功");
        }

        // 线程结束
        protected void onPostExecute(Integer result) {
            int i;
            if (fingerCnt > 256) {
                logMsg("指纹存储256个手指已满，请删除指纹数据");
                return;
            }
            // logMsg("录入指纹成功");
            // logMsg("fingerFeature[0]:"+Base64.encodeToString(fingerFeature[0],
            // Base64.DEFAULT ));
            fingerCnt++;
            bar.setProgress(100);
        }

        // 线程开始
        protected void onPreExecute() {
            logMsg("录入指纹=>开始,请放上手指");
        }

        // 线程中间状态
        protected void onProgressUpdate(String... values) {

            if (values[0].equals("OK")) {
                String localName = "finger.bmp";
                FileInputStream localStream = null;
                try {
                    localStream = openFileInput(localName);
                } catch (FileNotFoundException e) {
                    return;
                }
                Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                fingerView.setImageBitmap(bitmap);
                Progress += 50;
                bar.setProgress(Progress);

                return;
            }
            logMsg(values[0]);
            // Toast.makeText(getApplicationContext(), values[0],
            // Toast.LENGTH_SHORT).show();
        }
    }

    Runnable mGetFingerThread = new Runnable() {

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            int ret;
            if (thread_i == 0) {
                msg.arg1 = 0;
                handler.sendMessage(msg);
            } else if (thread_i == 1) {
                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
                try {
                    Thread.sleep(30);
                } catch (Exception e) {
                }
                if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A)) != PS_OK) {
                    logMsg("指纹1生成特征失败:" + ret);
                } else {
                    logMsg("指纹1生成特征成功");
                }

                thread_sum += 50;
                msg.arg1 = thread_sum;
                handler.sendMessage(msg);
            } else if (thread_i == 2) {
                while (true) {
                    if (msyUsbKey.PSGetImage(DEV_ADDR) != PS_NO_FINGER) {
                        break;
                    }
                    try {
                        Thread.sleep(30);
                    } catch (Exception e) {

                    }
                }
                logMsg("请第二次放入相同手指");

                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    try {
                        Thread.sleep(30);
                    } catch (Exception e) {
                    }
                }
                logMsg("获取第二个指纹成功");
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
                if (msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_B) != PS_OK) {
                    bar.setProgress(0);
                    thread_i = 0;
                    thread_sum = 0;
                    logMsg("第二枚指纹获取特征失败，请重新录入");
                    handler.removeCallbacks(mGetFingerThread);
                    return;
                } else {
                    logMsg("第二枚指纹获取特征成功");
                }
                thread_sum += 50;
                msg.arg1 = thread_sum;
                // handler.sendMessage(msg);
                if (msyUsbKey.PSRegModule(DEV_ADDR) != PS_OK) {
                    bar.setProgress(0);
                    thread_i = 0;
                    thread_sum = 0;
                    logMsg("生成模板失败，请重新录入");
                    handler.removeCallbacks(mGetFingerThread);
                    return;
                }
                // logMsg("生成模板成功");
                // need to fix the 1
                if (fingerCnt >= 256) {
                    logMsg("模板存满，请删除部分指纹信息");
                }
                if (msyUsbKey.PSStoreChar(DEV_ADDR, 1, fingerCnt) != PS_OK) {
                    bar.setProgress(0);
                    thread_i = 0;
                    thread_sum = 0;
                    logMsg("存储特征失败,请重新录入");
                    handler.removeCallbacks(mGetFingerThread);
                    return;
                }
                logMsg("录入指纹成功,=====>ID:" + fingerCnt);
                fingerCnt++;
                bar.setProgress(thread_sum);
                thread_i = 0;
                thread_sum = 0;
                handler.removeCallbacks(mGetFingerThread);
            }
            thread_i++;
        }
    };

    public int getUserContent(int[] max, byte[] fingerid, int[] len) {
        byte[] UserContent = new byte[32];
        byte bt, b;
        int ret = 0;
        int i;
        int iBase;
        int iIndex = 0;
        int iIndexOffset;
        int[] indexFinger = new int[256];
        int j = 0;
        int indexMax = 0;
        ret = msyUsbKey.PSReadIndexTable(DEV_ADDR, 0, UserContent);
        if (ret != 0) {
            return -1;
        }

        for (i = 0; i < 32; i++) {
            bt = UserContent[i];
            iBase = i * 8;
            if (bt == (byte) 0x00) {
                continue;
            }

            for (b = (byte) 0x01, iIndexOffset = 0; iIndexOffset < 8; b = (byte) (b << 1), iIndexOffset++) {
                if (0 == (bt & b)) {
                    continue;
                }
                iIndex = iBase + iIndexOffset;
                indexFinger[j] = iIndex;
                j++;
                if (iIndex > indexMax) {
                    indexMax = iIndex;
                }
            }

        }
        max[0] = indexMax;
        len[0] = j;
        for (i = 0; i < j; i++) {
            fingerid[i] = (byte) indexFinger[i];
        }
        return 0;
    }

    public int WriteBmp(byte[] imput) {
        String fileName = "finger.bmp";
        FileOutputStream fout = null;
        try {
            fout = openFileOutput(fileName, MODE_PRIVATE);
        } catch (FileNotFoundException e2) {
            return -100;
        }
        // FileOutputStream out = null;
        /*
         * try { out = this.openFileOutput("finger.bmp",MODE_PRIVATE); } catch
         * (FileNotFoundException e) { // TODO Auto-generated catch block
         * e.printStackTrace(); }
         */
        // OutputStreamWriter outWrite = new OutputStreamWriter(out);

        byte[] temp_head = {0x42, 0x4d,// file type
                // 0x36,0x6c,0x01,0x00, //file size***
                0x0, 0x0, 0x0, 0x00, // file size***
                0x00, 0x00, //reserved
                0x00, 0x00,//reserved
                0x36, 0x4, 0x00, 0x00,//head byte***
                // infoheader
                0x28, 0x00, 0x00, 0x00,//struct size

                // 0x00,0x01,0x00,0x00,//map width***
                0x00, 0x00, 0x0, 0x00,// map width***
                // 0x68,0x01,0x00,0x00,//map height***
                0x00, 0x00, 0x00, 0x00,// map height***

                0x01, 0x00,// must be 1
                0x08, 0x00,// color count
                0x00, 0x00, 0x00, 0x00, // compression
                // 0x00,0x68,0x01,0x00,//data size***
                0x00, 0x00, 0x00, 0x00,// data size***
                0x00, 0x00, 0x00, 0x00, // dpix
                0x00, 0x00, 0x00, 0x00, // dpiy
                0x00, 0x00, 0x00, 0x00,// color used
                0x00, 0x00, 0x00, 0x00,// color important
        };
        byte[] head = new byte[1078];
        byte[] newbmp = new byte[1078 + IMAGE_X * IMAGE_Y];
        System.arraycopy(temp_head, 0, head, 0, temp_head.length);

        int i, j;
        long num;
        num = IMAGE_X;
        head[18] = (byte) (num & 0xFF);
        num = num >> 8;
        head[19] = (byte) (num & 0xFF);
        num = num >> 8;
        head[20] = (byte) (num & 0xFF);
        num = num >> 8;
        head[21] = (byte) (num & 0xFF);

        num = IMAGE_Y;
        head[22] = (byte) (num & 0xFF);
        num = num >> 8;
        head[23] = (byte) (num & 0xFF);
        num = num >> 8;
        head[24] = (byte) (num & 0xFF);
        num = num >> 8;
        head[25] = (byte) (num & 0xFF);

        j = 0;
        for (i = 54; i < 1078; i = i + 4) {
            head[i] = head[i + 1] = head[i + 2] = (byte) j;
            head[i + 3] = 0;
            j++;
        }
        System.arraycopy(head, 0, newbmp, 0, head.length);
        System.arraycopy(imput, 0, newbmp, 1078, IMAGE_X * IMAGE_Y);

        try {
            fout.write(newbmp);
        } catch (IOException e1) {
            return -101;
        }
        try {
            fout.close();
        } catch (IOException e) {
            return -102;
        }
        return 0;
    }

    private void openState(boolean state) {
        mClose.setEnabled(!state);
        mInit.setEnabled(!state);
        mDevMsg.setEnabled(!state);
        mUpImage.setEnabled(!state);
        mLogOut.setEnabled(!state);
        mLogIn.setEnabled(!state);
    }

    private class TransferHandler implements Runnable {
        private int what;

        TransferHandler(int what) {
            this.what = what;
        }

        @Override
        public void run() {
            switch (what) {
                case INPUT_FINGER_SUCCESS: {
                    Log.i(TAG, "AA 录入成功");
                    break;
                }
                case Constants.NO_DATA: {
                    Log.i(TAG, "AA 没有数据");
                    break;
                }
                case Constants.NO_FINGER_PRINT: {
                    Log.i(TAG, "AA 没有放上指纹");
                    break;
                }
                case Constants.NPUT_FINGER_FAILED: {
                    Log.i(TAG, "AA 指纹shibai");
                    break;
                }
                case Constants.RECEIVER_KIT_WRONG: {
                    Log.i(TAG, "AA  包有问题");
                    break;
                }
            }
        }
    }

    private class TransferThread extends Thread {
        private int what;

        TransferThread(int what) {
            this.what = what;
        }

        @Override
        public void run() {
            if (what == 2) {
                int ret;
                try {
                    InputStream is = MainActivity.this.getAssets().open("default.bmp");//文件夹下的一个二进制文件
//                  byte b[]=new byte[]
                    int a = is.read(fingerBuf);
                    if ((ret = WriteBmp(fingerBuf)) != 0) {
                        Log.i(TAG, "AA :生成bmp文件失败" + ret);
                        return;
                    }
                    if ((ret = msyUsbKey.PSDownImage(DEV_ADDR)) != PS_OK) {
                        Log.i(TAG, "AA no发送数据包:ret=" + ret);
//                      return;
                    } else {
                        Log.i(TAG, "AA yes  发送数据包:ret=" + ret);
                        msyUsbKey.sendDataPacket(fingerBuf);
                    }
                    if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A)) != PS_OK) {
                        Log.i(TAG, "AA 指纹1生成特征失败:" + ret);
                        return;
                    } else {
                        if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_B)) != PS_OK) {
                            Log.i(TAG, "AA指纹2生成特征失败:" + ret);
                            return;
                        } else {
                            Log.i(TAG, "AA 指纹2生成特征ok:" + ret);
                        }
                        if (msyUsbKey.PSRegModule(DEV_ADDR) != PS_OK) {
                            Log.i(TAG, "AA 生成模板失败，请重新录入");
                            return;
                        }
                        if (fingerCnt >= 256) {
                            Log.i(TAG, "AA模板存满，请删除部分指纹信息");
                            return;
                        }
                        if (msyUsbKey.PSStoreChar(DEV_ADDR, 1, fingerCnt) != PS_OK) {
                            Log.i(TAG, "AA存储特征失败,请重新录入");
                            return;
                        }
                        Log.i(TAG, "AA录入指纹成功,=====>ID:" + fingerCnt);
                    }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            String localName = "finger.bmp";
                            FileInputStream localStream = null;
                            try {
                                localStream = openFileInput(localName);
                            } catch (FileNotFoundException e) {
                                return;
                            }
                            Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                            fingerView.setImageBitmap(bitmap);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
            int cnt = 1;
            while (true) {
                int ret;
                while (msyUsbKey.PSGetImage(DEV_ADDR) != PS_NO_FINGER) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                while (msyUsbKey.PSGetImage(DEV_ADDR) == PS_NO_FINGER) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
                if (cnt == 1) {
                    //将图像缓冲区中的数据上传给上位机,就是读取指纹中的图像
                    if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf)) != 0) {
                        Log.i(TAG, "AA 上传图像失败:" + ret);
                        continue;
                    }
                    Log.i(TAG, "AA 指纹1上传图像ok:" + ret);
                } else if (cnt == 2) {
                    //将图像缓冲区中的数据上传给上位机,就是读取指纹中的图像
                    if ((ret = msyUsbKey.PSUpImage(DEV_ADDR, fingerBuf2)) != 0) {
                        Log.i(TAG, "AA 上传图像失败:" + ret);
                        continue;
                    }
                    Log.i(TAG, "AA 指纹2上传图像ok:" + ret);
                }
                if ((ret = WriteBmp(fingerBuf)) != 0) {
                    Log.i(TAG, "AA 生成bmp文件失败:" + ret);
                    continue;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        String localName = "finger.bmp";
                        FileInputStream localStream = null;
                        try {
                            localStream = openFileInput(localName);
                        } catch (FileNotFoundException e) {
                            return;
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(localStream);
                        fingerView.setImageBitmap(bitmap);
                    }
                });
//                publishProgress("OK");
                // 生成模板
                if (cnt == 1) {
                    if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_A)) != PS_OK) {
                        Log.i(TAG, "AA 指纹1生成特征失败:" + ret);
                        continue;
                    } else {
                        Log.i(TAG, "AA 请再次放上手指");
                        //publishProgress("生成模板失败");
                    }
                }

                if (cnt == 2) {
                    if ((ret = msyUsbKey.PSGenChar(DEV_ADDR, CHAR_BUFFER_B)) != PS_OK) {
                        Log.i(TAG, "AA 指纹2生成特征失败:" + ret);
                        continue;
                    } else {
//                        Log.i(TAG,"AA "生成模板失败");
                    }
                    if (msyUsbKey.PSRegModule(DEV_ADDR) != PS_OK) {
                        bar.setProgress(0);
                        thread_i = 0;
                        thread_sum = 0;
                        Log.i(TAG, "AA 生成模板失败，请重新录入");
//                      handler.removeCallbacks(mGetFingerThread);
                        continue;
                    }
                    Log.i(TAG, "AA 生成模板成功");
                    // need to fix the 1
                    if (fingerCnt >= 256) {
                        Log.i(TAG, "AA 模板存满，请删除部分指纹信息");
                    }
                    if (msyUsbKey.PSStoreChar(DEV_ADDR, 1, fingerCnt) != PS_OK) {
                        bar.setProgress(0);
                        thread_i = 0;
                        thread_sum = 0;
                        Log.i(TAG, "AA 存储特征失败,请重新录入");
//                        handler.removeCallbacks(mGetFingerThread);
                        continue;
                    }
                    Log.i(TAG, "AA 录入指纹成功,=====>ID:" + fingerCnt);
                    return;
                }
                cnt++;
            }
        }
    }
}

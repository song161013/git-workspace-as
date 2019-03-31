package com.main.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.main.app.MainActivity;
import com.main.app.MainApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 记录文本日志
 *
 * @author zhangqi
 */
public final class RecordHelper {
    public static final String TAG = "RecordHelper";
    private static RecordHelper instance;
    private OutputStream output;
    private File logFile;
    private String filename;
    public static String appId = "";

    private RecordHelper() {
//        open(this.filename);
        open();
    }

    public static RecordHelper getInstance() {
        if (null == instance) {
            instance = new RecordHelper();
        }

        return instance;
    }

    private boolean open() {
        //路径是  /mnt/sdcard包名/文件名
        String packname = MainApplication.getInstance().getPackageName();

        String sdPath = Environment.getExternalStorageDirectory().getPath();
        String sperator = File.separator;
        File dir = new File(sdPath, "appLog" + sperator + packname);
        if (!dir.exists()) {
            boolean b = dir.mkdir();
            if (!b) {
                Log.e(TAG, "AA 创建目录失败");
            }
        }
        try {
            Date curDate = new Date(System.currentTimeMillis());
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月", new Locale("zh"));
            String time = format.format(curDate);
            File file = new File(dir, time + ".trace");
            if (!file.exists()) {
                boolean b = file.createNewFile();
                if (!b) {
                    Log.e(TAG, "AA 创建文件失败");
                }
            }
            output = new FileOutputStream(file, true);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private boolean open(String sFileName) {
        try {
            String path = null;
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return false;
            } else {
                path = Environment.getExternalStorageDirectory().toString()
                        + "/BootDemo/log/";
            }

            File logPath = new File(path);
            if (!logPath.exists()) {
                logPath.mkdirs();
            }
            if (sFileName == null || sFileName.length() == 0) {
                String sDateStr = getTimeStr("yyyy-MM-dd");
                sFileName = path + sDateStr + "-app-" + ".log";
            } else {
                sFileName = path + sFileName + ".log";
            }

            logFile = new File(sFileName);
            if (!logFile.exists())
                logFile.createNewFile();

            output = new FileOutputStream(logFile, true);

        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
        if (output == null)
            return false;
        return true;
    }

    public void close() {
        try {
            if (null != output) {
                output.close();
                output = null;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public synchronized void writeLog(String str) {
        try {
            if (output == null) {
                if (!open()) {
                    return;
                }
            }
            if (null != output) {
                String content = "[" + getTimeStr("yyyy-MM-dd HH:mm:ss") + "] " + str + "\r\n";
                output.write(content.getBytes("UTF-8"));
                output.flush();
            }
            close();
        } catch (UnsupportedEncodingException ee) {
            System.out.println("UnsupportedEncodingException:"
                    + ee.getMessage());
        } catch (IOException ioe) {
            System.out.println("IOerror:" + ioe.getMessage());
        }

    }

    @SuppressLint("SimpleDateFormat")
    private String getTimeStr(String datetype) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(datetype);
        Calendar calendarLocal = Calendar.getInstance();
        return dateFormat.format(calendarLocal.getTime());
    }

}

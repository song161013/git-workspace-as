package com.main.tools;

import android.util.Log;

public class LogUtil {
    public static final String TAG = "android-demos";
    public static boolean showLog = true;
    private static String s;

    public static void d(Class<?> paramClass, String paramString) {
        if (showLog)
            Log.d("LEKANGEBUY", "[" + paramClass.getSimpleName() + "]" + paramString);
        RecordHelper.getInstance().writeLog(paramClass + "-------" + paramString);
    }

    public static void d(Object paramObject, String paramString) {
        if (showLog)
            Log.d("LEKANGEBUY", "[" + paramObject.getClass().getSimpleName() + "]" + paramString);
        RecordHelper.getInstance().writeLog(paramObject + "-------" + paramString);
    }

    public static void d(String paramString) {
        if (showLog)
            Log.d("LEKANGEBUY", String.valueOf(paramString));
        RecordHelper.getInstance().writeLog("-------" + paramString);
    }

    public static void d(String paramString1, String paramString2) {
        if (showLog)
            Log.d(paramString1, paramString2);
        RecordHelper.getInstance().writeLog(paramString1 + "-------" + paramString2);
    }

    public static void e(String paramString) {
        if (showLog)
            Log.e("LEKANGEBUY", String.valueOf(paramString));
        RecordHelper.getInstance().writeLog(paramString);
    }

    public static void e(String paramString1, String paramString2) {
        if (showLog)
            Log.e(paramString1, paramString2);
        RecordHelper.getInstance().writeLog(paramString1 + "-------" + paramString2);
    }

    public static void i(String paramString) {
        if (showLog)
            Log.i("LEKANGEBUY", String.valueOf(paramString));
        RecordHelper.getInstance().writeLog(paramString);
    }

    public static void i(String paramString1, String paramString2) {
        if (showLog)
            Log.i(paramString1, paramString2);
        RecordHelper.getInstance().writeLog(paramString1 + "-------" + paramString2);
    }

    public static void v(String paramString) {
        if (showLog)
            Log.v("LEKANGEBUY", String.valueOf(paramString));
        RecordHelper.getInstance().writeLog(paramString);
    }

    public static void v(String paramString1, String paramString2) {
        if (showLog)
            Log.v(paramString1, paramString2);
        RecordHelper.getInstance().writeLog(paramString1 + "-------" + paramString2);
    }

    public static void w(String paramString) {
        if (showLog)
            Log.w("LEKANGEBUY", String.valueOf(paramString));
    }

    public static void w(String paramString1, String paramString2) {
        if (showLog)
            Log.w(paramString1, paramString2);
    }

    public static void debug(String string) {
        if (isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, s);
            RecordHelper.getInstance().writeLog(string);
        }
    }

    private static boolean isLoggable(String tag2, int debug) {
        return false;
    }
}
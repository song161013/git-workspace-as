package com.android.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by songfei on 2018/5/25
 * Description：
 */

public class ToolToast {
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int resID) {
        String content = context.getResources().getString(resID);
        showToast(context, content);
    }
}

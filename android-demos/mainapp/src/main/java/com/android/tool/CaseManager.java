package com.android.tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.android.tool.CaseInfo;

public class CaseManager {
    public static ArrayList<CaseInfo> getCaseLists(Context context) {
        InputStream is = null;
        try {
            //获取当前语音环境
            String language = context.getResources().getConfiguration().locale.toString();
            String filename;
            if (language.equals("zh_CN"))
                filename = "case.txt";
            else
                filename = "case_us.txt";
            //
            is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);

            String str = new String(buffer, "utf-8");
            JSONObject obj;
            obj = new JSONObject(str);

            JSONArray arrs = obj.getJSONArray("results");
            int len = arrs.length();
            if (len > 0) {
                ArrayList<CaseInfo> list = new ArrayList<CaseInfo>();
                for (int i = 0; i < len; i++) {
                    obj = arrs.getJSONObject(i);
                    String name = obj.getString("name");
                    String file = obj.getString("file");
//					System.out.println("name="+name+",file="+file);
                    CaseInfo ci = new CaseInfo(name, file);
                    ci.setDetail(obj.getString("detail"));
                    list.add(ci);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

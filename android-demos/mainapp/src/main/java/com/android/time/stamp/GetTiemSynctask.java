package com.android.time.stamp;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 别乱动 on 2017/12/19.
 */

public class GetTiemSynctask extends AsyncTask<String, Void, Long> {
    private String datetime;

    public GetTiemSynctask() {
    }

    public String getDateTime() {
        return this.datetime;
    }

    @Override
    protected Long doInBackground(String... params) {
        URLConnection uc = null;// 生成连接对象
        try {
            URL url = new URL(params[0]);// 取得资源对象
            uc = url.openConnection();
            uc.connect();// 发出连接
        } catch (IOException e) {
            e.printStackTrace();
        }
        long ld = uc.getDate();// 读取网站日期时间
        return ld;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        datetime = String.valueOf(aLong);
    }
}

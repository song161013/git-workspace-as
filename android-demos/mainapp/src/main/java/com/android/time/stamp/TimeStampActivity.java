package com.android.time.stamp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.main.app.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by songfei on 2018/5/22
 * Description：获取网络时间
 */

public class TimeStampActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "TimetempActivity";
    private Button btn2Timetemp, btn2String;
    private TextView tvShowContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_stamp);
        findView();
        setListener();
    }

    private void setListener() {
        btn2Timetemp.setOnClickListener(this);
        btn2String.setOnClickListener(this);
    }

    private void findView() {
        btn2String = (Button) findViewById(R.id.btn_2string);
        btn2Timetemp = (Button) findViewById(R.id.btn_2timestamp);
        tvShowContent = (TextView) findViewById(R.id.tv_showcontent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_2string:
                String tt = getLocalDatetimeString("GMT+8");
                tvShowContent.setText(tt);
                Log.e(TAG, "转为字符串" + tt);
                break;

            case R.id.btn_2timestamp:
                GetTiemSynctask getTiemSynctask = new GetTiemSynctask();
                getTiemSynctask.execute("http://www.ntsc.ac.cn");
                String s = getTiemSynctask.getDateTime();
                break;
        }
    }

    private void getWebsiteDatetime() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                long time = getWebsiteDatetime("http://www.ntsc.ac.cn");//中国科学院国家授时中心
                String s = timestamp2String(time);
                String minutes = s.substring(s.length() - 6, s.length() - 4);
                String hour = s.substring(0, s.length() - 6);
                Log.e(TAG, "fengzhong," + minutes);
                Log.e(TAG, "分钟前的时间," + hour);
                Log.e(TAG, "截取前," + time);
                Log.e(TAG, "截取前字符串," + s);
                //拿到字符串，截取；
                s = s.substring(0, s.length() - 3) + "00秒";
                String s1 = string2Timestamp(s);
                String ss = timestamp2String(Long.valueOf(s1));
                Log.e(TAG, "字符串," + ss);
                Log.e(TAG, "时间戳," + s1);
            }
        }.start();
    }

    private long getWebsiteDatetime(String webUrl) {
        try {
            URL url = new URL(webUrl);// 取得资源对象
            URLConnection uc = url.openConnection();// 生成连接对象
            uc.connect();// 发出连接
            long ld = uc.getDate();// 读取网站日期时间
            return ld;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String getLocalDatetimeString(String local) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone(local));
//        cal.setTimeInMillis(Calendar.getInstance().getTimeInMillis());
        String date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        String time = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        return date + " " + time;
    }

    private String string2Timestamp(Date d) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dff.setTimeZone(TimeZone.getDefault());
        String ee = dff.format(d);
        return ee;
    }

    private String string2Timestamp(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.US);
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            re_time = String.valueOf(l);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    private String timestamp2String(long time) {
        String re_StrTime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒", Locale.US);
        re_StrTime = sdf.format(new Date(time));
        return re_StrTime;
    }
}

package com.android.regextest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.main.app.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songfei on 2018/5/3
 * Description：
 */

public class RegexTestActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "RegexTestActivity";
    private EditText etNumber;
    private TextView tvQrCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regex);
        findView();
    }

    private void findView() {
        etNumber = (EditText) findViewById(R.id.et_number);
        tvQrCode = findViewById(R.id.tv_qrcode);
        Button btnTest = (Button) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(this);
    }

    private void regexTest() {
        String str = "zhangsantttxiaoqiangmmmmzhaoliu";
        //此处的t至少有一个，
        String[] names = str.split("(.)\\1+");//()用括号封装成组，并且从1开始自动编号。((a)((b(c)))，从左括号开始数
        //先用括号封装成组，因为t和m有多个，故要重复使用这个组，因为组的编号为1，（+代表一次或多次，1代表组的编号）
        for (String name : names) {
            System.out.println(name);
        }
        String s = "01087147293248741720072211170822102103987821170180770011384";
        String regex_di = "[01][0-9]{14}[17]";
        String regex_expiration_date = "[17][0-9]{5,}[11]";
        String regex_productor_date = "[11][0-9]{5,}[10]";
        Pattern p_di = Pattern.compile(regex_di);
        Pattern p_expiration = Pattern.compile(regex_expiration_date);
        Pattern p_productor = Pattern.compile(regex_productor_date);

        String di_regex;
        String expiration_regex;
        String productor_regex;
        Matcher m_di = p_di.matcher(s);
        Matcher m_expiration = p_expiration.matcher(s);
        Matcher m_productor = p_productor.matcher(s);
        while (m_di.find()) {
            di_regex = m_di.group();
        }
        while (m_expiration.find()) {
            expiration_regex = m_expiration.group();
        }
        while (m_productor.find()) {
            productor_regex = m_productor.group();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test: {
                btnTest1();
                break;
            }
        }
    }

    private void btnTest1() {
        String s = "01087147293248741720072211170822102103987821170180770011384";

        String regex01 = "01[0-9]{14,18}17[0-9]{6}11";
        String regex17 = "17[0-9]{6}11[0-9]{6}10";//这是[17]表示1或7

        String array[] = s.split(regex17);

        {
            Pattern pattern17 = Pattern.compile(regex17);
            Matcher matcher17 = pattern17.matcher(s);
            ArrayList<String> list17 = new ArrayList<>();
            while (matcher17.find()) {
                list17.add(matcher17.group());
            }
            for (String str : list17) {
                Log.e(TAG, "str=" + str);
            }
        }

        ArrayList<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex01);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            list.add(matcher.group());
        }
        for (String str : list) {
            String text01 = str.substring(str.length() - 10, str.length());
            Log.e(TAG, "str=" + str);
        }
    }

    private void btnTest() {
        if (!scannTimes) {
            resetQrCode();
        }
        if (etNumber.getText().toString().equals("1")) {
            String s = "0108714729324874";
            scannTimes = true;
            parserUID(s);
        } else if (etNumber.getText().toString().equals("2")) {
            String s = "1720072211170822102103987821170180770011384";
            parserUID(s);
            scannTimes = true;
        } else {
            String s = "01087147293248741720072211170822102103987821170180770011384";
            parserUID(s);
        }
        if (!qrCodeDi.equals("") && !qrCodeExpiration.equals("")) {
            udi = qrCodeDi + qrCodeExpiration;
            scannTimes = false;
        }
        String qr = "di=" + qrCodeDi + "\n有效期=" + qrCodeExpiration + "\nudi=" + udi;
        Log.e(TAG, "qr=" + qr);
        tvQrCode.setText(qr);
    }

    private String qrCodeDi = "";
    private String qrCodeExpiration = "";
    private String udi = "";
    private String di = "";
    private String expiration_date = "";
    private boolean scannTimes = false;

    private void resetQrCode() {
        qrCodeDi = "";
        qrCodeExpiration = "";
        udi = "";
        di = "";
        expiration_date = "";
        scannTimes = false;
    }

    /**
     * 条形码解析：
     * 1).只扫描一次："udi": "(01)08714729324874(17)200722(11)170822(10)21039878(21)170180770011384",
     * DI编码          有效期    生产日期    批号        序列号
     * 2).分2次扫描,扫描顺序为：先扫（01）后扫（17）
     *
     * @param s 条形码
     */
    private void parserUID(String s) {
        try {
            //di，uid,生产日期
            if (s != null && s.length() > 30) {
                String di_head = s.substring(0, 2);
                String expiration_head = s.substring(16, 18);
                if (di_head.equals("01") && expiration_head.equals("17")) {
                    expiration_date = s.substring(18, 24);
                    di = s.substring(2, 16);
                    qrCodeDi = di;
                    qrCodeExpiration = expiration_date;
                    udi = s;
                } else {
                    // //有时第二次扫描也会遇到条码长度大于30的情况。所以加个else
                    String expiration_id = s.substring(0, 2);
                    if (expiration_id.equals("17")) {
                        expiration_date = s.substring(2, 8);
                        qrCodeExpiration = expiration_date;
                    }
                }
            } else {
                //如果条码长度<30,属于第二种情况
                if (s != null && s.length() >= 16) {
                    String di_head = s.substring(0, 2);
                    if (di_head.equals("01")) {
                        di = s.substring(2, 16);
                        scannTimes = true;
                        qrCodeDi = di;
                    } else if (di_head.equals("17")) {
                        expiration_date = s.substring(2, 8);
                        qrCodeExpiration = expiration_date;
                    }
                } else if (s != null && s.length() >= 8) {
                    scannTimes = true;
                    String expiration_head = s.substring(0, 2);
                    if (expiration_head.equals("17")) {
                        expiration_date = s.substring(2, 8);
                        qrCodeExpiration = s;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getMail_ByWeb() {
        List<String> list = new ArrayList<>();
        try {
            String regex = "\\w+@\\w+(\\.\\w+)+";
            Pattern p = Pattern.compile(regex);
            URL url = new URL("http://site.baidu.com/");
            BufferedReader bufin = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = null;
            while ((line = bufin.readLine()) != null) {
                Matcher m = p.matcher(line);
                while (m.find()) {
                    list.add(m.group());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}

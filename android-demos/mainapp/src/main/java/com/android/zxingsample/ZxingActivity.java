package com.android.zxingsample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.client.result.ParsedResultType;
import com.main.app.R;
import com.mylhyl.zxing.scanner.common.Scanner;
import com.mylhyl.zxing.scanner.encode.QREncode;

import static android.graphics.Bitmap.Config.RGB_565;

/**
 * Created by songfei on 2018/5/22
 * Description：
 */

public class ZxingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnScan, btnCreate;
    private ImageView ivQt;
    private TextView tvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        findView();
    }

    private void findView() {
        btnCreate = (Button) findViewById(R.id.btn_create_QR);
        btnScan = (Button) findViewById(R.id.btn_scant);
        ivQt = (ImageView) findViewById(R.id.iv_qt);
        tvResult = (TextView) findViewById(R.id.tv_result);

        btnCreate.setOnClickListener(this);
        btnScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create_QR: {
                Resources res = getResources();
                BitmapFactory.Options opts = new BitmapFactory.Options();
//                opts.inJustDecodeBounds = true;

//                opts.inSampleSize = 2;
                opts.inPreferredConfig = RGB_565;
                Bitmap logoBitmap = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher1, opts);
//                int width = opts.outWidth;
//                int height = opts.outHeight;
//                Log.e(TAG, "宽X高=" + width + "X" + height);
                String json = "{\"tablet_id\":\"133\",\"paw\":\"123\",\"name\":\"宋飞\"}";
                //文本类型
                Bitmap bitmap = new QREncode.Builder(this)
                        .setColor(getResources().getColor(R.color.black_color))//二维码颜色
                        //.setParsedResultType(ParsedResultType.TEXT)//默认是TEXT类型
                        .setContents(json)//二维码内容
                        .setLogoBitmap(logoBitmap, 70)//二维码中间logo
                        .build().encodeAsBitmap();
                ivQt.setImageBitmap(bitmap);
                break;
            }
            case R.id.btn_scant: {
                if (ContextCompat.checkSelfPermission(ZxingActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(ZxingActivity.this,
                            new String[]{Manifest.permission.CAMERA}, 60);
                } else {
                    //权限已经被授予，在这里直接写要执行的相应方法即可
                    Intent intent = new Intent(this, ZxingScanActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCANNER);
                }
                break;
            }
        }
    }

    public static final int REQUEST_CODE_SCANNER = 188;
    private static final String TAG = "BasicScannerActivity";
    private static final int PICK_CONTACT = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_CANCELED && resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_SCANNER) {
                if (data != null) {
                    String stringExtra = data.getStringExtra(Scanner.Scan.RESULT);
                    tvResult.setText(stringExtra);
                }
            } else if (requestCode == PICK_CONTACT) {
                // Data field is content://contacts/people/984
                showContactAsBarcode(data.getData());
            }
        }
    }

    private void showContactAsBarcode(Uri contactUri) {
        //可以自己组装bundle;
//        ParserUriToVCard parserUriToVCard = new ParserUriToVCard();
//        Bundle bundle = parserUriToVCard.parserUri(this, contactUri);
//        if (bundle != null) {
//            Bitmap bitmap = QREncode.encodeQR(new QREncode.Builder(this)
//                    .setParsedResultType(ParsedResultType.ADDRESSBOOK)
//                    .setBundle(bundle).build());
//            imageView.setImageBitmap(bitmap);
//            tvResult.setText("单击二维码图片识别");
//        } else tvResult.setText("联系人Uri错误");

        //只传Uri
        Bitmap bitmap = new QREncode.Builder(this)
                .setParsedResultType(ParsedResultType.ADDRESSBOOK)
                .setAddressBookUri(contactUri).build().encodeAsBitmap();
        ivQt.setImageBitmap(bitmap);
        tvResult.setText("单击二维码图片识别");
    }
}
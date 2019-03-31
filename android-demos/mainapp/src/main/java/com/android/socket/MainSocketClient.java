package com.android.socket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.main.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kys-29 on 2016/10/18.
 * Des
 */

public class MainSocketClient extends AppCompatActivity implements View.OnClickListener, SocketClient.ReceiverListener {
    private static final String TAG = "MainSocketClient";
    private SocketClient client;
    private ListView mListView;
    private EditText edit;
    private Button btn;
    private EditText edit_server;
    private Button server_OK;
    private EditText edit_ip;
    private LinearLayout mLinearLayout;
    private LinearLayout mLinearLayout_client;

    private int pite;
    private String ip = "";

    private ListAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);

        findView();
        initView();
    }

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new ListAdapter(this, mList);
        mListView.setAdapter(mAdapter);
    }

    private void initSocket() {
        client = new SocketClient();
        //服务端的IP地址和端口号
        client.clintValue(MainSocketClient.this, ip, pite);
        //开启客户端接收消息线程
        client.openClientThread();
        client.setReceiverListener(this);
    }

    private void findView() {
        mListView = (ListView) findViewById(R.id.textView);

        edit = (EditText) findViewById(R.id.edit);
        edit_server = (EditText) findViewById(R.id.editText_server);
        edit_ip = (EditText) findViewById(R.id.client_ip);
        mLinearLayout = (LinearLayout) findViewById(R.id.lin_1);
        mLinearLayout_client = (LinearLayout) findViewById(R.id.lin_ip);

        btn = (Button) findViewById(R.id.btn);
        server_OK = (Button) findViewById(R.id.server_OK);

        server_OK.setOnClickListener(this);
        //发送消息
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn: {
                if (!client.isConnect()) {
                    Log.e(TAG, "client.isConnect=" + client.isConnect());
                } else {
                    client.sendMessage(edit.getText().toString());
                    Log.e(TAG, "client.isConnect=" + client.isConnect());
                    Log.e(TAG, "客户端送数据=" + edit.getText().toString());
                    edit.setText("");
                }
                if (!client.isBound()) {
                    Log.e(TAG, "client.isBound=" + client.isBound());
                }
                if (!client.isClose()) {
                    Log.e(TAG, "client.isClose=" + client.isClose());
                }
                break;
            }
            case R.id.server_OK: {
                mLinearLayout.setVisibility(View.GONE);
                mLinearLayout_client.setVisibility(View.GONE);
                try {
                    pite = Integer.parseInt(edit_server.getText().toString());
                    ip = edit_ip.getText().toString();
                    initSocket();

                } catch (Exception e) {
                    Toast.makeText(MainSocketClient.this, "请检查ip及地址", Toast.LENGTH_SHORT).show();
                    mLinearLayout.setVisibility(View.VISIBLE);
                    mLinearLayout_client.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void receiverMessage(String message) {
        mList.add(message);
        mAdapter.notifyDataSetChanged();
    }
}

package com.android.socket;

import android.os.Bundle;
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

public class MainSocketServer extends AppCompatActivity implements View.OnClickListener, SocketServer.ReceiverListener {
    private static final String TAG = "MainSocketServer";
    private ListView mListView;
    private EditText edit;
    private Button btn;
    private EditText edit_server;
    private EditText edit_ip;
    private Button server_OK;
    private LinearLayout mLinearLayout;
    private LinearLayout mLinearLayout_client;


    private ListAdapter mAdapter;
    private List<String> mList;

    /**
     * 启动服务端端口
     * 服务端IP为手机IP
     */
    private int pite;
    private SocketServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        initView();

//        /**socket收到消息线程*/
//        SocketServer.ServerHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                txt.setText(msg.obj.toString());
//            }
//        };

    }

    private void initView() {
        mList = new ArrayList<>();
        mAdapter = new ListAdapter(this, mList);
        mListView.setAdapter(mAdapter);
    }


    private void findView() {
        mListView = (ListView) findViewById(R.id.textView);
        edit = (EditText) findViewById(R.id.edit);
        edit_server = (EditText) findViewById(R.id.editText_server);
        edit_ip = (EditText) findViewById(R.id.client_ip);

        mLinearLayout = (LinearLayout) findViewById(R.id.lin_1);
        mLinearLayout_client = (LinearLayout) findViewById(R.id.lin_ip);
        mLinearLayout_client.setVisibility(View.GONE);

        server_OK = (Button) findViewById(R.id.server_OK);
        btn = (Button) findViewById(R.id.btn);
        server_OK.setOnClickListener(this);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.server_OK: {
                mLinearLayout.setVisibility(View.GONE);
                try {
                    pite = Integer.parseInt(edit_server.getText().toString());
                    server = new SocketServer(pite);
                    /**socket服务端开始监听*/
                    server.beginListen();
                    Log.e(TAG, "beginListen");
                    server.setReceiverListener(this);
                } catch (Exception e) {
                    Toast.makeText(MainSocketServer.this, "请输入数字", Toast.LENGTH_SHORT).show();
                    mLinearLayout.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
                break;
            }
            case R.id.btn: {
                /**socket发送数据*/
                if (!server.isConnect()) {
                    Log.e(TAG, "server.isConnect=" + server.isConnect());
                } else {
                    server.sendMessage(edit.getText().toString());
                    Log.e(TAG, "服务发送数据=" + edit.getText().toString());
                    Log.e(TAG, "server.isConnect=" + server.isConnect());
                    edit.setText("");
                }
                if (!server.isBound()) {
                    Log.e(TAG, "server.isBound=" + server.isBound());
                }
                if (!server.isClose()) {
                    Log.e(TAG, "server.isServerClose=" + server.isClose());
                }


                break;
            }
        }
    }

    @Override
    public void receiverMessage(String message) {
        mList.add(message);
        mAdapter.notifyDataSetChanged();
    }
}

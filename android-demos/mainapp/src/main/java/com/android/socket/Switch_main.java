package com.android.socket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.tool.Tools;
import com.main.app.R;


public class Switch_main extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "Switch_main";
    private Button btn_server, btn_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_switch_main_);
        initView();
    }

    private void initView() {
        btn_server = (Button) findViewById(R.id.btn_server);
        btn_client = (Button) findViewById(R.id.btn_client);

        btn_server.setOnClickListener(this);
        btn_client.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_server: {
                String isAddress = Tools.getIPAddress(Switch_main.this);
                Log.e(TAG, "服务ip" + isAddress);//服务ip10.51.117.55
                startActivity(new Intent(Switch_main.this, MainSocketServer.class));
            }
            case R.id.btn_client: {
                startActivity(new Intent(Switch_main.this, MainSocketClient.class));
                break;
            }
        }
    }
}

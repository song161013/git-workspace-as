package com.android.socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by kys-29 on 2016/9/21.
 */

/*
MainActivity代码：
    SocketServer server=new SocketServer ( port );
    server.beginListen ();

* */
public class SocketServer {
    private static final String TAG = "SocketServer";
    private ServerSocket server;
    private Socket socket;
    private InputStream in;
    private String str = null;
    private boolean isClint = false;
    public static Handler ServerHandler;

    /**
     * @param port 端口号
     * @steps bind();绑定端口号
     * @effect 初始化服务端
     */
    public SocketServer(int port) {
        try {
            server = new ServerSocket(port);
            isClint = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @steps listen();
     * @effect socket监听数据
     */
    public void beginListen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    /**
                     * accept();
                     * 接受请求
                     * */
                    socket = server.accept();
                    Log.e(TAG, "服务端开始监听");
                    try {
                        /**得到输入流*/
                        in = socket.getInputStream();
                        /**实现数据循环接收*/
                        while (!socket.isClosed()) {
                            if (!socket.isConnected()) {
                                Log.e(TAG, "连接断开");
                                return;
                            }
                            byte[] bt = new byte[50];
                            in.read(bt);
                            str = new String(bt, "UTF-8");                  //编码方式  解决收到数据乱码
                            if (str != null && str != "exit") {
                                returnMessage(str);
                            } else if (str == null || str == "exit") {
                                break;                                     //跳出循环结束socket数据接收
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        socket.isClosed();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    socket.isClosed();
                }
            }
        }).start();
    }

    public boolean isBound() {
        if (socket == null) {
            Log.e(TAG, "server is null");
            return false;
        }
        return socket.isBound();
    }

    public boolean isClose() {
        if (socket == null) {
            Log.e(TAG, "server is null");
            return false;
        }
        return socket.isClosed();
    }

    public boolean isConnect() {
        if (socket == null) {
            Log.e(TAG, "server is null");
            return false;
        }
        return socket.isConnected();
    }

    /**
     * @steps write();
     * @effect socket服务端发送信息
     */
    public void sendMessage(final String chat) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.print(chat);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * @steps read();
     * @effect socket服务端得到返回数据并发送到主界面
     */
    public void returnMessage(String chat) {
        Message msg = new Message();
        msg.obj = chat;
//        ServerHandler.sendMessage(msg);
        mHandler.sendMessage(msg);
    }

    private ReceiverHandler mHandler = new ReceiverHandler();

    private class ReceiverHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String str = (String) msg.obj;
            mListener.receiverMessage(str);
        }
    }

    public interface ReceiverListener {
        void receiverMessage(String message);
    }

    private ReceiverListener mListener;

    public void setReceiverListener(ReceiverListener listener) {
        this.mListener = listener;
    }
}

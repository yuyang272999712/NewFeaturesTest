package com.yuyang.aidlclient;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用Socket进行跨进程通讯
 */
public class TCPClientActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "TCPClientActivity";

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private Button mSendBtn;
    private TextView mMessageTextView;
    private EditText mMessageEditText;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_RECEIVE_NEW_MSG:
                    mMessageTextView.setText(mMessageTextView.getText()+(String)msg.obj);
                    break;
                case MESSAGE_SOCKET_CONNECTED:
                    mSendBtn.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);

        mMessageTextView = (TextView) findViewById(R.id.msg_container);
        mSendBtn = (Button) findViewById(R.id.send);
        mSendBtn.setOnClickListener(this);
        mMessageEditText = (EditText) findViewById(R.id.msg);

        Intent service = new Intent();
        service.setComponent(new ComponentName("com.yuyang.fitsystemwindowstestdrawer","com.yuyang.fitsystemwindowstestdrawer.IPC.socketService.TCPServerService"));
        startService(service);

        new Thread(){
            @Override
            public void run() {
                connectTCPServer();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity退出时，关闭当前Socket
        if (mClientSocket != null){
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        String msg = mMessageEditText.getText().toString();
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null){
            mPrintWriter.println(msg);
            mPrintWriter.flush();
            mMessageEditText.setText("");
            String time = formatDateTime(System.currentTimeMillis());
            String showedMsg = "发送消息 "+time+": "+msg+"\n";
            mMessageTextView.setText(mMessageTextView.getText() + showedMsg);
        }
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("(HH:mm:ss)").format(new Date(time));
    }

    /**
     * 建立连接
     */
    private void connectTCPServer() {
        Socket socket = null;
        //轮询，直到建立连接为止
        while (socket == null){
            try {
                socket = new Socket("localhost", 8688);
                mClientSocket = socket;
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
                mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                Log.e(TAG, "连接服务器成功");
            } catch (IOException e) {
                SystemClock.sleep(1000);
                e.printStackTrace();
            }
        }

        //接收服务器信息
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!TCPClientActivity.this.isFinishing()){
                String msg = br.readLine();
                if (msg != null){
                    String time = formatDateTime(System.currentTimeMillis());
                    String showedMsg = "接收消息 "+time+": "+msg+"\n";
                    mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                }
            }
            Log.e(TAG, "退出");
            mPrintWriter.close();
            br.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

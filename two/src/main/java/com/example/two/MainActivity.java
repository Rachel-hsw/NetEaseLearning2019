package com.example.two;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.library.NetworkManager;
import com.example.library.listener.NetChangeObserver;
import com.example.library.type.NetType;
import com.example.library.utils.Constants;

public class MainActivity extends AppCompatActivity implements NetChangeObserver {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getDefault().setListener(this);
    }

    @Override
    public void onConnect(NetType type) {
        //没法知道具体的网络类型 wifi or gprs
        Log.e(Constants.LOG_TAG,type.name());
    }

    @Override
    public void onDisConnect() {
        Log.e(Constants.LOG_TAG,"网络中断");
    }
}

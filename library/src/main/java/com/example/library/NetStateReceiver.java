package com.example.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.library.listener.NetChangeObserver;
import com.example.library.type.NetType;
import com.example.library.utils.Constants;
import com.example.library.utils.NetworkUtils;

public class NetStateReceiver extends BroadcastReceiver {

    private NetType netType;//网络类型
    private NetChangeObserver listener;//网络监听接口

    public NetStateReceiver() {
        this.netType = NetType.NONE;
    }

    public void setListener(NetChangeObserver listener) {
        this.listener = listener;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        //初始化网络，可不写
        if (intent==null||intent.getAction()==null){
            Log.e(Constants.LOG_TAG,"异常");
            return;
        }
        if(intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)){
            Log.e(Constants.LOG_TAG,"监听网络变化");
            netType= NetworkUtils.getNetType();//赋值网络类型，唯一
            if (NetworkUtils.isNetWorkAvailable()){
                Log.e(Constants.LOG_TAG,"网络连接成功");
                if (listener!=null)listener.onConnect(netType);
            }else {
                Log.e(Constants.LOG_TAG,"监听网络变化");
                if (listener!=null)listener.onDisConnect();
            }
        }
    }
}

package com.example.library.listener;

import com.example.library.type.NetType;

public  interface  NetChangeObserver {
    //连接
     void onConnect(NetType type);
    //断开
    void onDisConnect();
}

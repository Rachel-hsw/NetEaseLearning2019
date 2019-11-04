package com.example.library.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.library.NetworkManager;
import com.example.library.type.NetType;

public class NetworkUtils {
    /**
     * 获取当前的网络类型 没有网络 wifi网络 wap net
     * @return
     */
    public static NetType getNetType() {
        ConnectivityManager connMg = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMg==null){
            return NetType.NONE;
        }
        NetworkInfo networkInfo=connMg.getActiveNetworkInfo();
        if (networkInfo==null){
            return NetType.NONE;
        }
        int nType=networkInfo.getType();
        if (nType==ConnectivityManager.TYPE_MOBILE){
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
                return NetType.CMNET;
            }else {
                return NetType.CMWAP;
            }
        }else if(nType==ConnectivityManager.TYPE_WIFI){
            return NetType.WIFI;
        }
        return NetType.NONE;
    }

    /**
     * 网络是否可用
     * @return
     */
    public static boolean isNetWorkAvailable() {
        ConnectivityManager connMg = (ConnectivityManager) NetworkManager.getDefault().getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connMg==null){
            return false;
        }
        //返回所有网络信息
        NetworkInfo[] info=connMg.getAllNetworkInfo();
        if (info!=null){
            for (NetworkInfo anInfo:info){
                if (anInfo.getState()==NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }
}

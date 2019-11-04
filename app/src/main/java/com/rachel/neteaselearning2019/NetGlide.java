package com.rachel.neteaselearning2019;

import android.content.Context;

public class NetGlide {
    public  static  BitmapRequest with(Context context){
        return new BitmapRequest(context);
    }
}

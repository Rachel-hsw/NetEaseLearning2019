package com.rachel.neteaselearning2019;

import android.graphics.Bitmap;

interface RequestListener {
    boolean onSuccess(Bitmap bitmap);
    void onFailure();
}

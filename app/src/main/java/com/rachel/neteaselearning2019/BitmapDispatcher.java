package com.rachel.neteaselearning2019;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.LinkedBlockingQueue;

//图片处理类==银行的业务处理窗口   队列永远只有一个
public class BitmapDispatcher extends Thread {
    //handler的内存泄露 不用开辟内存空间的话，根本无所谓
    private Handler handler = new Handler(Looper.getMainLooper());
    /**
     * 请求队列
     */
    private LinkedBlockingQueue<BitmapRequest> requestQueue;

    /**
     * 请求队列不是处理窗口创建的，而是银行创建的，所以这里是传进来的
     *
     * @param requestQueue
     */
    public BitmapDispatcher(LinkedBlockingQueue<BitmapRequest> requestQueue) {
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            //从队列中获取请求
            BitmapRequest br;
            try {
                br = requestQueue.take();
                //先设置占位图片
                showLoadingImage(br);
                //去网络（缓存）加载图片
                Bitmap bitmap = findImage(br);
                //将图片加载imageView
                showImageView(br, bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageView(final BitmapRequest br, final Bitmap bitmap) {
        if (bitmap != null && br.getImageView() != null && br.getUrlMd5().equals(br.getImageView().getTag())) {
            final ImageView iv = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iv.setImageBitmap(bitmap);
                    if (br.getRequestListener() != null) {
                        RequestListener requestListener = br.getRequestListener();
                        requestListener.onSuccess(bitmap);
                    }
                }
            });
        }
    }

    private Bitmap findImage(BitmapRequest br) {
        Bitmap bitmap = downloadImage(br.getUrl());
        return bitmap;
    }

    private Bitmap downloadImage(String uri) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            //创建一个URL对象
            URL url = new URL(uri);
            //然后使用HttpURLConnection通过URL去开始读数据
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //10m 1m requestListener
            //超时，错误
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    private void showLoadingImage(BitmapRequest br) {
        if (br.getResId() > 0 && br.getImageView() != null) {
            final int resId = br.getResId();
            final ImageView iv = br.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iv.setImageResource(resId);
                }
            });
        }
    }
}

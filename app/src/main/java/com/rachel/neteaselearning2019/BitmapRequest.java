package com.rachel.neteaselearning2019;

import android.content.Context;
import android.widget.ImageView;

import java.lang.ref.SoftReference;

//图片请求类

/**
 * url  相当于你去银行办理业务需要提供身份证
 */
public class BitmapRequest {
    //请求地址
    private String url;
    //上下文
    private Context context;
    //需要加载图片的控件 强引用 软引用：内存不够，gc都会回收
    // 弱引用：不管内存够不够，gc都会回收  虚引用：很少有用，垃圾回收时会回调这个方法
    //强软弱
    //假设我们做的图片应用，用弱引用，你不停的回收，就会造成内存抖动，gc频繁的去回收，gc一回收，
    // 其他线程会全部挂起，如果我们正在做ui线程的更新，就会造成UI的卡顿
    private SoftReference<ImageView> imageView;
    //设置占位图片
    private int resId;
    //回调对象
    private RequestListener requestListener;
    //图片的标识
    private String urlMd5;
    public BitmapRequest(Context context){
        this.context=context;
    }
    public  BitmapRequest load(String url){
        this.url=url;
        this.urlMd5=MD5Utils.toMD5(url);
        return this;
    }
    public BitmapRequest loading(int resId){
        this.resId=resId;
        return  this;
    }
    public BitmapRequest listener(RequestListener listener){
        this.requestListener=listener;
        return this;
    }

    /**
     * imageView复用会出现错位 setTag
     * @param imageView
     */
    public  void into(ImageView imageView){
        imageView.setTag(this.urlMd5);
        this.imageView=new SoftReference<>(imageView);
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageView() {
        return imageView.get();
    }

    public void setImageView(SoftReference<ImageView> imageView) {
        this.imageView = imageView;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public RequestListener getRequestListener() {
        return requestListener;
    }

    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }
}

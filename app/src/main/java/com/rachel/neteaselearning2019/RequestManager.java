package com.rachel.neteaselearning2019;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生命周期和APP一样
 * @author Rachel
 */
public class RequestManager {
    /**
     * 创建队列
     */
    private LinkedBlockingQueue<BitmapRequest> requestQueue = new LinkedBlockingQueue<>();

    /**
     * 相当于到银行之后先取到号
     *
     * @param bitmapRequest
     */
    public void addBitmapRequest(BitmapRequest bitmapRequest) {
        if (bitmapRequest == null) {
            return;
        }
        if (!requestQueue.contains(bitmapRequest)) {
            requestQueue.add(bitmapRequest);
        }
    }

    private static RequestManager requestManager = new RequestManager();

    private RequestManager() {
        start();
    }

    public static RequestManager getInstance() {
        return requestManager;
    }

    private BitmapDispatcher[] bitmapDispatchers;

    private void start() {
        stop();
        startAllDispatchers();
    }

    private void startAllDispatchers() {
        //创建处理类（数组），并开始工作
        //获取手机支持的单个应用最大的线程数
        int threadCount = Runtime.getRuntime().availableProcessors();
        bitmapDispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < threadCount; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            //为什么不用线程池 如果一些页面就刷新十几张图片，如果用线程池，开销会比用线程要大。一个线程消耗多少kb的内存
            //处理窗口开始工作
            bitmapDispatcher.start();
            //便于管理
            bitmapDispatchers[i] = bitmapDispatcher;
        }
    }

    private void stop() {
        if (bitmapDispatchers != null && bitmapDispatchers.length > 0) {
            for (BitmapDispatcher bitmapDispatcher : bitmapDispatchers) {
                if (!bitmapDispatcher.isInterrupted()) {
                    bitmapDispatcher.isInterrupted();
                }
            }
        }
    }
}

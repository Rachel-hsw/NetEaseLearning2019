package com.rachel.neteaselearning2019;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.appcompat.widget.AppCompatImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 3
 * 2019Android 长图加载
 * 手写自定义view加载长图
 * touch事件，手势处理
 */
public class BigView extends AppCompatImageView implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private final Rect mRect;
    private final BitmapFactory.Options mOption;
    private final GestureDetector mGestureDetector;
    private final Scroller mScroller;
    private int mImageWidth;
    private int mImageHeight;
    private BitmapRegionDecoder mDecoder;
    private int mViewWidth;
    private int mViewHeight;
    private float mScale;
    private Bitmap mBitmap;

    public BigView(Context context) {
        this(context, null);
    }

    public BigView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //第1步：设置BigView所需要的成员变量
        mRect = new Rect();
        //内存复用
        mOption = new BitmapFactory.Options();
        //手势识别
        mGestureDetector = new GestureDetector(context, this);
        //滚动类
        mScroller = new Scroller(context);
        setOnTouchListener(this);
    }

    public void setImage(Drawable drawable) {
        setImage(Drawable2InputStream(drawable));
    }

    public InputStream Drawable2InputStream(Drawable d) {
        Bitmap bitmap = this.drawable2Bitmap(d);
        return this.Bitmap2InputStream(bitmap, 100);
    }

    public Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    void setImage(Bitmap bitmap) {
        setImage(Bitmap2InputStream(bitmap, 100));
    }

    //第2步，设置图片，得到图片的信息
    void setImage(InputStream is) {
        //获取到图片的宽和高（没有将整个图片加载到内存,如果把整个图片加载进去，我们做分块加载还有什么意义呢）
        mOption.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOption);
        mImageWidth = mOption.outWidth;
        mImageHeight = mOption.outHeight;
        //开启复用
        mOption.inMutable = true;
        //设置格式RGB565  ARGB每一个占8位，一共32位，是4个字节 RGB565 16位 2个字节
        mOption.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mOption.inJustDecodeBounds = false;
        //区域解码器
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }
    //第三步，开始测量

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
        //确定图片的加载区域
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWidth;
        //得到图片加载的具体的高度
        //根据图片的宽度，以及view的宽度，计算缩放因子  宽度是图片实际的宽度，但是高度是
        mScale = mViewWidth / (float) mImageWidth;
        mRect.bottom = (int) (mViewHeight / mScale);
    }

    //第四步，画出具体的内容
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDecoder == null) {
            return;
        }
        //内存复用(复用的bitmap必须要和即将解码的bitmap的尺寸一样)
        mOption.inBitmap = mBitmap;
        //指定解码区域
        mBitmap = mDecoder.decodeRegion(mRect, mOption);
        //得到矩阵进行缩放，得到view的大小
        Matrix matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        canvas.drawBitmap(mBitmap, matrix, null);
    }

    //第五步，处理点击事件
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //直接将事件交给手势事件
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    //第六步，手按下去
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        //如果移动没有停止，强行停止
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        //交给后面的人处理
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * 第七步，处理滑动事件
     *
     * @param motionEvent  开始事件，手按下去，获取坐标
     * @param motionEvent1 获取当前事件坐标
     * @param v            x方向上移动的距离
     * @param v1           y方向上移动的距离
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        //上下移动时，mRect需要该表显示区域
        mRect.offset(0, (int) v1);
        //移动的时候，处理到达顶部和底部的情况
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = mImageHeight;
            //图片高-显示的高度=显示的起点高
            mRect.top = (int) (mImageHeight - (mViewHeight / mScale));
        }
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight / mScale);
        }
        //失效
        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    //第8步，处理惯性问题
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        mScroller.fling(0, mRect.top, 0, (int) -v1, 0, 0, 0, mImageHeight - (int) (mViewHeight / mScale));
        return false;
    }

    //第九步，处理计算结果
    @Override
    public void computeScroll() {
        if (mScroller.isFinished()) {
            return;
        }
        //为true，滑动还没有结束
        if (mScroller.computeScrollOffset()) {
            mRect.top = mScroller.getCurrY();
            mRect.bottom = (int) (mRect.top + (mViewHeight / mScale));
            invalidate();
        }
    }

}

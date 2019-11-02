package com.rachel.neteaselearning2019;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IrregularView extends View {
    private Bitmap bitmap;
    private int width = -1;
    private int height = -1;
    private int cx, cy;
    private Canvas canvasTemp;
    private Path path;

    public IrregularView(Context context) {
        this(context, null);
    }

    public IrregularView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IrregularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

    }


    //处理事件

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_UP) {
            return super.onTouchEvent(event);
        }
        //获取触摸位置
        float x = event.getX();
        float y = event.getY();

        //判断颜色
        Drawable background = getBackground();
        bitmap = ((BitmapDrawable) background).getBitmap();
        //位置调整
        if (bitmap == null || x < 0 || y < 0 || x > width || y > width) {
            return false;
        }
        //缩放(为什么图片要进行缩放，是因为虽然一张图片被设置为某控件的背景后会自动填充，但是图片的真正大小并没有改变)
        int pixel = bitmap.getPixel((int) x * bitmap.getWidth() / width, (int) y * bitmap.getHeight() / height);
        if (pixel == Color.TRANSPARENT) {
            //如果是透明色，不对事件进行处理
            return false;
        }
        return super.onTouchEvent(event);
    }
}

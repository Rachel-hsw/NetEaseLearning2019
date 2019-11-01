package com.rachel.neteaselearning2019;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class IrregularView extends View {
    private Paint paint;
    private int[] colors = {0xFFD21D22, 0xFFFBD109, 0xFF4BB748, 0xFF2F7Abb};
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
        //初始化
        paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(4);
        path = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cx = width / 2;
        cy = height / 2;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvasTemp = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int outerCr = 100;
        int innerCr = 50;
        RectF innerRectF = new RectF(cx - innerCr, cy - innerCr, cx + innerCr, cy + innerCr);
        RectF outerRectF = new RectF(cx - outerCr, cy - outerCr, cx + outerCr, cy + outerCr);
        path.reset();
        //获取红色区域
        path.addArc(innerRectF, 150, 120);
        path.lineTo((float) (cx + innerCr * Math.sqrt(3)), cy - innerCr);
        path.addArc(outerRectF, -30, -120);
        path.lineTo((float) (cx - innerCr * Math.sqrt(3) / 2), cy + innerCr / 2);
        paint.setColor(colors[0]);
        canvasTemp.drawPath(path, paint);
        //黄色
        Matrix matrix = new Matrix();
        matrix.setRotate(120, cx, cy);
        path.transform(matrix);
        paint.setColor(colors[1]);
        canvasTemp.drawPath(path, paint);
        //绿色
        path.transform(matrix);
        paint.setColor(colors[2]);
        canvasTemp.drawPath(path, paint);
        //蓝色
        paint.setColor(Color.WHITE);
        canvasTemp.drawCircle(cx, cy, innerCr, paint);
        paint.setColor(colors[3]);
        canvasTemp.drawCircle(cx, cy, innerCr - 10, paint);
        //想让用户看到，得加上这句
        canvas.drawBitmap(bitmap, 0, 0, paint);
        super.onDraw(canvas);
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
        //位置调整
        if (bitmap == null || x < 0 || y < 0 || x > width || y > width) {
            return false;
        }
        //判断颜色
        int pixel = bitmap.getPixel((int) x, (int) y);
        if (pixel == Color.TRANSPARENT) {
            //如果是透明色，不对事件进行处理
            return false;
        } else {
            //蓝色和白色全部算3区域
            this.setTag(this.getId(), 3);
            for (int i = 0; i < colors.length; i++) {
                if (pixel == colors[i]) {
                    this.setTag(this.getId(), i);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}

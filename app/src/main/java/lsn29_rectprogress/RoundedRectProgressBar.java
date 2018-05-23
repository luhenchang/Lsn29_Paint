package lsn29_rectprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.ls.lsn29_paint.R;

/**
 * 作者:王飞
 * 邮箱:1276998208@qq.com
 * create on 2018/5/22 16:31
 */

 /*
    * 1.第一步:我们需要定义一个水平矩形边缘圆形的进度条。
    *   进度条需要的属性有:{1:默认底层矩形颜色,2:滑动矩形滑块的颜色,3:字体的颜色}
    * 2.我们去定义这个属性:
    *    1.兴建一个attrs.xml用来定义需要的属性
    *      <declare-styleable name="RoundedRectProgressBar">
            <attr name="roundColor" format="color"></attr>
            <attr name="progressColor" format="color"></attr>
            <attr name="progressTextColor" format="color"></attr>
            <attr name="progressTextSize" format="dimension"></attr>
          </declare-styleable>

         2.这里进行初始化自定义的属性


      3.在onDraw里面进行画默认情况下背景。然后画滑动时候的背景。

    *
    * */
public class RoundedRectProgressBar extends View {
    private float progressTextSize;
    private Paint paint;
    private int progressTextColor;
    private int progressColor;
    private int roundColor;
    private float ridus;
    private float progress;

    public RoundedRectProgressBar(Context context) {
        super(context);
    }

    //2.这里进行初始化自定义的属性
    public RoundedRectProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedRectProgressBar);
        roundColor = typedArray.getColor(R.styleable.RoundedRectProgressBar_roundsColor, Color.GRAY);
        progressColor = typedArray.getColor(R.styleable.RoundedRectProgressBar_progressColor, Color.RED);
        progressTextColor = typedArray.getColor(R.styleable.RoundedRectProgressBar_progressTextColor, Color.WHITE);
        progressTextSize = typedArray.getDimension(R.styleable.RoundedRectProgressBar_progressTextSize, 15);
        typedArray.recycle();


    }

    //3.在onDraw里面进行画默认情况下背景。然后画滑动时候的背景。
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.首先画出矩形
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
        //    public void drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint) {
        //    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry,
        //    public RectF(float left, float top, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight()), ridus, ridus, paint);

        //2.画滑动从左到右的有颜色的滑条
        paint.setColor(progressColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawRoundRect(new RectF(0, 0, (float) (progress / 100) * this.getMeasuredWidth(), this.getMeasuredHeight()), ridus, ridus, paint);

        //3.画出字体

        paint.setColor(progressTextColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setTextSize(progressTextSize);

        // drawText(@NonNull String text, float x, float y, @NonNull Paint paint) {
        //测量字体x坐标:变化的我们设置为在x轴上从左向右滑动的距离-子体本身的宽度
        float mx = (float) (progress / 100) * this.getMeasuredWidth() - paint.measureText((progress / 100) + "%")*2;
        //测量字体在y轴坐标  :对于y的计算:https://blog.csdn.net/u013015161/article/details/50493333保证在中间
        float my = this.getMeasuredHeight() / 2 - (paint.getFontMetrics().descent+paint.getFontMetrics().ascent) / 2;
        canvas.drawText((progress / 100)*100 + "%", mx, my, paint);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.ridus = this.getMeasuredHeight() / 5;
    }

    public void setProgress(float progress) {
        if (progress <= 0) {
            this.progress = 0;
        }

        if (progress <= 100) {
            this.progress = progress;
        }

        if (progress > 100) {
            this.progress = 100;
        }
        //子线程
        postInvalidate();//会从新绘制走onDray方法
    }
}

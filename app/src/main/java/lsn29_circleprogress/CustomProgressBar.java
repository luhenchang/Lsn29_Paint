package lsn29_circleprogress;

/**
 * 作者:王飞
 * 邮箱:1276998208@qq.com
 * create on 2018/5/22 16:31
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.ls.lsn29_paint.R;

public class CustomProgressBar extends View {
    private Paint paint;
    private int style=1;
    private boolean textShow;
    private float roundWidth;
    private float textSize;
    private int textColor;
    private int roundProgressColor;
    private int roundColor;
    private int max;
    private int progress;
    private static final int STROKE=0;
    private static final int FILL=1;

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        //拿到自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        max = typedArray.getInteger(R.styleable.CustomProgressBar_max, 100);
        roundColor = typedArray.getColor(R.styleable.CustomProgressBar_roundColor, Color.RED);
        roundProgressColor = typedArray.getColor(R.styleable.CustomProgressBar_roundProgressColor, Color.BLUE);
        textColor = typedArray.getColor(R.styleable.CustomProgressBar_textColor, Color.BLUE);
        textSize = typedArray.getDimension(R.styleable.CustomProgressBar_textSize, 15);
        roundWidth = typedArray.getDimension(R.styleable.CustomProgressBar_roundWidth, 10);
        textShow = typedArray.getBoolean(R.styleable.CustomProgressBar_textShow, true);
        style = typedArray.getInt(R.styleable.CustomProgressBar_style, 0);
        typedArray.recycle();
    }

    //开始画
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //默认的大圆环
        int center = getWidth() / 2;
        float radius = center-roundWidth / 2;//中心点坐标
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);//设置空心描边
        paint.setStrokeWidth(roundWidth);//圆环的宽度
        paint.setAntiAlias(true);


        canvas.drawCircle(center, center, radius, paint);

        //画进度百分比
        //paint.reset();
        paint.setColor(textColor);
        paint.setStrokeWidth(0);//圆环的宽度
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);

        float percent= (float) (progress/(float)max)*100;
        //y的公式:baselineY = top + (fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom;
        if(textShow&&percent!=0&&style==STROKE){
            canvas.drawText(percent+"%",(getWidth()-paint.measureText(percent+"%"))/2f,
                    getHeight()/2f-(paint.descent()+paint.ascent())/2f,paint);//x,y放到哪个地方呢？
        }

        //画圆弧
        //矩形区域，定义圆弧的形状大小
        RectF oval=new RectF(center-radius,center-radius,center+radius,center+radius);
        paint.setColor(roundProgressColor);
        paint.setStrokeWidth(roundWidth);
        switch (style){
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);

                canvas.drawArc(oval,0,360*progress/max,false,paint);

                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if(progress!=0){
                    canvas.drawArc(oval,0,360*progress/max,true,paint);

                }

                break;
        }

    }

    public synchronized int getMax() {
        return max;
    }

    public synchronized void setMax() {
        if (max < 0) {
            throw new IllegalArgumentException("max不能小于0");
        }
    }

    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress不能小于0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            //子线程
            postInvalidate();
        }
    }

    public boolean isTextShow() {
        return textShow;
    }

    public void setTextShow(boolean textShow) {
        this.textShow = textShow;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(int roundWidth) {
        this.roundWidth = roundWidth;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getRoundProgressColor() {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }
}

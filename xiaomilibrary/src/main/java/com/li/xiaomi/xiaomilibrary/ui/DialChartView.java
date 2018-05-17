package com.li.xiaomi.xiaomilibrary.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.li.xiaomi.xiaomilibrary.R;


/**
 * 类描述：一个指针转盘的view,在天然气里边用到了
 * 作  者：Admin or 李小米
 * 时  间：2017/10/30
 * 修改备注：
 */
public class DialChartView extends View {

    Context mContext;

    /**
     * 进度条所占用的角度
     */
    private static final int ARC_FULL_DEGREE = 180;
    /**
     * 弧线的宽度
     */
    private int STROKE_WIDTH = 14;
    /**
     * 组件的宽，高
     */
    private int width = 0, height = 0;
    /**
     * 进度条最大值和当前进度值
     */
    private float max = 25, progress = 0;

    /**
     * 圆弧的半径
     */
    private int circleRadius;
    /**
     * 圆弧圆心位置
     */
    private int centerX, centerY;

    /**
     * 绘制弧线的矩形区域
     */
    private RectF circleRectF;

    private String StrTv = "0";
    //中间字所在矩形区域
    private Rect textBounds = new Rect();

    Paint progressPaint;//画扇形的
    Paint textPaint;//写字的
    Paint textCenterPaint;//写字的
    Paint pointerPaint;//中间的指针

    public DialChartView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DialChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public DialChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        //圆弧
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        //文字
        textPaint = new Paint();
        textPaint.setColor(getContext().getResources().getColor(R.color.color_0b0b0b));
        textPaint.setAntiAlias(true);
        //指针
        pointerPaint = new Paint();
        pointerPaint.setStrokeWidth(5);
        pointerPaint.setColor(mContext.getResources().getColor(R.color.dialchart_pointer));
        pointerPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width == 0 || height == 0) {
            width = getMeasuredWidth();
            height = getMeasuredHeight();
            //计算圆弧(外圆)半径和圆心点,因为这里是半圆，所以是height,如果是整圆用height/2
            circleRadius = Math.min(width / 2, height);

            STROKE_WIDTH = circleRadius / 12;//弧线的宽度
            circleRadius = circleRadius - STROKE_WIDTH * 2;//圆的半径等于圆的初始半径-圆弧宽度*2

            centerX = width / 2;//圆心的位置
            centerY = height;//因为这里画的是半圆，所以Y轴的坐标==高度，如果是整个圆形，就是height/2

            //圆弧所在矩形区域（内圆）
            circleRectF = new RectF();
            circleRectF.left = centerX - circleRadius + STROKE_WIDTH / 2;//X轴的-半径+圆弧的宽度/2（左上角的点到X轴的距离）
            circleRectF.top = centerY - circleRadius + STROKE_WIDTH / 2;//Y轴左边-半径+圆弧的宽度/2（左上角的点到Y轴的距离）

            circleRectF.right = centerX + circleRadius - STROKE_WIDTH / 2;//X轴+半径-圆弧的宽度/2（右下角的点到X轴的距离）
            circleRectF.bottom = centerY + circleRadius - STROKE_WIDTH / 2;//Y轴+半径-圆弧的宽度/2（右下角的点到Y轴的距离）
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float start = 90 + ((360 - ARC_FULL_DEGREE) >> 1); //进度条起始点
        float sweep1 = ARC_FULL_DEGREE * (progress / max); //进度划过的角度

        //画圆心
        Paint circularPaint = new Paint();
        circularPaint.setColor(getResources().getColor(R.color.dialchart_pointer));
        circularPaint.setStrokeWidth(STROKE_WIDTH);
        circularPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(centerX, centerY, circularPaint);

        //绘制圆弧的背景
//        绿色（#5CD919）：0-3   蓝色（#2848FD）：3-5   黄色（#FEC70B）：5-10  橙色（#F85900）：10-20  红色（#C12729）：20-25
        float sweepProgress1 = ARC_FULL_DEGREE * (3f / 25f);
        float sweepProgress2 = ARC_FULL_DEGREE * (2f / 25f);
        float sweepProgress3 = ARC_FULL_DEGREE * (5f / 25f);
        float sweepProgress4 = ARC_FULL_DEGREE * (10f / 25f);
        float sweepProgress5 = ARC_FULL_DEGREE * (5f / 25f);

        //绘制进度绿色
        Paint progressPaint1 = new Paint();
        progressPaint1.setStrokeWidth(STROKE_WIDTH);//设置空心宽度
        progressPaint1.setStyle(Paint.Style.STROKE);//设置模式  实心
        progressPaint1.setColor(getContext().getResources().getColor(R.color.warning_color1));
        canvas.drawArc(circleRectF, start, sweepProgress1, false, progressPaint1);

        //绘制进度蓝色
        progressPaint1.setColor(getContext().getResources().getColor(R.color.warning_color2));
        canvas.drawArc(circleRectF, start + sweepProgress1, sweepProgress2, false, progressPaint1);
        //绘制进度黄色
        progressPaint1.setColor(getContext().getResources().getColor(R.color.warning_color3));
        canvas.drawArc(circleRectF, start + sweepProgress1 + sweepProgress2, sweepProgress3, false, progressPaint1);
        //绘制进度橙色
        progressPaint1.setColor(getContext().getResources().getColor(R.color.warning_color4));
        canvas.drawArc(circleRectF, start + sweepProgress1 + sweepProgress2 + sweepProgress3, sweepProgress4, false, progressPaint1);
        //绘制进度红色
        progressPaint1.setColor(getContext().getResources().getColor(R.color.warning_color5));
        canvas.drawArc(circleRectF, start + sweepProgress1 + sweepProgress2 + sweepProgress3 + sweepProgress4, sweepProgress5, false, progressPaint1);

        //画指针
        float progressRadians = (float) (((360.0f - ARC_FULL_DEGREE) / 2 + sweep1) / 180 * Math.PI);
        float thumbX = centerX - (float) (circleRadius - STROKE_WIDTH / 2) * (float) Math.sin(progressRadians);
        float thumbY = centerY + (float) (circleRadius - STROKE_WIDTH / 2) * (float) Math.cos(progressRadians);
        canvas.drawLine(centerX, centerY, thumbX, thumbY, pointerPaint);

        //画刻度、写度数
        Paint scalePaint = new Paint();
        scalePaint.setColor(Color.WHITE);
        scalePaint.setAntiAlias(true);
        scalePaint.setStyle(Paint.Style.STROKE);//设置模式  实心

        for (int i = 0; i < max + 1; i++) {
            float Degrees = (360.0f - ARC_FULL_DEGREE) / 2 + start - ARC_FULL_DEGREE / max * i;
            //把度数转化成弧度
            float progressRadians1 = (float) (Degrees / 180 * Math.PI);

            float thumbX1 = centerX - (float) (circleRadius - STROKE_WIDTH) * (float) Math.sin(progressRadians1);
            float thumbY1 = centerY + (float) (circleRadius - STROKE_WIDTH) * (float) Math.cos(progressRadians1);

            float thumbX2 = centerX - (float) (circleRadius) * (float) Math.sin(progressRadians1);
            float thumbY2 = centerY + (float) (circleRadius) * (float) Math.cos(progressRadians1);

            if (i % 5 == 0) {
                float thumbX3 = centerX - (float) (circleRadius - STROKE_WIDTH * 3) * (float) Math.sin(progressRadians1);
                float thumbY3 = centerY + (float) (circleRadius - STROKE_WIDTH * 3) * (float) Math.cos(progressRadians1);
                scalePaint.setStrokeWidth(5);
                textPaint.setTextSize((float) (circleRadius - STROKE_WIDTH) / 6);//根据半径来设置刻度字体的大小
                textPaint.setTextAlign(Paint.Align.CENTER);//这是文字居中对齐
                canvas.drawText((int) max - i + "  ", thumbX3, thumbY3, textPaint);
            } else {
                scalePaint.setStrokeWidth(2);
            }
            canvas.drawLine(thumbX1, thumbY1, thumbX2, thumbY2, scalePaint);
        }

        //画中间的文字
        //计算文字高度
        textCenterPaint = new Paint();
        textCenterPaint.setColor(getContext().getResources().getColor(R.color.color_0b0b0b));
        textCenterPaint.setAntiAlias(true);
        textCenterPaint.setTextSize((float) (circleRadius - STROKE_WIDTH) / 6);//根据半径来设置中间字体的大小
        textCenterPaint.getTextBounds(StrTv, 0, StrTv.length(), textBounds);
        textCenterPaint.setTextAlign(Paint.Align.CENTER);//这是文字居中对齐
        canvas.drawText(StrTv, centerX, centerY - circleRadius / 4, textCenterPaint);
    }

    public void setProgress(float mprogress, String mProgressTv) {
        progress = mprogress > max ? max : mprogress;
        StrTv = mProgressTv;
        invalidate();
    }
}



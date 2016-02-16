package qjlogo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuchen on 2016/1/12.圆不同心
 */
public class QJLogo extends View {

    /**帧计时节点*/
    public static final int TRACK1_END_TIME = 15;
    public static final int TRACK1_START_TIME = 1;
    public static final int TRACK3_START_TIME = 9;
    public static final int TRACK3_END_TIME = 18;
    public static final int TRACK2_START_TIME = 17;
    public static final int TRACK2_END_TIME = 28;
    public static final int CANVAS_START_TIME = 32;
    public static final int CANVAS_END_TIME = 42;
    public static final int ANIM_END_TIME = 60;

    public static final int TRACK1_DEGREE = -212;

    /**半圆块的颜色*/
    private int LEFT_BLUE = 0XFF003BF5;
    private int RIGHT_BLUE = 0XFF3267FF;
    private int RIGHT_RED = 0XFFFF3B3B;

    /**调整最小单位*/
    public static final float UNIT_DP = 10;

    //原始参数
//    public static final float TRACK2X_INNER_INIT = UNIT_DP*8.5f;
//    public static final float TRACK2Y_INNER_INIT = -UNIT_DP*8.25f;
//    public static final float TRACK2X_OUTER_INIT = UNIT_DP*11;
//    public static final float TRACK2Y_OUTER_INIT = -UNIT_DP*10;
//    public static final float TRACK3X_INNER_INIT = UNIT_DP*9;
//    public static final float TRACK3Y_INNER_INIT = UNIT_DP*9;
//    public static final float TRACK3X_OUTER_INIT = UNIT_DP*12;
//    public static final float TRACK3Y_OUTER_INIT = UNIT_DP*10.5f;
//
//
//    public static final float TRACK2X_INNER_TARGET = UNIT_DP*0;
//    public static final float TRACK2Y_INNER_TARGET = -UNIT_DP*0;
//    public static final float TRACK2X_OUTER_TARGET = UNIT_DP*2.5f;
//    public static final float TRACK2Y_OUTER_TARGET = -UNIT_DP*2.5f;
//    public static final float TRACK3X_INNER_TARGET = UNIT_DP*0;
//    public static final float TRACK3Y_INNER_TARGET = UNIT_DP*0;
//    public static final float TRACK3X_OUTER_TARGET = UNIT_DP*3;
//    public static final float TRACK3Y_OUTER_TARGET = UNIT_DP*1.6f;
//
//    public static final float TRACK1_INNER_CIRCLE = UNIT_DP*10;
//    public static final float TRACK1_OUTTER_CIRCLE = UNIT_DP*20;
//
//    public static final float TRACK2_INNER_CIRCLE = UNIT_DP*10;
//    public static final float TRACK2_OUTTER_CIRCLE = UNIT_DP*17;
//
//    public static final float TRACK3_INNER_CIRCLE = UNIT_DP*10;
//    public static final float TRACK3_OUTTER_CIRCLE = UNIT_DP*17f;


    public static final float TRACK2X_INNER_INIT = UNIT_DP*8.5f;
    public static final float TRACK2Y_INNER_INIT = -UNIT_DP*8.25f;
    public static final float TRACK2X_OUTER_INIT = UNIT_DP*11;
    public static final float TRACK2Y_OUTER_INIT = -UNIT_DP*10;
    public static final float TRACK3X_INNER_INIT = UNIT_DP*9;
    public static final float TRACK3Y_INNER_INIT = UNIT_DP*9;
    public static final float TRACK3X_OUTER_INIT = UNIT_DP*12;
    public static final float TRACK3Y_OUTER_INIT = UNIT_DP*10.5f;


    public static final float TRACK2X_INNER_TARGET = UNIT_DP*0;
    public static final float TRACK2Y_INNER_TARGET = -UNIT_DP*0;
    public static final float TRACK2X_OUTER_TARGET = UNIT_DP*0f;
    public static final float TRACK2Y_OUTER_TARGET = -UNIT_DP*0f;
    public static final float TRACK3X_INNER_TARGET = UNIT_DP*0;
    public static final float TRACK3Y_INNER_TARGET = UNIT_DP*0;
    public static final float TRACK3X_OUTER_TARGET = UNIT_DP*0f;
    public static final float TRACK3Y_OUTER_TARGET = UNIT_DP*0f;

    public static final float TRACK1_INNER_CIRCLE = UNIT_DP*10;
    public static final float TRACK1_OUTTER_CIRCLE = UNIT_DP*20;

    public static final float TRACK2_INNER_CIRCLE = UNIT_DP*10;
    public static final float TRACK2_OUTTER_CIRCLE = UNIT_DP*20;

    public static final float TRACK3_INNER_CIRCLE = UNIT_DP*10;
    public static final float TRACK3_OUTTER_CIRCLE = UNIT_DP*20f;


    /**过程控制变量*/
    private int mEndAngle = 0;          //
    private int mTrack2alpha = 0;
    private int mTrack3alpha = 0;
    private int mCanvasalpha = 255;
    public float mScaleRate = 1;

    private float mTrackInner2X = TRACK2X_INNER_INIT;
    private float mTrackInner2Y = TRACK2Y_INNER_INIT;
    private float mTrackInner3X = TRACK3X_INNER_INIT;
    private float mTrackInner3Y = TRACK3Y_INNER_INIT;
    private float mTrackOuter2X = TRACK2X_OUTER_INIT;
    private float mTrackOuter2Y = TRACK2Y_OUTER_INIT;
    private float mTrackOuter3X = TRACK3X_OUTER_INIT;
    private float mTrackOuter3Y = TRACK3Y_OUTER_INIT;

    /**画布缩放动画：缩放中*/
    public static final float SCALE_MIDDLE_RATE = 1.2f;
    public static final float SCALE_END_RATE = 0f;
    public static final float SCALE_DURING = 3;//帧

    /**定时器，用来严格控制时间*/
    Handler mHandler = new Handler();
    public boolean isTimerRunning = false;
    private int mFrameTime = 42;
    private int mFrameNumber = 0;

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mFrameNumber ++;
            invalidate();
        }
    };

    public QJLogo(Context context) {
        super(context);
        initView();
    }

    private void initView() {
    }

    public QJLogo(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QJLogo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(TRACK1_OUTTER_CIRCLE * 1.5f, TRACK1_OUTTER_CIRCLE * 1.5f);
        scaleAnim(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        track1(canvas, paint);
        track2(canvas, paint);
        track3(canvas, paint);

        if(!isTimerRunning){
            mHandler.post(mRunnable);
            isTimerRunning = true;
        }else{
            mHandler.postDelayed(mRunnable, mFrameTime);
        }
        if(mFrameNumber>ANIM_END_TIME){
            resetAnim();
        }
    }

    private void scaleAnim(Canvas canvas) {
        int unit = 255/(CANVAS_END_TIME - CANVAS_START_TIME);
        if(mFrameNumber>=CANVAS_START_TIME && mFrameNumber<CANVAS_START_TIME + SCALE_DURING){
            mCanvasalpha -= unit;
            mScaleRate += (SCALE_MIDDLE_RATE - 1.0f)/SCALE_DURING;
        }else if(mFrameNumber>=CANVAS_START_TIME + SCALE_DURING && mFrameNumber<CANVAS_END_TIME){
            mCanvasalpha -= unit;
            mScaleRate -= (SCALE_MIDDLE_RATE - SCALE_END_RATE)/(CANVAS_END_TIME-CANVAS_START_TIME-SCALE_DURING);
        }
        canvas.scale(mScaleRate, mScaleRate);
    }


    public void resetAnim(){

        mHandler.removeCallbacks(mRunnable);
        isTimerRunning = false;
        //初始化位置信息
        mTrackInner2X = TRACK2X_INNER_INIT;
        mTrackInner2Y = TRACK2Y_INNER_INIT;
        mTrackInner3X = TRACK3X_INNER_INIT;
        mTrackInner3Y = TRACK3Y_INNER_INIT;
        mTrackOuter2X = TRACK2X_OUTER_INIT;
        mTrackOuter2Y = TRACK2Y_OUTER_INIT;
        mTrackOuter3X = TRACK3X_OUTER_INIT;
        mTrackOuter3Y = TRACK3Y_OUTER_INIT;

        mEndAngle = 0;
        mTrack2alpha = 0;
        mTrack3alpha = 0;
        mFrameNumber = 0;
        mScaleRate = 1;
        mCanvasalpha = 255;
        mHandler.post(mRunnable);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void track1(Canvas canvas,Paint paint){
        //平移画布，便于坐标转换
        paint.setColor(LEFT_BLUE);
        paint.setAlpha(mCanvasalpha ==1?255: mCanvasalpha);
        if(mFrameNumber>=TRACK1_START_TIME && mFrameNumber<TRACK1_END_TIME){
            mEndAngle += TRACK1_DEGREE/(TRACK1_END_TIME-TRACK1_START_TIME);
        }
        drawRing(canvas, paint, 0, 0, 277, mEndAngle,TRACK1_INNER_CIRCLE,TRACK1_OUTTER_CIRCLE);
    }

    public void track2(Canvas canvas,Paint paint){

        paint.setColor(RIGHT_RED);
        if(mFrameNumber>=TRACK2_START_TIME && mFrameNumber<TRACK2_END_TIME){
            float unit = TRACK2_END_TIME - TRACK2_START_TIME;
            mTrackInner2X = changeNumber(mTrackInner2X,TRACK2X_INNER_INIT,TRACK2X_INNER_TARGET,unit);
            mTrackInner2Y = changeNumber(mTrackInner2Y,TRACK2Y_INNER_INIT,TRACK2Y_INNER_TARGET,unit);
            mTrackOuter2X = changeNumber(mTrackOuter2X,TRACK2X_OUTER_INIT,TRACK2X_OUTER_TARGET,unit);
            mTrackOuter2Y = changeNumber(mTrackOuter2Y,TRACK2Y_OUTER_INIT,TRACK2Y_OUTER_TARGET,unit);
            mTrack2alpha += 255/unit;
        }

        paint.setAlpha(mCanvasalpha ==255? mTrack2alpha : mCanvasalpha);
        drawRing(canvas, paint, mTrackInner2X, mTrackInner2Y, mTrackOuter2X, mTrackOuter2Y, 285, 85, TRACK2_INNER_CIRCLE, TRACK2_OUTTER_CIRCLE);
    }

    public void track3(Canvas canvas,Paint paint){
        paint.setColor(RIGHT_BLUE);
        if(mFrameNumber>=TRACK3_START_TIME && mFrameNumber<TRACK3_END_TIME){
            float unit = TRACK3_END_TIME - TRACK3_START_TIME;
            mTrackInner3X = changeNumber(mTrackInner3X,TRACK3X_INNER_INIT,TRACK3X_INNER_TARGET,unit);
            mTrackInner3Y = changeNumber(mTrackInner3Y,TRACK3Y_INNER_INIT,TRACK3Y_INNER_TARGET,unit);
            mTrackOuter3X = changeNumber(mTrackOuter3X,TRACK3X_OUTER_INIT,TRACK3X_OUTER_TARGET,unit);
            mTrackOuter3Y = changeNumber(mTrackOuter3Y,TRACK3Y_OUTER_INIT,TRACK3Y_OUTER_TARGET,unit);
            mTrack3alpha += 255/unit;
        }
//        Log.i("logo", "第几帧:" + mFrameNumber + ",透明度:" + mTrack3alpha);
        paint.setAlpha(mCanvasalpha ==255? mTrack3alpha : mCanvasalpha);
        drawRing(canvas, paint, mTrackInner3X, mTrackInner3Y, mTrackOuter3X, mTrackOuter3Y,16,42, TRACK3_INNER_CIRCLE, TRACK3_OUTTER_CIRCLE);
    }

    public float changeNumber(float runningNum,float source,float target,float unit){
         return (target-source)/unit + runningNum;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawRing(Canvas canvas, Paint paint,float x, float y,float startAngle,float sweepAngle,float inner_circle,float outter_circle) {
        drawRing(canvas, paint,x, y,x, y,startAngle,sweepAngle,inner_circle,outter_circle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawRing(Canvas canvas, Paint paint,float interX, float innerY,float outerX, float outerY,float startAngle,float sweepAngle,float inner_circle,float outter_circle) {

        Path outterpath = new Path();

        outterpath.moveTo(outerX, outerY);
        outterpath.lineTo(outerX, outerY - outter_circle);
        outterpath.arcTo(outerX - outter_circle, outerY - outter_circle, outerX + outter_circle, outerY + outter_circle, startAngle, sweepAngle, true);
        outterpath.lineTo(outerX, outerY);
        outterpath.close();

        Path innerpath = new Path();

        innerpath.moveTo(interX, innerY);
        innerpath.lineTo(interX, innerY - inner_circle);
        innerpath.arcTo(interX - inner_circle, innerY - inner_circle, interX + inner_circle, innerY + inner_circle, startAngle, -360, true);
        innerpath.lineTo(interX, innerY);
        innerpath.close();

        outterpath.op(innerpath,Path.Op.DIFFERENCE);
        canvas.drawPath(outterpath,paint);
    }

}
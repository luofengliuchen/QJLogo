package recyclerview.qjlogo;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuchen on 2016/1/12.
 */
public class QJLogo extends View {

    public static final int TRACK1_START_TIME = 0;
    public static final int TRACK1_END_TIME = 0;
    public static final int TRACK2_START_TIME = 0;
    public static final int TRACK2_END_TIME = 0;
    public static final int TRACK3_START_TIME = 0;
    public static final int TRACK3_END_TIME = 0;

    /***/
    public   int endAngle = 0;
    public int track2alpha = 0;
    public int track3alpha = 0;

    public int track2X = 20;
    public int track2Y = -20;
    public int track3X = 20;
    public int track3Y = 0;


    public boolean track2IsPlay = false;
    public boolean track3IsPlay = false;

    public static final int TRACK2X_INIT = 20;
    public static final int TRACK2Y_INIT = -20;
    public static final int TRACK3X_INIT = 20;
    public static final int TRACK3Y_INIT = 0;

    public static final int END_ANGLE_MAX = -240;

    public static final int INNER_CIRCLE  = 50;
    public static final int OUTTER_CIRCLE = 100;




    public QJLogo(Context context) {
        super(context);
        initView();
    }

    private void initView() {
//        setBackgroundColor(Color.BLACK);
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
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(OUTTER_CIRCLE*2, OUTTER_CIRCLE*2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        track1(canvas, paint);
        track2(canvas, paint);
        track3(canvas, paint);
        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void track1(Canvas canvas,Paint paint){
        //平移画布，便于坐标转换
        paint.setColor(Color.BLUE);
        paint.setAlpha(255);
        drawRing(canvas, paint, 0, 0, 270, endAngle);
        if(endAngle<END_ANGLE_MAX){
            endAngle = 0;
            track2IsPlay = false;
            track3IsPlay = false;
        }
        endAngle -= 3 ;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void drawRing(Canvas canvas, Paint paint,float x, float y,float startAngle,float sweepAngle) {
        Path outterpath = new Path();

        outterpath.moveTo(x, y);
        outterpath.lineTo(x, y-OUTTER_CIRCLE);
        outterpath.arcTo(x-OUTTER_CIRCLE, y-OUTTER_CIRCLE, x+OUTTER_CIRCLE, y+OUTTER_CIRCLE, startAngle, sweepAngle, true);
        outterpath.lineTo(x, y);
        outterpath.close();

        Path innerpath = new Path();

        innerpath.moveTo(x, y);
        innerpath.lineTo(x, y-INNER_CIRCLE);
        innerpath.arcTo(x-INNER_CIRCLE, y-INNER_CIRCLE, x+INNER_CIRCLE, y+INNER_CIRCLE, startAngle, sweepAngle, true);
        innerpath.lineTo(x, y);
        innerpath.close();

        outterpath.setFillType(Path.FillType.EVEN_ODD);
        outterpath.addPath(innerpath);
        canvas.drawPath(outterpath,paint);
    }

    public void track2(Canvas canvas,Paint paint){
        paint.setColor(Color.RED);
        paint.setAlpha(track2alpha);
        if(!track2IsPlay){
            if(endAngle<-100){
                track2alpha += 12;
                track2X -= 1;
                track2Y += 1;
                if(track2X<=0){
                    track2alpha = 255;
                    track2X = TRACK2X_INIT;
                    track2Y = TRACK2Y_INIT;
                    track2IsPlay = true;
                }
            }else{
                track2alpha = 0;
                track2X = TRACK2X_INIT;
                track2Y = TRACK2Y_INIT;
            }
            if(!track2IsPlay){
                drawRing(canvas, paint,track2X,track2Y, 270, 70);
            }

        }else{
            drawRing(canvas, paint,0,0, 270, 70);
        }


    }

    public void track3(Canvas canvas,Paint paint){
        paint.setColor(Color.YELLOW);
        paint.setAlpha(track3alpha);

        if(!track3IsPlay){
            if(endAngle<-50){
                track3alpha += 12;
                track3X -= 1;
                if(track3X<=0){
                    track3alpha = 255;
                    track3X = TRACK3X_INIT;
                    track3IsPlay = true;
                }
            }else{
                track3alpha = 0;
                track3X = TRACK3X_INIT;
                track3Y = TRACK3Y_INIT;
            }
            if(!track3IsPlay){
                drawRing(canvas, paint,track3X,track3Y,340,50);
            }

        }else{
            drawRing(canvas, paint,0,0,340,50);
        }
    }
}

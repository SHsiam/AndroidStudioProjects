package com.example.ca2__animation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;


public class DrawClass extends View {
    Path p;
    Paint paint;


    public DrawClass(Context context) {
        super(context);
        paint=new Paint();
        p=new Path();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);
        canvas.drawPath(p,paint);
    }
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                p.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                int x=(int)event.getX();
                int y=(int)event.getY();
                if((x>=10 & x<=110)& (y>=10 & y<=120) ){
                    p.reset();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                p.lineTo(event.getX(),event.getY());
                break;
        }
        invalidate();
        return true;
    }
}

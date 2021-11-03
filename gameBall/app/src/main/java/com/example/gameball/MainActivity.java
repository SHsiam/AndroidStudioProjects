package com.example.gameball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
SensorManager sm;
Bitmap b;
DrawBall db;
int x,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        db=new DrawBall(this);
      setContentView(db);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
    x=(int)Math.pow(event.values[1],3);
    y=(int)Math.pow(event.values[1],3);
}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),sm.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ORIENTATION),sm.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    class DrawBall extends View{

        public DrawBall(Context context) {
            super(context);
            Bitmap ball= BitmapFactory.decodeResource(getResources(),R.drawable.soccer);
            final int dWidth=160;
            final int dHeight=160;
            b=Bitmap.createScaledBitmap(ball,dWidth,dHeight,true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap(b,x,y,null);
            invalidate();
        }
    }
}
package com.example.megneticfiledsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity  extends AppCompatActivity implements SensorEventListener {
    TextView tv1;
    ImageView iv1;
    SensorManager sm;
    Sensor mg,ac;
    float[] acl=new float[3];
    float[] mgl=new float[3];
    boolean acb=false;
    boolean mgb=false;
    float[] rotationmatrix=new float[3];
    float[] orien=new float[3];
    float currentDegree=0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1=findViewById(R.id.iv1);
        tv1=findViewById(R.id.tv1);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        ac=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mg=sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,ac,SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,mg,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this,ac);
        sm.unregisterListener(this,mg);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor==ac)
        {
            System.arraycopy(event.values,0,acl,0,event.values.length);
            acb=true;
        }
        else if(event.sensor==mg)
        {
            System.arraycopy(event.values,0,mgl,0,event.values.length);
            mgb=true;
        }
        if(acb && mgb)
        {
            SensorManager.getRotationMatrix(rotationmatrix,null,acl,mgl);
            SensorManager.getOrientation(rotationmatrix,orien);
            float radian=orien[0];
            float degree=(float)(Math.toDegrees(radian));
            RotateAnimation ra=new RotateAnimation(currentDegree,degree, Animation.RELATIVE_TO_SELF,
                    0.5f,Animation.RELATIVE_TO_SELF,0.05f);
            ra.setDuration(260);
            ra.setFillAfter(true);
            tv1.setText("Heading: "+degree+"degree");
            iv1.startAnimation(ra);
            currentDegree=degree;
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
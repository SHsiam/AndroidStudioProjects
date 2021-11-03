package com.example.sensor_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
SensorManager sm;
Sensor s;
SensorEventListener sl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sm=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sl=new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] rotaionMertix=new float[16];
                SensorManager.getRotationMatrixFromVector(rotaionMertix,event.values);
                float[] remapRotationMertix=new float[16];
                SensorManager.remapCoordinateSystem(rotaionMertix,SensorManager.AXIS_X,SensorManager.AXIS_Z,remapRotationMertix);
                        float[]oriantion=new float[3];
                SensorManager.getOrientation(remapRotationMertix,oriantion);
                for(int i=0;i<3;i++){
                    oriantion[i]=(float)(Math.toDegrees(oriantion[i]));

                }if(oriantion[2]>45){
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                }
               else if(oriantion[2]<-45){
                    getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
                }
                else if(Math.abs(oriantion[2])<10){
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sm.registerListener(sl,s,SensorManager.SENSOR_DELAY_NORMAL);
    }
}